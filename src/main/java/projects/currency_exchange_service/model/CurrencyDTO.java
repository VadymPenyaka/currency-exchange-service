package projects.currency_exchange_service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CurrencyDTO {
    private String shortName;
    private String fullName;
    private int code;
    private double buyRate;
    private double sellRate;
//    @Builder.Default
//    private double availableAmount = 0;
//    @Builder.Default
//    private boolean isAvailable = false;

//    public boolean isAvailable() {
//        return availableAmount>0 ? true : false;
//    }
}
