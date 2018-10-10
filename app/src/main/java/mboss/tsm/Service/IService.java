package mboss.tsm.Service;

import java.util.List;

import mboss.tsm.Model.Boss;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IService {
    @GET("/boss")
    Call<List<Boss>> getBossListByAccount();
}
