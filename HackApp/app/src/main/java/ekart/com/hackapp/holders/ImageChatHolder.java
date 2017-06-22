package ekart.com.hackapp.holders;

import android.view.View;
import android.widget.ImageButton;

import ekart.com.hackapp.R;
import ekart.com.hackapp.models.ChatModel;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class ImageChatHolder extends BaseChatViewHolder {
    private ImageButton imageButton;

    public ImageChatHolder(View itemView) {
        super(itemView);
        imageButton = (ImageButton) itemView.findViewById(R.id.imageViewChat);
    }

    @Override
    public void bind(ChatModel chatModel){
    }

}
