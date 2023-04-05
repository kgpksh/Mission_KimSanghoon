package com.ll.gramgram.standard.util;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ut {
    public static class url {
        public static String encode(String str) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }

        public static String modifyQueryParam(String url, String paramName, String paramValue) {
            url = deleteQueryParam(url, paramName);
            url = addQueryParam(url, paramName, paramValue);

            return url;
        }

        public static String addQueryParam(String url, String paramName, String paramValue) {
            if (url.contains("?") == false) {
                url += "?";
            }

            if (url.endsWith("?") == false && url.endsWith("&") == false) {
                url += "&";
            }

            url += paramName + "=" + paramValue;

            return url;
        }

        private static String deleteQueryParam(String url, String paramName) {
            int startPoint = url.indexOf(paramName + "=");
            if (startPoint == -1) return url;

            int endPoint = url.substring(startPoint).indexOf("&");

            if (endPoint == -1) {
                return url.substring(0, startPoint - 1);
            }

            String urlAfter = url.substring(startPoint + endPoint + 1);

            return url.substring(0, startPoint) + urlAfter;
        }
    }

    public static class IO {
        public static List<List<String>> getOAuthUniqueCode() throws IOException {
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/resources/UniqueCodes.txt")
            );

            List<List<String>> result = new ArrayList<>();

            String str;
            while ((str = reader.readLine()) != null) {
                str = str.stripIndent().trim();
                List<String> row = new ArrayList<>(Arrays.asList(str.split(",")));
                result.add(row);
            }

            reader.close();

            return result;
        }
    }
}
