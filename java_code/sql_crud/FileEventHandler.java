package sql_crud;

/*
  The main component of the file Monitor & remote backUp Application.
  on instantiation it should be provided with a String path to a file to be monitored.
  it monitors every change in the file, and sends all the changes to a backup database.

  The EventHandler holds a DirectoryMonitor Object, and an Adapter to connect it with the DB server.
  on receiving a change event, it reads the updated part of the original file,
  and sends it to be written in the DB by the SQServer.
 */


import observer.CallBack;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

public class FileEventHandler {
    private DirectoryMonitor dirMonitor;
/* for DEBUG	private final String backUp = "C:\\Users\\WIN10\\IdeaProjects\\WorkSpace\\src\\IOTProject\\sqlCrud\\backup.txt"; */
	private final CRUD<Integer,String> udpCrud; /* for DEBUG	new FileCRUDImp(backUp); */
	private final Path monitoredFilePath;
	private final String origFile;
	private final BufferedReader lineReader;
	private Thread monitorThread;

	/**
	 * Constructor
	 * @param origFile - a string of a path to the original file.
	 * @throws IOException thrown when there is problem with one of the files.
	 */
    public FileEventHandler(String origFile, String host, int port) throws IOException{
		makeSureFileExists(origFile);
		this.origFile = origFile;
		monitoredFilePath = Paths.get(origFile);   //new File(origFile).toPath();
		udpCrud = new UDPClientAdapter(new InetSocketAddress(host, port));
		lineReader = new BufferedReader(new FileReader(origFile));
		backUpLinesFromOrigFile();
		initDirMonitor();
	}

	private void makeSureFileExists(String origFilePathString) throws IOException {
		if (!new File(origFilePathString).isFile()) {
			throw new IOException();
		}
	}

	private void backUpLinesFromOrigFile() throws IOException {
		while (true){
			String line = lineReader.readLine();

			if (line == null) {
				break;
			}

			udpCrud.create(line);
		}
	}

	private void initDirMonitor() throws IOException {
		dirMonitor = new DirectoryMonitor(origFile);
		monitorThread = new Thread(dirMonitor);
		monitorThread.start();
		CallBack<WatchKey> callBack = new CallBack<>(this::handleEventInDirectory);
		dirMonitor.register(callBack);
	}

	/**
	 * checks whether the change occurred in monitored file.
	 * @param watchKey - a key that contains all the information about the given event.
	 */
	private void handleEventInDirectory(WatchKey watchKey) {
		for (WatchEvent<?> event : watchKey.pollEvents()) {
			Path eventFilePath = (Path)event.context();

			if (eventFilePath.equals(monitoredFilePath.getFileName())) {
				try {
					backUpLinesFromOrigFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

    /**
     * stops the monitoring of the file, closes the streams to the file.
     */
    public void stop(){
    	dirMonitor.stopMonitoring();
    	try {monitorThread.join();}		catch (InterruptedException ignored) {}
		try {lineReader.close();}		catch (IOException e) {e.printStackTrace();}
		try {udpCrud.close();}			catch (Exception e) {e.printStackTrace();}
    }
}