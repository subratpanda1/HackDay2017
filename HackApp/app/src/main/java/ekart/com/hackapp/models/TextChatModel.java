package ekart.com.hackapp.models;

import lombok.Data;

/**
 * Created by brinder.singh on 22/06/17.
 */
@Data
public class TextChatModel extends ChatModel {
    private String text;

    public TextChatModel(WHO who, String text) {
        super(who, TYPE_TEXT);
        this.text = text;
    }
}
