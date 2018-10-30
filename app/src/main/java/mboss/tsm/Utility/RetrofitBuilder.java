package mboss.tsm.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static Retrofit retrofit;

    public static Retrofit getBuilder(String baseURL) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().
                    baseUrl(baseURL).
                    addConverterFactory(GsonConverterFactory.create(gson)).
                    client(okHttpClient).
                    build();
        }
        return retrofit;
    }
}
