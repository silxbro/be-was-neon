package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RequestLineParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_RESOURCES_PATH = "src/main/resources/static";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // 클라이언트 요청을 읽기 위해 BufferedReader 사용
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = reader.readLine();
            logger.debug("request header : {}", line);
            String url = STATIC_RESOURCES_PATH + RequestLineParser.extractPath(line);
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                logger.debug("request header : {}", line);
            }

            // 파일 읽기
            byte[] body;
            try (FileInputStream fileInputStream = new FileInputStream(url)) {
                body = fileInputStream.readAllBytes();
            }

            // 응답 보내기
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, Files.probeContentType(Paths.get(url)), body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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
}