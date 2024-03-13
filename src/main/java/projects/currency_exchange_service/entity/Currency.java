package projects.currency_exchange_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Entity
@Table(name = "CURRENCY")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    @Column(nullable = false, length = 45)
    private String shortName;
    @NonNull
    @Column(nullable = false, length = 45)
    private String fullName;
    @NonNull
    @Column(nullable = false, length = 3)
    private String code;
    @NonNull
    @Column(nullable = false)
    private Double buyRate;
    @NonNull
    @Column(nullable = false)
    private Double sellRate;

}
