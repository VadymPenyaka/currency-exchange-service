package projects.currency_exchange_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.currency_exchange_service.entity.Currency;
import projects.currency_exchange_service.model.CurrencyDTO;

import java.util.List;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    List<Currency> findAllByFullName (String name);
}
