package unb.tppe.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "SELLER")
public class Seller extends PanacheEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    public Person person;

    public double baseSalary;
    public double numberHours;
}
