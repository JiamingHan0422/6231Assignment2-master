package Server;


import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;


public class MTLServer {

    public static void main(String args[]) {
        try{

            // create servant and register it with the ORB
            MethodImpl serverImpl = new MethodImpl("MTL");

            System.out.println("HelloServer ready and waiting ...");

            // wait for invocations from clients
            //.run();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    serverImpl.UDPServer(5053);
                }
            }).start();

        }

        catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        //System.out.println("HelloServer Exiting ...");

    }

}