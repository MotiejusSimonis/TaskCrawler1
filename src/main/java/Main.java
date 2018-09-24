import models.DTO.CollectionDTO;
import servises.ICollectionService;
import servises.impl.CollectionService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Main {
    public static void main(String[] args) {

        ICollectionService collectionService = new CollectionService();
        Date fromDate = new GregorianCalendar(2018, Calendar.OCTOBER, 01).getTime();
        Date toDate = new GregorianCalendar(2018, Calendar.OCTOBER, 31).getTime();
        CollectionDTO dto = ((CollectionService) collectionService).addInfoByDateIntervalToDatabase(fromDate, toDate);
    }
}
