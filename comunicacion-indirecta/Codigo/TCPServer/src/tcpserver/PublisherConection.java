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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author randy
 */
public class PublisherConection extends Thread{
    private DataInputStream in;
   private DataOutputStream out;
   private Socket clientSocket;
   private TupleSpace tts;
   private ServerConectionManager scm;
   public PublisherConection (Socket aClientSocket, TupleSpace ts,ServerConectionManager sv) {
      try {
	  this.clientSocket = aClientSocket;
	  this.in = new DataInputStream(clientSocket.getInputStream());
	  this.out =new DataOutputStream(clientSocket.getOutputStream());
          this.tts=ts;
          this.scm=sv;
          this.start();
	} catch(IOException e){
	      System.out.println("Connection:"+e.getMessage());
       }
   } // end Connection
    @Override
    public void run() {
     while(true){
      try {
            String op=this.in.readUTF().trim();
            String topic=this.in.readUTF().trim();
            String data= this.in.readUTF();
            Tuple t= new Tuple(topic,data);
            this.scm.sendTupleServer(op,topic, data);
            System.out.println(this.clientSocket.getPort()+": "+data);
            this.tts.sendNewMessage(op,t);
          
      } catch (EOFException e){
	     this.closePort();
      } catch(IOException e){
	     this.closePort();
      }
      }
   } // end run

    public void closePort(){
       try {
           this.clientSocket.close();
       } catch (IOException ex) {
           Logger.getLogger(ClientConection.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
