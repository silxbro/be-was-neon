package service;

import java.util.HashMap;
import java.util.Map;
import webserver.http.RequestResult;

public class ServiceExecutor {

    private static final Map<ServiceType, Service> executorMap = new HashMap<>();

    static {
        executorMap.put(ServiceType.REGISTRATION, new RegistrationService());
        executorMap.put(ServiceType.LOGIN, new LoginService());
    }

    public RequestResult getExecutionResult(String absolutePath, String parameterData) {
        ServiceType serviceType = ServiceType.of(absolutePath);
        Service serviceExecutor = executorMap.get(serviceType);

        return serviceExecutor.execute(parameterData);
    }
}