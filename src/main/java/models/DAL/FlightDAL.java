package models.DAL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDAL {
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
    private String cheapestPrice;
}
