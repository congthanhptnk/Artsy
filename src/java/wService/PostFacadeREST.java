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
@Path("posts")
public class PostFacadeREST extends AbstractFacade<Post> {

    @PersistenceContext(unitName = "ArtsyPU")
    private EntityManager em;

    public PostFacadeREST() {
        super(Post.class);
    }

    @POST
    @Path("{userid}/newpost")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean createPost(
            @FormParam("title")String title, 
            @FormParam("caption")String caption,
            @FormParam("picture")String picture, 
            @PathParam("userid")int id) {
        
        boolean isOk;
        if(picture.isEmpty() || id==0){
            isOk = false;
        }
        else{
            Post newPost = new Post();
            newPost.setCaption(caption);
            newPost.setPicture(picture);
            newPost.setTitle(title);
            newPost.setId(id);
            super.create(newPost);
            isOk = true;
        }
        return isOk;
    }

    @PUT
    @Path("{postid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void editPost(@PathParam("postid")int pid, Post entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{userid}/{postid}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean removePost(@PathParam("postid")int pid, @PathParam("userid")int id) {
        boolean isOk = true;
        
        if(super.find(pid)==null){
            isOk = false;
        }
        super.remove(super.find(pid));
        return isOk;
    }

    @GET
    @Path("{userid}/main")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Post> findAllPost() {
        return super.findAll();
    }
    
    @GET
    @Path("{userid}/mypost")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Post> findMyPost(@PathParam("userid")int id) {
        List<Post> myPost = em.createNamedQuery("Post.findById").setParameter("ID", id).getResultList();
        return myPost;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
