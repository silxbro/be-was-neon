package http;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String METHOD_DELIMITER = " ";
    private static final String PATH_DELIMITER = "\\?";
    private static final String HEADER_PRINT_FORMAT = "request header : {}";
    private static final String EMPTY_DATA = "";
    private final List<String> headers;

    public HttpRequest(List<String> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return getFirstHeader().split(METHOD_DELIMITER)[0];
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

    public List<String> getRequestedTypes() {
        return Arrays.stream(getAcceptHeader().replace("Accept:", "").split(",")).map(String::trim)
            .toList();
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
        String headerPath = getFirstHeader().split(METHOD_DELIMITER)[1];
        return headerPath.split(PATH_DELIMITER);
    }

    private boolean hasParameterData() {
        return getPathTokens().length > 1;
    }

    private String getAcceptHeader() {
        return headers.stream().filter(header -> header.startsWith("Accept:")).findFirst()
            .orElseThrow();
    }
}