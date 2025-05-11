package unb.tppe.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SellerDTO {
    private String name;
    private String email;
    private LocalDate birthdate;
    public double baseSalary;
    public double numberHours;
}
