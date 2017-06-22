package ekart.com.hackapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.ui.AIButton;
import ekart.com.hackapp.R;
import ekart.com.hackapp.adapters.ChatRVAdapter;
import ekart.com.hackapp.models.ChatModel;
import ekart.com.hackapp.models.TextChatModel;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class ChatFragment extends BaseFragment implements AIButton.AIButtonListener{
    private RecyclerView recyclerViewChat;
    private ChatRVAdapter chatRVAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViews();
    }

    private void setUpViews() {
        recyclerViewChat = (RecyclerView) getView().findViewById(R.id.chatRV);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(layoutManager);
        chatRVAdapter = new ChatRVAdapter();
        recyclerViewChat.setAdapter(chatRVAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < 800; i++) {
            chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.USER, "Hidsdasdasdasdasdssadasdasdasdasdas " + i));
            chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, "Hellodasdsadasdasdasdasdasdasdasdasdasdasd " + i*2));
        }
//        chatRVAdapter.addChat(new ImageChatModel(ChatModel.WHO.USER, Arrays.asList("Hi")));
//        chatRVAdapter.addChat(new ImageChatModel(ChatModel.WHO.COMPUTER, Arrays.asList("Hi")));

    }

    public static ChatFragment newInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onCancelled() {

    }
}
