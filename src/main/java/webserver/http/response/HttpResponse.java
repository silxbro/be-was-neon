package webserver.http.response;

import static webserver.http.response.statusline.HttpStatus.FOUND;
import static webserver.http.response.statusline.HttpStatus.METHOD_NOT_ALLOWED;
import static webserver.http.response.statusline.HttpStatus.NOT_FOUND;
import static webserver.http.response.statusline.HttpStatus.OK;

import java.io.IOException;
import java.util.List;
import webserver.http.body.HttpBody;
import webserver.http.cookie.Cookie;
import webserver.http.headers.HttpHeaders;
import webserver.http.response.statusline.StatusLine;

public class HttpResponse {
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";
    private static final String LOCATION_HEADER_NAME = "Location";
    private static final String SET_COOKIE_NAME = "Set-Cookie";

    private final StatusLine statusLine;
    private final HttpHeaders httpHeaders;
    private final HttpBody httpBody;

    public HttpResponse() {
        this.statusLine = new StatusLine();
        this.httpHeaders = new HttpHeaders();
        this.httpBody = new HttpBody();
    }

    public String getStatusLine() {
        return statusLine.toString();
    }

    public List<String> getHeaders() {
        return httpHeaders.getList();
    }

    public byte[] getBody() {
        return httpBody.toByteArray();
    }

    public void setLoadFile(byte[] content, String contentType) throws IOException {
        statusLine.setStatus(OK);
        httpHeaders.add(CONTENT_TYPE_HEADER_NAME, contentType);
        httpHeaders.add(CONTENT_LENGTH_HEADER_NAME, String.valueOf(content.length));
        httpBody.setContent(content);
    }

    public void setRedirect(String path) {
        statusLine.setStatus(FOUND);
        httpHeaders.add(LOCATION_HEADER_NAME, path);
    }

    public void setCookie(Cookie cookie) {
        httpHeaders.add(SET_COOKIE_NAME, cookie.toString());
    }

    public void setPathError() {
        statusLine.setStatus(NOT_FOUND);
    }

    public void setMethodError() {
        statusLine.setStatus(METHOD_NOT_ALLOWED);
    }
}