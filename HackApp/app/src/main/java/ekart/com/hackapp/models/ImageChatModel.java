package ekart.com.hackapp.models;

import lombok.Data;

/**
 * Created by brinder.singh on 22/06/17.
 */
@Data
public class ImageChatModel extends ChatModel {
    private String url;

    public ImageChatModel(WHO who, String url) {
        super(who, IMAGE_LIST);
        this.url = url;
    }
}
