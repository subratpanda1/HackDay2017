package ekart.com.hackapp.fragments;

import android.content.DialogInterface;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class BaseDialogFragment extends android.support.v4.app.DialogFragment{
    public boolean isShowing() {
        return isShowing;
    }

    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }

    protected boolean isShowing = false;

    @Override
    public void onDismiss(DialogInterface dialog) {
        isShowing = false;
        super.onDismiss(dialog);
    }
}
