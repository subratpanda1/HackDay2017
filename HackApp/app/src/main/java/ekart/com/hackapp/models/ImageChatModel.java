package ekart.com.hackapp.models;

import java.util.List;

import lombok.Data;

/**
 * Created by brinder.singh on 22/06/17.
 */
@Data
public class ImageChatModel extends ChatModel {
    private List<String> urls;

    public ImageChatModel(WHO who, List<String> urls) {
        super(who, IMAGE_LIST);
        this.urls = urls;
    }
}
