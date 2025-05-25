package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.DepartamentCreateDTO;
import unb.tppe.aplication.dto.ProductDTO;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.respository.DepartmentRepository;
import unb.tppe.domain.respository.ProductRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DepartmentSerivce {

    private CreateBaseUseCase<Department, DepartmentRepository> createUseCase;
    private ReadBaseUseCase<Department, DepartmentRepository> readUseCase;
    private DeleteBaseUseCase<Department, DepartmentRepository> deleteBseCase;
    private UpdateBaseUseCase<Department, DepartmentRepository> updateBseCase;


    public DepartmentSerivce(CreateBaseUseCase<Department, DepartmentRepository> createUseCase,
                             ReadBaseUseCase<Department, DepartmentRepository> readUseCase,
                             DeleteBaseUseCase<Department, DepartmentRepository> deleteBseCase,
                             UpdateBaseUseCase<Department, DepartmentRepository> updateBseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
        this.deleteBseCase = deleteBseCase;
        this.updateBseCase = updateBseCase;
    }

    public Department create(DepartamentCreateDTO dto){

        Department product = Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        return createUseCase.execute(product);
    }

    public List<Department> listAll(){
        return readUseCase.listAll();
    }

    public Optional<Department> findById(Long id){
        return readUseCase.findById(id);
    }

    public Department update(Long id, ProductDTO dto){

        Department product = Department.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        return updateBseCase.execute(id, product);
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }
}
