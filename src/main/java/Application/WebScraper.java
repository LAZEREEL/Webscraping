package Application;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

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
            if (filteredDirectory.equals("")) {
                fileToBeWritten = new File("ScrapedWebsite", filteredFileName);
            } else {
                fileToBeWritten = new File("ScrapedWebsite/" + filteredDirectory, filteredFileName);
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

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        } catch (IOException ie) {
            System.out.println("IOException raised");
        }
    }
}
