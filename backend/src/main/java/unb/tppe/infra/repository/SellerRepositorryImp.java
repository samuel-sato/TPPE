package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.UserRoleEnum;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.infra.Constantes;
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
        schema.getPersonSchema().setExclusionDate(Constantes.DATA_NULL);
        schema.getPersonSchema().setRole(UserRoleEnum.SELLER.value);
        personRepository.persist(schema.getPersonSchema());
        persist(schema);
        return mapper.toDomain(schema);
    }


    public Optional<Seller> listById(Long id) {
        Optional<SellerSchema> sellerSchema = findByIdOptional(id);

        if (sellerSchema.isPresent()){
            if(sellerSchema.get().getPersonSchema().getExclusionDate().isBefore(Constantes.DATA_02))
                return Optional.of(mapper.toDomain(sellerSchema.get()));
        }


        return Optional.empty();
    }


    public List<Seller> listAllEntity() {
        List<SellerSchema> sellerSchemas = listAll();
        return sellerSchemas.stream().map(mapper::toDomain).toList();
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

    public Seller findByIdPerson(Long idPerson) {
        return mapper.toDomain(list("personSchema.id", idPerson).getFirst());
    }
}
