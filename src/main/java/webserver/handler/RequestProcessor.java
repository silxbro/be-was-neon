package webserver.handler;

import java.util.List;
import service.ServiceExecutor;
import service.ServiceType;
import webserver.http.RequestResult;
import webserver.http.HttpRequest;
import webserver.http.HttpStatus;
import webserver.http.MethodType;

public class RequestProcessor {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final String SESSION_ID = "sid";

    public RequestResult process(HttpRequest request) {
        if (!isMethodValid(request)) {
            return new RequestResult(HttpStatus.METHOD_NOT_ALLOWED);
        }
        if (!request.needService()) {
            return staticProcess(request);
        }
        return serviceProcess(request);
    }

    private boolean isMethodValid(HttpRequest request) {
        try {
            MethodType methodType = request.getMethod();
            if (!request.needService()) {
                return methodType == MethodType.GET;
            }
            ServiceType serviceType = ServiceType.of(request.getAbsolutePath());
            return methodType.isValidService(serviceType);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public RequestResult staticProcess(HttpRequest request) {
        String staticResourcePath = PathHandler.getStaticResourcePath(request.getAbsolutePath());
        String contentType = getContentType(request.getAcceptTypes(), request.getAbsolutePath());
        return new RequestResult(HttpStatus.OK, staticResourcePath, contentType);
    }

    public RequestResult serviceProcess(HttpRequest request) {
        ServiceType serviceType = ServiceType.of(request.getAbsolutePath());
        String data = getDataByRequest(serviceType, request);
        return new ServiceExecutor().getExecutionResult(serviceType, data);
    }

    private String getContentType(List<String> acceptTypes, String targetPath) {
        String extension = PathHandler.getExtension(targetPath);
        return acceptTypes.stream()
            .filter(type -> type.contains(extension))
            .findAny().orElse(DEFAULT_CONTENT_TYPE);
    }

    private String getDataByRequest(ServiceType serviceType, HttpRequest request) {
        if (serviceType == ServiceType.LOGOUT) {
            return request.getCookieValues().get(SESSION_ID);
        }
        if (request.getMethod() == MethodType.GET) {
            return request.getQuery();
        }
        if (request.getMethod() == MethodType.POST) {
            return request.getBody();
        }
        return "";
    }
}
