package como.isil.mynotes.rest.storage.request;

import como.isil.mynotes.rest.storage.entity.LogInRaw;
import como.isil.mynotes.rest.storage.entity.LogInResponse;
import como.isil.mynotes.rest.storage.entity.fundo.FundoRaw;
import como.isil.mynotes.rest.storage.entity.fundo.FundoResponse;
import como.isil.mynotes.rest.storage.entity.fundo.FundosResponse;

import como.isil.mynotes.rest.storage.entity.programa.ProgramaRaw;
import como.isil.mynotes.rest.storage.entity.programa.ProgramaResponse;
import como.isil.mynotes.rest.storage.entity.programa.ProgramasResponse;
import como.isil.mynotes.rest.storage.entity.visita.EditVisitaRaw;
import como.isil.mynotes.rest.storage.entity.visita.VisitaRaw;
import como.isil.mynotes.rest.storage.entity.visita.VisitaResponse;
import como.isil.mynotes.rest.storage.entity.visita.VisitasResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by em on 8/06/16.
 */
public class ApiClient {

    private static final String TAG = "ApiClient";
    private static final String API_BASE_URL="http://api.backendless.com";

    private static ServicesApiInterface servicesApiInterface;
    private static OkHttpClient.Builder httpClient;


    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            Retrofit.Builder builder =new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            httpClient =new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor());

            Retrofit retrofit = builder.client(httpClient.build()).build();
            servicesApiInterface = retrofit.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {

        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        //v1/users/login
        @POST("/v1/users/login")
        Call<LogInResponse> login(@Body LogInRaw raw);


        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        //v1/data/Fundo
        @GET("/v1/data/Fundo")
        Call<FundosResponse> fundos();


        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        @POST("/v1/data/Fundo")
        Call<FundoResponse> addFundo(@Body FundoRaw raw);


        //v1/data/Programa
        @GET("/v1/data/Programa")
        Call<ProgramasResponse> programas();


        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        @POST("/v1/data/Programa")
        Call<ProgramaResponse> addPrograma(@Body ProgramaRaw raw);





        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        //v1/data/Fundo
        @GET("/v1/data/Visita")
        Call<VisitasResponse> visitas();


        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        @POST("/v1/data/Visita")
        Call<VisitaResponse> addVisita(@Body VisitaRaw raw);




        @Headers({
                "Content-Type: application/json",
                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        @PUT("/v1/data/Visita")
        Call<VisitaResponse> editVisita(@Body EditVisitaRaw raw);

        @Headers({

                "application-id: 4D006B01-79B4-555F-FFFB-11B8906DD600",
                "secret-key: 5D028822-220D-1323-FF00-BE26C7693D00",
                "application-type: REST"
        })
        //Eliminar Visita
        @DELETE("/v1/data/Visita/{object-id}")
        Call<VisitaResponse> deleteVisita(@Path("object-id") String objectId);


    }

    /*private static OkHttpClient.Builder client(){
        if(httpClient==null)httpClient=new OkHttpClient.Builder();
        return httpClient;
    }*/
    private  static  HttpLoggingInterceptor interceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor= new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
