package http;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String SINGLE_SPACE = " ";
    private static final int PATH_TOKEN_ORDER = 1;
    private final List<String> headers;

    public HttpRequest(List<String> headers) {
        this.headers = headers;
    }

    public String getHeaderPath() {
        String[] tokens = headers.get(0).split(SINGLE_SPACE);
        return tokens[PATH_TOKEN_ORDER];
    }

    public void printHeaders() {
        for (String header : headers) {
            logger.debug("request header : {}", header);
        }
    }
}
