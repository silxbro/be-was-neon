package webserver.http.request.requestline;

import java.util.List;
import utils.DataParser;

public class RequestLine {

    private static final String VALUE_DELIMITER = " ";
    private static final String TARGET_DELIMITER = "\\?";
    private final String requestLine;

    public RequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public MethodType getMethod() {
        String methodPart = getTokens().get(0);
        return MethodType.valueOf(methodPart);
    }

    public String getAbsolutePath() {
        return getTargetTokens().get(0);
    }

    public String getQuery() {
        if (hasQuery()) {
            return getTargetTokens().get(1);
        }
        return "";
    }

    public String toString() {
        return requestLine;
    }

    private List<String> getTokens() {
        return DataParser.parseValues(requestLine, VALUE_DELIMITER);
    }

    private List<String> getTargetTokens() {
        String target = getTokens().get(1);
        return DataParser.parseValues(target, TARGET_DELIMITER);
    }

    private boolean hasQuery() {
        return getTargetTokens().size() > 1;
    }
}