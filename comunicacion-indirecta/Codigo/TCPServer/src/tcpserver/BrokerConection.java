/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sistemas
 */
public class BrokerConection extends Thread {
    
    private Socket sClient;
    private DataInputStream in;
    private DataOutputStream out;
    private String ip;
    private List<BrokerConection> brokers;
    private TupleSpace ts;
    
    public BrokerConection(Socket cs,List<BrokerConection> bc,TupleSpace tsl){
        try {
            this.sClient=cs;
            this.in=new DataInputStream(this.sClient.getInputStream());
            this.ip=this.in.readUTF().trim();
            System.out.println(this.ip);
            this.out=new DataOutputStream(this.sClient.getOutputStream());
            this.brokers=bc;
            this.ts=tsl;
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(BrokerConection.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public void run(){
        while(true){
            try {
                String op=this.in.readUTF().trim();
                String filter=this.in.readUTF().trim();
                String info=this.in.readUTF().trim();
                Tuple t=new Tuple(filter, info);
                this.ts.sendNewMessage(op,t);
                this.sendTuples(filter, info);
            } catch (IOException ex) {
                try {
                    this.sClient.close();
                } catch (IOException ex1) {
                    Logger.getLogger(BrokerConection.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
    
    public String getIp(){
        return this.ip;
    }
    
    public void sendTuples(String filter,String info){
        
           
        for(BrokerConection bc:this.brokers){
            if(!this.ip.equals(bc.getIp())){
                bc.sendTuple(filter,info);
            }

        }    
    }
    public void sendTuple(String filter,String info){
        try {
            this.out.writeUTF(filter);
            this.out.writeUTF(info);
        } catch (IOException ex) {
            Logger.getLogger(BrokerConection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
