package webserver.executor;

import db.SessionManager;
import java.io.FileInputStream;
import java.io.IOException;
import utils.HtmlModifier;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class LoadExecutor extends GetExecutor {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            byte[] content = reflectLogin(readFileContent(request.getStaticResourcePath()), request.getSessionId());
            String contentType = request.getContentType();

            response.setLoadFile(content, contentType);
        } catch (IOException e) {
            response.setPathError();
        }
    }

    private byte[] reflectLogin(byte[] originalContent, String sessionId) throws IOException {
        if (isLogin(sessionId)) {
            String userName = SessionManager.findUserNameBySession(sessionId);
            return HtmlModifier.setLogin(originalContent, userName);
        }
        return originalContent;
    }

    private boolean isLogin(String sessionId) {
        return SessionManager.isSessionIdExist(sessionId);
    }

    private byte[] readFileContent(String targetPath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(targetPath)) {
            return fileInputStream.readAllBytes();
        }
    }
}