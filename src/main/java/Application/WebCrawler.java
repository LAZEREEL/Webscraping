package Application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class WebCrawler {
    private HashSet<String> urlLinks;

    public WebCrawler() {
        urlLinks = new HashSet<String>();
    }

    public void getPageLinks(String URL) {

        if (!urlLinks.contains(URL)) {
            try {
                if (urlLinks.add(URL)) {
                    System.out.println(URL.substring(26));
//                    Application.DownloadWebPage(URL);
                }
                Document doc = Jsoup.connect(URL).get();
                Elements availableLinksOnPage = doc.select("a[href]");
                for (Element ele : availableLinksOnPage) {
                    getPageLinks(ele.attr("abs:href"));
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
}


