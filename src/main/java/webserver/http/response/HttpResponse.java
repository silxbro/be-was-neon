package webserver.http.response;

import static webserver.http.response.HttpStatus.FOUND;
import static webserver.http.response.HttpStatus.METHOD_NOT_ALLOWED;
import static webserver.http.response.HttpStatus.NOT_FOUND;
import static webserver.http.response.HttpStatus.OK;
import static webserver.http.response.ResponseHeaderType.CONTENT_LENGTH;
import static webserver.http.response.ResponseHeaderType.CONTENT_TYPE;
import static webserver.http.response.ResponseHeaderType.LOCATION;
import static webserver.http.response.ResponseHeaderType.SET_COOKIE;
import static webserver.http.response.ResponseHeaderType.STATUS_LINE;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import webserver.http.cookie.Cookie;

public class HttpResponse {
    private static final String STATUS_LINE_HEADER_FORMAT = "%s %s";
    private static final String HTTP_1_1_VERSION = "HTTP/1.1";
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        this.headers = new HashMap<>();
    }

    public List<String> getHeaders() {
        List<String> headerList = new ArrayList<>();
        for (String headerName : headers.keySet()) {
            if (headerName.equals(STATUS_LINE.getName())) {
                headerList.add(headers.get(headerName));
                continue;
            }
            headerList.add(headerName + ": " + headers.get(headerName));
        }
        return headerList;
    }

    public byte[] getBody() {
        return body;
    }

    public void setLoadFile(String filePath, String contentType) throws IOException {
        byte[] body = createBody(filePath);
        addStatusLine(OK);
        addHeader(CONTENT_TYPE.getName(), contentType);
        addHeader(CONTENT_LENGTH.getName(), String.valueOf(body.length));
        setBody(body);
    }

    public void setRedirect(String path) {
        addStatusLine(FOUND);
        addHeader(LOCATION.getName(), path);
    }

    public void setCookie(Cookie cookie) {
        addHeader(SET_COOKIE.getName(), cookie.toString());
    }

    public void setPathError() {
        addStatusLine(NOT_FOUND);
    }

    public void setMethodError() {
        addStatusLine(METHOD_NOT_ALLOWED);
    }

    private void addStatusLine(HttpStatus httpStatus) {
        addHeader(STATUS_LINE.getName(),
            String.format(STATUS_LINE_HEADER_FORMAT, HTTP_1_1_VERSION, httpStatus.toString()));
    }

    private void addHeader(String name, String value) {
        headers.put(name, value);
    }

    private void setBody(byte[] body) {
        this.body = body;
    }

    private byte[] createBody(String targetPath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(targetPath)) {
            return fileInputStream.readAllBytes();
        }
    }
}