package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ParameterUtils {
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static Map<String, String> parseParams(String query) {
        Map<String, String> params = new HashMap<>();
        for (String param : query.split(PARAM_DELIMITER)) {
            String[] keyValue = param.split(KEY_VALUE_DELIMITER);
            try {
                params.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException();
            }
        }
        return params;
    }
}