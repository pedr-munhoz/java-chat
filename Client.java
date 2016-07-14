import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import javax.imageio.*;


class Chat /*extends JFrame*/ {
   //JTextField new_message;
   ArrayList<String> messages = new ArrayList<String>();
   ArrayList<Integer> k_count = new ArrayList<Integer>();
   String next_message = new String();

   /*Chat(){

   }*/

   void refresh(){
      int aux = this.messages.size()-20;
      if(aux>0){
         for(int i=0;i<aux;i++){
            this.messages.remove(0);
         }
      }
   }
}

class Client extends Munhoz_Engine {
   Keyboard k = new Keyboard();
   Chat chat = new Chat();
   Character aux;
   int x;
   String InputInfo;
   String OutputInfo;
   Image backgroung_img;
   Integer zatd;

   BufferedReader entry = new BufferedReader(new InputStreamReader(System.in));
   int port = 0;
   Socket clientSocket;
   DataOutputStream clientToServer;
   BufferedReader serverString;

   public void init(){
      String aux = new String();
      chat.next_message=" ";
      setSize(400, 600);
      try {
         backgroung_img = ImageIO.read(new File("background.png"));
      } catch (IOException e) {
         System.out.println("A imagem nao pode ser carregada.");
      }

      try {
         port = zatd.decode(entry.readLine());
      } catch (IOException e) {
         System.out.println("nhe");
      }

      //port = 9090;

      //p.add(textField = new JTextField(15),BorderLayout.WEST);
   }

   public void paint(Graphics g) {
      chat.messages.clear();
      g.drawImage(backgroung_img,0,0, this);


      for (int i=32; i<=127; i++) {
         if(keyboard.keys.contains(i)){
            if(!chat.k_count.contains(i)){
               chat.next_message = chat.next_message + aux.toString(keyboard.keys_c.get(keyboard.keys.indexOf(i)));
               chat.k_count.add(i);
            }
         }
         else{
            int y = chat.k_count.indexOf(i);
            if(y!=-1){
               chat.k_count.remove(y);
            }
         }


      }

      if(keyboard.keys.contains(8)){
         if(!chat.k_count.contains(8)&&next_message.length()>0){
            chat.next_message = chat.next_message.substring(0,chat.next_message.length()-1);
            chat.k_count.add(8);
         }
      }
      else{
         int y = chat.k_count.indexOf(8);
         if(y!=-1){
            chat.k_count.remove(y);
         }
      }


      if(keyboard.keys.size()>0){
         if(keyboard.keys.get(keyboard.keys.size()-1)=='\n'){
            if (!chat.k_count.contains(27)) {
               //BufferedReader userString = new BufferedReader(new InputStreamReader(System.in));
               try {
                  clientSocket = new Socket("localhost", port);
                  clientToServer = new DataOutputStream(clientSocket.getOutputStream());
                  BufferedReader serverString = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                  //OutputInfo = userString.readLine();
                  clientToServer.writeBytes(chat.next_message + '\n');
                  x = serverString.read();
                  for (int i =0; i<x; i++) {
                     chat.messages.add(serverString.readLine());
                  }
                  //chat.refresh();
                  for (int i =chat.messages.size()-1; i>=0; i--) {
                     g.drawString("    "+chat.messages.get(i),0,10+20*(i));
                  }
                  chat.next_message=" ";
               } catch (IOException e) {
                  System.out.println("Host not avaliable");
               }
               chat.k_count.add(27);
            }
         }
         else {
            try {
               clientSocket = new Socket("localhost", port);
               clientToServer = new DataOutputStream(clientSocket.getOutputStream());
               BufferedReader serverString = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               //OutputInfo = userString.readLine();
               clientToServer.writeBytes("404040" + '\n');
               x = serverString.read();
               for (int i =0; i<x; i++) {
                  chat.messages.add(serverString.readLine());
               }
               //chat.refresh();
               for (int i =chat.messages.size()-1; i>=0; i--) {
                  g.drawString("    "+chat.messages.get(i),0,10+20*(i));
               }
            } catch (IOException e) {
               System.out.println("Host not avaliable");
            }
            int y = chat.k_count.indexOf(27);
            if(y!=-1){
               chat.k_count.remove(y);
            }
         }
      }

      else {
         try {
            clientSocket = new Socket("localhost", port);
            clientToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader serverString = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //OutputInfo = userString.readLine();
            clientToServer.writeBytes("404040" + '\n');
            x = serverString.read();
            for (int i =0; i<x; i++) {
               chat.messages.add(serverString.readLine());
            }
            //chat.refresh();
            for (int i =chat.messages.size()-1; i>=0; i--) {
               g.drawString("    "+chat.messages.get(i),0,10+20*(i));
            }
         } catch (IOException e) {
            System.out.println("Host not avaliable");
         }
         int y = chat.k_count.indexOf(27);
         if(y!=-1){
            chat.k_count.remove(y);
         }
      }
      g.drawString("    "+chat.next_message,0,550);
   }

   public static void main(String args[]){
      Munhoz_Engine canvas = new Client();
      new Start(canvas);
   }

}
