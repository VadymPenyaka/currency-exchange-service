package projects.currency_exchange_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.currency_exchange_service.entity.Currency;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
}
