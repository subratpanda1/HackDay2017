package ekart.com.hackapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import ekart.com.hackapp.fragments.SwipeFragment;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class SwipeFragmentPagerAdapter extends FragmentPagerAdapter {


    public SwipeFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        return SwipeFragment.newInstance("http://cdn.grofers.com/app/images/products/normal/pro_318555.jpg", "Detergent", "43");
    }
}