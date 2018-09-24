package models.DTO;

import lombok.Getter;
import lombok.Setter;
import models.businessLogic.FlightInfoModel;
import java.util.List;

@Getter
@Setter
public class CrawlDTO {
    private String message;
    private boolean success;
    private List<FlightInfoModel> flightInfoModelList;

    public CrawlDTO(String Message, boolean Success, List<FlightInfoModel> FlightInfoModelList) {
        message = Message;
        success = Success;
        flightInfoModelList = FlightInfoModelList;
    }
}
