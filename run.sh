#!/bin/bash

# Compile the calculator
echo "Compiling Calculator.java..."
javac src/calculator/Calculator.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Starting calculator..."
    java -cp src calculator.Calculator
else
    echo "Compilation failed!"
    exit 1
fi
