package ekart.com.hackapp.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import ekart.com.hackapp.fragments.BaseDialogFragment;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class FragmentUtils {
    public enum FragmentTag {
    }

    public static void showCustomDialog(BaseDialogFragment customDialogFragment, FragmentManager fragmentManager) {
        if (!customDialogFragment.isShowing()) {
            customDialogFragment.setIsShowing(true);
            customDialogFragment.show(fragmentManager, null);
        }
    }

    public static void createFragment(int containerId, Fragment fragment, Bundle bundle,
                                      FragmentManager fragmentManager, FragmentTag fragmentTag) {
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(containerId, fragment, fragmentTag.name()).commit();
    }

    public static void replaceFragment(int containerId, Fragment fragment, Bundle bundle,
                                       FragmentManager fragmentManager, FragmentTag fragmentTag) {
        Fragment myFragment = fragmentManager.findFragmentByTag(fragmentTag.name());
        if (myFragment == null || !myFragment.isVisible()) {
            myFragment = fragment;
            myFragment.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction = transaction.replace(containerId, myFragment, fragmentTag.name());
            transaction = transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /**
     * Should be called with even values where odd value should be key and even should be value
     */
    public static Bundle getBundle(String... args) {
        Bundle bundle = new Bundle();
        if (args.length % 2 == 0) {
            for (int i = 0; i < args.length; i += 2) {
                bundle.putString(args[i], args[i + 1]);
            }
        }
        return bundle;
    }

    public static Bundle getBundle(String key, Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(key, parcelable);
        return bundle;
    }
}
