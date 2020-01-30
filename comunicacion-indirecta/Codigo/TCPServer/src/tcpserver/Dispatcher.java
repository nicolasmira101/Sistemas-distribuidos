/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.util.List;

/**
 *
 * @author sistemas
 */
public class Dispatcher extends Thread {
    
    private List<ClientConection> cll;
    public Dispatcher(List<ClientConection> cll) {
        this.cll = cll;
    }
    
    @Override
    public void run(){

    }
    public void SendMessage(String op,Tuple t){
        if(op.equals("tuple")){
            for(ClientConection c:this.cll){
                for(Tuple aux:c.getTuple()){
                    if(aux.Equals(t)){
                    c.sendInformation(t.getInformation());
                    System.out.println(t.getInformation());
                    }
                }    
            }
        }else{
            for(ClientConection c:this.cll){
                c.sendMessage(t.getFilters()[0], t.getInformation());
            }
        }
        
    }
    
}
