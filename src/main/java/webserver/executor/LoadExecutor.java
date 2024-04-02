package webserver.executor;

import java.io.IOException;
import webserver.path.PathHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class LoadExecutor extends AbstractExecutor {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            String filePath = PathHandler.getStaticResourcePath(request.getAbsolutePath());
            response.setLoadFile(filePath, request.getContentType());
        } catch (IOException e) {
            response.setPathError();
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        response.setMethodError();
    }
}
