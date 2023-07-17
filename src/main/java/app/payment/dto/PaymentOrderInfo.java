package app.payment.dto;

import app.payment.ItemsMP;

import java.util.List;

public record PaymentOrderInfo(String external_reference,
                               String title,
                               String description,
                               Integer total_amount,
                               List<ItemsMP> items) {
}
