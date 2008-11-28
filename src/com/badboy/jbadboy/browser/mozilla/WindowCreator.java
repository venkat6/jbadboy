/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id$
 *
 * This file is part of JBadboy.
 * 
 * JBadboy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JBadboy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBadboy.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.badboy.jbadboy.browser.mozilla;

import static org.mozilla.browser.MozillaAutomation.findWindow;
import static org.mozilla.browser.MozillaExecutor.isMozillaThread;
import static org.mozilla.browser.XPCOMUtils.create;
import static org.mozilla.browser.XPCOMUtils.qi;
import static org.mozilla.interfaces.nsIWebBrowserChrome.CHROME_ALL;
import static org.mozilla.interfaces.nsIWebBrowserChrome.CHROME_DEFAULT;
import static org.mozilla.xpcom.IXPCOMError.NS_ERROR_FAILURE;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.mozilla.browser.IMozillaWindow;
import org.mozilla.browser.IMozillaWindowFactory;
import org.mozilla.browser.MozillaWindow;
import org.mozilla.browser.impl.ChromeAdapter;
import org.mozilla.browser.impl.DOMAdapter;
import org.mozilla.browser.impl.MozillaContainer;
import org.mozilla.interfaces.nsIBaseWindow;
import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsIDOMWindow2;
import org.mozilla.interfaces.nsIDocShellTreeItem;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowser;
import org.mozilla.interfaces.nsIWebBrowserChrome;
import org.mozilla.interfaces.nsIWebProgressListener;
import org.mozilla.interfaces.nsIWindowCreator;
import org.mozilla.xpcom.Mozilla;
import org.mozilla.xpcom.XPCOMException;

/**
 * @see org.mozilla.browser.impl.WindowCreator
 */
public class WindowCreator implements nsIWindowCreator {
    
    private static IMozillaWindowFactory winFactory = new DefaultWindowFactory();
    
    static class DefaultWindowFactory implements IMozillaWindowFactory {
        public IMozillaWindow create(boolean attach) {
            return new MozillaWindow(attach, null, null);
        }
    }
    
    /**
     * Sets factory for creating new windows.
     *
     * @param f factory for creating new windows
     */
    public static void setWindowFactory(IMozillaWindowFactory f) {
        WindowCreator.winFactory = f;
    }
    
    /**
     * Returns factory for creating new windows.
     *
     * @return factory for creating new windows
     */
    public static IMozillaWindowFactory getWindowFactory() {
        return WindowCreator.winFactory;
    }
    
    public WindowCreator() {
    }
    
    public nsISupports queryInterface(String aIID) {
        return Mozilla.queryInterface(this, aIID);
    }
    
    /**
     * When mozilla does a callback to createChromeWindow()
     * we need to create a swing window. But doing this on
     * Swing thread using invokeAndWait sometimes ends
     * with deadlock in AWT.
     * Therefore we keep a list of precreated windows
     * in case we will need them.
     */
    private List<IMozillaWindow> precreatedWins = new LinkedList<IMozillaWindow>();
    
    public void ensurePrecreatedWindows() {
        ensurePrecreatedWindows(3);
    }
    public void ensurePrecreatedWindows(int winNum) {
        assert !isMozillaThread(); //has to be called from swing
        
        while (precreatedWins.size()<winNum) {
            
            if (winFactory==null) return;
            IMozillaWindow w = winFactory.create(false);
            if (!(w instanceof Component)) return;
            
            // w is instance of something we can work with
            precreatedWins.add(w);
            Component c = (Component)w;
            c.addNotify();
        }
    }
    
    // nsIWindowCreator methods
    
    /**
     * Callback when mozilla wants to open a new window
     */
    public nsIWebBrowserChrome createChromeWindow(nsIWebBrowserChrome parent, final long chromeFlags) {
        assert !precreatedWins.isEmpty();
        
        IMozillaWindow newWin = precreatedWins.remove(0);
        SwingUtilities.invokeLater(new Runnable() { public void run() {
            ensurePrecreatedWindows();
        }});
        
        if (newWin==null)
            throw new XPCOMException(NS_ERROR_FAILURE);
        
        MozillaContainer newWinContainer = newWin.getMozillaContainer();
        if (newWinContainer==null)
            throw new XPCOMException(NS_ERROR_FAILURE);
        
        IMozillaWindow parentWin = null;
        if (parent!=null) {
            // Find the EmbedPrivate object for this web browser chrome object.
            parentWin = findWindow(parent);
        }
        newWin.setParentWindow(parentWin);
        
        ChromeAdapter newChromeAdapter = attachBrowser(newWin, chromeFlags, parentWin);
        
        // check to make sure that we made a new window
        if (newChromeAdapter==null)
            throw new XPCOMException(NS_ERROR_FAILURE);
        
        return newChromeAdapter;
    }
    
    public static interface ChromeAdaptorFactory {
        ChromeAdapter create(IMozillaWindow win, nsIWebBrowser webBrowser, long chromeFlags);
    }
    
    public static ChromeAdapter attachBrowser(IMozillaWindow win, long chromeFlags, IMozillaWindow parentWin) {
        return attachBrowser(win,chromeFlags, parentWin, new ChromeAdaptorFactory() {
            public ChromeAdapter create(IMozillaWindow win, nsIWebBrowser webBrowser, long chromeFlags) {
              return new ChromeAdapter(win, webBrowser, chromeFlags);
            }
        });
	}
    
    /**
     * Creates a mozilla browser and adds it into a Swing window.
     * The Swing window must be already created and realized!!
     * (i.e. after addNotify())
     */
    public static ChromeAdapter attachBrowser(IMozillaWindow win, long chromeFlags, IMozillaWindow parentWin, ChromeAdaptorFactory adaptorFactory)
    {
        assert isMozillaThread();
        
        MozillaContainer con = win.getMozillaContainer();
        if (!con.isDisplayable()) {
            //the swing widget must be already realized
            return null;
        }
        
        if (chromeFlags==CHROME_DEFAULT) chromeFlags = CHROME_ALL;
        
        //create new webbrowser component
        nsIWebBrowser webBrowser = create("@mozilla.org/embedding/browser/nsWebBrowser;1", nsIWebBrowser.class); //$NON-NLS-1$
        
        //create and set our embedding implementation
        // ChromeAdapter chromeAdapter = new ChromeAdapter(win, webBrowser, chromeFlags);
        ChromeAdapter chromeAdapter = adaptorFactory.create(win,webBrowser, chromeFlags);
        webBrowser.setContainerWindow(chromeAdapter);
        
        //set flag whether displaying html content or chrome content
        nsIDocShellTreeItem item = qi(webBrowser, nsIDocShellTreeItem.class);
        item.setItemType(chromeAdapter.isChromeWindow() ?
                nsIDocShellTreeItem.typeChromeWrapper :
                    nsIDocShellTreeItem.typeContentWrapper);
        
        //get dimension of the mozilla area. Usually, the widget does not
        //have the correct bounds at this stage yet, so use defaults
        Rectangle dim = con.getBounds();
        nsIBaseWindow baseWindow = qi(webBrowser, nsIBaseWindow.class);
        //create the native mozilla area
        baseWindow.initWindow(con.getMozillaCanvas().createHandle(dim), 0, 0, 0, dim.width, dim.height);
        baseWindow.create();
        
        // bind the progress listener to the browser object
        webBrowser.addWebBrowserListener(chromeAdapter.getProgressAdapter(), nsIWebProgressListener.NS_IWEBPROGRESSLISTENER_IID);
        
        // set ourselves as the parent uri content listener
        webBrowser.setParentURIContentListener(chromeAdapter.getContentAdapter());
        
        // bind the dom listener
        nsIDOMWindow2 domWin = qi(webBrowser.getContentDOMWindow(), nsIDOMWindow2.class);
        nsIDOMEventTarget et = domWin.getWindowRoot();
        for (String ev : DOMAdapter.hookedEvents) {
            et.addEventListener(ev, chromeAdapter.getDOMAdapter(), false);
        }
        
        //nsIWebBrowserFocus webBrowserFocus = (nsIWebBrowserFocus) webBrowser.queryInterface(nsIWebBrowserFocus.NS_IWEBBROWSERFOCUS_IID);
        //webBrowserFocus.activate();
        
        ChromeAdapter parentChromeAdapter = null;
        if (parentWin!=null) {
            parentChromeAdapter = parentWin.getChromeAdapter();
        }
        
        win.onAttachBrowser(chromeAdapter, parentChromeAdapter);
        
        return chromeAdapter;
    }
    
    /**
     * Remove mozilla browser from Swing window, and then
     * destroys the browser. The Swing window will continue
     * to exist.
     */
    public static void detachBrowser(IMozillaWindow win)
    {
        assert isMozillaThread();
        
        MozillaContainer con = win.getMozillaContainer();
        ChromeAdapter chromeAdapter = win.getChromeAdapter();
        if (chromeAdapter==null) {
            //already destroyed
            return;
        }
        win.onDetachBrowser();
        
        nsIWebBrowser webBrowser = chromeAdapter.getWebBrowser();
        
        nsIDOMWindow2 domWin = qi(webBrowser.getContentDOMWindow(), nsIDOMWindow2.class);
        nsIDOMEventTarget et = domWin.getWindowRoot();
        for (String ev : DOMAdapter.hookedEvents) {
            et.removeEventListener(ev, chromeAdapter.getDOMAdapter(), false);
        }
        
        webBrowser.setParentURIContentListener(null);
        webBrowser.removeWebBrowserListener(chromeAdapter.getProgressAdapter(), nsIWebProgressListener.NS_IWEBPROGRESSLISTENER_IID);
        
        nsIBaseWindow baseWindow = qi(webBrowser, nsIBaseWindow.class);
        baseWindow.destroy();
        con.getMozillaCanvas().destroyHandle();
        
        webBrowser.setContainerWindow(null);
    }
    
}
