package webserver.path;

import business.BusinessExecutor;
import business.RegistrationExecutor;
import java.util.Arrays;

public enum BusinessPath {
    USER_CREATE("/user/create", new RegistrationExecutor()),
    ;

    private final String absolutePath;
    private final BusinessExecutor executor;

    BusinessPath(String absolutePath, BusinessExecutor executor) {
        this.absolutePath = absolutePath;
        this.executor = executor;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public BusinessExecutor getExecutor() {
        return executor;
    }

    public static boolean contains(String absolutePath) {
        for (BusinessPath path : BusinessPath.values()) {
            if (path.absolutePath.equals(absolutePath)) {
                return true;
            }
        }
        return false;
    }

    public static BusinessPath of(String absolutePath) {
        return Arrays.stream(BusinessPath.values())
            .filter(path -> path.absolutePath.equals(absolutePath))
            .findAny().orElseThrow();
    }
}