package models.businessLogic;

import lombok.Getter;
import lombok.Setter;

/**
 * This model contains information business are chosen to take from website.
 * Note: Task says: You need to collect departure airport, arrival airport, connection airport,
 * departure time, arrival time, cheapest price and taxes for all flights from OSL (Oslo) to RIX (Riga).
 * So it collect all of it.
 */
@Getter
@Setter
public class FlightInfoModel {
    private String departureAirport;
    private String arrivalAirport;
    private String connectionAirport;
    private String departureTime;
    private String arrivalTime;
    private String lowFare;
}
