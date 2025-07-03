package unb.tppe.aplication.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class SellerDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthdate;
    public double baseSalary;
    public double numberHours;
}
