package crawler;

import model.UnvisitedUrlQueueMessage;
import org.jsoup.select.Elements;

import java.util.Set;

public interface WebCrawler {
    void crawl(UnvisitedUrlQueueMessage message);

    /**
     * customized url filter logic
     */
    Set<String> filter(Elements links, String rootUrl);
}
