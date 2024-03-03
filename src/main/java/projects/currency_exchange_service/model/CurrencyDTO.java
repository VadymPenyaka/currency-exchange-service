package projects.currency_exchange_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    private UUID id;

    private String shortName;
    private String fullName;
    private String code;
    private Double buyRate;
    private Double sellRate;

}
