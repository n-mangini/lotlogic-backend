package app.payment;

import app.payment.dto.PaymentOrderInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class MercadoPagoService {
    private final WebClient webClient;

    private static String ACCESS_TOKEN = "";
    private static String USER_ID = "";
    private static String POS_ID = "";

    public MercadoPagoService() {
        String apiUrl = "https://api.mercadopago.com";
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> makePaymentOrderRequest(PaymentOrderInfo paymentOrderInfo) {
        String url = String.format("/instore/orders/qr/seller/collectors/%s/pos/%s/qrs", USER_ID, POS_ID);
        return this.webClient.put()
                .uri(url, USER_ID, POS_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                .body(BodyInserters.fromValue(paymentOrderInfo))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> makeCancelOrderRequest() {
        String url = String.format("/instore/qr/seller/collectors/%s/pos/%s/orders", USER_ID, POS_ID);
        return this.webClient.delete()
                .uri(url, USER_ID, POS_ID)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                .retrieve()
                .bodyToMono(String.class);
    }
}
