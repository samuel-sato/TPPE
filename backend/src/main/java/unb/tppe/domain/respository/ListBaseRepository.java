package unb.tppe.domain.respository;

import unb.tppe.domain.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface ListBaseRepository <E extends BaseEntity> {
    public Optional<E> listById(Long id);
    public List<E> listAllEntity();
}
