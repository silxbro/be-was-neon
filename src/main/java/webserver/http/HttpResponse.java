package webserver.http;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PathUtils;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private final OutputStream out;

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public void send(HttpRequest request) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            String resourcePath = PathUtils.getStaticResourcePath(request.getAbsolutePath());
            byte[] body = readResponseFile(resourcePath);

            response200Header(dos, getContentType(request.getAcceptTypes(), resourcePath), body.length);
            responseBody(dos, body);
        }
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBody) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBody + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] readResponseFile(String targetPath) throws IOException {
        // 응답 대상 파일 읽기
        try (FileInputStream fileInputStream = new FileInputStream(targetPath)) {
            return fileInputStream.readAllBytes();
        }
    }

    private String getContentType(List<String> acceptTypes, String targetPath) {
        String extension = PathUtils.getExtension(targetPath);
        return acceptTypes.stream()
            .filter(type -> type.contains(extension))
            .findAny().orElse(DEFAULT_CONTENT_TYPE);
    }
}