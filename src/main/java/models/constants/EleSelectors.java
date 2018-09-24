package models.constants;

/**
 * This class contains selects that are needed to get element info from html document
 */
public enum EleSelectors {

    //Main table of flights
    TABLE_OF_FLIGHTS("avadaytable"),

    //ROWINFO1, ROWINFO2, LASTROW these 3 rows in html document contains all info
    //about one flight and all flights have the same rows.
    //These elements selectors can be found in tableOfFlights
    ROWINFO1("tbody .rowinfo1"),
    ROWINFO2("tbody .rowinfo2"),
    LASTROW("tbody .lastrow"),

    //Elements with this selectors below can be found in rowInfo1
    DEPARTURE_TIME(".depdest .content"),
    ARRIVAL_TIME(".arrdest .content"),
    LOW_FARE(".fareselect.standardlowfare .label"),
    DIRECT(".duration .content"),

    //Elements with this selectors below can be found in rowInfo2
    DEPARTURE_AIRPORT(".depdest .content"),
    ARRIVAL_AIRPORT(".arrdest .content"),

    //Elements with this selectors below can be found in lastRow
    STOP_INFO(".flightinfolist .TooltipBoxTransit"),
    STOP_INFO2(".flightinfolist .TooltipBoxNightstop");

    private final String selector;

    EleSelectors(String newSelector) {
        selector = newSelector;
    }

    public String get() {
        return selector;
    }

}
