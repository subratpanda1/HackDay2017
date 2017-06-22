package ekart.com.hackapp.networkHandler;

import retrofit2.Retrofit;

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
                .build();

        NetworkHandlerInterface nhIntf = retrofit.create(NetworkHandlerInterface.class);

    }

    private NetworkHandlerInterface nhIntf;

    public Object doHealthCheck() {
        return nhIntf.listRepos();
    }

}
