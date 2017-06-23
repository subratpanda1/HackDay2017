package ekart.com.hackapp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import ekart.com.hackapp.R;
import ekart.com.hackapp.adapters.SwipeFragmentPagerAdapter;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class SwipeDialogFragment extends BaseDialogFragment {
    private ViewPager viewPager;
    private SwipeFragmentPagerAdapter swipeFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.chat_item_pager);
        swipeFragmentPagerAdapter = new SwipeFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(swipeFragmentPagerAdapter);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        SettingsViewModel.INSTANCE.publishSettingsSaveEvent(false);
        super.onCancel(dialog);
    }
}
