package utils;

import business.BusinessMapper;
import java.io.File;

public class PathUtils {

    private static final String STATIC_RESOURCES_PATH = "src/main/resources/static";
    private static final String COMMON_FILE = "/index.html";

    public static String getStaticPath(String requestPath) {
        File file = new File(STATIC_RESOURCES_PATH + resolvePath(requestPath));
        if (file.isDirectory()) {
            return file.getPath() + COMMON_FILE;
        }
        return file.getPath();
    }

    private static String resolvePath(String requestPath) {
        if (BusinessMapper.isBusiness(requestPath)) {
            return COMMON_FILE;
        }
        return requestPath;
    }
}