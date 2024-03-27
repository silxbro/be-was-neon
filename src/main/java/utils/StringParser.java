package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringParser {
    private static final String USER_PARAM_DELIMITER = "&";
    private static final String ACCEPT_HEADER_DELIMITER = ",";
    private static final String COOKIE_HEADER_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static Map<String, String> parseUserData(String data) {
        return parseStringToMap(data, USER_PARAM_DELIMITER);
    }

    public static List<String> parseAcceptHeader(String headerValue) {
        return parseStringToList(headerValue, ACCEPT_HEADER_DELIMITER);
    }

    public static Map<String, String> parseCookieHeader(String headerValue) {
        return parseStringToMap(headerValue, COOKIE_HEADER_DELIMITER);
    }

    private static Map<String, String> parseStringToMap(String target, String delimiter) {
        Map<String, String> params = new HashMap<>();
        parseStringToList(target, delimiter).stream()
            .map(each -> each.split(KEY_VALUE_DELIMITER))
            .forEach(keyValue -> params.put(keyValue[0], decodeValue(keyValue[1])));
        return params;
    }

    private static List<String> parseStringToList(String target, String delimiter) {
        return Arrays.stream(target.split(delimiter))
            .map(String::trim).toList();
    }

    private static String decodeValue(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}