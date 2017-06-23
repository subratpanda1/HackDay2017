package ekart.com.hackapp.networkHandler;

import android.util.Log;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.fsm.InputType;
import ekart.com.hackapp.models.Category;
import ekart.com.hackapp.models.ItemDetail;
import ekart.com.hackapp.models.OrderDetail;
import ekart.com.hackapp.models.SlotDiscount;
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

        Retrofit retrofitSlot = new Retrofit.Builder()
                .baseUrl("http://172.20.168.17:30020")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nhIntf1 = retrofitSlot.create(SlotApis.class);
    }

    private NetworkHandlerInterface nhIntf;
    private SlotApis nhIntf1;

    public List<Category> getCategories() {
        Call<List<Category>> response = nhIntf.getCategories();
        try {
            Response<List<Category>> response1 = response.execute();
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

    public List<ItemDetail> getProductsForCategory(String categoryName, InputType inputType) {
        Call<List<ItemDetail>> response = nhIntf.getProductsForCategory(categoryName);
        try {
            Response<List<ItemDetail>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

    public Map<String, List<SlotDiscount>> getSlots() {
        Call<Map<String, List<SlotDiscount>>> response = nhIntf1.getSlots();
        try {
            Response<Map<String, List<SlotDiscount>>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

    public Long placeOrder(OrderDetail orderDetail) {
        try {
            Call<Long> response = nhIntf1.placeOrder(orderDetail);
            Response<Long> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

    public List<String> getLocalities() {
        Call<List<String>> response = nhIntf1.getLocalities();
        try {
            Response<List<String>> response1 = response.execute();
            return response1.body();
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            return null;
        }
    }

}
