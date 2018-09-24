package servises.helpers;

import models.businessLogic.URLParametersModel;
import models.constants.DefaultURLParameters;
import java.text.SimpleDateFormat;
import java.util.Date;

public class URLParametersModelBuilder {

    private URLParametersModel model;

    public URLParametersModelBuilder() {
        model = new URLParametersModel();
    }


    public URLParametersModelBuilder setDefaultValues() {
        model.setA_City(DefaultURLParameters.A_City);
        model.setAdultCount(DefaultURLParameters.AdultCount);
        model.setChildCount(DefaultURLParameters.ChildCount);
        model.setCurrencyCode(DefaultURLParameters.CurrencyCode);
        model.setD_City(DefaultURLParameters.D_City);
        model.setIncludeTransit(DefaultURLParameters.IncludeTransit);
        model.setInfantCount(DefaultURLParameters.InfantCount);
        model.setTripType(DefaultURLParameters.TripType);
        model.setMode(DefaultURLParameters.mode);
        return this;
    }


    public URLParametersModelBuilder setDepartureTime(Date date) {
        SimpleDateFormat yearAndMonth = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        model.setD_Day(day.format(date));
        model.setD_Month(yearAndMonth.format(date));
        model.setD_SelectedDay(day.format(date));

        return this;
    }


    public URLParametersModel build() {
        return model;
    }
}
