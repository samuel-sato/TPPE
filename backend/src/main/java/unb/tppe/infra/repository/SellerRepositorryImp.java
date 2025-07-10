package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.UserRoleEnum;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.infra.mapping.SellerMapper;
import unb.tppe.infra.schema.SellerSchema;

import java.time.LocalDate;
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
        schema.getPersonSchema().setExclusionDate(LocalDate.of(0001,01,01));
        schema.getPersonSchema().setRole(UserRoleEnum.SELLER.value);
        personRepository.persist(schema.getPersonSchema());
        persist(schema);
        return mapper.toDomain(schema);
    }


    public Optional<Seller> listById(Long id) {
        Optional<SellerSchema> sellerSchema = findByIdOptional(id);

        if (sellerSchema.isPresent()){
            if(sellerSchema.get().getPersonSchema().getExclusionDate().isBefore(LocalDate.of(0002,01,01)))
            return Optional.of(mapper.toDomain(sellerSchema.get()));
        }


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
        schema.getPersonSchema().setPassword(entity.getPerson().getPassword());

        persist(schema);
        return mapper.toDomain(schema);
    }


    @Transactional
    public boolean deleteEntity(long id) {
        Optional<SellerSchema> sellerSchema = findByIdOptional(id);

        if (sellerSchema.isPresent()){
            sellerSchema.get().getPersonSchema().setExclusionDate(LocalDate.now());
            persist(sellerSchema.get());
            return true;
        }
        return false;

    }
}
