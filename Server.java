import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class Server extends BaseTutorial{
  //setSize(1200,700);
   public static void main(String argv[]) throws Exception{
      Chat chat = new Chat();
      int aux;
      String clientInput;
      ServerSocket welcomeSocket = new ServerSocket(9090);
      ServerSocket welcomeSocket2 = new ServerSocket(9000);

      chat.messages.add("inicio");

      while(true){
         Socket conectionSocket = welcomeSocket.accept(); //Espera a conexão
         Socket conectionSocket2 = welcomeSocket2.accept(); //Espera a conexão

         BufferedReader clientString = new BufferedReader(new InputStreamReader(conectionSocket.getInputStream()));
         BufferedReader clientString2 = new BufferedReader(new InputStreamReader(conectionSocket2.getInputStream()));

         DataOutputStream serverToClient = new DataOutputStream(conectionSocket.getOutputStream());
         DataOutputStream serverToClient2 = new DataOutputStream(conectionSocket2.getOutputStream());

         clientInput = clientString.readLine();//Le uma linha do clienteInput
         if(clientInput.length()>5){
            if(!(clientInput.substring(0,5).equals("40404"))){
               chat.messages.add("1:" + clientInput);
            }
         }
         else{
            chat.messages.add("1:" + clientInput);;
         }

         clientInput = clientString2.readLine();//Le uma linha do clienteInput
         if(clientInput.length()>5){
            if(!(clientInput.substring(0,5).equals("40404"))){
               chat.messages.add("2:" + clientInput);
            }
         }
         else{
            chat.messages.add("2:" + clientInput);;
         }

         aux = chat.messages.size()- 20;
         if (aux<0) {
            aux = 0;
         }
         serverToClient.write(chat.messages.size()-aux);

         for(int i=aux;i<chat.messages.size();i++){
            serverToClient.writeBytes(chat.messages.get(i) + '\n');
         }

         aux = chat.messages.size()- 20;
         if (aux<0) {
            aux = 0;
         }
         serverToClient2.write(chat.messages.size()-aux);

         for(int i=aux;i<chat.messages.size();i++){
            serverToClient2.writeBytes(chat.messages.get(i) + '\n');
         }
      }
   }
}
