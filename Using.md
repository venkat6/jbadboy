# Installing and Running JBadboy #

This page is a short tutorial on how to download, install and start using JBadboy.

## Installing ##
JBadboy should be installed using the downloadable installer.  The JBadboy installer uses Java so you need to have Java 1.5 or higher installed before you try to run it.

Please note that on Windows you should install [Badboy](http://www.badboy.com.au/?p=download) first before installing JBadboy.  This allows JBadboy to copy some files and configuration settings from Badboy.  Once installation is completed there are no dependencies and you may uninstall Badboy.

In most cases you should be able to simply double click on the installer to run it.  If not, you can open a command line console and run it using
```
java -jar <installer file>
```
**Note**:  Windows users are advised to use the .exe installer available for their platform because it will be recognized by Vista as requiring Administrator permissions.

After installing you may wish to place the installation directory in your PATH environment variable, if you want to run JBadboy from the command line.

## Running ##
JBadboy can only load Badboy scripts that are saved in the XML ".bx" format.  So first make sure you have saved your script with this extension.

You can then run the script in either of two ways:

  1. On Windows, you can right click on a Badboy script and choose "Play with JBadboy"
  1. You can open a command prompt and type:
```
  jbadboy <script file>
```

This will launch and run the script and save an output HTML report in the local directory as <script file>.html.

When running from the command line you can also pass the name of the file to write the output report to.  For example:

```
jbadboy test.bx results.html
```

Please note that JBadboy only currently supports a limited subset of the features available in Badboy, and it is possible that even those that are supported may not behave identically with how they do in Badboy.  See the [Limitations](Limitations.md) page for information on compatibility.

If you want to build JBadboy from source code, see the [Building](Building.md) page.