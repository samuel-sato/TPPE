package unb.tppe.aplication.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.aplication.dto.SellerDTO;
import unb.tppe.aplication.service.SellerSerivce;

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

        SellerDTO seller = sellerSerivce.create(sellerDto);
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
        try{
            return Response.status(Response.Status.OK).entity(sellerSerivce.findById(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(Long id, SellerDTO sellerDto){
        return Response.status(Response.Status.OK).entity(sellerSerivce.update(id, sellerDto)).build();
    }

    @DELETE()
    @Path("{id}")
    public Response delete(Long id){
        return Response.status(Response.Status.NO_CONTENT).entity(sellerSerivce.deleteById(id)).build();
    }
}