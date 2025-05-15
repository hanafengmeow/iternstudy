package utils;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public class UrlUtils {

    public static String extractBaseUrl(String websiteUrl) {
        try {
            URL url = new URL(websiteUrl);
            // Extract the scheme (protocol) and domain (netloc) and combine them
            String baseUrl = url.getProtocol() + "://" + url.getHost();
            return baseUrl;
        } catch (Exception exp) {
            throw new RuntimeException("fail to extract base url for " + websiteUrl, exp);
        }
    }

    public static boolean isSameDomain(String cur, String root) {
        try {
            URL rootURL = new URL(root);
            URL curURL = new URL(cur);
            //make sure the url p
            return curURL.getHost().toLowerCase().endsWith(rootURL.getHost().toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public static String normalize(String url) {
        try {
            URL instance = new URL(url);
            // Normalize the URL
            URL normalizedURL = new URL(instance.getProtocol(),
                    instance.getHost(),
                    instance.getPort(),
                    instance.toURI().normalize().getPath());
            return normalizedURL.toExternalForm().toLowerCase();
        } catch (Exception e) {
            //not a valid url.
            return "";
        }
    }

    //to handle the edge case: http://xxxxxx/xxx/, remove the tail slash.
    public static String removeTailSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
//
//    public static String normalize(final String taintedURL) throws MalformedURLException {
//        final URL url;
//        try {
//            url = new URI(taintedURL).normalize().toURL();
//        } catch (URISyntaxException e) {
//            throw new MalformedURLException(e.getMessage());
//        }
//
//        final String path = url.getPath().replace("/$", "");
//        final SortedMap<String, String> params = createParameterMap(url.getQuery());
//        final int port = url.getPort();
//        final String queryString;
//
//        if (params != null) {
//            // Some params are only relevant for user tracking, so remove the most commons ones.
//            for (Iterator<String> i = params.keySet().iterator(); i.hasNext(); ) {
//                final String key = i.next();
//                if (key.startsWith("utm_") || key.contains("session")) {
//                    i.remove();
//                }
//            }
//            queryString = "?" + canonicalize(params);
//        } else {
//            queryString = "";
//        }
//
//        return url.getProtocol() + "://" + url.getHost()
//                + (port != -1 && port != 80 ? ":" + port : "")
//                + path + queryString;
//    }
//
//    private static SortedMap<String, String> createParameterMap(final String queryString) {
//        if (queryString == null || queryString.isEmpty()) {
//            return null;
//        }
//
//        final String[] pairs = queryString.split("&");
//        final Map<String, String> params = new HashMap<String, String>(pairs.length);
//
//        for (final String pair : pairs) {
//            if (pair.length() < 1) {
//                continue;
//            }
//
//            String[] tokens = pair.split("=", 2);
//            for (int j = 0; j < tokens.length; j++) {
//                try {
//                    tokens[j] = URLDecoder.decode(tokens[j], "UTF-8");
//                } catch (UnsupportedEncodingException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            switch (tokens.length) {
//                case 1: {
//                    if (pair.charAt(0) == '=') {
//                        params.put("", tokens[0]);
//                    } else {
//                        params.put(tokens[0], "");
//                    }
//                    break;
//                }
//                case 2: {
//                    params.put(tokens[0], tokens[1]);
//                    break;
//                }
//            }
//        }
//
//        return new TreeMap<String, String>(params);
//    }
}
