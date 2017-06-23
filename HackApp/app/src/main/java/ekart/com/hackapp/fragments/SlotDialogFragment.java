package ekart.com.hackapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ekart.com.hackapp.AppInfoProvider;
import ekart.com.hackapp.R;
import ekart.com.hackapp.models.SlotDiscount;
import lombok.Setter;

/**
 * Created by brinder.singh on 23/06/17.
 */
@Setter
public class SlotDialogFragment extends BaseDialogFragment {
    private Spinner localitySpinner;
    private Spinner slotSpinner;
    private TextView textView;
    private String selectedKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        localitySpinner = (Spinner) getView().findViewById(R.id.localitySpinner);
        slotSpinner = (Spinner) getView().findViewById(R.id.slotSpinner);
        textView = (TextView) getView().findViewById(R.id.discountMessage);
        final List<String> localitiesList = new ArrayList<String>(AppInfoProvider.INSTANCE.getDiscountMap().keySet());
        ArrayAdapter<String> ladapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, localitiesList);
        localitySpinner.setAdapter(ladapter);
        localitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<SlotDiscount> sadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                        AppInfoProvider.INSTANCE.getDiscountMap().get(localitiesList.get(i)));
                slotSpinner.setAdapter(sadapter);
                selectedKey = localitiesList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        slotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                double amount = AppInfoProvider.INSTANCE.getDiscountMap().get(selectedKey).get(i).getAmount();
                double discount = AppInfoProvider.INSTANCE.getDiscountMap().get(selectedKey).get(i).getDiscount();
                textView.setText("Total order from your locality till now is Rs." + amount
                        + ". Please order in this slot to get " + discount + "% discount");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
