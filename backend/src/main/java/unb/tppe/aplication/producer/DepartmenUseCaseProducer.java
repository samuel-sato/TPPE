package unb.tppe.aplication.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.respository.DepartmentRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

@ApplicationScoped
public class DepartmenUseCaseProducer {

    private final DepartmentRepository repository;

    public DepartmenUseCaseProducer(DepartmentRepository repository){
        this.repository = repository;
    }

    @RequestScoped
    @Produces
    public CreateBaseUseCase<Department, DepartmentRepository> createUseCase(){
        return new CreateBaseUseCase<Department, DepartmentRepository>(repository);
    }

    @RequestScoped
    @Produces
    public ReadBaseUseCase<Department, DepartmentRepository> readUseCase(){
        return new ReadBaseUseCase<Department, DepartmentRepository>(repository);
    }

    @RequestScoped
    @Produces
    public DeleteBaseUseCase<Department, DepartmentRepository> deleteUseCase(){
        return new DeleteBaseUseCase<Department, DepartmentRepository>(repository);
    }

    @RequestScoped
    @Produces
    public UpdateBaseUseCase<Department, DepartmentRepository> updateUseCase(){
        return new UpdateBaseUseCase<Department, DepartmentRepository>(repository);
    }
}



















