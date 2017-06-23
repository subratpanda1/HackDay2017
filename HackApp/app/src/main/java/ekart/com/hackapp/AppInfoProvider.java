package ekart.com.hackapp;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.models.SlotDiscount;

/**
 * Created by brinder.singh on 23/06/17.
 */

public enum AppInfoProvider {
    INSTANCE;
    private Map<String, List<SlotDiscount>> discountMap;

    public Map<String, List<SlotDiscount>> getDiscountMap() {
        return discountMap;
    }

    public void setDiscountMap(Map<String, List<SlotDiscount>> discountMap) {
        this.discountMap = discountMap;
    }

}
