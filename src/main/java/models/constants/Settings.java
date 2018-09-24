package models.constants;

import org.omg.CORBA.TIMEOUT;

public final class Settings {
    public final static String NORWEGIAN_WEB_PAGE_WITH_PATH = "https://www.norwegian.com/uk/ipc/availability/avaday?";
    public final static long MILLISECONDS_IN_ONE_DAY = 86400000;
    public final static String CONNECTIONS_WAS_SUCCESSFUL = "Connection to database and to website was successful";
    public final static String PLAINS_NOT_FLYING_TODAY = "Plains are not flying today";
    public final static int TIMEOUT = 100000;
    public final static String DB_CONNECTION_URL = "jdbc:sqlserver://localhost;" +
            "databaseName=FightDBB;user=test;password=Asdasd123";
}
