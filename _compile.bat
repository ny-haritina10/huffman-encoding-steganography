@echo off
REM Create temp directory if it doesn't exist
if not exist temp mkdir temp

REM Copy all Java files directly to temp folder (flattening structure)
for /r src %%f in (*.java) do copy "%%f" temp\

REM Check if any Java files exist in temp before compiling
if not exist temp\*.java (
    echo No Java files found in temp folder.
    exit /b 1
)

REM Compile all Java files in the temp folder
javac -d bin -cp "lib/*" temp\*.java

REM Check if compilation was successful
if errorlevel 1 (
    echo Compilation failed.
    rmdir /s /q temp
    exit /b 1
)

REM Cleanup temp folder
rmdir /s /q temp

REM Create JAR file
if not exist bin (
    echo No compiled classes found in bin folder.
    exit /b 1
)

REM Define the JAR file name and manifest file
set JAR_FILE=tp-final-codage.jar
set MANIFEST_FILE=manifest.txt

REM Create a manifest file with Main-Class attribute 
echo Main-Class: mg.itu.Main > %MANIFEST_FILE%

REM Package the compiled classes into a JAR file
jar cfm %JAR_FILE% %MANIFEST_FILE% -C bin .

REM Check if JAR creation was successful
if errorlevel 1 (
    echo Failed to create JAR file.
    del %MANIFEST_FILE%
    exit /b 1
)

REM Cleanup manifest file
del %MANIFEST_FILE%

echo JAR file created successfully: %JAR_FILE%
echo Compilation and packaging finished.