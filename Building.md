## Building JBadboy ##

This page describes the essentials of how to get the source code and build it.

### Getting Source Code ###

The source code should be obtained by using Subversion to check out the source code from the Google Code web site.

### Dependencies ###

To build the source code of JBadboy, you need some dependencies installed on your computer:

  * Sun JDK 1.5 or later
  * [Groovy 1.6 Beta 2](http://groovy.codehaus.org/) or higher
  * [Gant 1.4](http://gant.codehaus.org/)
  * [Mozswing](http://sourceforge.net/projects/mozswing) distribution - unzip and place in the source directory as 'mozswing'


### Building ###

Once you have the dependencies in place, building JBadboy is straight forward.  Just change directory to the root of the source tree and type:

```
gant dist
```

This will compile and package all JBadboy files and place the results into the "dist" directory.  After successfully doing that, you can launch JBadboy using the script provided in the 'bin' directory:

```
bin\jbadboy some_script.bx
```

(Adjust file paths to match your platform).

You can produce a Windows platform specific distribution by using the 'win' target:
```
gant win
```

You can create a packaged zip file of the distribution you have created by using the 'install' target:

```
gant install
```

Note that this will use the settings cached from the previous invocation that created the distribution.