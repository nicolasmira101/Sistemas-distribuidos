/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

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
public class PublisherConectionManager extends Thread {
    private ServerSocket pp;
    private List<PublisherConection> ppl;
    private TupleSpace tts;
    private ServerConectionManager scm;
    
    public PublisherConectionManager(int SocketNumber,TupleSpace ts,ServerConectionManager sv){
            try {
                this.pp= new ServerSocket(SocketNumber);
                this.ppl = new ArrayList<PublisherConection>();
                this.tts=ts;
                this.scm=sv;
                this.start();
            } catch (IOException ex) {
                Logger.getLogger(PublisherConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override
    public void run(){
        while(true){
            try {
                Socket publisherClientSocket = pp.accept();
                this.ppl.add(new PublisherConection(publisherClientSocket,this.tts,this.scm));
            } catch (IOException ex) {
                Logger.getLogger(PublisherConectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
}
