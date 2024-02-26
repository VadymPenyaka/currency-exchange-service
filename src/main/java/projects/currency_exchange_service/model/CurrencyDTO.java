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

}
