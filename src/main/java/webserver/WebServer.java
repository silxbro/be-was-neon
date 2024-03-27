package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.HttpHandler;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10; // CPU 코어의 수를 고려하여 스레드 개수 설정

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // ExecutorService를 사용하여 스레드 풀 생성
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            // 클라이언트가 연결될때까지 대기하고, 요청이 오면 스레드 풀에서 처리
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new HttpHandler(connection));
            }
        }
    }
}