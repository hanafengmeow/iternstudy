import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is used to fetch all thread urls
 */
public class ThreadUrlFetcher {
    public static void main(String[] args) throws IOException {
        getAllMainPagesAndThreadUrls(Constant.NIW_TAG, 47);
    }

    //go to the 1p3arc EB1 page, and check the page number
    private static void getAllMainPagesAndThreadUrls(String tag, int pageCnt) throws IOException {
        String threadURLPath = Constant.getThreadURLPath(tag);
        File file = new File(threadURLPath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        Set<String> urls = new HashSet();

        for (int i = 1; i <= pageCnt; i++) {
            String url = String.format(Constant.getStartURL(tag), i);
            System.out.println(url);
            try {
                // Connect to the web page using Jsoup
                Document doc = Jsoup.connect(url).get();
                // Get the HTML content of the page
                String htmlContent = doc.html();
                urls.addAll(getThreadList(tag, htmlContent));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // FileWriter with append mode
            FileWriter fileWriter = new FileWriter(Constant.getThreadURLPath(tag), true); // true for append mode
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the text to the file
            for (String url : urls) {
                bufferedWriter.write(url + "\n");
            }
            // Close the writers
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<String> getThreadList(String tag, String body) {
        Document doc = Jsoup.parse(body, Constant.BASE_URL);
        //1. select attributes with href
        Elements links = doc.select("a[href]");
        Set<String> urls = links.stream().map(link -> link.attr("abs:href")).collect(Collectors.toSet());
        List<String> threadUrls = urls.stream()
                .filter(u -> u.contains(Constant.THREAD_URL_PREFIX))
                .filter(u -> u.endsWith(Constant.THREAD_URL_SUFFIX))
                .collect(Collectors.toList());

        return threadUrls;
    }
}
