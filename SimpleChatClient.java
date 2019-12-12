package cs326;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClient {
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	public SimpleChatClient(String port, int peer) throws IOException {
		socket = new Socket(port, peer);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void run() throws IOException{
		
		Scanner keyboard = new Scanner(System.in);
		
		boolean loop = true;
		
		System.out.println("Waiting (listening): ");
		String receivedMessage;
		String sendMessage;
		
		
		while (loop == true) {
			//reads
			receivedMessage = reader.readLine();
			
			
			System.out.println("Your friend said: " + receivedMessage);
			
			//if what's written is bye, set loop to false
			if (receivedMessage.equals("bye")) {
				System.out.println("Shutting down because your friend said bye");
				break;
				
			}
			
			//then writes
			System.out.print("Reply: ");
			sendMessage = keyboard.nextLine();
			
			//send the new message 
			writer.println(sendMessage);
			
			//if what's written is bye, set loop to false
			if (sendMessage.equals("bye")) {
				System.out.println("Shutting down because you want to leave...");
				break;

			}
		}
		socket.close();
		keyboard.close();
		
	}
	public static void main(String[] args) throws IOException {
		SimpleChatClient client = new SimpleChatClient("localhost", 2571);
		client.run();
	}
}
