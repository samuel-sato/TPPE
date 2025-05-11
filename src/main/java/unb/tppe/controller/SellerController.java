package unb.tppe.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.domain.entity.Seller;
import unb.tppe.view.SellerSerivce;
import unb.tppe.view.dto.SellerDTO;

@Path("/sellers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SellerController {

    private SellerSerivce sellerSerivce;

    @POST
    @Transactional
    public Response create(SellerDTO dto) {

        Seller seller = sellerSerivce.create(dto);
        if (seller.id != 0)
            return Response.status(Response.Status.CREATED).entity(seller).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }
}