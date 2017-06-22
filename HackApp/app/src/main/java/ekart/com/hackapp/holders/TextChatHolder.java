package ekart.com.hackapp.holders;

import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ekart.com.hackapp.R;
import ekart.com.hackapp.models.ChatModel;
import ekart.com.hackapp.models.TextChatModel;
import ekart.com.hackapp.utils.Utils;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class TextChatHolder extends BaseChatViewHolder {
    private TextView text;
    private CardView cardView;

    public TextChatHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.textViewChat);
        cardView = (CardView) itemView.findViewById(R.id.card_view_text_chat);
    }

    @Override
    public void bind(ChatModel chatModel) {
        text.setText(((TextChatModel) chatModel).getText());
        if(chatModel.getWho() == ChatModel.WHO.USER) {
            Utils.setLayoutGravity(cardView, Gravity.START);
        }else {
            Utils.setLayoutGravity(cardView, Gravity.END);
        }
    }

}
