package projects.currency_exchange_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CurrencyRateScheduler {
    @Scheduled(cron = "0 20 12 * * *")
    public void updateCurrenciesRate () {
        System.out.println("123234234 23423");
    }

}
