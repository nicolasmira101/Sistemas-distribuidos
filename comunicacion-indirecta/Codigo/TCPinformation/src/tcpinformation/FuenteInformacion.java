/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpinformation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sistemas
 */
public class FuenteInformacion {

    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) { 
        // args message and hostname
        Socket s = null;
        try{
        int serverPort = 9541;
        String ipBroker = "10.192.101.36";
        s = new Socket(ipBroker, serverPort);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out =new DataOutputStream(s.getOutputStream());
        BufferedReader bf = new BufferedReader(new FileReader("./src/data/info.txt"));
        String strCurrentLine;
        while ((strCurrentLine = bf.readLine()) != null) {
            String op=strCurrentLine.trim();
            String tuple=bf.readLine().trim();
            String info=bf.readLine().trim();
            
            out.writeUTF(op);
            out.writeUTF(tuple);
            out.writeUTF(info);
            Thread.sleep(Integer.parseInt(bf.readLine().trim())*1000);
        }
        out.writeUTF("acabar");
        s.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FuenteInformacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FuenteInformacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FuenteInformacion.class.getName()).log(Level.SEVERE, null, ex);
        
        }finally {if(s!=null) try { s.close(); }catch (IOException e){
        System.out.println("close:"+e.getMessage());}}
        


    }   
}
