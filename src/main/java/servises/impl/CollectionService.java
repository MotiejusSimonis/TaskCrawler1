package servises.impl;

import models.DAL.FlightDAL;
import models.DTO.CollectionDTO;
import models.DTO.CrawlDTO;
import models.DTO.DBQueryDTO;
import models.businessLogic.FlightInfoModel;
import models.constants.Settings;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import servises.ICollectionService;
import servises.ICrawlService;
import servises.ICrud;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CollectionService implements ICollectionService {

    private ICrawlService crawlService = new CrawlService();
    private ICrud crud = new Crud();
    private ModelMapper modelMapper = new ModelMapper();

    public CollectionDTO addInfoByDateIntervalToDatabase(Date fromDate, Date toDate) {
        StringBuilder message = new StringBuilder();
        long amountOfDays = TimeUnit.DAYS.convert(
                toDate.getTime() - fromDate.getTime() + Settings.MILLISECONDS_IN_ONE_DAY, TimeUnit.MILLISECONDS);

        int counter = 0;

        List<FlightInfoModel> allDaysCombinedList = new ArrayList<>();
        for (int i = 0; i < amountOfDays; i++) {
            CrawlDTO crawlDTO = crawlService.getFlightsByDate(new Date(fromDate.getTime() + i * Settings.MILLISECONDS_IN_ONE_DAY));
            counter++;
            System.out.println("collection " + counter);
            if (!crawlDTO.isSuccess()) {
                message.append(crawlDTO.getMessage()).append("\n");
                continue;
            }
            List<FlightInfoModel> flightInfoList = sortOutDirectFlights(crawlDTO.getFlightInfoModelList());
            allDaysCombinedList.addAll(flightInfoList);
        }

        List<FlightDAL> flightDALList = changeFlightInfoModelToDAL(allDaysCombinedList);
        for (FlightDAL dal: flightDALList) {
            DBQueryDTO dbQueryDTO = crud.create(dal);
            if (!dbQueryDTO.isSuccess()){
                message.append(dbQueryDTO.getMessage()).append("\n");
            }
        }

        if (!message.toString().isEmpty()) {
            return new CollectionDTO(message.toString(), false);
        }

        return new CollectionDTO(Settings.CONNECTIONS_WAS_SUCCESSFUL, true);
    }


    private List<FlightInfoModel> sortOutDirectFlights(List<FlightInfoModel> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<FlightInfoModel> newList = new ArrayList<>();
        for (FlightInfoModel model: list) {
            if (model.getConnectionAirport() == null) {
                newList.add(model);
            }
        }
        return newList;
    }


    private List<FlightDAL> changeFlightInfoModelToDAL(List<FlightInfoModel> listOfModels) {
        List<FlightDAL> dalList = new ArrayList<>();

        modelMapper.addMappings(new PropertyMap<FlightInfoModel, FlightDAL>() {
            @Override
            protected void configure() {
                map().setCheapestPrice(source.getLowFare());
            }
        });
        for (FlightInfoModel model: listOfModels) {
            dalList.add(modelMapper.map(model, FlightDAL.class));
        }
        return dalList;
    }
}
