package models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DBQueryDTO {
    private boolean success;
    private String message;
    private List<List<Object>> list;

    public DBQueryDTO(boolean Success, String Message, List<List<Object>> List) {
        success = Success;
        message = Message;
        list = List;
    }
}
