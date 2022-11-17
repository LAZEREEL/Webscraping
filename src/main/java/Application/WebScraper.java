package Application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;


public class WebScraper {

    public static void DownloadWebPage(String webpage) {
        try {

            // Create URL object
            URL url = new URL("http://books.toscrape.com/" + webpage);
            System.out.println("http://books.toscrape.com/" + webpage);

            int indexToFilterDirectory = webpage.lastIndexOf('/');
            int indexToFilterFileName = webpage.length();


            String filteredFileName = webpage.substring(indexToFilterDirectory + 1, indexToFilterFileName);
            String filteredDirectory = "";

            try {
                filteredDirectory = webpage.substring(0, indexToFilterDirectory);
                String directories = filteredDirectory;
                File file = new File("ScrapedWebsite", directories);
                file.mkdirs();
                System.out.println("Directory Created: " + filteredDirectory);
            } catch (StringIndexOutOfBoundsException sioobe) {
                //If there's no directory
                System.out.println("StringIndexOutOfBoundsException = No directory needed!");
            }

            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            File fileToBeWritten;
            File imgToBeWritten;
            if (filteredFileName.endsWith(".jpg")) {
                if (filteredDirectory.equals("")) {
                    imgToBeWritten = new File("ScrapedWebsite", filteredFileName);
                    System.out.println("ENDS WITH JPG, DIRECTORY NOT NEEDED ");
                } else {
                    imgToBeWritten = new File("ScrapedWebsite/" + filteredDirectory, filteredFileName);
                    System.out.println("ENDS WITH JPG, DIRECTORY NEEDED ");
                }
                BufferedImage image =null;

                image = ImageIO.read(url);

                ImageIO.write(image, "jpg",new File(String.valueOf(imgToBeWritten)));



            } else {
                if (filteredDirectory.equals("")) {
                    fileToBeWritten = new File("ScrapedWebsite", filteredFileName);
                    System.out.println("DOES NOT END WITH JPG, DIRECTORY NOT NEEDED ");
                } else {
                    fileToBeWritten = new File("ScrapedWebsite/" + filteredDirectory, filteredFileName);
                    System.out.println("DOES NOT END WITH JPG, DIRECTORY NEEDED ");
                }
                BufferedWriter writer =
                        new BufferedWriter(new FileWriter(fileToBeWritten));

                // read each line from stream till end
                String line;
                while ((line = readr.readLine()) != null) {
                    writer.write(line);
                }

                readr.close();
                writer.close();
                System.out.println("Successfully Downloaded: " + filteredFileName);
            }

        }

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        } catch (IOException ie) {
            System.out.println("IOException raised");
        }
    }
}
