/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpclient;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author sistemas
 */
public class ClientButton extends Button {
    private PP aux;
    private TCPClient tcp; 
    public ClientButton(String name,TCPClient tcp){
        super(name);
        this.tcp=tcp;
        this.tcp.setClientButton(this);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event(e);
            }
        });
    }
    public void event(ActionEvent e){
        this.aux= new PP();
        this.aux.stablishCl(this.tcp);
        this.aux.setVisible(true);
    }
    public void refresh(){
        if(this.aux!=null &&this.aux.isActive()){
            this.aux.refresh();
        }
        
    }
}
