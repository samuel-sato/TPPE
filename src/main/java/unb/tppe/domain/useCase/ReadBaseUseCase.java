package unb.tppe.domain.useCase;

import unb.tppe.domain.entity.BaseEntity;
import unb.tppe.domain.respository.ListBaseRepository;

import java.util.List;
import java.util.Optional;


public class ReadBaseUseCase<E extends BaseEntity, R extends ListBaseRepository<E>> {

    private R repository;

    public ReadBaseUseCase(){

    }

    public ReadBaseUseCase(R repository){
        this.repository = repository;
    }

    public List<E> listAll(){
        return repository.listAllEntity();
    }

    public Optional<E> findById(Long id){
        return repository.listById(id);
    }
}
