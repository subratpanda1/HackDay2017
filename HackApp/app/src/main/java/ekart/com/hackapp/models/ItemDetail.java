package ekart.com.hackapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class ItemDetail {
    @SerializedName("id")
    private Long id;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("level0_category")
    private String level0Category;

    @SerializedName("level1_category")
    private String level1Category;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("price")
    private Double price;

    @SerializedName("brand")
    private String brand;
}
