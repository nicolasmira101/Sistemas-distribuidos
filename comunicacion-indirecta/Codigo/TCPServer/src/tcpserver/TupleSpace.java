/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sistemas
 */
public class TupleSpace {
    
    private List<Tuple> information;
    private ClientConnectionManager ccml[];
    
    public TupleSpace(ClientConnectionManager clien[]){
        this.information= new ArrayList<>();
        this.ccml=clien;
    }
    
    public void sendNewMessage(String op,Tuple t){
        this.information.add(t);
        System.out.println(t.getInformation());
        for(ClientConnectionManager cm :this.ccml){
            cm.sendNewTuple(op,t);
        }   
    }
    
    
}
