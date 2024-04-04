package webserver.http.headers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class HttpHeaders {

    private final Map<String, String> headerMap;

    public HttpHeaders() {
        this.headerMap = new HashMap<>();
    }

    public HttpHeaders(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public List<String> getList() {
        return headerMap.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue())
            .toList();
    }

    public void add(String key, String value) {
        headerMap.put(key, value);
    }

    public String getValueByName(String name) {
        return headerMap.get(name);
    }

    public void process(BiConsumer<String, String> consumer) {
        headerMap.forEach(consumer);
    }
}