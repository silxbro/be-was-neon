package utils;

import java.util.HashMap;
import java.util.Map;

public class ParameterParser {

    public static Map<String, String> getUserInfo(String input) {
        Map<String, String> userInfo = new HashMap<>();
        for (String parameter : input.split("&")) {
            String[] entry = parameter.split("=");
            userInfo.put(entry[0], entry[1]);
        }

        return userInfo;
    }
}
