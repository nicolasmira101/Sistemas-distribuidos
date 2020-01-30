/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author randy
 */
public class ClientConection extends Thread{
    
   private DataInputStream in;
   private DataOutputStream out;
   private Socket clientSocket;
   private List<Tuple> tupleFilter;
   private List<String> filters;

   public ClientConection (Socket aClientSocket) {
      try {
	  this.clientSocket = aClientSocket;
	  this.in = new DataInputStream(clientSocket.getInputStream());
	  this.out =new DataOutputStream(clientSocket.getOutputStream());
          this.tupleFilter= new ArrayList<>();
          this.filters = new ArrayList<>();
          this.start();
	} catch(IOException e){
	      this.closePort();
       }
   }
   @Override
   public void run(){
       while(true){
           try {
               String op=this.in.readUTF().trim();
               if(op.equals("filter")){
                   String word=this.in.readUTF().trim();
                   if(!this.filters.contains(word)){
                       this.filters.add(word);
                   }
               }else{
                   this.tupleFilter.add(new Tuple(this.in.readUTF().trim(),null));
               }
               
           } catch (IOException ex) {
               Logger.getLogger(ClientConection.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
    public void sendInformation(String info ){
       try {
           this.out.writeUTF(info);
       } catch (IOException ex) {
           Logger.getLogger(ClientConection.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public void sendMessage(String word,String info){
        if(this.filters.contains(word)){
            try {
                this.out.writeUTF(info);
            } catch (IOException ex) {
                Logger.getLogger(ClientConection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public List<Tuple> getTuple(){
        return this.tupleFilter;
    }
    private void closePort(){
       try {
           this.clientSocket.close();
       } catch (IOException ex) {
           Logger.getLogger(ClientConection.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
