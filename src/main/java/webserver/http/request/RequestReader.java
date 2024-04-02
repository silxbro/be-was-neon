package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DataParser;

public class RequestReader {

    private static final String HEADER_DELIMITER = ":";
    private static final String REQUEST_LINE_HEADER_NAME = "Request Line";

    public HttpRequest readRequest(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        return new HttpRequest(readHeaders(reader), readBody(reader));
    }

    private Map<String, String> readHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        
        String line = reader.readLine();
        headers.put(REQUEST_LINE_HEADER_NAME, line);
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            List<String> headerNameAndValue = DataParser.parseValues(line, HEADER_DELIMITER);
            headers.put(headerNameAndValue.get(0), headerNameAndValue.get(1));
        }
        return headers;
    }

    private String readBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        while (reader.ready()) {
            body.append((char) reader.read());
        }
        return body.toString();
    }
}