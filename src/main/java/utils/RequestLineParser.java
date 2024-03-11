package utils;

public class RequestLineParser {

    private static final int MIN_REQUEST_TOKEN_COUNT = 2;
    private static final int PATH_TOKEN_ORDER = 1;
    public static final String INVALID_REQUEST_LINE = "Invalid request line: ";

    public static String extractPath(String requestLine) {
        // HTTP 요청 라인에서 공백을 기준으로 분리
        String[] tokens = validateRequestLine(requestLine);
        // 두 번째 토큰이 경로이므로 반환
        return tokens[PATH_TOKEN_ORDER];
    }

    private static String[] validateRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        if (tokens.length < MIN_REQUEST_TOKEN_COUNT) {
            throw new IllegalArgumentException(INVALID_REQUEST_LINE + requestLine);
        }
        return tokens;
    }
}