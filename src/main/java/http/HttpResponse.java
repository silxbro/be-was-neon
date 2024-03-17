package http;

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
            String responsePath = PathUtils.getStaticPath(request.getRequestPath());
            byte[] body = readResponseFile(responsePath);

            response200Header(dos, responsePath), body.length);
            responseBody(dos, body);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBody) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: text/html;\r\n");
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

    private byte[] readResponseFile(String filePath) throws IOException {
        // 응답 대상 파일 읽기
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return fileInputStream.readAllBytes();
        }
    }
}