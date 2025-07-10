package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.BaseEntity;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.entity.Product;
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
    private ProductRepositorryImp productRepositorryImp;

    public DepartmentRepositoryImp(DepartmentMapper mapper, ProductRepositorryImp productRepositorryImp){
        this.mapper = mapper;
        this.productRepositorryImp = productRepositorryImp;
    }

    @Transactional
    public Department create(Department entity) {
        DepartmentSchema schema = mapper.toSchema(entity);

        //buscar produtos
        if(entity.getProducts() != null){
            List<ProductSchema> productSchemas = productRepositorryImp.listByIdList(
                    entity.getProducts()
                            .stream()
                            .map(BaseEntity::getId)
                            .toList());

            schema.setProducts(productSchemas);
        }
        else
            schema.setProducts(List.of());


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
        return schemas.stream().map(mapper::toDomain).toList();
    }

    @Transactional
    public Department update(Department entity) {
        DepartmentSchema schema = findById(entity.getId());

        schema.setName(entity.getName());
        schema.setDescription(entity.getDescription());

        if (entity.getProducts() != null && !entity.getProducts().isEmpty()) {
            List<ProductSchema> productSchemas = productRepositorryImp.listByIdList(
                    entity.getProducts().stream()
                            .map(Product::getId)
                            .toList()
            );

            // Atualiza a referência bidirecional
            productSchemas.forEach(p -> p.setDepartment(schema));

            schema.setProducts(productSchemas);
        } else {
            // Remove produtos do departamento
            schema.getProducts().forEach(p -> p.setDepartment(null));
            schema.getProducts().clear(); // limpa a lista no schema
        }

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
