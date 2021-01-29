import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;


public class MyCanvas extends Canvas{

    static String extensionStr = "";
    static int intensity = 0, scale = 0, enlarge = 0, bold = 0;
    static float intensityValue, scaleValue, enlargePercent;

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

            for(int i = 0; i < 5; i++){
                if (scanner.hasNextLine()) {
                    text[i] = scanner.nextLine(); 
                }
                else{
                    text[i] = "0"; // if the second and third line is empty; issue a default value
                }
            }

            /* 
            
            Establish a check for the extension when the letter is equal to a dot
            
            */
            
            int count = 0;
            int letter = text[0].length()-1;
            while(text[0].charAt(letter) != '.'){
                count++;
                letter--;
            }

            /*

            Scan for the last three characters of the image name such as JPG, PNG, BMP to be placed
            in the invertImage function.

            Note: This "for-loop" section may requires changes in the future since the application cannot read in
            JPEG.
            
            */

            for(int j = text[0].length() - count; j < text[0].length(); j++){
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
        enlargePercent = Float.parseFloat(text[2]);
        scaleValue = Float.parseFloat(text[3]);
        bold = Integer.parseInt(text[4]);
       

        switch (intensity) {
        
        case 0:
            intensityValue = 0.0f;

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

        /*switch (enlarge) {
        
        case 0:
            enlargePercent = 1.0f;
            break;
        case 1:
            enlargePercent = 1.1f;
            break;
        case 2:
            enlargePercent = 1.2f;
            break;
        case 3:
            enlargePercent = 1.3f; 
            break;
        case 4:
            enlargePercent = 1.4f;
            break;
        case 5:
            enlargePercent = 1.5f;
            break;
        }

        switch (scale) {
        
        case 0:
            scaleValue = 1.0f;
            break;
        case 1:
            scaleValue = 1.5f;
            break;
        case 2:
            scaleValue = 2.0f;
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
        }*/

        switch (bold) {
        
        case 0:
            bold = 0;
            break;
        case 1:
            bold= 2;
            break;
        case 2:
            bold = 4;
            break;
        case 3:
            bold = 6; 
            break;
        case 4:
            bold = 8;
            break;
        case 5:
            bold = 10;
            break;
        }

        invertImage(text[0], intensityValue, enlargePercent, scaleValue, bold);
    }

    // Invert Image and contrast change Function

	public static void invertImage(String imageName, float intensityValue, float pixelValue, float scaleValue, int bold) {
        
        BufferedImage image = null;
        BufferedImage inputFile = null;

        //Reading the image

        try {

            image = ImageIO.read(new File(imageName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int width = image.getWidth();
        int height = image.getHeight();

        if(width <= 210 || height <= 210){
            width = (int) (width * 1.5f);
            height = (int) (height * 1.5f);
        }

        inputFile = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D addSize = inputFile.createGraphics();
        addSize.drawImage(image, 0, 0, width, height, null);
        addSize.dispose();

        // Enlarging the pixel of a line/graph
        if(bold != 0){
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgba = inputFile.getRGB(x, y);
                    Color col = new Color(rgba, true);
                    if (col.getRed() != 255 && col.getGreen() != 255 && col.getBlue() != 255){
                        col = new Color(col.getRed(),
                                        col.getGreen(),
                                        col.getBlue());
                        for(int i = 0; i < bold; i++){
                            int back = x--;
                            inputFile.setRGB(back, y, col.getRGB());
                        }

                        for (int j = 0; j < bold; j++){
                            x++;
                            inputFile.setRGB(x, y, col.getRGB());
                        }
                    }
                }
            }

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int rgba = inputFile.getRGB(x, y);
                    Color col = new Color(rgba, true);
                    if (col.getRed() != 255 && col.getGreen() != 255 && col.getBlue() != 255){
                        col = new Color(col.getRed(),
                                        col.getGreen(),
                                        col.getBlue());
                        for(int i = 0; i < bold; i++){
                            int up = y--;
                            inputFile.setRGB(x, up, col.getRGB());
                        }

                        for (int j = 0; j < bold; j++){
                            y++;
                            inputFile.setRGB(x, y, col.getRGB());
                        }
                    }
                }
            }
        }

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

        int pixelWidth = (int) (width * pixelValue);
        int pixelHeight = (int) (height * pixelValue);

        BufferedImage zoomImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D zoomGraphics2D = zoomImage.createGraphics();
        zoomGraphics2D.drawImage(inputFile, 0, 0, pixelWidth, pixelHeight, null); // different
        zoomGraphics2D.dispose();

        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(zoomImage, 0, 0, resizedWidth, resizedHeight, null);
        graphics2D.dispose();

        //Creating an output image
        try {
            ImageIO.write(resizedImage, extensionStr, new File("invert-" + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
