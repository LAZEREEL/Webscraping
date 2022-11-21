package Application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebCrawlerNotOptimized {
    private HashSet<String> paths;
    private int pathStartIndex = 26;
    File baseFolder;
    //Executor pool = Executors.newFixedThreadPool(10);
    static ExecutorService scraperPool = Executors.newFixedThreadPool(5);
    static ExecutorService crawlerPool = Executors.newFixedThreadPool(4);


    public WebCrawlerNotOptimized() {
        paths = new HashSet<String>();
        //Ignore external library
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

                Runnable r = () -> performTask(path);
                scraperPool.execute(r);
            }
            Runnable r = () -> findAllImagesLinksCssAndScriptsRecursively(URL);
            crawlerPool.execute(r);
            //findAllImagesLinksCssAndScriptsRecursively(URL);
        } else {
            System.out.println("Crawler ignored duplicate");
        }
    }
    static void performTask(String path){
        WebScraperNotOptimized.scrape(path);
    }
    static void taskFinished(){
        System.out.println("Waiting for all threads to terminate");

            scraperPool.shutdown();while(!scraperPool.isTerminated()) {}
            crawlerPool.shutdown();while(!crawlerPool.isTerminated()) {}


        scraperPool.shutdown();
        crawlerPool.shutdown();
        System.out.println("Threads shutdown");
        LocalTime endTime = LocalTime.now();
        LocalTime startTime = LocalTime.now();
        System.out.println("Finished scraping after " + getDurationAsString(startTime, endTime));
    }

    private static String getDurationAsString(LocalTime startTime, LocalTime endTime) {

        Duration duration = Duration.between(startTime, endTime);

        long h = duration.toHours();
        long min = duration.toMinutesPart();
        long sec = duration.toSecondsPart();

        return String.format("%02d h %02d min %02d sec", h, min, sec);
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


