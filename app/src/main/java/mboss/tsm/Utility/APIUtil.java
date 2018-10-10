package mboss.tsm.Utility;

import mboss.tsm.Service.IService;

public class APIUtil {
    private static final String BASE_URL = "http://192.168.1.113:3000/";

    public static IService getIService() {
        return RetrofitBuilder.getBulder(BASE_URL).create(IService.class);
    }
}
