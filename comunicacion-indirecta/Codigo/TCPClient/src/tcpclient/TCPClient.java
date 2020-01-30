/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sistemas
 */
public class TCPClient extends Thread {
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private List<String> topics;
    private List<String> messages;
    private ClientButton cb;
    private String ip;
    public TCPClient (int serverPort,String ip,String top){
        try {
            this.s = new Socket(ip, serverPort);
            this.ip=ip;
            this.in = new DataInputStream(this.s.getInputStream());
            this.out= new DataOutputStream(this.s.getOutputStream());
            this.topics=new ArrayList<>();
            this.messages=new ArrayList<>();
            this.topics.add(top);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        
        try {
            this.out.writeUTF("tuple");
            this.out.writeUTF(this.topics.get(0));
            
            while(true){  	 // UTF is a string encoding
                String data = this.in.readUTF();	 // read a line of data from the stream
                this.messages.add(data.trim()+"\n");
                this.cb.refresh();
            }
        } catch (IOException ex) {
            try {
                this.s.close();
            } catch (IOException ex1) {
                
            }
        }
    }

    public List<String> getTopics() {
        return this.topics;
    }

    public List<String> getMessages() {
        return this.messages;
    }
    
    public String getIp(){
        return this.ip;
    }
    
    public void setClientButton(ClientButton cb){
        this.cb=cb;
    }
    
    public void addTuple(String op,String t){
        try {
            this.out.writeUTF(op);
            this.out.writeUTF(t);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
