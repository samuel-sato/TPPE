package unb.tppe.domain.useCase;

import unb.tppe.domain.entity.BaseEntity;
import unb.tppe.domain.respository.CreateBaseRepository;


public class CreateBaseUseCase<E extends BaseEntity, R extends CreateBaseRepository<E>> {

    private R repository;

    public CreateBaseUseCase(){

    }

    public CreateBaseUseCase(R repository){
        this.repository = repository;
    }

    public E execute(E dto){
        return repository.create(dto);
    }
}
