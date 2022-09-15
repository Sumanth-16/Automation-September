SET SIKULIX_HOME=%~dp0%Resources\SikuliX64
SET ProgFiles86Root=%ProgramFiles(x86)%
IF NOT "%ProgFiles86Root%"=="" GOTO amd64
::SET SIKULIX_HOME=%~dp0%Resources\SikuliX86
:amd64
:: Set Variables 
set MAVEN_HOME=%~dp0%Resources\Maven
set PATH=%PATH%;%MAVEN_HOME%\bin;%~dp0%Resources\allure-cli\bin
set JDK="%ProgramFiles%\Java\jdk*"
for /d %%i in (%JDK%) do set JAVA_HOME=%%i
:: Display Variables and Launch Maven
set JAVA_HOME
echo %MAVEN_HOME%
echo %PATH%
pushd %~dp0%
mvn clean install