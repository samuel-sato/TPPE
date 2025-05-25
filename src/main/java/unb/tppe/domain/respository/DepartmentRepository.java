package unb.tppe.domain.respository;

import unb.tppe.domain.entity.Department;


public interface DepartmentRepository extends BaseRepository<Department> {

    public boolean removeProductById(Long id);
    public boolean addProductById(Long id);
}
