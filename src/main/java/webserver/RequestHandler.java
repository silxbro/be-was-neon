package webserver;

import http.HttpRequest;
import http.HttpResponse;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParameterParser;
import utils.PathUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CRLF = "\r\n";
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

            // 정적인 html 파일 응답
            String responseFilePath = PathUtils.getStaticPath(request.getHeaderPath());
            HttpResponse response = new HttpResponse(out, readResponseFile(responseFilePath));
            response.send();

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
        byte[] body;
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            body = fileInputStream.readAllBytes();
        }
        return body;
    }

    private User parseUser(String requestPath) {
        Map<String, String> userInfo = ParameterParser.getUserParams(validateCreateUserPath(requestPath));
        return new User(userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
    }

    private String validateCreateUserPath(String createUserPath) {
        String[] pathAndInfo = createUserPath.split("\\?");
        if (pathAndInfo.length != 2 || !pathAndInfo[0].equals("/user/create")) {
            throw new IllegalArgumentException("INVALID PATH");
        }
        return pathAndInfo[1];
    }
}