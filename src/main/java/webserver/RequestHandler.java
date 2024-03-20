package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            // 클라이언트 요청 및 반응 객체 생성
            HttpRequest request = new RequestReader().getRequest(in);
            HttpResponse response = new HttpResponse(out);

            // 헤더 출력
            request.printHeaders();

            // 요청 처리
            Router router = new Router();
            router.route(request, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}