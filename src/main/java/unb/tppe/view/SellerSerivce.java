package unb.tppe.view;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Seller;
import unb.tppe.view.dto.SellerDTO;

@ApplicationScoped
public class SellerSerivce {

    public SellerSerivce(){

    }

    public Seller create(SellerDTO dto){

        Person p = new Person();
        p.name = dto.getName();
        p.email = dto.getEmail();
        p.birthdate = dto.getBirthdate();

        Seller seller = new Seller();
        seller.person = p;
        seller.baseSalary = dto.getBaseSalary();
        seller.numberHours = dto.getNumberHours();

        seller.persist();

        return  seller;
    }

}
