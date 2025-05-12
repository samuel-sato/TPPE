package unb.tppe.view;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Seller;
import unb.tppe.view.dto.SellerDTO;

import java.util.List;

@ApplicationScoped
public class SellerSerivce {

    public SellerSerivce(){

    }

    public Seller create(SellerDTO dto){

        Person p = new Person();
        p.name = dto.getName();
        p.email = dto.getEmail();
        p.birthdate = dto.getBirthdate();
        p.persist();

        Seller seller = new Seller();
        seller.person = p;
        seller.baseSalary = dto.getBaseSalary();
        seller.numberHours = dto.getNumberHours();

        seller.persist();

        return  seller;
    }

    public List<Seller> listAll(){
        return Seller.listAll();
    }

    public Seller listById(Long id){
        return Seller.findById(id);
    }
}
