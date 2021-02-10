import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
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

        //Resizing an Image

        int resizedWidth = (int) (width * scaleValue);
        int resizedHeight = (int) (height * scaleValue);

        int pixelWidth = (int) (width * pixelValue);
        int pixelHeight = (int) (height * pixelValue);

        BufferedImage zoomImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D zoom = zoomImage.createGraphics();
        zoom.setComposite(AlphaComposite.Src);
        zoom.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        zoom.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        zoom.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        zoom.drawImage(image, 0, 0, pixelWidth, pixelHeight, null); // different
        zoom.dispose();

        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D resized = resizedImage.createGraphics();
        resized.setComposite(AlphaComposite.Src);
        resized.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        resized.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        resized.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        resized.drawImage(zoomImage, 0, 0, resizedWidth, resizedHeight, null);
        resized.dispose();

        // Enlarging the pixel of a line/graph
        if(bold != 0){
            for (int y = 0; y < resizedHeight; y++) {
                for (int x = 0; x < resizedWidth; x++) {
                    int rgba = resizedImage.getRGB(x, y);
                    Color col = new Color(rgba, true);
                    if ((col.getRed() < 32) || (col.getGreen() < 32) || (col.getBlue() < 32)){
                        col = new Color(col.getRed(),
                                        col.getGreen(),
                                        col.getBlue());
                        
                        for(int i = 1; i <= bold; i++){
                        int down = y - i;
                        resizedImage.setRGB(x, down, col.getRGB());
                            for(int j = 1; j <= bold; j++){
                                int forward = x + j;
                                resizedImage.setRGB(forward, down, col.getRGB());
                            }
                        }
                    }
                }
            }
        }

        //Loop and grab the RGB pixel from the image and invert it
        for (int x = 0; x < resizedWidth; x++) {
            for (int y = 0; y < resizedHeight; y++) {
                int rgba = resizedImage.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                resizedImage.setRGB(x, y, col.getRGB());
            }
        }

        // Change the contrast of the image

        RescaleOp op = new RescaleOp(intensityValue, 0, null);
        inputFile = op.filter(resizedImage, resizedImage);

        //Creating an output image
        try {
            ImageIO.write(inputFile, extensionStr, new File("invert-" + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
