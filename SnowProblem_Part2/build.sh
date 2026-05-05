#!/bin/bash
# ─────────────────────────────────────────────────────────────
# Snow Problem  –  Parts 1 & 2  –  Build Script  (Linux / Mac)
# Requires: JDK 11+   (https://adoptium.net/)
# ─────────────────────────────────────────────────────────────

set -e

echo ""
echo "❄  Snow Problem – Build Script"
echo "──────────────────────────────"

# 1. Compile sources
echo "→ Compiling sources..."
mkdir -p build
javac -d build src/snowproblem/*.java
echo "   ✓ Sources compiled"

# 2. Compile tests (needs sources on classpath)
echo "→ Compiling tests..."
javac -d build -cp build test/snowproblem/GameBoardTest.java
echo "   ✓ Tests compiled"

# 3. Copy image resources into build output
echo "→ Copying resources..."
mkdir -p build/snowproblem/resources
cp src/snowproblem/resources/*.png build/snowproblem/resources/
echo "   ✓ Resources copied"

# 4. Run unit tests
echo "→ Running unit tests..."
java -cp build snowproblem.GameBoardTest
echo ""

# 5. Package runnable JAR
echo "→ Packaging JAR..."
echo "Main-Class: snowproblem.MainWindow" > manifest.txt
cd build && jar cfm ../SnowProblem_Part1_2.jar ../manifest.txt . && cd ..
rm manifest.txt
echo "   ✓ SnowProblem_Part1_2.jar created"

echo ""
echo "✓ Build complete!"
echo ""
echo "  Run the GUI:   java -jar SnowProblem_Part1_2.jar"
echo "  Run tests:     java -cp build snowproblem.GameBoardTest"
echo ""
