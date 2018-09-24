package servises;

import models.DTO.CrawlDTO;
import java.util.Date;

public interface ICrawlService {
    CrawlDTO getFlightsByDate(Date date);
}
