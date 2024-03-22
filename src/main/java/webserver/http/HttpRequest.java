package webserver.http;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.method.Method;
import webserver.path.BusinessPath;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String SPACE = " ";
    private static final String COMMA = ",";
    private static final String QUESTION_MARK = "\\?";
    private static final String HEADER_PRINT_FORMAT = "request header : {}";
    private static final String ACCEPT_HEADER_NAME = "Accept:";

    private final List<String> headers;
    private final String body;

    public HttpRequest(List<String> headers, String body) {
        this.headers = headers;
        this.body = body;
    }

    public Method getMethod() {
        return Method.valueOf(getRequestLine().split(SPACE)[0]);
    }

    public boolean needBusinessExecution() {
        return BusinessPath.contains(getAbsolutePath());
    }

    public String getAbsolutePath() {
        return getTargetTokens()[0];
    }

    public String getQuery() {
        if (hasQuery()) {
            return getTargetTokens()[1];
        }
        return "";
    }

    public String getBody() {
        return body;
    }

    public List<String> getAcceptTypes() {
        return Arrays.stream(getAcceptHeaderValue().split(COMMA)).map(String::trim)
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