package ekart.com.hackapp.models;

import lombok.Data;

/**
 * Created by brinder.singh on 22/06/17.
 */
@Data
public class ChatModel {
    public enum WHO {
        USER, COMPUTER
    }

    public ChatModel(WHO who, int type) {
        this.who = who;
        this.type = type;
    }

    public static final int TYPE_TEXT = 0;
    public static final int IMAGE_LIST = 1;

    protected WHO who;
    protected int type;
}
