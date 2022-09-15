::Run this file if you would like not to record video in Grid execution
for /f "tokens=5" %%a in ('netstat -aon ^| find ":4444" ^| find "LISTENING"') do taskkill /f /pid %%a
pushd %~dp0%..\..\..\Resources\lib
set SELSERV="selenium-server-standalone-*.jar"
for /f %%i in (%SELSERV%) do set SELSERV=%%~fi
java -jar "%SELSERV%" -role hub -hubConfig "..\..\ConfigFiles\HubConfig.json"
::echo %~dp0%..lib
pause
