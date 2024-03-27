package webserver.http;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ServiceType;

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

    public MethodType getMethod() {
        return MethodType.valueOf(getRequestLine().split(SPACE)[0]);
    }

    public String getAbsolutePath() {
        return getTargetTokens()[0];
    }

    public boolean needService() {
        return ServiceType.contains(getAbsolutePath());
    }

    public String getParameterData() {
        if (getMethod() == MethodType.GET) {
            return getQuery();
        }
        if (getMethod() == MethodType.POST) {
            return body;
        }
        return "";
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

    private String getQuery() {
        return getTargetTokens()[1];
    }

    private String[] getTargetTokens() {
        String requestTarget = getRequestLine().split(SPACE)[1];
        return requestTarget.split(QUESTION_MARK);
    }

    private String getAcceptHeaderValue() {
        return headers.stream().filter(header -> header.startsWith(ACCEPT_HEADER_NAME))
            .findFirst().orElseThrow()
            .replace(ACCEPT_HEADER_NAME, "");
    }
}