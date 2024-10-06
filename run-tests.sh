#!/bin/bash

for file in /app/tests/step*/*.json; do
    echo "Running test for $file"
    java -cp /app SimpleJsonParser "$file"
done
