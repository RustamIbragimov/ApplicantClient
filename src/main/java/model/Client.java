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
    private static DataInputStream in;
    private static DataOutput out;

    final private static byte SEARCH_BY_NUMBER = 1;
    final private static byte ATTENDANCE = 2;
    final private static byte PHOTO = 3;

    public Client() {}

    public void build(int portNumber, String ipAddress) {
        this.portNumber = portNumber;
        this.ipAddress = ipAddress;
        connectToServer();
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
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
        byte[] temp = Serializer.serialize(number);
        byte[] bytes = new byte[temp.length + 1];
        bytes[0] = SEARCH_BY_NUMBER;
        for (int i = 1; i < bytes.length; i++) {
            bytes[i] = temp[i - 1];
        }

        out.writeInt(bytes.length);
        out.write(bytes);

        List<Person> list = getList();
        return list;
    }

    public void setAttended(String number) throws IOException {
        byte[] temp = Serializer.serialize(number);
        byte[] bytes = new byte[temp.length + 1];
        bytes[0] = ATTENDANCE;
        for (int i = 1; i < bytes.length; i++) {
            bytes[i] = temp[i - 1];
        }

        out.writeInt(bytes.length);
        out.write(bytes);
    }

    public void updatePhoto(Person person) {

    }



    private static List<Person> getList() {
        List<Person> list = null;
        try {
            int length = in.readInt();
            if (length > 0) {
                byte[] bytes = new byte[length];
                in.readFully(bytes, 0, bytes.length);
                list = (LinkedList<Person>) Serializer.deserialize(bytes);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static Client getInstance() {
        return instance;
    }
}
