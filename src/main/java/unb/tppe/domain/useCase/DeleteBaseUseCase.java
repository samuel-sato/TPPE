package unb.tppe.domain.useCase;

import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.BaseEntity;
import unb.tppe.domain.respository.BaseRepository;
import unb.tppe.domain.respository.ListBaseRepository;

import java.util.List;
import java.util.Optional;


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
