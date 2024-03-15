package method;

import business.Registration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MethodMapper {
    private static final Map<String, Consumer<String>> methodMap = initMethodMapper();

    // 경로와 해당 경로에 매핑될 함수를 추가하는 메서드
    public void addMapping(String path, Consumer<String> method) {
        methodMap.put(path, method);
    }

    public boolean hasMethod(String path) {
        return methodMap.containsKey(path);
    }

    // 주어진 경로에 해당하는 함수를 실행하는 메서드
    public void executeMethod(String path, String data) {
        if (hasMethod(path)) {
            methodMap.get(path).accept(data);
        }
    }

    private static Map<String, Consumer<String>> initMethodMapper() {
        MethodMapper methodMapper = new MethodMapper();
        methodMapper.addMapping("/user/create", Registration::execute);
        return methodMapper;
    }
}
