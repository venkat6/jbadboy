/**
 * Copyright 2008, Simon Sadedin, Badboy Software
 * and authors of MozSwing class, {@link MozillaWindow}
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

import static com.badboy.jbadboy.util.Str.blank;
import static org.mozilla.browser.MozillaExecutor.*;
import static org.mozilla.browser.XPCOMUtils.qi;
import static org.mozilla.interfaces.nsIWebBrowserChrome.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.browser.*;
import org.mozilla.browser.impl.ChromeAdapter;
import org.mozilla.browser.impl.MozillaContainer;
import org.mozilla.browser.impl.components.JChromeButton;
import org.mozilla.dom.NodeFactory;
import org.mozilla.interfaces.*;
import org.mozilla.xpcom.Mozilla;
import org.mozilla.xpcom.XPCOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.*;
import org.w3c.dom.traversal.NodeFilter;

import com.badboy.jbadboy.IBadboy;
import com.badboy.jbadboy.browser.*;

/**
 * Browser window adapted to implement the Badboy {@link Browser}
 * interface.
 * <p>
 * This code was copied wholesale from the MozSwing class by the 
 * same name.  It would be much better to inherit from that class,
 * however unfortunately it's use of static methods in various places
 * makes achieving the desired reuse somewhat difficult.
 * 
 * @author ssadedin@badboy.com.au
 */
public class MozillaBrowser
    extends JFrame //Dialog
    implements IMozillaWindow, Browser
{
    private static final long serialVersionUID = 1L;

    static Log log = LogFactory.getLog(MozillaBrowser.class);

    protected final VisibilityMode toolbarVisMode;
    protected final VisibilityMode statusbarVisMode;

    protected JToolBar toolbar;
    protected JButton backButton, forwardButton, reloadButton, stopButton, goButton;
    protected JTextField urlBar;
    protected MozillaContainer mozContainer;
    protected JTextField statusField;

    protected IMozillaWindow parentWin;
    
    protected IBadboy badboy;

    private ChromeAdapter chromeAdapter;

    private boolean attachNewBrowserOnCreation;

    private String pendingUriToLoad, pendingContentToLoad, pendingContentUriToLoad;

    private Dimension initialPreferredlSize;

    private LoadStateSignal loadStateSignal = new LoadStateSignal();

    private static int DEFAULT_WIDTH = 300;
    private static int DEFAULT_HEIGHT = 300;

    /**
     * Creates a new MozillaWindow with default
     * toolbar and statusbar visibility mode.
     */
    public MozillaBrowser(IBadboy badboy)
    {
        this(badboy, true, null, null);
    }

    /**
     * Creates a new MozillaWindow with the given
     * toolbar and statusbar visibility mode.
     *
     * @param toolbarVisMode toolbar visibility mode
     * @param statusbarVisMode status visibility mode
     */
    public MozillaBrowser(IBadboy badboy, VisibilityMode toolbarVisMode,
                         VisibilityMode statusbarVisMode)
    {
        this(badboy, true, toolbarVisMode, statusbarVisMode);
    }

    /**
     * Internal constructor.
     *
     * @param attachNewBrowserOnCreation true if a new mozilla browser
     *   should be immediately attached. Used with false when pre-creating
     *   popups in {@link WindowCreator}
     * @param toolbarVisMode toolbar visibility mode
     * @param statusbarVisMode status visibility mode
     */
    public MozillaBrowser(IBadboy badboy, boolean attachNewBrowserOnCreation,
                         VisibilityMode toolbarVisMode,
                         VisibilityMode statusbarVisMode)
    {
        this.badboy = badboy;
        this.attachNewBrowserOnCreation = attachNewBrowserOnCreation;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        MozillaInitialization.initialize();

        this.toolbarVisMode = toolbarVisMode!=null ? toolbarVisMode : VisibilityMode.DEFAULT;
        this.statusbarVisMode = statusbarVisMode!=null ? statusbarVisMode : VisibilityMode.DEFAULT;

        this.initialPreferredlSize = new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        createChrome();
        
        this.create();
    }
    
    void onContentReady() {
        log.info("Page complete, adding DOM Listener");
        addDOMListener();
        this.loadStateSignal.notifyStateChange(State.COMPLETE);
    }

    /**
     * Internal method.
     * Related to pre-creating of swing windows for {@link WindowCreator}.
     */
    public void attachNewBrowser() {
        assert isMozillaThread();
        WindowCreator.attachBrowser(MozillaBrowser.this, CHROME_DEFAULT, null,  new WindowCreator.ChromeAdaptorFactory() {
                    public ChromeAdapter create(IMozillaWindow win, nsIWebBrowser webBrowser, long chromeFlags) {
                        return new ChromeAdapter(win,webBrowser, chromeFlags) {
                            @Override
                            public void contentFinishedLoading() {
                                onContentReady();
                            }
                        };
                    }
        });
    }

    public void onAttachBrowser(final ChromeAdapter chromeAdapter, final ChromeAdapter parentChromeAdapter) {
        assert isMozillaThread();
        assert chromeAdapter!=null;
        assert this.chromeAdapter==null;

        this.chromeAdapter = chromeAdapter;
        //notify swing canvas component, that the native area is ready
        mozContainer.onAttachBrowser(chromeAdapter);

        swingAsyncExec(new Runnable() { public void run() {
            final boolean showToolbar;
            {
                VisibilityMode vm = toolbarVisMode;
                if (vm==VisibilityMode.DEFAULT && parentChromeAdapter!=null)
                    vm = parentChromeAdapter.getWindow().getToolbarVisibilityMode();
                switch (vm) {
                    case FORCED_VISIBLE:
                        showToolbar = true;
                        break;
                    case FORCED_HIDDEN:
                        showToolbar = false;
                        break;
                    case DEFAULT:
                    default:
                        showToolbar = (chromeAdapter.getChromeFlags() & (CHROME_TOOLBAR|CHROME_LOCATIONBAR))!=0;
                }
            }
            toolbar.setVisible(showToolbar);

            final boolean showStatusbar;
            {
                VisibilityMode vm = statusbarVisMode;
                if (vm==VisibilityMode.DEFAULT && parentChromeAdapter!=null)
                    vm = parentChromeAdapter.getWindow().getStatusbarVisibilityMode();
                switch (vm) {
                case FORCED_VISIBLE:
                    showStatusbar = true;
                    break;
                case FORCED_HIDDEN:
                    showStatusbar = false;
                    break;
                case DEFAULT:
                default:
                    showStatusbar =
                        (chromeAdapter.getChromeFlags() & CHROME_STATUSBAR)!=0 &&
                        (chromeAdapter.getChromeFlags() & nsIWebBrowserChrome.CHROME_OPENAS_DIALOG)==0;
                }
            }
            statusField.setVisible(showStatusbar);

            adjustLocation(chromeAdapter.getChromeFlags());
            if (parentWin!=null) {
                setSize(parentWin.getSize());
            } else {
                setPreferredSize(initialPreferredlSize);
            }
            if (pendingUriToLoad!=null)
                load(pendingUriToLoad);
            else if (pendingContentToLoad!=null)
                loadHTML(pendingContentToLoad, pendingContentUriToLoad);
            pendingUriToLoad = pendingContentToLoad = pendingContentUriToLoad = null;
        }});
        
    }

    public void onDetachBrowser() {
        assert isMozillaThread();
        this.chromeAdapter = null;
        mozContainer.onDetachBrowser();
    }

    protected void createChrome()
    {

//        if ((chromeMask & CHROME_MENUBAR)!=0) {
//            createMenu();
//        }

        createToolbar();
        toolbar.setVisible(false);
        createMozillaPanel();
        createStatusbar();
        statusField.setVisible(false);
    }

    protected void createToolbar()
    {
        toolbar = new JToolBar();
        toolbar.setFloatable(false); //bug #18229
        add(toolbar, BorderLayout.NORTH);
        backButton = new JChromeButton("back", mt.t("MozillaWindow.Tooltip_Back")); //$NON-NLS-1$ //$NON-NLS-2$
        backButton.setEnabled(false);
        backButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            goBack();
        }});
        toolbar.add(backButton);
        forwardButton = new JChromeButton("forward", mt.t("MozillaWindow.Tooltip_Forward")); //$NON-NLS-1$ //$NON-NLS-2$
        forwardButton.setEnabled(false);
        forwardButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            goForward();
        }});
        toolbar.add(forwardButton);
        reloadButton = new JChromeButton("reload", mt.t("MozillaWindow.Tooltip_Reload")); //$NON-NLS-1$ //$NON-NLS-2$
        reloadButton.setEnabled(false);
        reloadButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            reload();
        }});
        toolbar.add(reloadButton);
        stopButton = new JChromeButton("stop", mt.t("MozillaWindow.Tooltip_Stop")); //$NON-NLS-1$ //$NON-NLS-2$
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            stop();
        }});
        toolbar.add(stopButton);

        //enclose the textfield into a panel,
        //to keep the default height
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        urlBar = new JTextField();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.weightx = 1.0; c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(urlBar, c);
        urlBar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            String uri = urlBar.getText();
            load(uri);
        }});
        toolbar.add(p);

        goButton = new JChromeButton("go", mt.t("MozillaWindow.Tooltip_Go")); //$NON-NLS-1$ //$NON-NLS-2$
        goButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            String uri = urlBar.getText();
            load(uri);
        }});
        toolbar.add(goButton);
    }

    protected void createMozillaPanel()
    {
        //enclose the awt canvas component with mozilla
        //(MozCanvas) into a jpanel (MozContainer).
        //otherwise relative positioning in a layout of the
        //parent component and focus traversal do not work
        //properly
        mozContainer = new MozillaContainer();
        add(mozContainer, BorderLayout.CENTER);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (MozillaInitialization.isInitialized()) {
            MozillaInitialization.getWinCreator().ensurePrecreatedWindows();
            if (attachNewBrowserOnCreation) {
                Runnable r = new Runnable() { public void run() {
                    attachNewBrowser();
                }};
                mozAsyncExec(r);
            }
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
    }

    protected void createStatusbar()
    {
        statusField = new JTextField(""); //$NON-NLS-1$
        statusField.setEditable(false);
        statusField.setFocusable(false);
        add(statusField, BorderLayout.SOUTH);
    }

    protected void adjustLocation(long chromeMask){

        if (parentWin==null || !parentWin.isShowing()) return;

        Point pos = parentWin.getLocationOnScreen();
        if ((chromeMask & (nsIWebBrowserChrome.CHROME_CENTER_SCREEN))!=0) {
            Point p=new Point((int)parentWin.getSize().getWidth()/2, (int)parentWin.getSize().getHeight()/2);
            p.translate((int)-getSize().getWidth()/2, (int)-getSize().getHeight()/2);
            log.trace(String.format("adjustLocation: %d, %d", p.x, p.y)); //$NON-NLS-1$
            pos.translate(p.x, p.y);
            if (pos.x<0) pos.x=0;
            if (pos.y<0) pos.y=0;
        } else {
            //shift right,down by the height of title bar
            Insets insets = getInsets();
            pos.translate(insets.top, insets.top);
        }
        setLocation(pos);
    }

    public void goBack() {
        mozAsyncExec(new Runnable() { public void run() {
            nsIWebNavigation nav = qi(chromeAdapter.getWebBrowser(), nsIWebNavigation.class);
            nav.goBack();
        }});
    }

    public void goForward() {
        if (chromeAdapter==null) return;
        mozAsyncExec(new Runnable() { public void run() {
            nsIWebNavigation nav = qi(chromeAdapter.getWebBrowser(), nsIWebNavigation.class);
            nav.goForward();
        }});
    }

    public void reload() {
        if (chromeAdapter==null) return;
        mozAsyncExec(new Runnable() { public void run() {
            nsIWebNavigation nav = qi(chromeAdapter.getWebBrowser(), nsIWebNavigation.class);
            nav.reload(nsIWebNavigation.LOAD_FLAGS_NONE);
        }});
    }

    public void stop() {
        if (chromeAdapter==null) return;
        mozAsyncExec(new Runnable() { public void run() {
            nsIWebNavigation nav = qi(chromeAdapter.getWebBrowser(), nsIWebNavigation.class);
            nav.stop(nsIWebNavigation.STOP_ALL);
        }});
    }

    public void load(final String uri) {
    	load(qi(chromeAdapter.getWebBrowser(), nsIWebNavigation.class), uri,null);
    }
    
    public void load(final nsIWebNavigation nav, final String uri, final byte[] postData) {
        if (uri==null || uri.length()==0) return;
        if (chromeAdapter==null) {
            pendingUriToLoad = uri;
            return;
        }
        
        
        mozAsyncExec(new Runnable() { public void run() {
        	// Get the component manager
        	nsIComponentManager cm = Mozilla.getInstance().getComponentManager();
        	nsIMIMEInputStream postDataStream = null;
        	if(postData != null) {
        		nsIStringInputStream stringStream = 
        			(nsIStringInputStream) cm.createInstanceByContractID("@mozilla.org/io/string-input-stream;1" , (nsISupports) null, nsIStringInputStream.NS_ISTRINGINPUTSTREAM_IID); 
        		stringStream.setData(new String (postData), postData.length);
        		
        		postDataStream = 
        			(nsIMIMEInputStream) cm.createInstanceByContractID("@mozilla.org/network/mime-input-stream;1" , 
        					(nsISupports) null, nsIMIMEInputStream.NS_IMIMEINPUTSTREAM_IID); 
        		
        		postDataStream.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
        		postDataStream.setAddContentLength(true);
        		postDataStream.setData(stringStream);
        	}
        
            long startMs = System.currentTimeMillis();
            nav.loadURI(uri, nsIWebNavigation.LOAD_FLAGS_NONE, null, postDataStream, null);
            System.out.println("URL " + uri + " loaded in " + (System.currentTimeMillis() - startMs) + " ms");
        }});
    }

    public void loadHTML(final String content) {
        loadHTML(null, content);
    }

    public void loadHTML(final String content, final String asUrl) {
        if (content==null) return;
        if (chromeAdapter==null) {
            pendingContentToLoad = content;
            pendingContentUriToLoad = asUrl;
            return;
        }

        mozAsyncExec(new Runnable() { public void run() {
            MozillaAutomation.triggerLoadHTML(MozillaBrowser.this, content, asUrl);
        }});
    }

    public String getUrl() {
        if (chromeAdapter==null) {
            return null;
        }
        return
            mozSyncExecQuiet(new Callable<String>() { public String call() {
                return MozillaAutomation.getCurrentURI(MozillaBrowser.this);
            }});
    }

    public Object jsexec(String script) {
        if (chromeAdapter==null) return null;
        return MozillaAutomation.executeJavascript(this, script);
    }

    public MozillaContainer getMozillaContainer() {
        return mozContainer;
    }

    public ChromeAdapter getChromeAdapter() {
        return chromeAdapter;
    }

    public void setParentWindow(IMozillaWindow parentWin) {
        this.parentWin = parentWin;
    }
    public IMozillaWindow getParentWindow() {
        return parentWin;
    }

    public void onSetStatus(String text) {
        statusField.setText(text);
    }
    public void onEnableBackButton(boolean enabled) {
        backButton.setEnabled(enabled);
    }
    public void onEnableForwardButton(boolean enabled) {
        forwardButton.setEnabled(enabled);
    }
    public void onEnableReloadButton(boolean enabled) {
        reloadButton.setEnabled(true);
    }
    public void onEnableStopButton(boolean enabled) {
        stopButton.setEnabled(enabled);
    }
    public void onSetUrlbarText(String url) {
        urlBar.setText(url);
    }
    public void onSetSize(int w, int h) {
        setSize(w, h);
    }
    public void onSetTitle(String title) {
        setTitle(title);
    }
    public void onSetVisible(boolean visibility) {
        setVisible(visibility);
    }
    public void onLoadingStarted() {
        this.loadStateSignal.notifyStateChange(State.LOADING);
    }
    public void onLoadingEnded() {
    }
    public void onCloseWindow() {
        onSetVisible(false);
        dispose();
    }
    public void onDispatchEvent(AWTEvent e) {
        super.processEvent(e);
    }

    public Document getDocument() {
        if (chromeAdapter==null)
            return null;
        else {
            return
                mozSyncExecQuiet(new Callable<Document>() { public Document call() {
                    nsIDOMDocument nsdoc = chromeAdapter.getWebBrowser().getContentDOMWindow().getDocument();
                    return (Document) NodeFactory.getNodeInstance(nsdoc);
                }});
        }
    }

    /**
     * Sets the initial preferred size of the Mozilla panel
     * used when the browser is attached
     *
     * @param preferredSize intial preferred size
     */
    public void setInitialPreferredlSize(Dimension preferredSize){
        this.initialPreferredlSize = preferredSize;
    }

    public VisibilityMode getToolbarVisibilityMode() { return toolbarVisMode; }
    public VisibilityMode getStatusbarVisibilityMode() { return statusbarVisMode; }
    public JToolBar getToolbar() { return toolbar; }
    public JTextField getStatusbar() { return statusField; }
    
    
    private void create() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 200, 800, 600);
        MozillaAutomation.blockingLoad(this, "about:blank");
        this.setVisible(true);
    }

    @Override
    public void navigate(String url, String target, String postData) {
        
        // If target not blank
        // 1. locate child frame
        // 2. get nsIDOMWindow
        // 3. get hold of nsIWindowWatcher (some global thing?)
        // 4. call nsIWindowWatcher to get nsIWebBrowserChrome
        // 5. get nsIWebBrowser property of nsIWebBrowserChrome
        // 6. get nsiWebNavigation from nsIWebBrowser
        // 7. call loadURI with post data
        // 8. Phew!!!
        
        if(!blank(target)) {
            nsIDOMWindow childFrame = this.findFrame(target);
            nsIInterfaceRequestor req = qi(childFrame, nsIInterfaceRequestor.class);
            nsIWebNavigation nav = (nsIWebNavigation) req.getInterface(nsIWebNavigation.NS_IWEBNAVIGATION_IID);  
            try { this.load(nav, url, postData != null ? postData.getBytes("UTF-8") : null); } catch (UnsupportedEncodingException e) { }
        }
        else
	        MozillaAutomation.blockingLoad(this, url, postData);
    }

    private void addDOMListener() {
        nsIWebBrowser brow = chromeAdapter.getWebBrowser();
        nsIDOMDocument doc = brow.getContentDOMWindow().getDocument();
        nsIDOMEventTarget docEvents = qi(doc, nsIDOMEventTarget.class);
        
        docEvents.addEventListener("DOMAttrModified",
                new nsIDOMEventListener() {
                   public void handleEvent(nsIDOMEvent evt) {
                      nsIDOMHTMLElement e = qi(evt.getTarget(), nsIDOMHTMLElement.class);
                      if("_badboy_data".equals(e.getId())) {
                          
                          try {
                            String value = e.getAttribute("value");
                              if(value != null) {
                                  value.replaceAll("::", "");
                                  String [] parts = value.split(":");
                                  if(parts.length == 1) {
                                      Method m = badboy.getClass().getMethod(parts[0]);
                                      m.invoke(badboy);
                                  }
                                  else
                                  if(parts.length == 2) {
                                      Method m = badboy.getClass().getMethod(parts[0], String.class);
                                      m.invoke(badboy, parts[1]);
                                  }
                                  else
                                  if(parts.length == 3) {
                                      Method m = badboy.getClass().getMethod(parts[0], String.class, String.class);
                                      m.invoke(badboy, parts[1], parts[2]);
                                  }
                                      
                                  System.err.println("event " + evt.getType() + " with value " + e.getAttribute("value"));
                              }
                        } catch (Exception ex) {
                            System.err.println("Failed to introspect message from javascript:" + ex.getMessage());
                        }
                      }
                   }
                   public nsISupports queryInterface(String uuid) {
                      return Mozilla.queryInterface(this, uuid);
                   }
                }, false);
    }

    @Override
    public HTMLElement getBodyElement() {
        nsIWebBrowser brow = chromeAdapter.getWebBrowser();
        nsIDOMDocument doc = brow.getContentDOMWindow().getDocument();
        nsIDOMNode n = doc.getElementsByTagName("body").item(0);        
        NodeFactory.setConvertNodeNamesToLowerCase(false);
        return this.cast().htmlElement(n);
    }

    @Override
    public void click(HTMLElement element) {
        
        final nsIDOMElement e = ((HTMLElementWrapper)element).getWrapped();
        mozSyncExecQuiet(new Callable<Boolean>() { public Boolean call() {
            return MozillaAutomation.click(e);
        }});
    }
    
    public String getElementText(HTMLElement e) {
        
        HTMLElementWrapper el = (HTMLElementWrapper) e;
        nsIDOMNode n = el.getWrapped();
 
        // Figured out several different ways to approach this,
        // in the end have settled for xpath approach because that is
        // what is used by the MozillaAutomation class, some day should
        // do a performance comparison here
        
        /*
        nsIDOMNSHTMLElement h = qi(e, nsIDOMNSHTMLElement.class);
        String result = null;
        if(h != null) {
            result = h.getInnerHTML();
        }*/
        
        /*
        nsIDOMNodeList children = e.getChildNodes();
        StringBuilder text = new StringBuilder();
        for(long i =0; i<children.getLength(); ++i) {
            nsIDOMNode n = children.item(i);
            nsIDOMCharacterData cdata = qi(n, nsIDOMCharacterData.class);
            if(cdata != null) {
               text.append(cdata.getData()); 
            }
        }
        */
        
        nsIDOMDocument doc = n.getOwnerDocument();
        nsIDOMXPathEvaluator eval = qi(doc, nsIDOMXPathEvaluator.class);
        nsIDOMXPathResult ret = qi(eval.evaluate("text()", n, null, nsIDOMXPathResult.STRING_TYPE, null), nsIDOMXPathResult.class);
        return ret.getStringValue();
    }
    
    public String getInnerHTML(HTMLElement e) {
        HTMLElementWrapper el = (HTMLElementWrapper) e;
        nsIDOMNode n = el.getWrapped();
        nsIDOMNSHTMLElement h = qi(n, nsIDOMNSHTMLElement.class);
        String result = null;
        if(h != null) {
            return h.getInnerHTML();
        }
        else
            return "";
    }
    
    public nsIDOMWindow findFrame(String target) {
        nsIWebBrowser brow = chromeAdapter.getWebBrowser();
        nsIDOMDocument doc = brow.getContentDOMWindow().getDocument();
        nsIDOMNodeList iframes = doc.getElementsByTagName("iframe");
        for(int i = 0; i<iframes.getLength(); ++i) {
            nsIDOMHTMLIFrameElement iframe = qi(iframes.item(i), nsIDOMHTMLIFrameElement.class);
            if(target.equals(iframe.getName())) {
                nsIDOMNSHTMLFrameElement frame = qi(iframes.item(i), nsIDOMNSHTMLFrameElement.class);
                nsIDOMWindow w = frame.getContentWindow();
                return w;
            }
        }
        return null;
    }

    @Override
    public List<HTMLElement> filterElements(HTMLElement root, DOMElementFilter f) {
        final DOMElementFilter filter = f;
        
        nsIDOMElement nsIRoot = ((HTMLElementWrapper)root).getWrapped();
        
        nsIWebBrowser brow = chromeAdapter.getWebBrowser();
        nsIDOMDocument doc = brow.getContentDOMWindow().getDocument();
        
        // If the element is an iframe, iterate into the iframe's document instead of
        // just iterating the iframe's children 
        if("iframe".compareToIgnoreCase(nsIRoot.getTagName()) == 0) {
            nsIDOMHTMLIFrameElement iframe = qi(nsIRoot, nsIDOMHTMLIFrameElement.class);
            doc = iframe.getContentDocument();
            nsIRoot = doc.getDocumentElement();
        }
        
        nsIDOMDocumentTraversal t = qi(doc, nsIDOMDocumentTraversal.class);
        final QueryInterface q = cast();
        nsIDOMTreeWalker w = t.createTreeWalker(nsIRoot, NodeFilter.SHOW_ELEMENT, new nsIDOMNodeFilter() {
            @Override
            public short acceptNode(nsIDOMNode n) {
                nsIDOMHTMLElement e = qi(n,nsIDOMHTMLElement.class);
                
                if("iframe".compareToIgnoreCase(e.getTagName()) == 0) {
                    log.debug("found iframe tag " + e.getTagName() + " name = " + e.getAttribute("name"));
                    return NodeFilter.FILTER_ACCEPT;
                }
                
                return filter.accept(q.htmlElement(e)) ? NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_SKIP;
            }
            @Override
            public nsISupports queryInterface(String arg0) {
                return null;
            }
        }, true);        
        
        List<HTMLElement> results = new ArrayList<HTMLElement>();
        try {
            for(nsIDOMNode n = w.nextNode(); n != null; n = w.nextNode()) {
                nsIDOMHTMLElement e = qi(n,nsIDOMHTMLElement.class);
                HTMLElement htmlElement = cast().htmlElement(e);
                
                // If the element is an iframe, recurse into the iframe's  own DOM                
                if("iframe".compareToIgnoreCase(e.getTagName())==0) {
                    results.addAll(filterElements(htmlElement, filter));
                }
                else {
                    results.add(htmlElement);
                }
            }
        }
        catch(XPCOMException ex) {
            System.out.println("WARN: tree walk terminated with exception: " + ex.getMessage());
        }
        return results;
    }

    @Override
    public QueryInterface cast() {
        
        return new QueryInterface() {
            @Override
            public HTMLFormElement formElement(Object obj) {
                nsIDOMHTMLFormElement e = XPCOMUtils.qi(getInterface(obj), nsIDOMHTMLFormElement.class);
                return e == null ? null : new HTMLFormElementWrapper(e);
            }
            @Override
            public HTMLElement htmlElement(Object obj) {
                nsIDOMHTMLElement e = XPCOMUtils.qi(getInterface(obj), nsIDOMHTMLElement.class);
                return e == null ? null: new HTMLElementWrapper(e);
            }
            @Override
            public HTMLInputElement inputElement(Object obj) {
                nsIDOMHTMLInputElement e = XPCOMUtils.qi(getInterface(obj), nsIDOMHTMLInputElement.class);
                return e == null ? null: new HTMLInputElementWrapper(e);
            }
            @Override
            public NodeList nodeList(Object obj) {
                nsIDOMNodeList e = XPCOMUtils.qi(getInterface(obj), nsIDOMNodeList.class);
                return e == null ? null: new NodeListWrapper(e);
            }
            @Override
            public HTMLSelectElement selectElement(Object obj) {
                nsIDOMHTMLSelectElement e = XPCOMUtils.qi(getInterface(obj), nsIDOMHTMLSelectElement.class);
                return e == null ? null: new HTMLSelectElementWrapper(e);
            }
            @Override
            public HTMLOptionElement optionElement(Object obj) {
                nsIDOMHTMLOptionElement e = XPCOMUtils.qi(getInterface(obj), nsIDOMHTMLOptionElement.class);
                return e == null ? null: new HTMLOptionElementWrapper(e); 
            }
            @Override
            public HTMLTextAreaElement textAreaElement(Object obj) {
                nsIDOMHTMLTextAreaElement e = XPCOMUtils.qi(getInterface(obj), nsIDOMHTMLTextAreaElement.class);
                return e == null ? null: new HTMLTextAreaElementWrapper(e); 
            }
            protected nsISupports getInterface(Object obj) {
                if(obj instanceof nsISupports)
                    return (nsISupports) obj;
                else
                if(obj instanceof HTMLElementWrapper) {
                    return ((HTMLElementWrapper)obj).getWrapped();
                }
                else
                if(obj instanceof Node) {
                    return NodeFactory.getnsIDOMNode((Node)obj);
                }
                else
                    throw new IllegalArgumentException("Unknown node type " + obj);
            }
        };
    }

    @Override
    public void executeScript(String script) {
        String bb =             
        "if(!document.getElementById('_badboy_data')) { var n = document.createElement('input');"+ 
        "n.id = '_badboy_data';" +
        "n.style.display = 'none';"+
        "document.body.appendChild(n);}" +            
        "window._badboy_send = function(command, arg1, arg2, arg3) { "+
        "  document.getElementById('_badboy_data').setAttribute('value',[command,arg1,arg2,arg3].join(':'));"+
        "};"+
        "window.badboy = {"+  
        "  info: function(a) {_badboy_send('info',a);}"+ 
        "};";        
        
        executeRaw(bb);        
        executeRaw(script);
    }
    
    /**
     * The default executeJavascript in MozillaAutomation has some quirks 
     * so we implement it ourselves.
     * 
     * @param script
     */
    public void executeRaw(final String script) {
        mozSyncExec(new Runnable() { public void run() {
            ChromeAdapter chromeAdapter = getChromeAdapter();
            if (chromeAdapter==null) return;
            nsIWebNavigation nav = qi(chromeAdapter.getWebBrowser(), nsIWebNavigation.class);
            try {
                String encoded = URLEncoder.encode(script,"UTF-8");
                nav.loadURI("javascript:"+script+";void(0);", nsIWebNavigation.LOAD_FLAGS_NONE, null, null, null); //$NON-NLS-1$
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }});
    }

    @Override
    public LoadStateSignal getLoadStateSignal() {
        return loadStateSignal;
    }
}