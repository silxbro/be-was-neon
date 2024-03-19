package http;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "\\?";
    private static final String HEADER_PRINT_FORMAT = "request header : {}";
    private static final String ACCEPT_HEADER_NAME = "Accept:";
    private final List<String> headers;

    public HttpRequest(List<String> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return getRequestLine().split(SPACE)[0];
    }

    public String getAbsolutePath() {
        return getTargetTokens()[0];
    }

    public String getQuery() {
        if (!hasQuery()) {
            throw new IllegalArgumentException();
        }
        return getTargetTokens()[1];
    }

    public List<String> getAcceptTypes() {
        return Arrays.stream(getAcceptHeaderValue().split(",")).map(String::trim)
            .toList();
    }

    public void printHeaders() {
        for (String header : headers) {
            logger.debug(HEADER_PRINT_FORMAT, header);
        }
    }

    private String getRequestLine() {
        return headers.get(0);
    }

    private String[] getTargetTokens() {
        String requestTarget = getRequestLine().split(SPACE)[1];
        return requestTarget.split(QUESTION_MARK);
    }

    private boolean hasQuery() {
        return getTargetTokens().length > 1;
    }

    private String getAcceptHeaderValue() {
        return headers.stream().filter(header -> header.startsWith(ACCEPT_HEADER_NAME))
            .findFirst().orElseThrow()
            .replace(ACCEPT_HEADER_NAME,"");
    }
}