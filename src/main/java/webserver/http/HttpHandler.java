package webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.router.HttpRouter;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestReader;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseWriter;

public class HttpHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private final Socket connection;

    public HttpHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // 클라이언트 요청 객체 생성, 처리, 응답 객체 생성
            HttpRequest request = new RequestReader().readRequest(in);
            HttpResponse response = new HttpResponse();
            new HttpRouter().execute(request, response);

            // 헤더 출력
            request.printHeaders();
            // 응답 전송
            new ResponseWriter().writeResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}