package models.businessLogic;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Element;

/**
 * This Class contains necessary 3 HTML rows elements in which we can find information about one flight,
 * like arrival time, destination and so on.
 */
@Getter
@Setter
public class FlightHtmlRowsModel {
    private Element rowInfo1;
    private Element rowInfo2;
    private Element lastRow;

    public FlightHtmlRowsModel(Element RowInfo1, Element RowInfo2, Element LastRow) {
        rowInfo1 = RowInfo1;
        rowInfo2 = RowInfo2;
        lastRow = LastRow;
    }
}
