/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.util.Scanner;


/**
 *
 * @author randy
 */
public class TCPServer {

   public static void main (String args[]) {

       
       int serverPort[] = {7896,7897,7898};  // the server port
       int publisherPort []= {9541,9542}; // the publisher port
       Scanner s= new Scanner(System.in);
       
       ClientConnectionManager ccml[]= {new ClientConnectionManager(serverPort[0]),new ClientConnectionManager(serverPort[1]),new ClientConnectionManager(serverPort[2])};
       TupleSpace ts= new TupleSpace(ccml);
       
       System.out.println("esclavo?");
       String op=s.nextLine().trim();
       ServerConectionManager scm;
       
       if(op.equals("si")){
            scm= new ServerConectionManager("10.192.101.35",ts);
       }
       else{
           scm= new ServerConectionManager(ts);
       }
       PublisherConectionManager pcml[] ={new PublisherConectionManager(publisherPort[0],ts,scm), new PublisherConectionManager(publisherPort[1],ts,scm)};

   }
}