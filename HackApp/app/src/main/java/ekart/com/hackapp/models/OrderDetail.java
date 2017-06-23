package ekart.com.hackapp.models;

import lombok.Data;

/**
 * Created by brinder.singh on 23/06/17.
 */
@Data
public class OrderDetail {
    Long slotId;
    Long customerId;
    Double orderValue;
    String locality;
}
