package ekart.com.hackapp.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class Category {
    @SerializedName("name")
    private String name;

    @SerializedName("image_url")
    private String imageUrl;
}
