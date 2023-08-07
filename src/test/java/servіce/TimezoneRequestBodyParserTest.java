package servіce;

import model.StateTimezone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class TimezoneRequestBodyParserTest {
    private TimezoneRequestBodyParser timezoneRequestBodyParser;

    @BeforeEach
    public void get() {
        timezoneRequestBodyParser = new TimezoneRequestBodyParser();
    }
    private HttpServletRequest getHttpServletReques(String timeZona) throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        BufferedReader reader = new BufferedReader(new StringReader(timeZona));
        when(request.getReader()).thenReturn(reader);
        return request;
    }

    @Test
    void parsRequest_WhenTimeZoneIsEmpty_ReturnModelRequestWithEmptyNameTimezone() throws IOException {
        assertEquals("",
                timezoneRequestBodyParser
                        .parsRequest(getHttpServletReques("ZoneName="))
                        .getModelRequest().getNameTimezone());

    }

    @Test
    void parsRequest_WhenTimeZoneIsEmpty_ReturnModelRequestWithStateABSENT() throws IOException {
        assertEquals(StateTimezone.ABSENT,
                timezoneRequestBodyParser
                        .parsRequest(getHttpServletReques("ZoneName="))
                        .getModelRequest().getState());
    }

    @Test
    void parsRequest_WhenTimeZoneIsNotValid_ReturnModelRequestWithErrorMessage() throws IOException {
        assertEquals("time zone does not exist", timezoneRequestBodyParser
                .parsRequest(getHttpServletReques("ZoneName=Ukraine"))
                .getModelRequest().getNameTimezone());
    }

    @Test
    void parsRequest_WhenTimeZoneIsNotValid_ReturnModelRequestWithStateINVALID() throws IOException {
        assertEquals(StateTimezone.ABSENT,
                timezoneRequestBodyParser
                        .parsRequest(getHttpServletReques(""))
                        .getModelRequest().getState());
    }

    @Test
    void parsRequest_WhenTimeZoneIsValid_ReturnModelRequestWithNameTimeZone() throws IOException {
        assertEquals("Iran", timezoneRequestBodyParser
                .parsRequest(getHttpServletReques("ZoneName=Iran"))
                .getModelRequest().getNameTimezone());
    }


    @Test
    void parsRequest_WhenTimeZoneIsValid_ReturnModelRequestWithStateVALID() throws IOException {
        assertEquals(StateTimezone.VALID,
                timezoneRequestBodyParser
                        .parsRequest(getHttpServletReques("ZoneName=Iran"))
                        .getModelRequest().getState());
    }
}