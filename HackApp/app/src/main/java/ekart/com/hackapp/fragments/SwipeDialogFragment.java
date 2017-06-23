package ekart.com.hackapp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ekart.com.hackapp.ChatViewModel;
import ekart.com.hackapp.R;
import ekart.com.hackapp.adapters.SwipeFragmentPagerAdapter;
import ekart.com.hackapp.models.ItemDetail;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import lombok.Setter;

/**
 * Created by brinder.singh on 23/06/17.
 */

@Setter
public class SwipeDialogFragment extends BaseDialogFragment {
    private ViewPager viewPager;
    private SwipeFragmentPagerAdapter swipeFragmentPagerAdapter;
    private List<ItemDetail> itemDetails;
    private CompositeDisposable compositeDisposable;

    public SwipeDialogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.chat_item_pager);
        swipeFragmentPagerAdapter = new SwipeFragmentPagerAdapter(getChildFragmentManager(), itemDetails);
        viewPager.setAdapter(swipeFragmentPagerAdapter);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    void bind() {
        unbind();
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(ChatViewModel.INSTANCE.getCloseDialogPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        SwipeDialogFragment.this.dismiss();
                    }
                }));
    }

    void unbind() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        unbind();
    }
}
