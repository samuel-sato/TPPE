package unb.tppe.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Departament extends BaseEntity{
    private String name;
    private String description;
}
