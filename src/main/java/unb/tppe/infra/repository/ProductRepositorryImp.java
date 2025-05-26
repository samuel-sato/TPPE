package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.respository.ProductRepository;
import unb.tppe.infra.mapping.ProductMapper;
import unb.tppe.infra.schema.DepartmentSchema;
import unb.tppe.infra.schema.ProductSchema;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepositorryImp implements ProductRepository, PanacheRepository<ProductSchema> {

    private ProductMapper mapper;
    private DepartmentRepositoryImp departmentRepository;

    public ProductRepositorryImp(DepartmentRepositoryImp departmentRepository, ProductMapper mapper){
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }


    @Transactional
    public Product create(Product entity) {

        Optional<DepartmentSchema> departmentSchema = departmentRepository.listSchemaById(entity.getIdDepartment());

        if (!departmentSchema.isPresent())
            throw new RuntimeException("Departamento não encontrado");

        ProductSchema schema = mapper.toSchema(entity);
        schema.setDepartment(departmentSchema.get());
        persist(schema);
        return mapper.toDomain(schema);
    }


    public Optional<Product> listById(Long id) {
        Optional<ProductSchema> schemaOptional = findByIdOptional(id);

        return schemaOptional.map(productSchema -> mapper.toDomain(productSchema));
    }


    public List<Product> listAllEntity() {
        List<ProductSchema> ProductSchemas = listAll();
        return ProductSchemas.stream().map(mapper::toDomain).toList();
    }

    public List<ProductSchema> listByIdList(List<Long> ids){
        return list("id in ?1", ids);
    }


    @Transactional
    public Product update(Product entity) {
        ProductSchema schema = findById(entity.getId());

        schema.setName(entity.getName());
        schema.setDescription(entity.getDescription());
        schema.setPrice(entity.getPrice());
        persist(schema);
        return mapper.toDomain(schema);
    }


    @Transactional
    public boolean deleteEntity(long id) {
        return deleteById(id);
    }
}
