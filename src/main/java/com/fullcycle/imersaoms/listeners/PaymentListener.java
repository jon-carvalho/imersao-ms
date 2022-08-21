package com.fullcycle.imersaoms.listeners;

import com.fullcycle.imersaoms.model.Payment;
import com.fullcycle.imersaoms.model.PubSubMessage;
import com.fullcycle.imersaoms.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener implements InitializingBean {

    private final Sinks.Many<PubSubMessage> sink;
    private final PaymentRepository paymentRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sink.asFlux()
                .delayElements(Duration.ofSeconds(2))
                .subscribe(
                        next -> {
                            log.info("On next message -{}", next.getKey());

                            this.paymentRepository.processPayment(next.getKey(), Payment.PaymentStatus.APPROVED);
                        },
                        error -> {
                            log.info("On pub-sub listener observer error", error);
                        },
                        () -> {
                            log.info("On pub-sub listener complete");
                        }
                );
    }

}
