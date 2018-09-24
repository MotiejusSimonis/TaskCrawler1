package servises.helpers;

import models.businessLogic.URLParametersModel;
import models.constants.Settings;
import java.lang.reflect.Field;

public final class URLBuilder {

    private URLBuilder() {}

    public static String build(URLParametersModel parameters) {
        try {
            StringBuilder url = new StringBuilder();
            url.append(Settings.NORWEGIAN_WEB_PAGE_WITH_PATH);

            Field[] fields = URLParametersModel.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                String name = fields[i].getName();
                String value = String.valueOf(fields[i].get(parameters));
                if (!value.equals("null")) {
                    url.append(name).append("=").append(value).append("&");
                }
            }
            return url.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
