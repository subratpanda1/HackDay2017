package ekart.com.hackapp.models;

import lombok.Data;

/**
 * Created by brinder.singh on 23/06/17.
 */
@Data
public class SlotDiscount {
    private final Slot slot;
    private final int discount;
    private final double amount;

    @Data
    public class Slot {
        private Long id;
        private String day;
        private String timeSlot;

        @Override
        public String toString() {
            return day + ", " + timeSlot;
        }
    }

    @Override
    public String toString() {
        return slot == null ? "Error" : slot.toString();
    }
}
