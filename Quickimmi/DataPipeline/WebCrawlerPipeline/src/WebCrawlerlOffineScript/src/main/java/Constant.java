import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    public static final String BASE_PATH = String.format("%s/%s", Paths.get(".").toAbsolutePath().normalize().toString(), "src/WebCrawlerlOffineScript");
    public static final String BASE_URL = "https://www.1point3acres.com/bbs";
    public static final String THREAD_URL_PREFIX = "https://www.1point3acres.com/bbs/thread-";
    public static final String THREAD_URL_SUFFIX = "-1-1.html";

    public static void main(String[] args) {
        System.out.println(getThreadURLPath("niw"));
    }
    public static final String  EB1A_TAG = "eb1a";
    public static final String  NIW_TAG = "niw";

    public static final String THREAD_PAGE_FOLDER = "/Users/xinxinhe/workspace/Test/threadpage";
    public static final Set<String> KEY_WORDS = new HashSet<>(Arrays.asList("分享", "经验", "时间线", "获批", "通过", "批准", "approve", "timeline", "data point", "案例", "approved", "DP", "绿了"));

    public static String getThreadURLPath(String tag){
        if (tag.equals(EB1A_TAG)) {
            return String.format("%s/%s%s", BASE_PATH, "data", EB1A_TAG + "_url.txt");
        } else if (tag.equals(NIW_TAG)) {
            return String.format("%s/%s/%s", BASE_PATH, "data",  NIW_TAG + "_url.txt");
        } else {
            return "";
        }
    }

    public static String getNeedReviewURLPath(String tag){
        if (tag.equals(EB1A_TAG)) {
            return String.format("%s/%s%s", BASE_PATH, "data", EB1A_TAG + "_need_review_url.txt");
        } else if (tag.equals(NIW_TAG)) {
            return String.format("%s/%s/%s", BASE_PATH, "data",  NIW_TAG + "_need_review_url.txt");
        } else {
            return "";
        }
    }

    public static String getThreadPagePath(String tag){
        if (tag.equals(EB1A_TAG)) {
            return String.format("%s/%s%s", BASE_PATH, "data", EB1A_TAG);
        } else if (tag.equals(NIW_TAG)) {
            return String.format("%s/%s/%s", BASE_PATH, "data",  NIW_TAG);
        } else {
            return "";
        }
    }

    public static String getStartURL(String tag){
        if(tag.equals(EB1A_TAG)){
            return "https://www.1point3acres.com/bbs/forum.php?mod=forumdisplay&fid=299&typeid=966&typeid=966&filter=typeid&t=2496383&page=%s";
        } else if(tag.equals(NIW_TAG)){
            return "https://www.1point3acres.com/bbs/forum.php?mod=forumdisplay&fid=299&typeid=974&filter=typeid&typeid=974&page=%s";
        } else {
            return "";
        }
    }}
