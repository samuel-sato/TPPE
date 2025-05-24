package unb.tppe.infra.schema;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SELLER")
public class SellerSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonSchema personSchema;

    private double baseSalary;
    private double numberHours;
}
