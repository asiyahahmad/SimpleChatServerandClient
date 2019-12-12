package cs326;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SimpleChatServer {
	private ServerSocket server;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	public SimpleChatServer(int port) throws IOException {
		server = new ServerSocket(port);
		socket = server.accept();
		writer = new PrintWriter(socket.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}
	
	public void run() throws IOException {
		
		Scanner keyboard = new Scanner(System.in);
		
		boolean keepChatting = true;
		
		System.out.print("Begin chatting: ");
		
		String sendMessage;
		String receivedMessage;
		
		//count used to display the reply after the first chat message 
		int count = 0; 
		//loop 
		while (keepChatting == true) {
			//to write
			if (count > 0) {
				System.out.print("Reply: ");
			}
			
			//read  the next line 
			sendMessage = keyboard.nextLine();
			
			//send the message
			writer.println(sendMessage);
			
			//if that message is bye 
			if (sendMessage.equals("bye") ) {
				//shut it down 
				System.out.println("Shutting down because you want to leave...");
				//leave the loop
				break;
			}
			
			//to read
			receivedMessage = reader.readLine();
			System.out.println("Your friend said: " + receivedMessage);
			
			//increase count to display the reply print
			count++;
			//if what's written is bye, set loop to false
			if (receivedMessage.equals("bye")) {
				System.out.println("Shutting down because your friend said bye");
				break;				
			}
			
		}
		//close everything 
		socket.close();
		server.close();
		keyboard.close();

	}

	public static void main(String[] args) throws IOException {
		SimpleChatServer serv = new SimpleChatServer(2571);
		serv.run();
	}
}
