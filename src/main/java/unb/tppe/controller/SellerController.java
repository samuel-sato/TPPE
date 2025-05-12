package unb.tppe.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.domain.entity.Seller;
import unb.tppe.view.SellerSerivce;
import unb.tppe.view.dto.SellerDTO;

@Path("/sellers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SellerController {

    @Inject
    private SellerSerivce sellerSerivce;

    @POST
    @RequestScoped
    @Transactional
    public Response create( SellerDTO dto) {

        Seller seller = sellerSerivce.create(dto);
        if (seller.id != 0)
            return Response.status(Response.Status.CREATED).entity(seller).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response read(){
        return Response.status(Response.Status.OK).entity(sellerSerivce.listAll()).build();
    }

    @GET()
    @Path("id")
    public Response read(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(sellerSerivce.listAll()).build();
    }
}