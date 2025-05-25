package unb.tppe.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Department extends BaseEntity{
    private String name;
    private String description;
    private List<Product> products;
}
