package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestReader;
import webserver.http.RequestResult;
import webserver.http.ResponseReader;

public class HttpHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private static final String CRLF = "\r\n";
    private final Socket connection;

    public HttpHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // 클라이언트 요청 객체 생성, 처리, 응답 객체 생성
            HttpRequest request = new RequestReader().getRequest(in);
            RequestResult result = new RequestProcessor().process(request);
            HttpResponse response = new ResponseReader().getResponse(result);

            // 헤더 출력
            request.printHeaders();
            // 응답 전송
            send(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void send(OutputStream out, HttpResponse response) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            sendHeader(response.getHeaders(), dos);
            sendBody(response.getBody(), dos);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendHeader(List<String> headers, DataOutputStream dos) throws IOException {
        for (String header : headers) {
            dos.writeBytes(header + CRLF);
        }
        dos.writeBytes(CRLF);
    }

    private void sendBody(byte[] body, DataOutputStream dos) throws IOException {
        dos.write(body, 0, body.length);
    }
}