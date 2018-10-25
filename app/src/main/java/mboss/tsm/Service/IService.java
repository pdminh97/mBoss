package mboss.tsm.Service;

import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IService {
    @GET("/boss")
    Call<List<Boss>> getBossListByAccount();

    @POST("/token")
    @FormUrlEncoded
    Call<Token> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

}
