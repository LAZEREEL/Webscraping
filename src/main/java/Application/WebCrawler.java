package Application;

import java.util.Set;

public interface WebCrawler {
	
	Set<String> findAllURLs(String initialURL);
}
