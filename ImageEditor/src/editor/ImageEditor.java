package editor;

import java.io.*;
import java.util.Scanner;

public class ImageEditor {

    // Initialize the pixel map and the image height and width
    private Pixel[][] PixelMap;
    private int height;
    private int width;

    public ImageEditor()
    {
        height = 0;
        width = 0;
    }

    public static void main(String[] args){
        // Count how many arguments are passed in
        int argc = args.length;
        // If the wrong number of arguments are passed in, then kill the program
        // and return a usage error
        if (argc < 3 || argc > 4) { usage_error(); }
        else{
            // Read input file
            ImageEditor editor = new ImageEditor();
            editor.read_input_image(args[0]);
            // then determine and perform the required image transformation
            switch (args[2])
            {
                case "grayscale":
                    editor.grayscale();
                    break;
                case "invert":
                    editor.invert();
                    break;
                case "emboss":
                    editor.emboss();
                    break;
                case "motionblur":
                    if (argc != 4)
                    {
                        System.out.println("Missing length argument for motionblur, see usage.");
                        usage_error();
                    }

                    editor.motionblur(Integer.parseInt(args[3].trim()));

                    break;
                default:
                    System.out.println("Invalid third argument " + args[2]+ " given, see usage.");
                    usage_error();
            }

            editor.save(args[1]);
        }

    }

    private static void usage_error()
    {
        System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
        System.exit(1);
    }

    private void read_input_image(String filename)
    {
        int[] temp_arr = new int[3]; // a temporary array to hold three pixel values

        try {
            // Create file reader
            FileReader file_reader = new FileReader(filename);
            // Create buffered file reader from file reader
            BufferedReader buffered_reader = new BufferedReader(file_reader);
            // Create scanner
            Scanner f = new Scanner(buffered_reader);
            f.next(); //skip the P3
            // Define delimiter using regular expressions
            f.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
            // Get width and height of image
            width = f.nextInt();
            height = f.nextInt();
            // Print values
            System.out.println("H: "+height + " W: " + width );
            f.next(); //skip max color value
            PixelMap = new Pixel[height][width]; // Create 2D array of pixel objects
            int row, col,i;
            for (row = 0; row < height; row++)
            {
                for (col = 0; col < width; col++)
                {
                    for (i = 0; i < 3; i++) // fill temporary array with pixel values
                    {
                        temp_arr[i] = f.nextInt();
                    }
                    // Create pixel object
                    PixelMap[row][col] = new Pixel(temp_arr[0], temp_arr[1], temp_arr[2]);
                }
            }
            f.close();
        }
        catch (Exception error)
        {
            System.out.println("Error reading the file: " + filename +
                    ".\nError Message: " + error.toString());
            usage_error();
        }
    }

    public void save(String outfile)
    {
        String line_sep = System.getProperty("line.separator");

        // To account for the amount of memory needed we have width*height pixels, and
        // each pixel has at most 3 integers, with one separating whitespace, thus we need at
        // least (3+1)*3*width*height memory. I'm also added 50 spaces of buffer for other characters
        // in the PPM file
        int PIXEL_LEN = 3;
        int WHITESPACE = 1;
        int NUM_COLORS = 3;
        int BUFFER = 50;
        StringBuilder data = new StringBuilder((PIXEL_LEN+WHITESPACE)*NUM_COLORS*width*height*+BUFFER);
        data.append("P3"+line_sep);
        data.append(width);
        data.append(" ");
        data.append(height+line_sep+255+line_sep); // append maximum color value

        // Append pixel colors
        int i,j;
        for (i = 0; i < height; i++)
        {
            for (j = 0; j < width; j++)
            {
                data.append(PixelMap[i][j].red+line_sep);
                data.append(PixelMap[i][j].green+line_sep);
                data.append(PixelMap[i][j].blue+line_sep);
            }
        }

        try {
            FileWriter file_writer = new FileWriter(outfile);
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            buffered_writer.append(data);
            buffered_writer.close();
        }
        catch (Exception error)
        {
            System.out.println("Error writing to output file: " + error.getMessage());
            usage_error();
        }
    }

    public void invert(){
        int PIXEL_MAX = 255;
        int i,j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                PixelMap[i][j].red = PIXEL_MAX - PixelMap[i][j].red;
                PixelMap[i][j].blue = PIXEL_MAX - PixelMap[i][j].blue;
                PixelMap[i][j].green = PIXEL_MAX - PixelMap[i][j].green;
            }
        }
    }

    public void grayscale(){
        int i,j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                 int avg = (PixelMap[i][j].red+PixelMap[i][j].blue+PixelMap[i][j].green)/3;
                 PixelMap[i][j].red = avg;
                 PixelMap[i][j].blue = avg;
                 PixelMap[i][j].green = avg;
            }
        }
    }

    public void emboss(){
        int i,j;
        int val, redDiff, blueDiff, greenDiff;
        for (i = height-1; i >=0; i--){
            for (j = width-1; j >=0; j--){
                // if the index is out of bounds set the value to 128
                if (i-1<0 || j-1 < 0) { val = 128; }
                else{
                    // Calculate the pixel difference
                    redDiff = PixelMap[i][j].red - PixelMap[i-1][j-1].red;
                    blueDiff = PixelMap[i][j].blue - PixelMap[i-1][j-1].blue;
                    greenDiff = PixelMap[i][j].green - PixelMap[i-1][j-1].green;

                    // find max difference
                    if (Math.abs(redDiff) >= Math.abs(greenDiff)){
                        if (Math.abs(blueDiff) > Math.abs(redDiff)) { val = 128 + blueDiff; }
                        else { val = 128 + redDiff; }
                    }
                    else{
                        if (Math.abs(blueDiff) > Math.abs(greenDiff)) { val = 128 + blueDiff; }
                        else { val = 128 + greenDiff; }
                    }
                    if (val < 0) { val = 0; }
                    if (val > 255) { val = 255; }
                }

                PixelMap[i][j].red = val;
                PixelMap[i][j].blue = val;
                PixelMap[i][j].green = val;
            }
        }

    }

    public void motionblur(int blur)
    {
        int i, j, k;
        int end;

        for (i = 0; i<height ; i++)
        {
            for (j = 0; j < width; j++)
            {
                int[] temp_arr = {0,0,0};
                // Calculate the end of the row to blur
                end = Math.min(blur, width-j);
                for (k = j; k < j + end; k++)
                {
                    temp_arr[0] += PixelMap[i][k].red;
                    temp_arr[1] += PixelMap[i][k].green;
                    temp_arr[2] += PixelMap[i][k].blue;
                }

                // average values
                for (k = 0; k < 3; k++)
                {
                    temp_arr[k] /= end;
                }

                PixelMap[i][j].red = temp_arr[0];
                PixelMap[i][j].green = temp_arr[1];
                PixelMap[i][j].blue = temp_arr[2];
            }
        }
    }

}
