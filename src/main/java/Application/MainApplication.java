package Application;

import java.time.Duration;
import java.time.LocalTime;


public class MainApplication {


    public static void main(String[] args) {


        WebCrawlerNotOptimized crawler = new WebCrawlerNotOptimized();

        crawler.findAndScrapePaths("http://books.toscrape.com/");

        //WebCrawlerNotOptimized.taskFinished();


    }


}
