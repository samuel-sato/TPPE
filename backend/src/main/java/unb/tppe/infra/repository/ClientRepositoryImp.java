package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.UserRoleEnum;
import unb.tppe.domain.entity.Client;
import unb.tppe.domain.respository.ClientRepository;
import unb.tppe.infra.Constantes;
import unb.tppe.infra.mapping.ClientMapper;
import unb.tppe.infra.schema.ClientSchema;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientRepositoryImp implements ClientRepository, PanacheRepository<ClientSchema> {

    private ClientMapper mapper;
    private PersonRepositoryImp personRepository;
    
    public ClientRepositoryImp(PersonRepositoryImp personRepository, ClientMapper mapper){
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Client create(Client entity) {
        ClientSchema schema = mapper.toSchema(entity);
        schema.getPersonSchema().setExclusionDate(Constantes.DATA_NULL);
        schema.getPersonSchema().setRole(UserRoleEnum.CLIENT.value);
        personRepository.persist(schema.getPersonSchema());
        persist(schema);
        return mapper.toDomain(schema);
    }

    public Optional<Client> listById(Long id) {
        Optional<ClientSchema> schemaOptional = findByIdOptional(id);

        if (schemaOptional.isPresent()){
            if(schemaOptional.get().getPersonSchema().getExclusionDate().isBefore(Constantes.DATA_02))
                return Optional.of(mapper.toDomain(schemaOptional.get()));
        }


        return Optional.empty();
    }

    public Optional<ClientSchema> listSchemaById(Long id) {
        return findByIdOptional(id);
    }


    public List<Client> listAllEntity() {
        List<ClientSchema> schemas = listAll();

        return schemas.stream().map(mapper::toDomain).toList();
    }

    @Transactional
    public Client update(Client entity) {
        ClientSchema schema = findById(entity.getId());

        schema.setNotifyPromotion(entity.isNotifyPromotion());
        schema.getPersonSchema().setName(entity.getPerson().getName());
        schema.getPersonSchema().setEmail(entity.getPerson().getEmail());
        schema.getPersonSchema().setBirthdate(entity.getPerson().getBirthdate());

        persist(schema);
        return mapper.toDomain(schema);
    }

    @Transactional
    public boolean deleteEntity(long id) {
        //return deleteById(id);
        Optional<ClientSchema> clientSchema = findByIdOptional(id);

        if (clientSchema.isPresent()){
            clientSchema.get().getPersonSchema().setExclusionDate(LocalDate.now());
            persist(clientSchema.get());
            return true;
        }
        return false;
    }

    public Client findByIdPerson(Long idPerson) {
        return mapper.toDomain(list("personSchema.id", idPerson).getFirst());
    }
}
