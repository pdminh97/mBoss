package mboss.tsm.Utility;

import mboss.tsm.Service.IService;

public class APIUtil {
    private static final String BASE_URL = "https://mbosstsm.azurewebsites.net";

    public static IService getIService() {
        return RetrofitBuilder.getBuilder(BASE_URL).create(IService.class);
    }
}
