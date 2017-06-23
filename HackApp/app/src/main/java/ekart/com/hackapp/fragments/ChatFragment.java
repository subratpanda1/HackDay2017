package ekart.com.hackapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.ui.AIButton;
import ekart.com.hackapp.ChatViewModel;
import ekart.com.hackapp.R;
import ekart.com.hackapp.adapters.ChatRVAdapter;
import ekart.com.hackapp.fsm.InputType;
import ekart.com.hackapp.fsm.MyFSM;
import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.models.ChatModel;
import ekart.com.hackapp.models.ImageChatModel;
import ekart.com.hackapp.models.ItemDetail;
import ekart.com.hackapp.models.TextChatModel;
import ekart.com.hackapp.sample.Config;
import ekart.com.hackapp.sample.TTS;
import ekart.com.hackapp.utils.FragmentUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by brinder.singh on 22/06/17.
 */

public class ChatFragment extends BaseFragment implements AIButton.AIButtonListener {
    private RecyclerView recyclerViewChat;
    private ChatRVAdapter chatRVAdapter;
    private AIButton aiButton;
    private CompositeDisposable subscription;


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
        chatRVAdapter = new ChatRVAdapter(getActivity());
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
        bind();
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
                chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.USER, result.getResult().getResolvedQuery()));
                chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, "Processed: " + result.getResult().getFulfillment().getSpeech()));
                if (chatRVAdapter.getItemCount() > 1) {
                    recyclerViewChat.getLayoutManager().smoothScrollToPosition(recyclerViewChat, null, chatRVAdapter.getItemCount() - 1);
                }
                e.onNext(MyFSM.getInstance().handleEvent(result.getResult().getFulfillment().getSpeech().toUpperCase(), InputType.VOICE));
            }
        }).subscribeOn(Schedulers.computation())
                .subscribe(new Observer<State>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(State state) {
                        List<ItemDetail> itemDetailList = (List<ItemDetail>) state.getStateEntity().getData();
                        SwipeDialogFragment swipeDialogFragment = new SwipeDialogFragment();
                        swipeDialogFragment.setItemDetails(itemDetailList);
                        FragmentUtils.showCustomDialog(swipeDialogFragment, getChildFragmentManager());
                        adjustRV();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String err = "Sorry, didn't get you.";
                        chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, err));
                        TTS.speak(err);
                        adjustRV();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void adjustRV() {
        if (chatRVAdapter.getItemCount() > 1) {
            recyclerViewChat.getLayoutManager().smoothScrollToPosition(recyclerViewChat, null, chatRVAdapter.getItemCount() - 1);
        }
    }

    String nextQuestion;
    private void handleCommand(final String command, final InputType inputType) {
        io.reactivex.Observable.create(new ObservableOnSubscribe<State>() {
            @Override
            public void subscribe(ObservableEmitter<State> e) throws Exception {
                e.onNext(MyFSM.getInstance().handleEvent(command, inputType));
            }
        }).subscribeOn(Schedulers.computation())
                .subscribe(new Observer<State>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(State state) {
                        nextQuestion = state.getStateEntity().getNextQuestion();
                        List<ItemDetail> itemDetailList = (List<ItemDetail>) state.getStateEntity().getData();
                        SwipeDialogFragment swipeDialogFragment = new SwipeDialogFragment();
                        swipeDialogFragment.setItemDetails(itemDetailList);
                        FragmentUtils.showCustomDialog(swipeDialogFragment, getChildFragmentManager());
                        if (chatRVAdapter.getItemCount() > 1) {
                            recyclerViewChat.getLayoutManager().smoothScrollToPosition(recyclerViewChat, null, chatRVAdapter.getItemCount() - 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String err = "There was some issue in processing your command.";
                        chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, err));
                        TTS.speak(err);
                        adjustRV();
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
        unbind();
    }

    void bind(){
        unbind();
        subscription = new CompositeDisposable();
        subscription.add(ChatViewModel.INSTANCE.getItemDetailPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ItemDetail>() {
                    @Override
                    public void accept(@NonNull ItemDetail itemDetail) throws Exception {
                        if(itemDetail.getItemType()== ItemDetail.ItemType.CATEGORY) {
                            handleCommand("SHOW PRODUCTS " + itemDetail.getName(), InputType.CLICK);
                        }
                        if(itemDetail.getItemType()== ItemDetail.ItemType.PRODUCT) {
                            handleCommand("ADD PRODUCT " + itemDetail.getName(), InputType.CLICK);
                            chatRVAdapter.addChat(new TextChatModel(ChatModel.WHO.COMPUTER, "Item added"));
                            TTS.speak("Item added");
                            chatRVAdapter.addChat(new ImageChatModel(ChatModel.WHO.COMPUTER, itemDetail.getImageUrl()));
                            adjustRV();
                        }
                    }
                }));
    }

    void unbind(){
        if(subscription != null) {
            subscription.dispose();
        }
    }

}
