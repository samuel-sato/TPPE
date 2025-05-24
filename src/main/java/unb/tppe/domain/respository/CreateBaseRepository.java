package unb.tppe.domain.respository;

import unb.tppe.domain.entity.BaseEntity;

public interface CreateBaseRepository <E extends BaseEntity>{
    public E create(E entity);
}
