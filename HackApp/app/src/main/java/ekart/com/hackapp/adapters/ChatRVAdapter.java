package ekart.com.hackapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ekart.com.hackapp.R;
import ekart.com.hackapp.holders.BaseChatViewHolder;
import ekart.com.hackapp.holders.ImageChatHolder;
import ekart.com.hackapp.holders.TextChatHolder;
import ekart.com.hackapp.models.ChatModel;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class ChatRVAdapter extends RecyclerView.Adapter<BaseChatViewHolder> {
    private List<ChatModel> chatModels = new ArrayList<>();
    private Context context;

    public ChatRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseChatViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ChatModel.IMAGE_LIST:
                View v1 = inflater.inflate(R.layout.image_chat_holder, parent, false);
                viewHolder = new ImageChatHolder(context, v1);
                break;
            case ChatModel.TYPE_TEXT:
                View v2 = inflater.inflate(R.layout.text_chat_holder, parent, false);
                viewHolder = new TextChatHolder(v2);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    public void addChat(ChatModel chatModel) {
        chatModels.add(chatModel);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseChatViewHolder viewHolder, int position) {
        viewHolder.bind(chatModels.get(position));
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatModels.get(position).getType();
    }
}
