# Project-Haptic
UON Summer Research Project - Facilitating learning for visually-impaired students using a haptic screen

# Background

The haptic device currently takes in an image that produces a static feel or friction. The problem with the device is that the friction is produced in an "inverted way".

For instance, imagine an image of a black filled coloured circle with a white background. When the device takes in the image, the friction will be produced on the white background while the black filled circle has a smooth feel where there is no friction at all. Intuitively, the white background should be smooth while the dark black circle should be producing the highest friction. We also wanted the friction level to vary as the colour changes too.

The purpose of the application is to produce an inverted image and integrate with the haptic device, so that it can produce an intuitive static projection from the image.

# Functionality of the Application

The application will takes an image in JPG, PNG and BMP and will perform the following functions:
 1. Invert the image
 2. Change the level of contrast of the image
 3. Enlarge the pixel, but still maintaining the resolution, based on an input provided
    For example: an image of a small line will be changed to a bigger line while the image resolution stays the same.
 4. Enlarge the resolution of the image based on an input provided
 5. Enlarge the pixel of lines/graphs in the image assuming that the background is white (i.e making the line/graphs more bold)

# Input

The application will read the input from a textfile named "config.txt" where the input consists of
 1. The name of the image in JPG, PNG and BMP
 2. An integer that controls the level of whiteness of the image. Integer (1 - 5)
 3. An integer that tells how large the pixel should be. Integer (1 - 5)
 4. An integer that tells how large the resolution should be. Integer (1 - 5)
 5. An integer that tells how large the bold should be. Integer(1 - 5)
 
 # Compile and Run the Application

Once the textfile is filled with relevent information, the application can be executed from the command prompt/terminal screen that takes in an argument which is the text file name.

The following command will allow you to run and compile the application

```
javac MyCanvas.java
java MyCanvas config.txt
```

