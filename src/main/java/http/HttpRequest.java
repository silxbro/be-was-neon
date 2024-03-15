package http;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String METHOD_SEPARATOR = " ";
    private static final String PATH_SEPARATOR = "\\?";
    private static final String HEADER_PRINT_FORMAT = "request header : {}";
    private static final String EMPTY_DATA = "";
    private final List<String> headers;

    public HttpRequest(List<String> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return getFirstHeader().split(METHOD_SEPARATOR)[0];
    }

    public String getRequestPath() {
        return getPathTokens()[0];
    }

    public String getParameterData() {
        if (hasParameterData()) {
            return getPathTokens()[1];
        }
        return EMPTY_DATA;
    }

    public void printHeaders() {
        for (String header : headers) {
            logger.debug(HEADER_PRINT_FORMAT, header);
        }
    }

    private String getFirstHeader() {
        return headers.get(0);
    }

    private String[] getPathTokens() {
        String headerPath = getFirstHeader().split(METHOD_SEPARATOR)[1];
        return headerPath.split(PATH_SEPARATOR);
    }

    private boolean hasParameterData() {
        return getPathTokens().length > 1;
    }
}