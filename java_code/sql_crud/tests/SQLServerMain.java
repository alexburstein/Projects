package sql_crud.tests;

import sql_crud.SQLServerLogic;

import java.util.Scanner;

public class SQLServerMain {
    private static SQLServerLogic server;

    public static void main(String[] args) {
        Thread serverThread;

        try{
            serverThread = new Thread(new SQLServerLogic());
            serverThread.start();
            listenToUser();
            serverThread.join();
        }
        catch (Exception e){e.printStackTrace();}
    }

    private static void listenToUser(){
        System.out.println("Type \"exit\" to exit server");
        Scanner scanner = new Scanner(System.in);

        String exit = "exit";
        String userInput = null;

        while (!exit.equals(userInput)){
            userInput = scanner.nextLine();
        }

        scanner.close();
        server.stop();
    }
}
