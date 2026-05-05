@echo off
REM ─────────────────────────────────────────────────────────────────────────
REM Snow Problem  –  Parts 1 & 2  –  Build Script  (Windows)
REM Requires: JDK 11+  (https://adoptium.net/)
REM ─────────────────────────────────────────────────────────────────────────

echo.
echo ^❄  Snow Problem - Build Script
echo --------------------------------

REM 1. Compile sources
echo ^-^> Compiling sources...
if not exist build mkdir build
javac -d build src\snowproblem\*.java
if errorlevel 1 ( echo Compilation failed! & pause & exit /b 1 )
echo    OK Sources compiled

REM 2. Compile tests
echo ^-^> Compiling tests...
javac -d build -cp build test\snowproblem\GameBoardTest.java
if errorlevel 1 ( echo Test compilation failed! & pause & exit /b 1 )
echo    OK Tests compiled

REM 3. Copy resources
echo ^-^> Copying resources...
if not exist build\snowproblem\resources mkdir build\snowproblem\resources
xcopy /q /y src\snowproblem\resources\*.png build\snowproblem\resources\
echo    OK Resources copied

REM 4. Run unit tests
echo ^-^> Running unit tests...
java -cp build snowproblem.GameBoardTest
echo.

REM 5. Package JAR
echo ^-^> Packaging JAR...
echo Main-Class: snowproblem.MainWindow > manifest.txt
cd build && jar cfm ..\SnowProblem_Part1_2.jar ..\manifest.txt . && cd ..
del manifest.txt
echo    OK SnowProblem_Part1_2.jar created

echo.
echo Build complete! Launching GUI...
echo.
java -jar SnowProblem_Part1_2.jar
