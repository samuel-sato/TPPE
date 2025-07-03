package unb.tppe.aplication.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> products;
}
