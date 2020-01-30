/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.DataInputStream;
import java.io.IOException;
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
public class ClientConnectionManager extends Thread{
    private ServerSocket ss ;
    private List<ClientConection> ccl;
    private Dispatcher dispatcher;
    public ClientConnectionManager(int portNumber) {
        try {
            this.ss = new ServerSocket(portNumber);
            this.ccl = new ArrayList<ClientConection>();
            this.dispatcher= new Dispatcher(ccl);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        while(true){
            try {
                Socket clientSocket = ss.accept();
                this.ccl.add(new ClientConection(clientSocket));
            } catch (IOException ex) {
                Logger.getLogger(PublisherConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
    public void sendNewTuple(String op,Tuple t){
        this.dispatcher.SendMessage(op,t);
    }
}
