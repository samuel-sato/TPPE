package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.ProductDTO;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.respository.ProductRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.util.List;

@ApplicationScoped
public class ProductSerivce {

    private CreateBaseUseCase<Product, ProductRepository> createUseCase;
    private ReadBaseUseCase<Product, ProductRepository> readUseCase;
    private DeleteBaseUseCase<Product, ProductRepository> deleteBseCase;
    private UpdateBaseUseCase<Product, ProductRepository> updateBseCase;


    public ProductSerivce(CreateBaseUseCase<Product, ProductRepository> createUseCase,
                          ReadBaseUseCase<Product, ProductRepository> readUseCase,
                          DeleteBaseUseCase<Product, ProductRepository> deleteBseCase,
                          UpdateBaseUseCase<Product, ProductRepository> updateBseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
        this.deleteBseCase = deleteBseCase;
        this.updateBseCase = updateBseCase;
    }

    public Product create(ProductDTO dto){

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .idDepartment(dto.getIdDepartment())
                .build();

        return createUseCase.execute(product);
    }

    public List<Product> listAll(){
        return readUseCase.listAll();
    }

    public Product findById(Long id){
        return readUseCase.findById(id);
    }

    public Product update(Long id, ProductDTO dto){

        Product product = Product.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .idDepartment(dto.getIdDepartment())
                .build();

        return updateBseCase.execute(id, product);
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }
}
