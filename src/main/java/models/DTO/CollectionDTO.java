package models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDTO {
    private String message;
    private boolean success;

    public CollectionDTO(String Message, boolean Success) {
        message = Message;
        success = Success;
    }
}
