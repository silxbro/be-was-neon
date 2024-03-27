package webserver.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ResponseReader {

    private static final String STATUS_LINE_HEADER_FORMAT = "%s %s";
    private static final String HTTP_1_1_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_HEADER_FORMAT = "Content-Type: %s";
    private static final String CONTENT_LENGTH_HEADER_FORMAT = "Content-Length: %s";
    private static final String LOCATION_HEADER_FORMAT = "Location: %s";
    private static final String SET_COOKIE_FORMAT = "Set-Cookie: %s";

    public HttpResponse getResponse(RequestResult result) throws IOException {
        return new HttpResponse(getHeaders(result), getBody(result));
    }

    private List<String> getHeaders(RequestResult result) throws IOException {
        List<String> headers = new ArrayList<>();

        headers.add(getStatusLine(result.getHttpStatus()));
        headers.addAll(getHeadersByStatus(result));
        return headers;
    }

    private List<String> getHeadersByStatus(RequestResult result) throws IOException {
        HttpStatus status = result.getHttpStatus();
        if (status.isSuccessful()) {
            return getSuccessfulHeaders(result.getFinalPath(), result.getContentType());
        }
        if (status.isRedirection()) {
            return getRedirectionHeaders(result.getFinalPath(), result.getCookies());
        }
        return new ArrayList<>();
    }

    private String getBody(RequestResult result) throws IOException {
        if (result.getHttpStatus().isSuccessful()) {
            byte[] bodyBytes = createBody(result.getFinalPath());
            return new String(bodyBytes, StandardCharsets.UTF_8);
        }
        return "";
    }

    private String getStatusLine(HttpStatus status) {
        return String.format(STATUS_LINE_HEADER_FORMAT, HTTP_1_1_VERSION, status.toString());
    }

    private List<String> getSuccessfulHeaders(String finalPath, String contentType) throws IOException {
        return List.of(
            String.format(CONTENT_TYPE_HEADER_FORMAT, contentType),
            String.format(CONTENT_LENGTH_HEADER_FORMAT, createBody(finalPath).length)
        );
    }

    private List<String> getRedirectionHeaders(String finalPath, List<Cookie> cookies) {
        List<String> headers = new ArrayList<>();
        headers.add(String.format(LOCATION_HEADER_FORMAT, finalPath));
        if (!cookies.isEmpty()) {
            cookies.forEach(
                cookie -> headers.add(String.format(SET_COOKIE_FORMAT, cookie.toString())));
        }
        return headers;
    }

    private byte[] createBody(String targetPath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(targetPath)) {
            return fileInputStream.readAllBytes();
        }
    }
}