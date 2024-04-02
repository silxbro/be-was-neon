package webserver.http.request;

import java.util.Arrays;

public enum ServiceType {

    REGISTRATION("/user/create"),
    LOGIN("/user/login"),
    LOGOUT("/user/logout"),
    USER_LIST("/user/list"),

    LOAD(""),
    ;

    private final String absolutePath;

    ServiceType(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public static ServiceType of(String targetPath) {
        return Arrays.stream(ServiceType.values())
            .filter(ServiceType -> ServiceType.absolutePath.equals(targetPath))
            .findAny().orElse(LOAD);
    }

}
