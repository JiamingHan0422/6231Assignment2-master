package Server;


import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;


public class DDOServer {

    public static void main(String args[]) {
        try{


            // create servant and register it with the ORB
            MethodImpl serverImpl = new MethodImpl("DDO");

            System.out.println("HelloServer ready and waiting ...");

            // wait for invocations from clients

            //orb.run();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    serverImpl.UDPServer(5051);
                }
            }).start();
        }

        catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

    }

}