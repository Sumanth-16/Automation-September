@echo off
::Run this file if you would like to record video in Grid execution
::Source file for SetupHub.jar is placed in com.jarconversions ->LaunchHub.java.
::To make any changes,open source code and make necessary changes and convert to runnable jar file and place it in lib folder
for /f "tokens=5" %%a in ('netstat -aon ^| find ":4444" ^| find "LISTENING"') do taskkill /f /pid %%a
pushd %~dp0%..\..\..\Resources\lib
call java -jar SetupHub.jar
