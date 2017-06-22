package ekart.com.hackapp.networkHandler;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by subrat.panda on 22/06/17.
 */

public interface NetworkHandlerInterface {
    @GET("/healthcheck")
    Call<Object> listRepos();
}
