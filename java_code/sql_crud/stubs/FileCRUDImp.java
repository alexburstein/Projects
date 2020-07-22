package sql_crud.stubs;

import sql_crud.CRUD;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCRUDImp implements CRUD<Integer,String> {
	private final RandomAccessFile randomAccess;
	private final FileReader fileReader;
	private final LineNumberReader lineNumReader;
	private final String backFile;

    public FileCRUDImp(String backupFile) throws IOException{
    	makeSureFileExist(backupFile);
		backFile = backupFile;
		randomAccess = new RandomAccessFile(backupFile, "rws");
		fileReader = new FileReader(backupFile);
		lineNumReader = new LineNumberReader(fileReader);
    }

	private void makeSureFileExist(String pathToCheck) throws IOException {
		if (!new File(pathToCheck).isFile()) {
			throw new IOException();
		}
	}

    @Override
    public Integer create(String data){
    	int lineNum = 0;
		try {
			randomAccess.skipBytes(Math.toIntExact(randomAccess.length()));
			randomAccess.write((data + System.lineSeparator()).getBytes());
			lineNum = getLineNum();
		}
		catch (IOException e) {e.printStackTrace();}

		return lineNum;
    }


	private Integer getLineNum() throws IOException {
		int lineNum;
		@SuppressWarnings("unused")
		long ignored = lineNumReader.skip(randomAccess.length());
		lineNum = lineNumReader.getLineNumber();

		return lineNum;
	}

	@Override
	public void close() throws IOException {
		randomAccess.close();
		lineNumReader.close();
		fileReader.close();
	}
    
    /**
     * not implemented
     */
    @Override
    public String read(Integer key){
        return null;
    }

	/**
	 * not implemented
	 */
    @Override
    public String update(Integer key, String newData) {
    	return null;
    }

	/**
	 * not implemented
	 */
    @Override
    public String delete(Integer key) {
    	return null;
    }

    public void eraseFileContent(){
		try {
			randomAccess.setLength(0);
		} catch (IOException e) {
			System.out.println("unable to erase file");
			e.printStackTrace();
		}
	}

	public void printFile(){
		try {
			Files.readAllLines(Paths.get(backFile), StandardCharsets.UTF_8).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
