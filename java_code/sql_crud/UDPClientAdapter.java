package sql_crud;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClientAdapter implements CRUD<Integer, String> {
	private final DatagramSocket clientSocket;
	private final InetSocketAddress serverAddress;
	private byte[] receivedData;

	public UDPClientAdapter(InetSocketAddress inetSocketAddress) throws IOException {
		serverAddress = inetSocketAddress;
		clientSocket = new DatagramSocket();
		communicateInfo("0 ");
	}

	@Override
	public Integer create(String data) {
		try { communicateInfo("0 " + data);}
		catch (IOException e) {System.err.println("a problem communicating with server");}
		System.err.println("DEBUG: Integer LineNum sent is: " + new String(receivedData).trim());
		return Integer.parseInt(new String(receivedData).trim());
	}

	/** unused */
	@Override
	public String read(Integer key) {
		return null;
	}

	/** unused */
	@Override
	public String update(Integer key, String newData) {
		return null;
	}

	/** unused */
	@Override
	public String delete(Integer key) {
		return null;
	}

	private void communicateInfo(String strToSend) throws IOException {
		receivedData = new byte[8];
		byte[] toSend = strToSend.getBytes();
		DatagramPacket readyDatagram = new DatagramPacket(toSend, toSend.length, serverAddress);
		clientSocket.send(readyDatagram);
		System.err.println("DEBUG: client data sent");

		DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
		clientSocket.receive(receivedPacket);
		System.err.println("DEBUG: answer received from server");
	}

	@Override
	public void close() {
		clientSocket.close();
	}
}
