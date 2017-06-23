package ekart.com.hackapp.networkHandler;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.models.OrderDetail;
import ekart.com.hackapp.models.SlotDiscount;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by brinder.singh on 23/06/17.
 */

public interface SlotApis {

    @GET("/hack/slots/all")
    Call<Map<String, List<SlotDiscount>>> getSlots();

    @GET("/hack/localities")
    Call<List<String>> getLocalities();

    @PUT("hack/slots?locality")
    Call<Long> placeOrder(@Body OrderDetail orderDetail);

}
