/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploobserv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flor
 */
public class ServerThread extends Thread{

    private ServerSocket server = null;
    private boolean isStoped =false;
    private Observer observer;
    
    public ServerThread(Observer observer) throws IOException{ //Desencadenar
        server = new ServerSocket(7700);
        this.observer = observer;
    }
    
    public synchronized void stoped(){
        isStoped = true;
    }
    
    @Override
    public void run() {
        Socket cliente = null;
        while(!isStoped){
            try {
                cliente = server.accept();
                HiloCliente hc = new HiloCliente(cliente);
                hc.addObserver(observer);
                Thread hilo = new Thread(hc);
                hilo.start();
            } catch (IOException ex) {
                stoped();
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        
        super.run(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
