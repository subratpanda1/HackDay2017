package ekart.com.hackapp.utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class Utils {
    public static void setLayoutGravity(CardView cardView, int gravity) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;
        if(gravity== Gravity.END){
            params.setMarginStart(100);
            params.setMarginEnd(10);
        }else {
            params.setMarginStart(10);
            params.setMarginEnd(100);
        }
        params.topMargin = 50;
        params.bottomMargin = 50;
        cardView.setLayoutParams(params);
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
