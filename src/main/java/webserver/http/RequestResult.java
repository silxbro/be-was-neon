package webserver.http;

import java.util.ArrayList;
import java.util.List;

public class RequestResult {

    private static final String EMPTY_STRING = "";
    private final HttpStatus httpStatus;
    private final String finalPath;
    private final String contentType;
    private final List<Cookie> cookies;

    public RequestResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.finalPath = EMPTY_STRING;
        this.contentType = EMPTY_STRING;
        this.cookies = new ArrayList<>();
    }

    public RequestResult(HttpStatus httpStatus, String finalPath) {
        this.httpStatus = httpStatus;
        this.finalPath = finalPath;
        this.contentType = EMPTY_STRING;
        this.cookies = new ArrayList<>();
    }

    public RequestResult(HttpStatus httpStatus, String finalPath, String contentType) {
        this.httpStatus = httpStatus;
        this.finalPath = finalPath;
        this.contentType = contentType;
        this.cookies = new ArrayList<>();
    }

    public RequestResult(HttpStatus httpStatus, String finalPath, List<Cookie> cookies) {
        this.httpStatus = httpStatus;
        this.finalPath = finalPath;
        this.contentType = EMPTY_STRING;
        this.cookies = cookies;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getFinalPath() {
        return finalPath;
    }

    public String getContentType() {
        return contentType;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}