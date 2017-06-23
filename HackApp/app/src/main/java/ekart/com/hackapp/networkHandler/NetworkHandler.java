package ekart.com.hackapp.networkHandler;

import android.content.ClipData;
import android.util.Log;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.fsm.InputType;
import ekart.com.hackapp.models.ItemDetail;
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
                .baseUrl("http://172.20.168.100:8080")
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
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

    public List<String> getCategories() {
        Call<List<String>> response = nhIntf.getCategories();
        try {
            Response<List<String>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

    public List<ItemDetail> getItems(String category) {
        Call<List<ItemDetail>> response = nhIntf.getItems(category);
        try {
            Response<List<ItemDetail>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

    public List<ItemDetail> searchProduct(String itemName, InputType inputType) {
        String inputTypeStr = (inputType == InputType.VOICE) ? "voice" : "text";
        Call<List<ItemDetail>> response = nhIntf.searchProduct(inputTypeStr, itemName);
        try {
            Response<List<ItemDetail>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

}
