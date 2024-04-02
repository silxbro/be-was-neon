package webserver.http.request;

import static webserver.http.request.RequestHeaderType.ACCEPT;
import static webserver.http.request.RequestHeaderType.COOKIE;
import static webserver.http.request.RequestHeaderType.REQUEST_LINE;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DataParser;
import webserver.path.PathHandler;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String HEADER_PRINT_FORMAT = "request header : {}: {}";
    private static final String DATA_PARAMETER_DELIMITER = "&";
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final String SESSION_COOKIE_NAME = "sid";

    private final Map<String, String> headers;
    private final Map<String, String> parameters;

    public HttpRequest(Map<String, String> headers, String body) {
        this.headers = headers;
        this.parameters = DataParser.parseParameters(getQuery() + body, DATA_PARAMETER_DELIMITER);
    }

    public boolean isGet() {
        return getMethod() == MethodType.GET;
    }

    public boolean isPost() {
        return getMethod() == MethodType.POST;
    }

    public ServiceType getServiceType() {
        return ServiceType.of(getAbsolutePath());
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getSessionId() {
        return getCookieValues().get(SESSION_COOKIE_NAME);
    }

    public String getContentType() {
        String filePath = PathHandler.getStaticResourcePath(getAbsolutePath());
        String extension = PathHandler.getFileExtension(filePath);
        return getAcceptTypes().stream().filter(type -> type.contains(extension)).findAny()
            .orElse(DEFAULT_CONTENT_TYPE);
    }

    public void printHeaders() {
        headers.forEach((key, value) -> logger.debug(HEADER_PRINT_FORMAT, key, value));
    }

    private MethodType getMethod() {
        String methodPart = getRequestLineTokens().get(0);
        return MethodType.valueOf(methodPart);
    }

    private List<String> getAcceptTypes() {
        String acceptHeaderValue = headers.get(ACCEPT.getName());
        return DataParser.parseValues(acceptHeaderValue, ACCEPT.getValueDelimiter());
    }

    private Map<String, String> getCookieValues() {
        String cookieHeaderValue = headers.get(COOKIE.getName());
        return DataParser.parseParameters(cookieHeaderValue, COOKIE.getValueDelimiter());
    }

    private String getRequestLine() {
        return headers.get(REQUEST_LINE.getName());
    }

    private List<String> getRequestLineTokens() {
        return DataParser.parseValues(getRequestLine(), REQUEST_LINE.getValueDelimiter());
    }

    private List<String> getRequestTargetTokens() {
        String requestTarget = getRequestLineTokens().get(1);
        return DataParser.parseValues(requestTarget, REQUEST_LINE.getValueDelimiter());
    }

    public String getAbsolutePath() {
        return getRequestTargetTokens().get(0);
    }

    private boolean hasQuery() {
        return getRequestTargetTokens().size() > 1;
    }

    private String getQuery() {
        if (hasQuery()) {
            return getRequestTargetTokens().get(1);
        }
        return "";
    }
}