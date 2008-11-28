;--------------- CONFIGURATION ----------------
;
; This is a small NSIS wrapper script that does a few things
; for windows users that are hard to do with IzPack.
;
; It also enables shipping a '.exe' file which helps Vista
; automatically ensure the user is presented with UAC prompts.
;
; see http://www.geocities.com/java_intro/?200825
;

!define APPNAME "JBadboy Script Player"
!define JARFILE "install.jar"

;Uncomment the next line to specify an icon for the EXE.
;Icon "test.ico"

;Uncomment the next line to specify a splash screen bitmap.
;!define SPLASH_IMAGE "splash.bmp"

;---------------------------------

Name "Jelude"
Caption "${APPNAME}"
OutFile "setup.exe"

SilentInstall silent
XPStyle on

!addplugindir .

Var BadboyInstallLocation

Section ""
  System::Call "kernel32::CreateMutexA(i 0, i 0, t 'jelude') i .r1 ?e"
  Pop $R0 
  StrCmp $R0 0 +2
  Quit

  ; Check if we have DLLs or not
  IfFileExists $SYSDIR\msvcr71.dll 0 NeedBadboy
  IfFileExists $SYSDIR\msvcp71.dll FoundBadboy NeedBadboy

  NeedBadboy:
  ; Check if Badboy installed - we require it for DLLs 
  ReadRegStr $BadboyInstallLocation HKLM "SOFTWARE\Badboy" ""

  IfErrors 0 FoundBadboy
  MessageBox MB_ICONEXCLAMATION|MB_OK \
             'Could not find an installation of Badboy on your computer. \
             $\n$\nJBadboy needs MSVCR71.DLL to run which can be obtained \
             $\nfrom many different sources, one of which is Badboy itself. \
             $\nYou can continue to install, however if you experience problems \
             $\nrunning JBadboy you should locate this library and copy it to your \
             $\nWindows System folder.'

  FoundBadboy:
  
  ClearErrors
  ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
  ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R0" "JavaHome"
  IfErrors 0 FoundVM

  ClearErrors
  ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Development Kit" "CurrentVersion"
  ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Development Kit\$R0" "JavaHome"
  IfErrors 0 FoundVM

  ClearErrors
  ReadEnvStr $R0 "JAVA_HOME"
  IfErrors NotFound 0

  FoundVM:
  StrCpy $R0 "$R0\bin\javaw.exe"
  IfFileExists $R0 0 NotFound

  StrCpy $R1 ""
  Call GetParameters
  Pop $R1

  SetOverwrite ifdiff
  SetOutPath $TEMP
  File "${JARFILE}"
  StrCpy $R0 '$R0 -jar "${JARFILE}" $R1'

  ExecWait "$R0"
  
  CopyFiles $BadboyInstallLocation\msvc*.dll $SYSDIR

  Delete "${JARFILE}"

  !ifdef SPLASH_IMAGE
    SetOutPath $TEMP
    File /oname=spltmp.bmp "${SPLASH_IMAGE}"
    Splash::show 2000 "$TEMP\spltmp"
    Delete "$TEMP\spltmp.bmp"
  !endif 

  Sleep 5000
  Quit

  NotFound:
  Sleep 800
  MessageBox MB_ICONEXCLAMATION|MB_YESNO \
                    'Could not find a Java Runtime Environment installed on your computer. \
                     $\nWithout it you cannot run "${APPNAME}". \
                     $\n$\nWould you like to visit the Java website to download it?' \
                    IDNO +2
  ExecShell open "http://java.sun.com/getjava"
  Quit
SectionEnd

Function GetParameters
  Push $R0
  Push $R1
  Push $R2
  StrCpy $R0 $CMDLINE 1
  StrCpy $R1 '"'
  StrCpy $R2 1
  StrCmp $R0 '"' loop
  StrCpy $R1 ' '
  loop:
    StrCpy $R0 $CMDLINE 1 $R2
    StrCmp $R0 $R1 loop2
    StrCmp $R0 "" loop2
    IntOp $R2 $R2 + 1
    Goto loop
  loop2:
    IntOp $R2 $R2 + 1
    StrCpy $R0 $CMDLINE 1 $R2
    StrCmp $R0 " " loop2
  StrCpy $R0 $CMDLINE "" $R2
  Pop $R2
  Pop $R1
  Exch $R0
FunctionEnd
