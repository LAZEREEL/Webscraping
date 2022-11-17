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

        String substringLink = URL.substring(26);

        if (!urlLinks.contains(substringLink)) {
            try {
                if (urlLinks.add(substringLink)) {
                    System.out.println(substringLink);
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


