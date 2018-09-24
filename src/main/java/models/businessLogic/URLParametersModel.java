package models.businessLogic;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains URL parameters used in page.
 * Note: its not all website use.
 */
@Getter
@Setter
public class URLParametersModel {
    private String A_City;
    private String AdultCount;
    private String ChildCount;
    private String CurrencyCode;
    private String D_City;
    private String D_Day;
    private String D_Month;
    private String D_SelectedDay;
    private String IncludeTransit;
    private String InfantCount;
    private String R_Day;
    private String R_Month;
    private String R_SelectedDay;
    private String TripType;
    private String mode;
}