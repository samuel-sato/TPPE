package unb.tppe.infra.schema;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class ClientSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonSchema personSchema;

    private LocalDate registrationDate;
    private boolean notifyPromotion;
}
