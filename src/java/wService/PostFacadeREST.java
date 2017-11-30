/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wService;

import entities.Post;
import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("posts")
public class PostFacadeREST extends AbstractFacade<Post> {

    @PersistenceContext(unitName = "ArtsyPU")
    private EntityManager em;

    public PostFacadeREST() {
        super(Post.class);
    }

    @POST
    @Path("addpost")
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean createPost(
            @PathParam("title")String title, 
            @PathParam("caption")String caption,
            @PathParam("picture")String picture, 
            @PathParam("ID")int id) {
        
        boolean isOk =false;
        if(!picture.isEmpty() && id!=0){
            isOk = true;
        }
        else {
            Post newPost = new Post();
            newPost.setCaption(caption);
            newPost.setPicture(picture);
            newPost.setTitle(title);
            newPost.setId(id);
        }
        return isOk;
    }

    @PUT
    @Path("{pid}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void editPost(@PathParam("PID")int pid, Post entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{pid}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean removePost(@PathParam("PID")int pid) {
        boolean isOk = true;
        if(super.find(pid)==null){
            isOk = false;
        }
        super.remove(super.find(pid));
        return isOk;
    }

    @GET
    @Path("{pid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Post findPost(@PathParam("PID") int pid) {
        return super.find(pid);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Post> findAllPost() {
        return super.findAll();
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Post> findMyPost(@PathParam("ID")int id) {
        List<Post> myPost = em.createNamedQuery("Post.findById").setParameter("ID", id).getResultList();
        return myPost;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Post> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
