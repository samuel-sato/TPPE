package unb.tppe.domain.useCase;

import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.BaseEntity;
import unb.tppe.domain.respository.BaseRepository;


public class DeleteBaseUseCase<E extends BaseEntity, R extends BaseRepository<E>> {

    private R repository;

    public DeleteBaseUseCase(){

    }

    public DeleteBaseUseCase(R repository){
        this.repository = repository;
    }


    @Transactional
    public boolean execute(Long id){
        return repository.deleteEntity(id);
    }
}
