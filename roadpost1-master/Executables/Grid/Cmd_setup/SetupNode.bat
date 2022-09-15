:::Run this file if you would like not to record video in Grid execution
pushd %~dp0%..\..\..\Resources\lib
set SELSERV="selenium-server-standalone-*.jar"
for /f %%i in (%SELSERV%) do set SELSERV=%%~fi
start java -Dwebdriver.chrome.driver="../../Resources/Drivers/chromedriver.exe" -Dwebdriver.gecko.driver="../../Resources/Drivers/geckodriver.exe" -jar "%SELSERV%" -role node -nodeConfig "..\..\ConfigFiles\NodeConfig.json"