/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author dattruong
 */
public class PicLink implements Serializable {
    private String url;
    public PicLink(){
        
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}
