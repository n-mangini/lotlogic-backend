package app.payment;

import app.payment.dto.PaymentOrderInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/mercadopago")
public class MercadoPagoController {
    final MercadoPagoService mercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping(path = "/payment", consumes = {"application/json"})
    public Mono<ResponseEntity<String>> createPaymentOrder(@RequestBody final PaymentOrderInfo paymentOrderInfo) {
        return this.mercadoPagoService.makePaymentOrderRequest(paymentOrderInfo)
                .map(qr_data -> ResponseEntity.status(HttpStatus.CREATED).body(qr_data))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @DeleteMapping(path = "/cancel-payment")
    public Mono<ResponseEntity<Object>> createPaymentOrder() {
        return this.mercadoPagoService.makeCancelOrderRequest()
                .thenReturn(ResponseEntity.ok().build())
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
}
