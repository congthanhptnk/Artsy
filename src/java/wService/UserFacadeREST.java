/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wService;

import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author congthanhptnk
 */
@Stateless
@Path("users")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "ArtsyPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("registration")
    public User register(@FormParam("username") String userName, @FormParam("password") String password) {
        boolean isOk = true;
        User newUser =null;
        List<User> userList = em.createNamedQuery("User.findAll").getResultList();
        for(User user: userList){
                if(user.getUsername().equals(userName)){
                isOk = false;
            }
        }
        if(isOk == true){
            newUser = new User();
            newUser.setUsername(userName);
            newUser.setPassword(password);
            super.create(newUser);
        }
        else if(isOk == false){
            newUser = em.find(User.class, 10);
        }
        return newUser;
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("login")
    public User login(@FormParam("username")String userName, @FormParam("password")String password){
        boolean isOk=false;
        User myUser = null;
        List<User> userList = em.createNamedQuery("User.findAll").getResultList();
        for(User user: userList){            
            if(user.getUsername().equals(userName) && user.getPassword().equals(password)){
                isOk = true;
                myUser = user;
            }
        }
        if(isOk == false){
            myUser = em.find(User.class, 10);
        }
        return myUser;
    }

    @DELETE
    @Path("{userid}")
    public void remove(@PathParam("userid")int id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("profile")
    @Produces({MediaType.APPLICATION_JSON})
    public User findMe(@QueryParam("username") String username) {
        List<User> allUser = em.createNamedQuery("User.findAll").getResultList();
        User user = null;
        for(User aUser: allUser){
           if(aUser.getUsername().equals(username)){
               user = aUser;
           }
        }
        return user;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
