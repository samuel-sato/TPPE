package unb.tppe.aplication.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.aplication.dto.SellerDTO;
import unb.tppe.aplication.service.SellerSerivce;
import unb.tppe.domain.entity.Seller;

@Path("/sellers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SellerController {

    @Inject
    private SellerSerivce sellerSerivce;

    @POST
    @RequestScoped
    @Transactional
    public Response create(SellerDTO sellerDto) {

        Seller seller = sellerSerivce.create(sellerDto);
        if (seller.getId() != 0)
            return Response.status(Response.Status.CREATED).entity(seller).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response read(){
        return Response.status(Response.Status.OK).entity(sellerSerivce.listAll()).build();
    }

    @GET()
    @Path("{id}")
    public Response read(Long id){
        return Response.status(Response.Status.OK).entity(sellerSerivce.findById(id)).build();
    }

    @DELETE()
    @Path("{id}")
    public Response delete(Long id){
        return Response.status(Response.Status.OK).entity(sellerSerivce.deleteById(id)).build();
    }
}