package com.springboot.web.problem;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Component
public class SubmitClient {
    String ip="52.79.239.248";
    Socket socket;
    InputStream in = null;
    OutputStream out = null;

    public int sendAndReceive(String str){
        try{
            socket = new Socket(ip, 3000);
            in = socket.getInputStream();
            out = socket.getOutputStream();

            out.write(str.getBytes());
            byte arr[] = new byte[100];
            in.read(arr);
            System.out.println("채점받은후 리턴 받은 채점 번호 " + new String(arr));

            return Integer.parseInt(new String(arr));

        } catch(Exception e){
            System.out.println(e.getMessage());
        } finally{
            try{
                in.close();
                out.close();
                socket.close();
            } catch(Exception e){}
        }
        return -1;
    }


}
