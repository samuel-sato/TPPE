package unb.tppe.aplication.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import unb.tppe.aplication.dto.UserLoginDTO;
import unb.tppe.aplication.service.LoginService;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @POST
    @PermitAll
    public Response login(UserLoginDTO user){

        String token = loginService.login(user);

        if(token.isEmpty()){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário ou senha incorreto").build();
        }

        return Response.status(Response.Status.OK).entity(token).build();
    }
}
