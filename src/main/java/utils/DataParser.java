package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParser {
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_VALUE_PAIR_LENGTH = 2;


    public static Map<String, String> parseParameters(String data, String delimiter) {
        Map<String, String> params = new HashMap<>();
        parseValues(data, delimiter).stream()
            .filter(DataParser::isValidParameter)
            .map(each -> each.split(KEY_VALUE_DELIMITER))
            .forEach(keyValue -> params.put(keyValue[0], decodeValue(keyValue[1])));
        return params;
    }

    public static List<String> parseValues(String target, String delimiter) {
        return Arrays.stream(target.split(delimiter))
            .map(String::trim).toList();
    }

    private static boolean isValidParameter(String parameter) {
        return parameter.split(KEY_VALUE_DELIMITER).length == KEY_VALUE_PAIR_LENGTH;
    }

    private static String decodeValue(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}