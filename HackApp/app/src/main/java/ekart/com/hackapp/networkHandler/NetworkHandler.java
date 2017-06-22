package ekart.com.hackapp.networkHandler;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by subrat.panda on 22/06/17.
 */

public class NetworkHandler {
    private static NetworkHandler instance = null;

    public static NetworkHandler getInstance() {
        if (instance == null) instance = new NetworkHandler();
        return instance;
    }

    private NetworkHandler() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-partner.ekartlogistics.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nhIntf = retrofit.create(NetworkHandlerInterface.class);

    }

    private NetworkHandlerInterface nhIntf;

    public Map<String, Long> doHealthCheck() {
        Call<Map<String, Long>> response = nhIntf.listRepos();
        try {
            Response<Map<String, Long>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            return null;
        }
    }

}
