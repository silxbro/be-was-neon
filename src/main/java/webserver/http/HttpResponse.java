package webserver.http;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpResponse {
    private final List<String> headers;
    private final String body;

    public HttpResponse(List<String> headers, String body) {
        this.headers = headers;
        this.body = body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body.getBytes(StandardCharsets.UTF_8);
    }
}