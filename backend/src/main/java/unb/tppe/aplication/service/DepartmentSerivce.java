package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.DepartmentCreateDTO;
import unb.tppe.aplication.dto.ProductDTO;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.respository.DepartmentRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DepartmentSerivce {

    private CreateBaseUseCase<Department, DepartmentRepository> createUseCase;
    private ReadBaseUseCase<Department, DepartmentRepository> readUseCase;
    private DeleteBaseUseCase<Department, DepartmentRepository> deleteUseCase;
    private UpdateBaseUseCase<Department, DepartmentRepository> updateUseCase;


    public DepartmentSerivce(CreateBaseUseCase<Department, DepartmentRepository> createUseCase,
                             ReadBaseUseCase<Department, DepartmentRepository> readUseCase,
                             DeleteBaseUseCase<Department, DepartmentRepository> deleteUseCase,
                             UpdateBaseUseCase<Department, DepartmentRepository> updateUseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
        this.deleteUseCase = deleteUseCase;
        this.updateUseCase = updateUseCase;
    }

    public Department create(DepartmentCreateDTO dto){

        Department product = Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .products(
                        dto.getProducts()
                        .stream()
                        .map(id -> Product.builder().id(id).build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();

        return createUseCase.execute(product);
    }

    public List<Department> listAll(){
        return readUseCase.listAll();
    }

    public Department findById(Long id){
        return readUseCase.findById(id);
    }

    public Department update(Long id, ProductDTO dto){

        Department product = Department.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        return updateUseCase.execute(id, product);
    }

    public boolean deleteById(Long id){
        return deleteUseCase.execute(id);
    }
}
