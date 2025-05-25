package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.DepartmentSchema;

@ApplicationScoped
public class DepartmentMapper implements Mapping<Department, DepartmentSchema> {

    public DepartmentMapper(){

    }

    public Department toDomain(DepartmentSchema schema) {

        return Department.builder()
                .id(schema.getId())
                .name(schema.getName())
                .description(schema.getDescription())
                .build();
    }

    public DepartmentSchema toSchema(Department entity) {
        return DepartmentSchema.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
