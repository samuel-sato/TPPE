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
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.aplication.dto.SaleDTO;
import unb.tppe.aplication.service.SaleSerivce;
import unb.tppe.domain.entity.Sale;

@Path("/sales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaleController {

    @Inject
    private SaleSerivce service;

    @POST
    @RequestScoped
    @Transactional
    public Response create(SaleDTO dto) {

        try{
            Sale sale = service.create(dto);
            if (sale.getId() != 0)
                return Response.status(Response.Status.CREATED).entity(sale).build();
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
        try{
            return Response.status(Response.Status.OK).entity(service.findById(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, SaleDTO dto){

        try{
            return Response.status(Response.Status.OK).entity(service.update(id, dto)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
    }

    @DELETE()
    @Path("{id}")
    public Response delete(Long id){
        return Response.status(Response.Status.NO_CONTENT).entity(service.deleteById(id)).build();
    }

    @GET()
    @Path("client/{id}")
    public Response readByClientId(Long id){
        try{
            return Response.status(Response.Status.OK).entity(service.listByIdClient(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}