package ekart.com.hackapp.networkHandler;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by subrat.panda on 22/06/17.
 */

public interface NetworkHandlerInterface {
    @GET("/healthcheck")
    Call<Map<String, Long>> listRepos();
}
