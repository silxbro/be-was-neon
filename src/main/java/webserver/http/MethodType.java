package webserver.http;

import java.util.Set;
import service.ServiceType;

public enum MethodType {
    GET(Set.of(
    )),
    POST(Set.of(
        ServiceType.REGISTRATION,
        ServiceType.LOGIN
    )),
    ;

    private final Set<ServiceType> validServices;

    MethodType(Set<ServiceType> validServices) {
        this.validServices = validServices;
    }

    public boolean isValidService(ServiceType service) {
        return validServices.contains(service);
    }
}
