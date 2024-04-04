package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);
    private static final String CRLF = "\r\n";

    public void writeResponse(OutputStream out, HttpResponse response) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeHeaders(response, dos);
            writeBody(response.getBody(), dos);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders(HttpResponse response, DataOutputStream dos) throws IOException {
        dos.writeBytes(response.getStatusLine() + CRLF);
        for (String header : response.getHeaders()) {
            dos.writeBytes(header + CRLF);
        }
        dos.writeBytes(CRLF);
    }

    private void writeBody(byte[] body, DataOutputStream dos) throws IOException {
        if (body != null) {
            dos.write(body, 0, body.length);
        }
    }
}