/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

/**
 *
 * @author sistemas
 */
public class Tuple {
    
    private String[] filters;
    private String information;
    public Tuple(String t,String infor){
        this.filters=t.split(",");
        this.information=infor;
    }
    
    public String[] getFilters(){
        return this.filters;
    }
    public String getStringFilters(){
        String sf="";
        for(String s:this.filters){
            sf.concat(s+",");
        }
        return sf;
    }
    public String getInformation(){
        return this.information;
    }
    public boolean Equals(Tuple t){
        
        int cont=0;
        
        if(this.filters.length==t.getFilters().length){
        for(int i = 0; i<this.filters.length; i++){
            if(this.filters[i].trim().equals(t.getFilters()[i].trim()) || this.filters[i].equals("?")|| t.filters[i].equals("?")){
                ++cont;
            }
        }
        if(cont==this.filters.length){
            return true;
        }
        }
        return false;
    } 
    
    
    
}
