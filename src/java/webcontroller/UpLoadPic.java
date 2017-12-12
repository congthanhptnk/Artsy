/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcontroller;

import entities.PicLink;
import entities.Post;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wService.AbstractFacade;
import wService.PostFacadeREST;

/**
 *
 * @author congthanhptnk
 */
@MultipartConfig(location= "/var/www/html/storage")
@WebServlet(name = "UpLoadPic", urlPatterns = {"/UpLoadPic"})
public class UpLoadPic extends HttpServlet {

    @EJB
    private PostFacadeREST postFacadeREST;
    private String picLink;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        PicLink newPic = new PicLink();
        newPic.setUrl("weed");
        
        out.println("{\"" + "url" + "\":" + "\"" + newPic.getUrl() + "\"}");
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        request.getPart("upFile").write(request.getPart("upFile").getSubmittedFileName());
        //this.picLink = "10.114.34.13/storage/" + request.getPart("upFile").getSubmittedFileName();
        this.picLink = request.getPart("upFile").getSubmittedFileName();
        PrintWriter out = response.getWriter();
        out.println("{\"" + "picture" + "\":" + "\"" + picLink + "\"}");
        /*String title = request.getParameter("title");
        String caption = request.getParameter("caption");
        int id = Integer.parseInt(request.getParameter("id"));
        postFacadeREST.createPost(title, caption, pictureLink, id);*/
    }

    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
