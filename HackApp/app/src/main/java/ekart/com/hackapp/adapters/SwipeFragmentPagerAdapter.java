package ekart.com.hackapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ekart.com.hackapp.fragments.SwipeFragment;
import ekart.com.hackapp.models.ItemDetail;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class SwipeFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<ItemDetail> itemDetails = new ArrayList<>();

    public SwipeFragmentPagerAdapter(FragmentManager fm, List<ItemDetail> itemDetails) {
        super(fm);
        this.itemDetails = itemDetails;
    }

    public SwipeFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return itemDetails.size();
    }

    @Override
    public Fragment getItem(int position) {
//        return SwipeFragment.newInstance("http://cdn.grofers.com/app/images/products/normal/pro_318555.jpg", "Detergent", "43");
        return SwipeFragment.newInstance(itemDetails.get(position).getImageUrl(), itemDetails.get(position).getName(), String.valueOf(itemDetails.get(position).getPrice()));
    }
}