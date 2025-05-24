package unb.tppe.domain.respository;

import unb.tppe.domain.entity.BaseEntity;

public interface BaseRepository <E extends BaseEntity> extends CreateBaseRepository<E>, ListBaseRepository<E>{
    public E update(E entyty);
    public void delete(E entity);
}
