@ECHO OFF
SETLOCAL

SET BASE_DIR=%~dp0
SET WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper
SET PROPS_FILE=%WRAPPER_DIR%\maven-wrapper.properties
SET WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar

IF NOT EXIST "%PROPS_FILE%" (
  ECHO Missing %PROPS_FILE%
  EXIT /B 1
)

FOR /F "tokens=1,* delims==" %%A IN (%PROPS_FILE%) DO (
  IF "%%A"=="wrapperUrl" SET WRAPPER_URL=%%B
)

IF "%WRAPPER_URL%"=="" SET WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven wrapper jar...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol=[Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%','%WRAPPER_JAR%')"
  IF ERRORLEVEL 1 EXIT /B 1
)

java -Dmaven.multiModuleProjectDirectory="%BASE_DIR%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
EXIT /B %ERRORLEVEL%
