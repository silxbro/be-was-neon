package webserver.http.router;

import java.util.Map;
import webserver.executor.Executor;
import webserver.executor.LoadExecutor;
import webserver.executor.LoginExecutor;
import webserver.executor.LogoutExecutor;
import webserver.executor.RegistrationExecutor;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.ServiceType;
import webserver.http.response.HttpResponse;

public class HttpRouter {

    private static final Map<ServiceType, Executor> executorMap = Map.ofEntries(
        Map.entry(ServiceType.REGISTRATION, new RegistrationExecutor()),
        Map.entry(ServiceType.LOGIN, new LoginExecutor()),
        Map.entry(ServiceType.LOGOUT, new LogoutExecutor()),
        Map.entry(ServiceType.LOAD, new LoadExecutor())
    );

    public void execute(HttpRequest request, HttpResponse response) {
        ServiceType serviceType = request.getServiceType();
        Executor executor = executorMap.get(serviceType);
        executor.service(request, response);
    }
}
