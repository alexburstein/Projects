package sql_crud;

/*
 * a component of the file Monitor & remote backUp Application.
 * the HelloWorld.main DB-side component. should be run before the fileEventHandler.
 * it receives UDP data to write into the DB from the fileEventHandler.
 * holds an instance of CRUD implementation component, that interacts with the DB.
 * on initialization: - listens to incoming socket on one thread
 * 					  - listens to the user for termination requests.
 */

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SQLServerLogic implements Runnable{
	private DatagramSocket serverSocket;
	private DatagramPacket receivedPacket;
	private InetSocketAddress client;
	private byte[] dataToSend;
	private byte[] dataReceived;
	private final CRUD<Integer,String> crud;

	public SQLServerLogic() throws SQLException, ClassNotFoundException {
		this("jdbc:mysql://localhost:XXXX/","XXXXX","XXXXX","XXXXX",6666);
	}

	/**
	 *
	 * @param sqlUri the URI to the sql databases.
	 * @param user userName.
	 * @param password passWord.
	 */
	public SQLServerLogic(String sqlUri, String user, String password, String serverHost, int serverPort) throws SQLException, ClassNotFoundException {
		try { serverSocket = new DatagramSocket(serverPort, InetAddress.getByName(serverHost));}
		catch (SocketException | UnknownHostException e) {System.err.println("exception thrown on connection");}
		crud = new SqlCrudImp(sqlUri, user, password);
		System.err.println("DEBUG: server starting");
	}

	public void run()  {
		try{
			initConnectionWithClient();

			while (!serverSocket.isClosed()) {
				try{ receiveTransmission(); }
				catch (SocketException e){ continue; }
				preformRequestedTask();
				sendReply();
			}
		}
		catch (IOException e){ e.printStackTrace(); }
	}

	public void stop(){
		serverSocket.close();
	}

	private void initConnectionWithClient() throws IOException {
		try{ receiveTransmission(); }
		catch (SocketException e) { return; }
		client = new InetSocketAddress(receivedPacket.getAddress(),receivedPacket.getPort());
		dataToSend = "".getBytes();
		sendReply();
		System.err.println("DEBUG: datagram sent from server to client");
	}

	private void receiveTransmission() throws IOException {
		dataReceived = new byte[1024];
		receivedPacket = new DatagramPacket(dataReceived, dataReceived.length);
		serverSocket.receive(receivedPacket);
	}

	private void sendReply() throws IOException {
		serverSocket.send(new DatagramPacket(dataToSend, dataToSend.length, client.getAddress(), client.getPort()));
	}
	
	private void preformRequestedTask() throws IOException {
		String incomingDataString = new String(dataReceived, 0, receivedPacket.getLength());
		List<String> parsedStringList = Arrays.asList(incomingDataString.split(" ", 2));
		System.err.println("DEBUG: "+ incomingDataString + " action = " + parsedStringList.get(0) + ". msg = " + parsedStringList.get(1));
		String res;
		int action = Integer.parseInt(parsedStringList.get(0));

		switch (action) {
			case 0 -> {
				Integer response = crud.create(parsedStringList.get(1));
				res = Integer.toString(response);
			}
			case 1 -> {
				Integer num = Integer.parseInt(parsedStringList.get(1));
				res = crud.read(num);
			}
			case 2 -> {
				List<String> secondParseList = Arrays.asList(parsedStringList.get(1).split(" ", 2));
				res = crud.update(Integer.valueOf(secondParseList.get(0)), secondParseList.get(1));
			}
			case 3 -> res = crud.delete(Integer.valueOf(parsedStringList.get(1)));
			default -> throw new IOException("Input received not compatible with current communication protocol");
		}

		if (res == null) {res = "null";}
		dataToSend = res.getBytes();
		System.err.println("DEBUG: reply for action *" + action + "* is ready in dataToSend buffer " + res);
	}
}