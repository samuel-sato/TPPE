package unb.tppe.domain.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.time.LocalDate;

@Entity
@Table(name = "PERSON")
public class Person extends PanacheEntity {
    public String name;
    public String email;
    public LocalDate birthdate;
}
