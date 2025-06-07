package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.respository.SaleRepository;
import unb.tppe.infra.mapping.SaleMapper;
import unb.tppe.infra.schema.ClientSchema;
import unb.tppe.infra.schema.ProductSchema;
import unb.tppe.infra.schema.SaleSchema;
import unb.tppe.infra.schema.SellerSchema;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SaleRepositoryImp implements SaleRepository, PanacheRepository<SaleSchema> {

    private SaleMapper mapper;
    private ClientRepositoryImp clientRepository;
    private SellerRepositorryImp sellerRepositorry;
    private ProductRepositorryImp productRepositorry;

    public SaleRepositoryImp(ClientRepositoryImp clientRepository, SellerRepositorryImp sellerRepositorry, ProductRepositorryImp productRepositorry, SaleMapper mapper){
        this.mapper = mapper;
        this.clientRepository = clientRepository;
        this.sellerRepositorry = sellerRepositorry;
        this.productRepositorry = productRepositorry;
    }


    @Transactional
    public Sale create(Sale entity) {
        Optional<ClientSchema> clientSchema = clientRepository.listSchemaById(entity.getIdClient());
        Optional<SellerSchema> sellerSchema = sellerRepositorry.findByIdOptional(entity.getIdSeller());
        List<ProductSchema> productSchemas = productRepositorry.listByIdList(entity.getIdProducts());

        if (!clientSchema.isPresent())
            throw new RuntimeException("Cliente não encontrado");

        if (!sellerSchema.isPresent())
            throw new RuntimeException("Vendedor não encontrado");

        SaleSchema schema = mapper.toSchema(entity);

        schema.setDateSale(LocalDate.now());
        schema.setClient(clientSchema.get());
        schema.setSeller(sellerSchema.get());
        schema.setProducts(productSchemas);

        persist(schema);
        return  mapper.toDomain(schema);
    }

    public Optional<Sale> listById(Long id) {
        Optional<SaleSchema> saleSchema = findByIdOptional(id);

        return saleSchema.map(schema -> mapper.toDomain(schema));

    }

    public List<Sale> listAllEntity() {
        List<SaleSchema> saleSchemas = listAll();
        return saleSchemas.stream().map(mapper::toDomain).toList();
    }

    @Transactional
    public Sale update(Sale entity) {
        SaleSchema schema = findById(entity.getId());

        if(schema == null)
            throw new RuntimeException("Venda não encontrada");

        Optional<ClientSchema> clientSchema = clientRepository.listSchemaById(entity.getIdClient());
        Optional<SellerSchema> sellerSchema = sellerRepositorry.findByIdOptional(entity.getIdSeller());
        List<ProductSchema> productSchemas = productRepositorry.listByIdList(entity.getIdProducts());

        if (clientSchema.isEmpty())
            throw new RuntimeException("Cliente não encontrado");

        if (sellerSchema.isEmpty())
            throw new RuntimeException("Vendedor não encontrado");

        schema.setClient(clientSchema.get());
        schema.setSeller(sellerSchema.get());
        schema.setProducts(productSchemas);

        persist(schema);
        return mapper.toDomain(schema);
    }

    @Transactional
    public boolean deleteEntity(long id) {
        return deleteById(id);
    }
}
