package unb.tppe.aplication.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.aplication.dto.ProductDTO;
import unb.tppe.aplication.service.ProductSerivce;
import unb.tppe.domain.entity.Product;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    private ProductSerivce service;

    @POST
    @RequestScoped
    @Transactional
    public Response create(ProductDTO dto) {

        try{
            Product seller = service.create(dto);
            if (seller.getId() != 0)
                return Response.status(Response.Status.CREATED).entity(seller).build();
            else
                return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao gravar entidade").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }

    }

    @GET
    public Response read(){
        return Response.status(Response.Status.OK).entity(service.listAll()).build();
    }

    @GET()
    @Path("{id}")
    public Response read(Long id){
        return Response.status(Response.Status.OK).entity(service.findById(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response update(Long id, ProductDTO dto){
        return Response.status(Response.Status.OK).entity(service.update(id, dto)).build();
    }

    @DELETE()
    @Path("{id}")
    public Response delete(Long id){
        return Response.status(Response.Status.OK).entity(service.deleteById(id)).build();
    }
}