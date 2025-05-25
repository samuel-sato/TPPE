package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.respository.DepartmentRepository;
import unb.tppe.infra.mapping.DepartmentMapper;
import unb.tppe.infra.schema.DepartmentSchema;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DepartmentRepositoryImp implements DepartmentRepository, PanacheRepository<DepartmentSchema> {

    private DepartmentMapper mapper;

    public DepartmentRepositoryImp(DepartmentMapper mapper){
        this.mapper = mapper;
    }

    public Department create(Department entity) {
        DepartmentSchema schema = mapper.toSchema(entity);
        persist(schema);
        return mapper.toDomain(schema);
    }

    public boolean removeProductById(Long id) {
        return false;
    }

    public boolean addProductById(Long id) {
        return false;
    }

    public Department update(Department entyty) {
        return null;
    }

    public boolean deleteEntity(long id) {
        return false;
    }


    public Optional<Department> listById(Long id) {
        return Optional.empty();
    }

    public List<Department> listAllEntity() {
        return List.of();
    }
}
