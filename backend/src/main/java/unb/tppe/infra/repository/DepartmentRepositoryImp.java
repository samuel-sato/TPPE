package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.respository.DepartmentRepository;
import unb.tppe.infra.mapping.DepartmentMapper;
import unb.tppe.infra.schema.DepartmentSchema;
import unb.tppe.infra.schema.ProductSchema;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DepartmentRepositoryImp implements DepartmentRepository, PanacheRepository<DepartmentSchema> {

    private DepartmentMapper mapper;

    public DepartmentRepositoryImp(DepartmentMapper mapper){
        this.mapper = mapper;
    }

    @Transactional
    public Department create(Department entity) {
        DepartmentSchema schema = mapper.toSchema(entity);
        persist(schema);
        return mapper.toDomain(schema);
    }

    public Optional<Department> listById(Long id) {
        Optional<DepartmentSchema> departmentSchema = findByIdOptional(id);

        if(departmentSchema.isPresent()) {
            if (departmentSchema.get().getExclusionDate() == null)
                return Optional.of(mapper.toDomain(departmentSchema.get()));
        }
        return Optional.empty();
    }

    public Optional<DepartmentSchema> listSchemaById(Long id) {
        return findByIdOptional(id);
    }

    public List<Department> listAllEntity() {
        List<DepartmentSchema> schemas = listAll();
        List<Department> departments = schemas.stream().map(mapper::toDomain).toList();
        return departments;
    }

    @Transactional
    public Department update(Department entity) {
        DepartmentSchema schema = findById(entity.getId());

        schema.setName(entity.getName());
        schema.setDescription(entity.getDescription());
        persist(schema);
        return mapper.toDomain(schema);
    }

    @Transactional
    public boolean deleteEntity(long id) {
        Optional<DepartmentSchema> schema = findByIdOptional(id);

        if (schema.isPresent()){
            schema.get().setExclusionDate(LocalDate.now());
            persist(schema.get());
            return true;
        }
        return false;
    }
}
