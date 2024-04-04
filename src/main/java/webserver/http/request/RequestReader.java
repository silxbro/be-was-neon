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
import webserver.http.body.HttpBody;
import webserver.http.headers.HttpHeaders;
import webserver.http.request.requestline.RequestLine;

public class RequestReader {

    private static final String HEADER_DELIMITER = ":";

    public HttpRequest readRequest(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        return new HttpRequest(readRequestLine(reader), readHeaders(reader), readBody(reader));
    }

    private RequestLine readRequestLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        return new RequestLine(line);
    }

    private HttpHeaders readHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            List<String> headerNameAndValue = DataParser.parseValues(line, HEADER_DELIMITER);
            headerMap.put(headerNameAndValue.get(0), headerNameAndValue.get(1));
        }
        return new HttpHeaders(headerMap);
    }

    private HttpBody readBody(BufferedReader reader) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        while (reader.ready()) {
            bodyBuilder.append((char) reader.read());
        }

        HttpBody body = new HttpBody();
        if (!bodyBuilder.isEmpty()) {
            body.setContent(bodyBuilder.toString());
        }
        return body;
    }
}