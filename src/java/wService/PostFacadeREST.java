/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wService;

import entities.PicLink;
import entities.Post;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import webcontroller.UpLoadPic;


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
    public Post createPost(
            @QueryParam("title") String title, 
            @QueryParam("caption")String caption,
            @QueryParam("picture")String picture, 
            @PathParam("userid")int id) {
        Post newPost;
        
        newPost = new Post();
        newPost.setCaption(caption);
        newPost.setPicture(picture);
        newPost.setTitle(title);
        newPost.setId(id);
        super.create(newPost);
        return newPost;
        
    }
    
    @POST
    @Path("newpost")
    @Produces({MediaType.APPLICATION_JSON})
    public String upPost(){
        
        return "Hello";
    }

    @DELETE
    @Path("{userid}/{postid}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean removePost(@PathParam("postid")int pid, @HeaderParam("userid")int id) {
        boolean isOk = true;
        
        if(super.find(pid)==null){
            isOk = false;
        }
        else if (super.find(pid).getId() != id){
            isOk = false;
        }
        
        if(isOk == true){
            super.remove(super.find(pid));
        }
        return isOk;
    }

    @GET
    @Path("{userid}/main")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Post> findAllPost() {
        List<Post> posts = super.findAll();
        Collections.reverse(posts);
        return posts;
    }
    
    @GET
    @Path("{userid}/mypost")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Post> findMyPost(@PathParam("userid")int id) {
        List<Post> myPost = em.createNamedQuery("Post.findById").setParameter("id", id).getResultList();
        return myPost;
    }
    
    @GET
    @Path("{postid}/post")
    @Produces({MediaType.APPLICATION_JSON})
    public Post findPost(@PathParam("postid")int pid) {       
        return super.find(pid);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
