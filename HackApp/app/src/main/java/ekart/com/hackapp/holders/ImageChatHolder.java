package ekart.com.hackapp.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ekart.com.hackapp.R;
import ekart.com.hackapp.models.ChatModel;
import ekart.com.hackapp.models.ImageChatModel;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class ImageChatHolder extends BaseChatViewHolder {
    private ImageView imageButton;
    private Context context;

    public ImageChatHolder(Context context, View itemView) {
        super(itemView);
        imageButton = (ImageView) itemView.findViewById(R.id.imageViewChat);
    }

    @Override
    public void bind(ChatModel chatModel){
        Picasso.with(context)
                .load(((ImageChatModel)chatModel).getUrl())
                .noFade()
                .into(imageButton);
    }

}
