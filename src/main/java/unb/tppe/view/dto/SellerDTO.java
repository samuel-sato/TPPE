package unb.tppe.view.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class SellerDTO {
    private String name;
    private String email;
    private LocalDate birthdate;
    public double baseSalary;
    public double numberHours;
}
