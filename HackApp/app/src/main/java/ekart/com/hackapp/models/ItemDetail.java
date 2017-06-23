package ekart.com.hackapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class ItemDetail implements Parcelable{
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

    public ItemDetail() {
    }

    protected ItemDetail(Parcel in) {
        imageUrl = in.readString();
        name = in.readString();
        level0Category = in.readString();
        level1Category = in.readString();
        tags = in.createStringArrayList();
        brand = in.readString();
    }

    public static final Creator<ItemDetail> CREATOR = new Creator<ItemDetail>() {
        @Override
        public ItemDetail createFromParcel(Parcel in) {
            return new ItemDetail(in);
        }

        @Override
        public ItemDetail[] newArray(int size) {
            return new ItemDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUrl);
        parcel.writeString(name);
        parcel.writeString(level0Category);
        parcel.writeString(level1Category);
        parcel.writeStringList(tags);
        parcel.writeString(brand);
    }

    public enum ItemType {
        CATEGORY,
        PRODUCT
    }

    private transient ItemType itemType = ItemType.PRODUCT;
}
