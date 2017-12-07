/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wService;

import entities.Comment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
@Path("comments")
public class CommentFacadeREST extends AbstractFacade<Comment> {

    @PersistenceContext(unitName = "ArtsyPU")
    private EntityManager em;

    public CommentFacadeREST() {
        super(Comment.class);
    }

    @POST
    @Path("{userid}/{postid}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean createComment(
            @PathParam("postid")int pid, 
            @HeaderParam("userid")int id, 
            @FormParam("comment")String comment) {
        boolean isOk =true;
        if(comment.isEmpty()){
            isOk = false;
        }
        else {
            Comment myComment = new Comment();
            myComment.setPid(pid);
            myComment.setId(id);
            myComment.setComment(comment);
            super.create(myComment);
        }
        
        return isOk;
    }

    @DELETE
    @Path("{userid}/{commentid}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean removeComment(@PathParam("commentid")int cid, @HeaderParam("userid")int id) {
        boolean isOk = false;
        Comment oldComment = super.find(cid);
        if(oldComment.getId() == id){
            super.remove(super.find(id)); 
            isOk = true;
        }
        return isOk;
    }

    @GET
    @Path("{userid}/{postid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Comment> findPicComment(@PathParam("postid")int pid) {
        List<Comment> comments = em.createNamedQuery("Comment.findCommentbyPid").setParameter("PID", pid).getResultList();
        return comments;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
