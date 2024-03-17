package webserver;

import business.Business;
import http.HttpRequest;
import http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            HttpRequest request = new HttpRequest(readRequestHeaders(in));
            HttpResponse response = new HttpResponse(out);

            // 요청 내용(헤더) 출력
            request.printHeaders();
            // 비즈니스 수행
            Business.execute(request);
            // 요청 응답
            response.send(request);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private List<String> readRequestHeaders(InputStream in) throws IOException {
        List<String> headers = new ArrayList<>();
        // 클라이언트 요청 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            headers.add(line);
        }
        return headers;
    }
}