import static java.nio.file.Files.lines;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.lang.Object;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;


public class MyCanvas extends Canvas{

    static String extensionStr = "";
    static int intensity = 0, scale = 0;
    static float intensityValue, scaleValue;


	public static void main(String[] args) throws IOException {

        String [] text = new String [10];

		try {
            /*

            Takes in an argument, in this case a text file
            For example, java MyCanvas config.txt

            */
            Scanner scanner = new Scanner(new File(args[0])); 

            //Reading the strings in the text file and save them into an array
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < 3; i++){
                if (scanner.hasNextLine()) {
                    text[i] = scanner.nextLine(); 
                }
                else{
                    text[i] = "5"; // if the second and third line is empty; issue a default value
                }
                System.out.println(text[i]); // print out the strings in the text file
            }

            /*

            Scan for the last three characters of the image name such as JPG, PNG, BMP to be placed
            in the invertImage function.

            Note: This "for-loop" section may requires changes in the future since the application cannot read in
            JPEG.
            
            */


            for (int j = text[0].length() - 3; j < text[0].length(); j++){
                sb.append(text[0].charAt(j));
            }
            
            extensionStr = sb.toString(); // The third characters are placed into a string variable
            scanner.close();
        }

        catch (FileNotFoundException e) {
			e.printStackTrace();
        }
        /*  
        Read the second line from the text file

        The textfile input measurement (1 - 5)

        The higher the number, the more whiter the image.
        */

        intensity = Integer.parseInt(text[1]); 
        scale = Integer.parseInt(text[2]);

        switch (intensity) {
        case 1:
            intensityValue = 0.6f;
            break;
        case 2:
            intensityValue = 0.7f;
            break;
        case 3:
            intensityValue = 0.8f; 
            break;
        case 4:
            intensityValue = 0.9f;
            break;
        case 5:
            intensityValue = 1.0f;
            break;
        }

        switch (scale) {
        case 1:
            scaleValue = 1.5f;
            break;
        case 2:
            scaleValue = 2.0F;
            break;
        case 3:
            scaleValue = 2.5f; 
            break;
        case 4:
            scaleValue = 2.5f;
            break;
        case 5:
            scaleValue = 3.0f;
            break;
        }


        invertImage(text[0], intensityValue, scaleValue);
    }

    // Invert Image and contrast change Function
	public static void invertImage(String imageName, float intensityValue, float scaleValue) {
        BufferedImage inputFile = null;

        //Reading the image
        try {
            inputFile = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = inputFile.getWidth();
        int height = inputFile.getHeight();

        //Loop and grab the RGB pixel from the image and invert it

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgba = inputFile.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                inputFile.setRGB(x, y, col.getRGB());
            }
        }
        // Change the contrast of the image

        RescaleOp op = new RescaleOp(intensityValue, 0, null);
        inputFile = op.filter(inputFile, inputFile);

        //Resizing an Image

        int resizedWidth = (int) (width * scaleValue);
        int resizedHeight = (int) (height * scaleValue);

        System.out.println(resizedWidth);
        System.out.println(width);

        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(inputFile, 0, 0, resizedWidth, resizedHeight, null);
        graphics2D.dispose();
        

        try {
            //Creating an output image
            ImageIO.write(resizedImage, extensionStr, new File("invert-" + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
