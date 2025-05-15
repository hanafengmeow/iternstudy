import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * this class is for download the
 */
public class ThreadPageCrawler {

    public static void main(String[] args) throws IOException {
        String tag = Constant.NIW_TAG;

        List<String> uniqueUrls = Files.readAllLines(Paths.get(Constant.getThreadURLPath(tag)));

        /**
         * TODO: talk to aws dynamboDB to filter a unvisited pages.
         *
         */
        int targetSize = uniqueUrls.size();

        int curSize = 0;

        while (curSize < targetSize) {
            int downloadNum = batchDownloadPages(tag, uniqueUrls);
            curSize += downloadNum;
        }
    }

    /**
     * @param uniqueUrls
     * @return successful download pages
     */
    private static int batchDownloadPages(String tag, List<String> uniqueUrls) {
        int cnt = 0;
        for (String url : uniqueUrls) {
            try {
                String[] parts = url.split("/");
                // Define the path and filename for the local file where you want to save the content
                String filePath = String.format("%s/%s", Constant.getThreadPagePath(tag), parts[parts.length - 1]);

                File file = new File(filePath);
                if (file.exists()) {
                    System.out.println("filePath exist -- skip downloading");
                    continue;
                }
                System.out.println(String.format("start downloading %s", url));
                Thread.sleep(3000);
                // Connect to the web page using Jsoup
                Document doc = Jsoup.connect(url).get();

                // Get the HTML content of the page
                String htmlContent = doc.html();

                // Write the HTML content to a local file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(htmlContent);
                }
                cnt++;
            } catch (Exception exp) {
                System.out.println("fail to craw + " + url);
                exp.printStackTrace();
            }
        }
        return cnt;
    }
}
