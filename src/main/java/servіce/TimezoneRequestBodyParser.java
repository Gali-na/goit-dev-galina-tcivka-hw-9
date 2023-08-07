package servіce;
import lombok.Getter;
import model.ModelRequest;
import model.StateTimezone;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;


public class TimezoneRequestBodyParser {
    @Getter
    private ModelRequest modelRequest;

    public TimezoneRequestBodyParser() {
        this.modelRequest = new ModelRequest();
    }

    private String readRequest(HttpServletRequest request) {
        String requestBodyString;
        try (BufferedReader reader = new BufferedReader(request.getReader())) {
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            requestBodyString = requestBody.toString();

            throw new RuntimeException(e);
        }
        return requestBodyString;

    }

    public TimezoneRequestBodyParser parsRequest(HttpServletRequest request) {
        String resultReadingRequest = readRequest(request);
        String[] requests = resultReadingRequest.split("=");
        if (requests.length == 1) {
            this.modelRequest.setState(StateTimezone.ABSENT);
            this.modelRequest.setNameTimezone("");
        }
        if (requests.length > 1 && TimeService.checkTimeZoneValidity(requests[1])) {
            this.modelRequest.setNameTimezone(requests[1]);
            this.modelRequest.setState(StateTimezone.VALID);
        }
        return this;
    }
}
