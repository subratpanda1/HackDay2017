package ekart.com.hackapp.networkHandler;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.models.AddProductResponse;
import ekart.com.hackapp.models.Category;
import ekart.com.hackapp.models.ItemDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by subrat.panda on 22/06/17.
 */

public interface NetworkHandlerInterface {
    @GET("/goliathus/category/list")
    Call<List<Category>> getCategories();

    @GET("/goliathus/category/{category}")
    Call<List<ItemDetail>> getProductsForCategory(@Path("category") String category);

    @POST("goliathus/product/{product}/add/{inputType}")
    Call<AddProductResponse> addProduct(@Path("product") String product, @Path("inputType") String inputType);

    @GET("goliathus/search/{inputType}/{productName}")
    Call<List<ItemDetail>> searchProduct(@Path("inputType") String inputType, @Path("productName") String productName);
}
