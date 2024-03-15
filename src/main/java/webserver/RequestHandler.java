package webserver;

import business.BusinessMapper;
import http.HttpRequest;
import http.HttpResponse;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PathUtils;

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

            // 클라이언트 요청 객체 생성 및 내용 출력
            HttpRequest request = new HttpRequest(readRequestHeaders(in));
            request.printHeaders();

            // 비즈니스 수행
            if (BusinessMapper.isBusiness(request.getRequestPath())) {
                BusinessMapper.getBusiness(request.getRequestPath()).accept(request.getParameterData());
            }

            // 정적 html 파일 응답
            String responsePath = PathUtils.getStaticPath(request.getRequestPath());
            HttpResponse.send(out, readResponseFile(responsePath));

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

    private byte[] readResponseFile(String filePath) throws IOException {
        // 응답 대상 파일 읽기
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return fileInputStream.readAllBytes();
        }
    }
}