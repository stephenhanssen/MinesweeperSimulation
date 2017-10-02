# MinesweeperSimulation
Coding exercise.  Simulate a script driving a space minesweeper and determine a score.

This code was developed and build on a Windows 10 machine.  I used Intellij Community Edition 2016.3.5 as my IDE.  To begin with I let Intellij build and run my code but I've included directions to build on Windows without intellij.  My tests were written with JUnit4 but I they may have broken when I adjusted things to push to the repo.

# Build
These instructions assume that either javac and jar are in your path or you fully specify them for each invokation
1. Create src\com\sah\builds
2. cd src\com\sah
3. javac -d build \*.java
4. cd build
5. jar cvfe Simulation.jar com.sah.Main *

# Running the Simulator
java -jar Simulation.jar pathToFieldFile pathToScriptFile

