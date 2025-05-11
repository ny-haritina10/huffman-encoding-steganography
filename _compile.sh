#!/bin/bash
# create temp directory if it doesn't exist
mkdir -p temp

# copy all Java files directly to temp folder (flattening structure)
find src -type f -name "*.java" -exec cp {} temp/ \;

# check if any Java files exist in temp before compiling
if [ ! -f temp/*.java ]; then
    echo "No Java files found in temp folder."
    exit 1
fi

# compile all Java files in the temp folder
javac -d bin -cp "lib/*" temp/*.java

# check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    rm -rf temp
    exit 1
fi

# cleanup temp folder
rm -rf temp

# create JAR file
if [ ! -d bin ]; then
    echo "No compiled classes found in bin folder."
    exit 1
fi

# define the JAR file name and manifest file
JAR_FILE=tp-final-codage.jar
MANIFEST_FILE=manifest.txt

# create a manifest file with Main-Class attribute
echo "Main-Class: mg.itu.Main" > $MANIFEST_FILE

# package the compiled classes into a JAR file
jar cfm $JAR_FILE $MANIFEST_FILE -C bin .

# check if JAR creation was successful
if [ $? -ne 0 ]; then
    echo "Failed to create JAR file."
    rm $MANIFEST_FILE
    exit 1
fi

# cleanup manifest file
rm $MANIFEST_FILE

echo "JAR file created successfully: $JAR_FILE"
echo "Compilation and packaging finished."