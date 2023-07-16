package app.payment;

public record ItemsMP(String sku_number,
                      String category,
                      String title,
                      String description,
                      Integer unit_price,
                      Integer quantity,
                      String unit_measure,
                      Integer total_amount) {
}
