package model;

import lombok.Getter;
import lombok.Setter;

public class ModelRequest {
    @Getter
    @Setter
    private String nameTimezone = "time zone does not exist";
    @Getter
    @Setter
    private StateTimezone state = StateTimezone.INVALID;

}
