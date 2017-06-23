package ekart.com.hackapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by brinder.singh on 23/06/17.
 */
@Data
@NoArgsConstructor
public class SlotDiscount {
    private Slot slot;
    private Integer discount;
    private Double amount;

    @Data
    @NoArgsConstructor
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
        return slot == null ? "Error" : slot.toString() + " (" + String.valueOf(discount) + "%)";
    }
}
