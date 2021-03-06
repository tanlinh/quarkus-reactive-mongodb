package boundary;


import dto.AuthRequest;
import dto.AuthResponse;
import entity.User;
import security.PasswordEncode;
import security.TokenUtil;
import serviceimpl.UserServiceImpl;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class AdminResource {

    @Inject
    UserServiceImpl userService;

    @Inject
    PasswordEncode passwordEncode;

    @PermitAll
    @POST @Path("/login") @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {
        User u = userService.findByUsername(authRequest.getUserName());
        if (u != null && u.getPassword().equals(passwordEncode.encode(authRequest.getPassword())) &&u.getRoles().equals(authRequest.getRoles())) {
            try {
                return Response.ok(new AuthResponse(TokenUtil.generateToken(u.getUserName(), u.getRoles()), u.getUserName(), u.getEmail(), u.getRoles())).build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity("UserName or Password in correct").build());
        }
    }


}
