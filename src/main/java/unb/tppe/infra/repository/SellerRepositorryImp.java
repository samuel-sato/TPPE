package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.infra.mapping.SellerMapper;
import unb.tppe.infra.schema.SellerSchema;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SellerRepositorryImp implements SellerRepository, PanacheRepository<SellerSchema> {

    private SellerMapper mapper;
    private PersonRepositoryImp personRepository;

    public SellerRepositorryImp(PersonRepositoryImp personRepository, SellerMapper mapper){
        this.personRepository = personRepository;
        this.mapper = mapper;
    }


    @Transactional
    public Seller create(Seller entity) {
        SellerSchema schema = mapper.toSchema(entity);
        personRepository.persist(schema.getPersonSchema());
        persist(schema);
        return mapper.toDomain(schema);
    }


    public Optional<Seller> listById(Long id) {
        Optional<SellerSchema> sellerSchema = findByIdOptional(id);

        if (sellerSchema.isPresent())
            return Optional.of(mapper.toDomain(sellerSchema.get()));

        return Optional.empty();
    }


    public List<Seller> listAllEntity() {
        List<SellerSchema> sellerSchemas = listAll();
        List<Seller> sellers = sellerSchemas.stream().map(mapper::toDomain).toList();
        return sellers;
    }


    @Transactional
    public Seller update(Seller entity) {
        SellerSchema schema = findById(entity.getId());

        schema.setBaseSalary(entity.getBaseSalary());
        schema.setNumberHours(entity.getNumberHours());
        schema.getPersonSchema().setName(entity.getPerson().getName());
        schema.getPersonSchema().setEmail(entity.getPerson().getEmail());
        schema.getPersonSchema().setBirthdate(entity.getPerson().getBirthdate());

        persist(schema);
        return mapper.toDomain(schema);
    }


    @Transactional
    public boolean deleteEntity(long id) {
        return deleteById(id);
    }
}
