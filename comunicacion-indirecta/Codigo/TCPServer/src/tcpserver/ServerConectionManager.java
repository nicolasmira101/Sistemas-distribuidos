/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sistemas
 */
public class ServerConectionManager extends Thread  {
    
    
    private ServerSocket ss;
    private Socket socket;
    private boolean slave;
    private TupleSpace ts;
    private List<BrokerConection> brokers;
    public ServerConectionManager(TupleSpace ts){
        try {
            this.ss= new ServerSocket(7000);
            this.slave=false;
            this.brokers=new ArrayList<>();
            this.ts=ts;
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerConectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ServerConectionManager(String ip,TupleSpace ts){
        try {
            this.socket= new Socket(ip,7000);
            this.ts=ts;
            this.slave=true;
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerConectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        if(this.slave){
            
            try {
                DataInputStream in= new DataInputStream(this.socket.getInputStream());
                DataOutputStream out= new DataOutputStream(this.socket.getOutputStream());
                out.writeUTF(InetAddress.getLocalHost().getHostAddress());
                while(true){
                    String op=in.readUTF().trim();
                    String tup=in.readUTF().trim();
                    String info=in.readUTF();
                    Tuple t=new Tuple(tup, info);
                    this.ts.sendNewMessage(op,t);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else{
            
            try {
                Socket s=null;
                while(true){
                    s=this.ss.accept();
                    this.brokers.add(new BrokerConection(s,brokers,this.ts));
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendTupleServer(String op,String filters,String data){
        if(this.slave){
            DataOutputStream out;
            try {
                out = new DataOutputStream(this.socket.getOutputStream());
                out.writeUTF(op);
                out.writeUTF(filters);
                out.writeUTF(data);
            } catch (IOException ex) {
                Logger.getLogger(ServerConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            for(BrokerConection bc:this.brokers ){
                bc.sendTuple(filters, data);
            }
        }
    }
}
