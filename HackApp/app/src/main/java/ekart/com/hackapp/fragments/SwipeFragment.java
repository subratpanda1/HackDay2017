package ekart.com.hackapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ekart.com.hackapp.ChatViewModel;
import ekart.com.hackapp.R;
import ekart.com.hackapp.models.ItemDetail;

/**
 * Created by brinder.singh on 23/06/17.
 */

public class SwipeFragment extends BaseFragment {
    public static final String ITEM = "item";
    private LinearLayout linearLayout;
    private ItemDetail itemDetail;

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
                ChatViewModel.INSTANCE.publishItemDetailSelection(itemDetail);
                ChatViewModel.INSTANCE.publishCloseDialogPublishSubject(true);
            }
        });

        Bundle bundle = getArguments();
        itemDetail = bundle.getParcelable(ITEM);

        if (itemDetail.getImageUrl() == null) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(itemDetail.getImageUrl())
                    .noFade()
                    .into(imageView);
        }

        if (itemDetail.getName() == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(itemDetail.getName());
        }

        if (itemDetail.getPrice() == null) {
            textView1.setVisibility(View.GONE);
        } else {
            textView1.setVisibility(View.VISIBLE);
            textView1.setText(String.valueOf(itemDetail.getPrice()));
        }

        return swipeView;
    }

    public static SwipeFragment newInstance(ItemDetail itemDetail) {
        SwipeFragment swipeFragment = new SwipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ITEM, itemDetail);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }
}
