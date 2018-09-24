package servises.impl;

import models.DTO.CrawlDTO;
import models.businessLogic.FlightInfoModel;
import models.businessLogic.FlightHtmlRowsModel;
import models.constants.EleSelectors;
import models.constants.Settings;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import servises.ICrawlService;
import servises.helpers.URLParametersModelBuilder;
import servises.helpers.URLBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrawlService implements ICrawlService {

    public CrawlDTO getFlightsByDate(Date date) {
        try {
            String url = URLBuilder.build(new URLParametersModelBuilder().setDefaultValues().setDepartureTime(date).build());
            Document doc = getDOcFromConnection(url);
            if (!isPlaneFlyingToday(doc)) {
                return new CrawlDTO(Settings.PLAINS_NOT_FLYING_TODAY, true, null);
            }
            List<FlightHtmlRowsModel> htmlFlightElements = getListOfHtmlFlightElementsFromDoc(doc);
            List<FlightInfoModel> flightInfoModels = getListOfFlightInfoFromHtmlElements(htmlFlightElements);
            return new CrawlDTO("Amount of flights in day found: " + flightInfoModels.size(), true, flightInfoModels);
        } catch (Exception e) {
            e.printStackTrace();
            return new CrawlDTO(e.getMessage(), false, null);
        }
    }


    private List<FlightHtmlRowsModel> getListOfHtmlFlightElementsFromDoc(Document doc) {
        Elements rowsInfo1 = doc.getElementsByClass(EleSelectors.TABLE_OF_FLIGHTS.get()).first().select(EleSelectors.ROWINFO1.get());
        Elements rowsInfo2 = doc.getElementsByClass(EleSelectors.TABLE_OF_FLIGHTS.get()).first().select(EleSelectors.ROWINFO2.get());
        Elements lastRow = doc.getElementsByClass(EleSelectors.TABLE_OF_FLIGHTS.get()).first().select(EleSelectors.LASTROW.get());

        List<FlightHtmlRowsModel> list = new ArrayList<>();
        for (int i = 0; i < rowsInfo1.size(); i++) {
            list.add(new FlightHtmlRowsModel(rowsInfo1.get(i), rowsInfo2.get(i), lastRow.get(i)));
        }
        return list;
    }


    private List<FlightInfoModel> getListOfFlightInfoFromHtmlElements(List<FlightHtmlRowsModel> list) {
        List<FlightInfoModel> flightInfoModelList = new ArrayList<>();
        for (FlightHtmlRowsModel rowsModel: list) {
            FlightInfoModel flightInfoModel = new FlightInfoModel();
            flightInfoModel.setDepartureTime(rowsModel.getRowInfo1().select(EleSelectors.DEPARTURE_TIME.get()).first().html());
            flightInfoModel.setArrivalTime(rowsModel.getRowInfo1().select(EleSelectors.ARRIVAL_TIME.get()).first().html());
            flightInfoModel.setLowFare(setPriceFromHtmlElements(rowsModel));
            flightInfoModel.setDepartureAirport(rowsModel.getRowInfo2().select(EleSelectors.DEPARTURE_AIRPORT.get()).first().html());
            flightInfoModel.setArrivalAirport(rowsModel.getRowInfo2().select(EleSelectors.ARRIVAL_AIRPORT.get()).first().html());
            flightInfoModel.setConnectionAirport(getConnectionAirportFromHtmlElement(rowsModel));
            flightInfoModelList.add(flightInfoModel);
        }
        return flightInfoModelList;
    }


    /**
     * This method sets values for lowFare field. Because some elements might not exit if
     * that plain seats are sold out. So it checks if element exist.
     */
    private String setPriceFromHtmlElements(FlightHtmlRowsModel rowsModel) {
        if (rowsModel.getRowInfo1().select(EleSelectors.LOW_FARE.get()).html().isEmpty()) {
            return null;
        }
        return rowsModel.getRowInfo1().select(EleSelectors.LOW_FARE.get()).first().html();
    }


    /**
     * This method checks if Direct html element contains innerHtml (innerText) "Direct", if not then it checks
     * StopInfo html element which contains the city of connection Airport and sets it to model;
     */
    private String getConnectionAirportFromHtmlElement(FlightHtmlRowsModel model) {
        if (model.getRowInfo1().select(EleSelectors.DIRECT.get()).first().html().equals("Direct")) {
            return null;
        }
        if (!model.getLastRow().select(EleSelectors.STOP_INFO.get()).isEmpty()) {
            String stopInfo = model.getLastRow().select(EleSelectors.STOP_INFO.get()).first().html();
            return removeUnnecessaryInfo(EleSelectors.STOP_INFO, stopInfo);
        }
        if (!model.getLastRow().select(EleSelectors.STOP_INFO2.get()).isEmpty()) {
            String stopInfo = model.getLastRow().select(EleSelectors.STOP_INFO2.get()).first().html();
            return removeUnnecessaryInfo(EleSelectors.STOP_INFO2, stopInfo);
        }
        return null;
    }


    private String removeUnnecessaryInfo(EleSelectors eletor, String str) {
        if (str == null) {
            return null;
        }
        if (eletor == EleSelectors.STOP_INFO || str.contains("1 stop")) {
            String removedNumbers = str.replaceAll("\\d+", "");
            return removedNumbers.replace(" stop (h m) in ", "");
        }
        if (eletor == EleSelectors.STOP_INFO2) {
            String removedNumbers = str.replaceAll("\\d+", "");
            String cityPlusTimeLeftOvers = removedNumbers.replace("This route has an overnight stop. Departure ", "");
            return cityPlusTimeLeftOvers.replace(" :", "");
        }
        return null;
    }


    private boolean isPlaneFlyingToday(Document doc) {
        if (doc.getElementsByClass(EleSelectors.TABLE_OF_FLIGHTS.get()).isEmpty()) {
            return false;
        }
        return true;
    }


    private Document getDOcFromConnection(String URL) throws IOException {
        return Jsoup.connect(URL)
                .timeout(Settings.TIMEOUT)
                .ignoreHttpErrors(true)
                .method(Connection.Method.GET)
                .get();
    }
}
