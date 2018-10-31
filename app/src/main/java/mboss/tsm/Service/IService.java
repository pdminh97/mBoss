package mboss.tsm.Service;

import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Clinic;
import mboss.tsm.Model.Comment;
import mboss.tsm.Model.Service;
import mboss.tsm.Model.ServiceDetail;
import mboss.tsm.Model.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IService {
    @GET("/boss")
    Call<List<Boss>> getBossListByAccount();

    @POST("/token")
    @FormUrlEncoded
    Call<Token> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);


    /* Service API */
    @GET("/api/services")
    Call<List<Service>> searchService(@Query("search") String search);
    @GET("/api/services/top_point")
    Call<List<Service>> getTopService();


    /* Service Detail API */
    @GET("api/service_details/{serviceID}")
    Call<List<ServiceDetail>> getServiceDetailByServiceID(@Path("serviceID") int serviceID);

    /* Comment API */
    @GET("api/comments/{clinicID}")
    Call<List<Comment>> getCommentByClinicID(@Path("clinicID") int clinicID);

    /* Clinic API */
    @GET("api/clinics/{serviceID}")
    Call<Clinic> getClinicByServiceID(@Path("serviceID") int serviceID);
}
