package webserver.http;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ServiceType;
import utils.StringParser;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String HEADER_PRINT_FORMAT = "request header : {}";
    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "\\?";
    private static final String ACCEPT_HEADER_NAME = "Accept:";
    private static final String COOKIE_HEADER_NAME = "Cookie:";
    private static final String SESSION_ID = "sid";

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

    public List<String> getAcceptTypes() {
        String acceptHeaderValue = getHeaderValue(ACCEPT_HEADER_NAME);
        return StringParser.parseAcceptHeader(acceptHeaderValue);
    }

    public String getSessionId() {
        return getCookieValues().get(SESSION_ID);
    }

    public String getQuery() {
        return getTargetTokens()[1];
    }

    public String getBody() {
        return body;
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

    private Map<String, String> getCookieValues() {
        String cookieHeaderValue = getHeaderValue(COOKIE_HEADER_NAME);
        return StringParser.parseCookieHeader(cookieHeaderValue);
    }

    private String getHeaderValue(String headerName) {
        return headers.stream().filter(header -> header.startsWith(headerName))
            .findFirst().orElseThrow()
            .replace(headerName, "");
    }
}