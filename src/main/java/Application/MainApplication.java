package Application;

public class MainApplication {

    public static void main(String[] args) {


        System.out.println("Starting here");

        WebCrawler crawler = new WebCrawler();

        crawler.getPageLinks("http://books.toscrape.com/");
    }
}
