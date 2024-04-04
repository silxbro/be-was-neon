package webserver.http.request;

import java.util.Set;

public enum MethodType {
    GET(Set.of(
        ServiceType.LOAD
    )),
    POST(Set.of(
        ServiceType.REGISTRATION,
        ServiceType.LOGIN,
        ServiceType.LOGOUT
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
