package mboss.tsm.Service;

import java.util.List;

import mboss.tsm.Model.Clinic;
import mboss.tsm.Model.Comment;
import mboss.tsm.Model.Service;
import mboss.tsm.Model.Token;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IService {

    @POST("/token")
    @FormUrlEncoded
    Call<Token> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);


    /* Service API */
    @GET("/api/services")
    Call<List<Service>> searchService(@Query("search") String search);
    @GET("/api/services/top_point")
    Call<List<Service>> getTopService();

    @GET("api/services/clinic/{clinicID}")
    Call<List<Service>> getServicesByClinicID(@Path("clinicID") int clinicID);

    /* Comment API */
    @GET("api/comments/{clinicID}")
    Call<List<Comment>> getCommentByClinicID(@Path("clinicID") int clinicID);

    /* Clinic API */
    @GET("api/clinics/{serviceID}")
    Call<Clinic> getClinicByServiceID(@Path("serviceID") int serviceID);
    @GET("api/clinics/{clinicID}")
    Call<Clinic> getClinicByID(@Path("clinicID") int serviceID);
    @GET("api/clinics/top_clinic")
    Call<List<Clinic>> getTopClinic();
    @GET("api/clinics/")
    Call<List<Clinic>> searchClinic(@Query("search") String search);
    @GET("api/clinics/nearby/{coordinate}/{radius}/")
    Call<List<Clinic>> searchNearBy(@Path("coordinate") String coordinate, @Path("radius") float radius);

    /* Image API */
    @GET("Asset/Image/Clinic/{imageName}")
    Call<ResponseBody> getClinicImage(@Path("imageName") String imageName);
}
