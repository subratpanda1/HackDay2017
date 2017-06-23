package ekart.com.hackapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ekart.com.hackapp.R;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class SwipeFragment extends BaseFragment {
    public static final String URL = "url";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    private String url;
    private String name;
    private String price;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
        ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageViewChatItem);
        TextView textView = (TextView) swipeView.findViewById(R.id.textViewChatItem);
        TextView textView1 = (TextView) swipeView.findViewById(R.id.textViewChatItem1);
        LinearLayout linearLayout = (LinearLayout) swipeView.findViewById(R.id.llChatViewItem1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Bundle bundle = getArguments();
        url = bundle.getString(URL);
        name = bundle.getString(NAME);
        price = bundle.getString(PRICE);

        if (url == null) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(url)
                    .noFade()
                    .into(imageView);
        }

        if (name == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(name);
        }

        if (price == null) {
            textView1.setVisibility(View.GONE);
        } else {
            textView1.setVisibility(View.VISIBLE);
            textView1.setText(price);
        }

        return swipeView;
    }

    public static SwipeFragment newInstance(String url, String name, String price) {
        SwipeFragment swipeFragment = new SwipeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(NAME, name);
        bundle.putString(PRICE, price);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }
}
