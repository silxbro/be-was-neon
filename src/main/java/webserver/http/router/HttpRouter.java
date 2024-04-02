package webserver.http.router;

import java.util.HashMap;
import java.util.Map;
import webserver.executor.Executor;
import webserver.executor.LoadExecutor;
import webserver.executor.LoginExecutor;
import webserver.executor.LogoutExecutor;
import webserver.executor.RegistrationExecutor;
import webserver.http.request.HttpRequest;
import webserver.http.request.ServiceType;
import webserver.http.response.HttpResponse;

public class HttpRouter {

    private static final Map<ServiceType, Executor> executorMap = new HashMap<>();

    static {
        executorMap.put(ServiceType.REGISTRATION, new RegistrationExecutor());
        executorMap.put(ServiceType.LOGIN, new LoginExecutor());
        executorMap.put(ServiceType.LOGOUT, new LogoutExecutor());
        executorMap.put(ServiceType.LOAD, new LoadExecutor());
    }

    public void execute(HttpRequest request, HttpResponse response) {
        ServiceType serviceType = ServiceType.of(request.getAbsolutePath());
        Executor executor = executorMap.get(serviceType);
        executor.service(request, response);
    }
}
