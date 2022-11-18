package Application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class WebCrawlerNotOptimized {
    private HashSet<String> paths;
    private int pathStartIndex = 26;
    File baseFolder;

    public WebCrawlerNotOptimized() {
        paths = new HashSet<String>();
        paths.add("/ajax/libs/jquery/1.9.1/jquery.min.js");

        baseFolder = new File("ScrapedWebsite");
        baseFolder.mkdirs();

        System.out.println("Created ScrapedWebsite folder, will now commence scraping!");
    }

    public void findAndScrapePaths(String URL) {
        System.out.println("-Crawler crawling-");

        String path = URL.substring(pathStartIndex);

        if (!paths.contains(path)) {
            System.out.println("Crawler found: " + path);

            if (paths.add(path)) {
                WebScraperNotOptimized.scrape(path);
            }
            findAllImagesLinksCssAndScriptsRecursively(URL);

        } else {
            System.out.println("Crawler ignored duplicate.");
        }
    }

    private void findAllImagesLinksCssAndScriptsRecursively(String URL) {
        try {
            Document doc = Jsoup.connect(URL).ignoreContentType(true).get();
            Elements availableImgsOnPage = doc.select("img[src]");
            Elements availableCssOnPage = doc.select("link[href]");
            Elements availableScriptOnPage = doc.select("script[src]");
            Elements availableLinksOnPage = doc.select("a[href]");
            for (Element ele1 : availableImgsOnPage) {
                findAndScrapePaths(ele1.attr("abs:src"));
                for (Element ele2 : availableCssOnPage) {
                    findAndScrapePaths(ele2.attr("abs:href"));
                    for (Element ele3 : availableScriptOnPage) {
                        findAndScrapePaths(ele3.attr("abs:src"));
                        for (Element ele4 : availableLinksOnPage) {
                            findAndScrapePaths(ele4.attr("abs:href"));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("For '" + URL + "': " + e.getMessage());
        }
    }
}


