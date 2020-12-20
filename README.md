# Project-Haptic
UON Summer Research Project

# Purpose of the Application

The application will takes an image in JPG, PNG and BMP and will perform the following functions:
 1. Invert the image
 2. Enlarge the pixel, but still maintaining the resolution, based on an input provided
    For example: an image of a small line will be changed to a bigger line while the image resolution stays the same.
 3. Enlarge the resolution of the image based on an input provided

# Input

The application will read the input from a textfile named "config.txt" where the input consists of
 1. The name of the image in JPG, PNG and BMP
 2. An integer that controls the level of friction for the touch. Integer(1 - 5)
 3. An integer that tells how large the pixel should be. Integer (1 - 5)
 
 # Compile and Run the Application

Once the textfile is filled with relevent information, the application can be executed from the command prompt/terminal screen that takes in an argument which is the text file name.

The following command will allow you to run and compile the application

```
javac MyCanvas.java
java MyCanvas config.txt
```

