/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wService;

import entities.Post;
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
    public boolean createPost(@PathParam("title")String title, @PathParam("caption")String caption,
            @PathParam("picture")String picture, @PathParam("id")int id) {
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
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean editPost(@PathParam("title")String title, @PathParam("caption")String caption,
            @PathParam("picture")String picture, @PathParam("id")int id) {
        boolean isOk;
        Post post = super.find(id);
        if(post == null){
            isOk = false;
        }
        else {
            post.setCaption(caption);
            post.setPicture(picture);
            post.setTitle(title);
            isOk = true;
        }
        return isOk;
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean removePost(@PathParam("id")int pid) {
        boolean isOk = true;
        if(super.find(pid)==null){
            isOk = false;
        }
        super.remove(super.find(pid));
        return isOk;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Post find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Post> findAll() {
        return super.findAll();
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
