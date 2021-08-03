package Fifo;


import frontEndModule.frontEndPOA;
import org.omg.CORBA.ORB;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class frontEndimpl extends frontEndPOA implements Serializable {

    private ORB orb;
    public void setORB(ORB orb_val) {
        orb = orb_val;
    }
    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }

    @Override
    public boolean createTRecord(String managerID, String firstName, String lastName, String Address, String Phone, String Specialization, String Location) {
        boolean flag = false;
        String messageString = ",1," + managerID + "," + firstName + "," + lastName + "," + Address + "," + Phone + "," + Specialization + "," + Location;
        String reply = sendUdpMessageWithRet(messageString, port);
        if (reply.equals("SUCCESS")){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean createSRecord(String managerID, String firstName, String lastName, String CoursesRegistered, String Status, String StatusDate) {
        return false;
    }

    @Override
    public boolean editRecord(String managerID, String recordID, String fieldName, String newValue) {
        return false;
    }

    @Override
    public boolean printRecord(String ManagerID) {
        return false;
    }

    @Override
    public boolean transferRecord(String managerID, String recordID, String remoteCenterServerName) {
        return false;
    }

    @Override
    public String getRecordCounts() {
        return null;
    }

    public String sendUdpMessageWithRet(String message, int serverPort) {
        String recvStr = "";
        DatagramSocket clientSocket = null;
        try {


            clientSocket = new DatagramSocket();
            byte[] sendData = new byte[1000];
            sendData = message.getBytes();
            InetAddress clientHost = InetAddress.getByName("127.0.0.1");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientHost, serverPort);
            clientSocket.send(sendPacket);

            // receiving process
            byte[] recvBuf = new byte[1000];
            DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
            clientSocket.receive(recvPacket);
            recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
            System.out.println(recvStr);
            if(recvStr.equals("Transfer success")){
                recvStr = "success";
            }
            clientSocket.close();

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (clientSocket != null) {
                clientSocket.close();
                clientSocket = null;
            }
        }

        return recvStr;
    }

    public void UDPServer(int port) {

        try {
            DatagramSocket server = null;
            server = new DatagramSocket(port);
            byte[] recvBuf = new byte[1000];
            String sendStr = "5053 HELLO";

            while (true) {
                DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
                server.receive(recvPacket);
                String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
                System.out.println(recvStr);
                int Sendport = recvPacket.getPort();
                if (recvStr != null && recvStr.startsWith("getCount")) {

                    sendStr = getCountForUDP(recvStr);

                } else if (recvStr != null && recvStr.startsWith("Transfer")) {
                    boolean result = transferForUDP(recvStr);
                    if(result){
                        sendStr = "Transfer success";
                    }else{
                        sendStr = "Transfer fail";
                    }
                }
                InetAddress addr = recvPacket.getAddress();

                byte[] sendBuf = sendStr.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, Sendport);
                server.send(sendPacket);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
