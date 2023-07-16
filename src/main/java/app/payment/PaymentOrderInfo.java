package app.payment;

import java.util.List;

public record PaymentOrderInfo(String external_reference,
                               String title,
                               String description,
                               Integer total_amount,
                               String expiration_date,
                               List<ItemsMP> items) {
}
