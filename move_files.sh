#!/bin/bash

# Move all specified files and folders into the employee-department-jpa directory

# Define source and destination directories
SOURCE_DIR="./"
DEST_DIR="./employee-department-jpa/"

# List of items to move
items=(".gitignore" ".idea" "src")

# Loop through each item and move it
for item in "${items[@]}"; do
    if [ -e "$SOURCE_DIR$item" ]; then
        mv "$SOURCE_DIR$item" "$DEST_DIR"
        echo "Moved $item to employee-department-jpa"
    else
        echo "$item does not exist in the source directory."
    fi
done

# Script completion message
echo "All specified items have been moved."