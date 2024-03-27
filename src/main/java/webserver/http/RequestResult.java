package webserver.http;

public class RequestResult {

    private static final String EMPTY_STRING = "";
    private final HttpStatus httpStatus;
    private final String finalPath;
    private final String contentType;

    public RequestResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.finalPath = EMPTY_STRING;
        this.contentType = EMPTY_STRING;
    }

    public RequestResult(HttpStatus httpStatus, String finalPath) {
        this.httpStatus = httpStatus;
        this.finalPath = finalPath;
        this.contentType = EMPTY_STRING;
    }

    public RequestResult(HttpStatus httpStatus, String finalPath, String contentType) {
        this.httpStatus = httpStatus;
        this.finalPath = finalPath;
        this.contentType = contentType;
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
}