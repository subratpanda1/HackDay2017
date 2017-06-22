package ekart.com.hackapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.ui.AIButton;
import ekart.com.hackapp.R;
import ekart.com.hackapp.adapters.ChatRVAdapter;
import ekart.com.hackapp.fsm.MyFSM;
import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.models.ChatModel;
import ekart.com.hackapp.models.TextChatModel;
import ekart.com.hackapp.sample.Config;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class ChatFragment extends BaseFragment implements AIButton.AIButtonListener {
    private RecyclerView recyclerViewChat;
    private ChatRVAdapter chatRVAdapter;
    private AIButton aiButton;

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

        aiButton = (AIButton) getView().findViewById(R.id.micButton);

        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        config.setRecognizerStartSound(getResources().openRawResourceFd(R.raw.test_start));
        config.setRecognizerStopSound(getResources().openRawResourceFd(R.raw.test_stop));
        config.setRecognizerCancelSound(getResources().openRawResourceFd(R.raw.test_cancel));

        aiButton.initialize(config);
        aiButton.setResultsListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        aiButton.resume();
        for (int i = 0; i < 800; i++) {
            chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.USER, "Hidsdasdasdasdasdssadasdasdasdasdas " + i));
            chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, "Hellodasdsadasdasdasdasdasdasdasdasdasdasd " + i * 2));
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
    public void onResult(final AIResponse result) {
        io.reactivex.Observable.create(new ObservableOnSubscribe<State>() {
            @Override
            public void subscribe(ObservableEmitter<State> e) throws Exception {
                chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, result.getResult().getResolvedQuery()));
                if (chatRVAdapter.getItemCount() > 1)
                {
                    recyclerViewChat.getLayoutManager().smoothScrollToPosition(recyclerViewChat, null, chatRVAdapter.getItemCount() - 1);
                }
                e.onNext(MyFSM.getInstance().handleEvent(result.getResult().getFulfillment().getSpeech()));
            }
        }).subscribeOn(Schedulers.computation())
                .subscribe(new Observer<State>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(State state) {
                        System.out.println("Got state: " + state.stateEntity.data.toString());
                        chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, state.stateEntity.data.toString()));
                        if (chatRVAdapter.getItemCount() > 1)
                        {
                            recyclerViewChat.getLayoutManager().smoothScrollToPosition(recyclerViewChat, null, chatRVAdapter.getItemCount() - 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPause() {
        super.onPause();
        aiButton.pause();
    }

}
