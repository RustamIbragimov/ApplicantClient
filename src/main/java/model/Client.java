package model;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ribra on 11/11/2015.
 */
public class Client {
    private static int portNumber;
    private static String ipAddress;
    private static Client instance = new Client();
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    final private static byte EXIT = 0;
    final private static byte SEARCH_BY_NUMBER = 1;
    final private static byte ATTENDANCE = 2;
    final private static byte PHOTO = 3;

    public Client() {}

    public void build(int portNumber, String ipAddress) {
        this.portNumber = portNumber;
        this.ipAddress = ipAddress;
        connectToServer();
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket(ipAddress, portNumber);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Person> getByNumber(String number) throws IOException {
        sendRequest(number, SEARCH_BY_NUMBER);

        List<Person> list = getList();
        return list;
    }

    public void setAttended(String number) throws IOException {
        sendRequest(number, ATTENDANCE);
    }

    public void updatePhoto(Person person) throws IOException{
        sendRequest(person, PHOTO);
    }

    public void sendRequest(Object obj, byte type) throws IOException {
        out.writeByte(type);
        out.writeObject(obj);
    }

    public void exit() throws IOException {
        out.writeByte(EXIT);
        out.writeObject(new Object());

        in.close();
        out.close();
    }


    @SuppressWarnings("unchecked")
    private static List<Person> getList() {
        List<Person> list = null;
        try {
            list = (LinkedList<Person>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static Client getInstance() {
        return instance;
    }
}
