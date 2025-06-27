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

import unb.tppe.aplication.dto.ClientDTO;
import unb.tppe.aplication.service.ClientService;
import unb.tppe.domain.entity.Client;

@Path("/clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientController {

    @Inject
    private ClientService serivce;

    @POST
    @RequestScoped
    @Transactional
    public Response create(ClientDTO dto) {

        Client seller = serivce.create(dto);
        if (seller.getId() != 0)
            return Response.status(Response.Status.CREATED).entity(seller).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response read(){
        return Response.status(Response.Status.OK).entity(serivce.listAll()).build();
    }

    @GET()
    @Path("{id}")
    public Response read(Long id){
        try{
            return Response.status(Response.Status.OK).entity(serivce.findById(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(Long id, ClientDTO dto){
        return Response.status(Response.Status.OK).entity(serivce.update(id, dto)).build();
    }

    @DELETE()
    @Path("{id}")
    public Response delete(Long id){
        return Response.status(Response.Status.NO_CONTENT).entity(serivce.deleteById(id)).build();
    }
}