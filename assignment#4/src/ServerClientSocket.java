import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * simple class to run the communication between the server and client.
 * @author mohamed
 */
public class ServerClientSocket implements Runnable{
    String socketName;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    OutputStream outputStream;
    ObjectOutputStream objectOutput;
    InputStream inputStream;
    ObjectInputStream objectInput;
    OneHundreds game;

    /** Creates an employee with the specified name.
     * @param socket The server-side client socket that was instantiated in Server.java
     * @param game Reference to a game object used.
     */
    public ServerClientSocket(Socket socket, OneHundreds game) throws IOException {
        this.socket = socket;
        this.game = game;
        socketSetup();
    }

    /** Sets up the input/output streams for the socket */
    public void socketSetup() throws IOException {
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outputStream = this.socket.getOutputStream();
        this.objectOutput = new ObjectOutputStream(this.outputStream);
        this.inputStream = this.socket.getInputStream();
        this.objectInput = new ObjectInputStream(this.inputStream);
    }

    synchronized void getPlayerName() throws IOException {
        String inputLine, outputLine;
        System.out.println(Thread.currentThread().getName());
        outputLine = "Welcome to One Hundreds! Please enter your name:";
        this.out.println(outputLine);
        String name = "";
        while ((inputLine = this.in.readLine()) != null) {

                name = inputLine;
                this.out.println("Waiting for player >>>");
                outputLine = "Good";
                this.out.println(outputLine);
                game.players.add(name);
                game.scores.put(name, 0);
                this.socketName = name;
                System.out.println(name + " has been added to the game");
                break;

        }
    }

    synchronized void getCard()throws IOException{
        this.objectOutput.writeObject(game.dealCard(this.socketName));

    }

    synchronized  void playRound() throws IOException{
        System.out.println("Thread for " + this.socketName + " in playRound(): " + Thread.currentThread().getName());
        String inputLine, outputLine;
        if (game.currentRound == 1) {
            outputLine = "Press Enter key to begin!";
        } else {
            outputLine = "Press Enter key to continue!";
        }
        this.out.println(outputLine);

        while (this.in.readLine() != null) {

            outputLine = "Round #" + game.currentRound;
            this.out.println(outputLine);
            Card cardToPlay = null;
            try {
                cardToPlay = (Card) this.objectInput.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            game.playRound(cardToPlay);
            if(!this.socketName.equals(game.players.get(game.players.size()-1))){
                this.out.println("Waiting on opponents...");
            }
            break;
        }


    }
    synchronized void displayRoundResults(){
        ArrayList<String> results;
        while (true){
            if (this.socketName.equals(game.players.get(game.currentPlayer))) {
                results = game.displayRoundResults();
                break;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                }
            }
        }

        for (String resultsLine : results){
            this.out.println(resultsLine);
        }
    }


    /** The run method of the class. It will handle all multithread requests as each thread proceeds through the method.
     * This will make use of game variables to ensure that each threads are in control when they need to be.
     * */
    @Override
    public void run() {
        if (game.gameState.equals("pregame")) {
            try {
                getPlayerName();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (game.players.size() == 4) {
                game.gameState = "deal";
            }
        String playerString = "";
        for (String player : game.players){
            playerString += player + ", ";
        }
        System.out.println("Players: " + playerString);
    }}
}
