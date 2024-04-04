package webserver.http.request;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import utils.DataParser;
import webserver.http.body.HttpBody;
import webserver.http.headers.HttpHeaders;
import webserver.http.request.requestline.MethodType;
import webserver.http.request.requestline.RequestLine;
import webserver.http.request.requestline.ServiceType;
import webserver.path.PathHandler;

public class HttpRequest {

    private static final String DATA_PARAMETER_DELIMITER = "&";
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final String SESSION_COOKIE_NAME = "sid";
    private static final String ACCEPT_HEADER_NAME = "Accept";
    private static final String ACCEPT_HEADER_VALUE_DELIMITER = ",";
    private static final String COOKIE_HEADER_NAME = "Cookie";
    private static final String COOKIE_HEADER_VALUE_DELIMITER = ";";

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final Map<String, String> parameters;

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.parameters = getParameters(requestLine.getQuery(), httpBody.toString());
    }

    public String getRequestLine() {
        return requestLine.toString();
    }

    public boolean isGet() {
        return requestLine.getMethod() == MethodType.GET;
    }

    public boolean isPost() {
        return requestLine.getMethod() == MethodType.POST;
    }

    public ServiceType getServiceType() {
        return ServiceType.of(requestLine.getAbsolutePath());
    }

    public String getStaticResourcePath() {
        return PathHandler.getStaticResourcePath(requestLine.getAbsolutePath());
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getSessionId() {
        return getCookieValues().get(SESSION_COOKIE_NAME);
    }

    public String getContentType() {
        String extension = PathHandler.getFileExtension(getStaticResourcePath());
        return getAcceptTypes().stream().filter(type -> type.contains(extension)).findAny()
            .orElse(DEFAULT_CONTENT_TYPE);
    }

    private List<String> getAcceptTypes() {
        String acceptHeaderValue = httpHeaders.getValueByName(ACCEPT_HEADER_NAME);
        return DataParser.parseValues(acceptHeaderValue, ACCEPT_HEADER_VALUE_DELIMITER);
    }

    private Map<String, String> getCookieValues() {
        String cookieHeaderValue = httpHeaders.getValueByName(COOKIE_HEADER_NAME);
        return DataParser.parseParameters(cookieHeaderValue, COOKIE_HEADER_VALUE_DELIMITER);
    }

    public void processHeaders(BiConsumer<String, String> consumer) {
        httpHeaders.process(consumer);
    }

    private Map<String, String> getParameters(String query, String bodyContent) {
        return DataParser.parseParameters(query + bodyContent, DATA_PARAMETER_DELIMITER);
    }
}