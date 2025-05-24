package unb.tppe.domain.useCase;

import unb.tppe.domain.entity.BaseEntity;
import unb.tppe.domain.respository.BaseRepository;


public class UpdateBaseUseCase<E extends BaseEntity, R extends BaseRepository<E>> {

    private R repository;

    public UpdateBaseUseCase(){

    }

    public UpdateBaseUseCase(R repository){
        this.repository = repository;
    }

    public E execute(Long id, E entity){
        return repository.update(entity);
    }
}
