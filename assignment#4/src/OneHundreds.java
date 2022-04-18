import java.io.IOException;
import java.util.*;

/**
 * simple class to run the game.
 * @author mohamed
 */
public class OneHundreds {

        ArrayList<String> players = new ArrayList<>();
        Map<String, Integer> scores = new HashMap<>();
        CardDeck cardDeck = new CardDeck();
        ArrayList<Card> deck;
        ArrayList<String> winners = new ArrayList<>();
        String gameState = "pregame";
        int currentPlayer = 0;
        int currentRound = 1;

        ArrayList<Card> cardsPlayedInRound = new ArrayList<>();
        List<Card> wildCardsInRound = new ArrayList<>();
        ArrayList<String> resultsOutput = new ArrayList<>();

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public Card dealCard(String player) throws InterruptedException {
        while(true) {
            Card card ;
            if (cardDeck.CardsRemaining(deck) > 0) {
                System.out.println("hello " + Thread.currentThread().getName());
                card = deck.get(0);
                System.out.println(players.get(currentPlayer) + " was dealt " + deck.get(0).getValue() + deck.get(0).isWildCard());
                deck.remove(0);
                if (currentPlayer == 3) {
                    currentPlayer = 0;
                } else {
                    currentPlayer++;
                }
                return card;
            }
        }

    }
    /**
     * remove card from player
     * @param playerList player list
     */
    private static void removeCardFromHand(List<Player> playerList) {
        for (Player player : playerList) {
            System.out.println(player.getName() + "\tCard " + player.getHand().get(0).getValue() +
                    player.getHand().get(0).isWildCard());
            player.getHand().removeFirst();
        }
    }
    /** This adds a card (played by a client) to the cardsPlayedInRound list
     * @param cardPlayed This represents the card that was played by the client/player
     */
    public void playRound(Card cardPlayed) {
        cardsPlayedInRound.add(cardPlayed);

        if (cardPlayed.isWildCard()) {
            wildCardsInRound.add(cardPlayed);
        }

        if (currentPlayer == 3) {
            currentPlayer = 0;
            currentRound++;
        } else {
            currentPlayer++;
        }
    }

    /**
     * get the winner every round
     * @param playerList  players list
     * @return the winner player
     */
    public static Player winnerPlayer(List<Player> playerList){
        Player winnerPlayer = playerList.get(0);
        for (int i = 1; i < playerList.size(); i++) {
            Card winnerCard = winnerPlayer.getHand().getFirst();
            Card playerCard = playerList.get(i).getHand().getFirst();
            if(winnerCard.compareTo(playerCard)!=1)
                winnerPlayer = playerList.get(i);
        }
        return winnerPlayer;
    }
    /** Method used to retrieve the results of a singular round to the client
     * @return resultsOutput The ArrayList that holds strings of information to be sent to the client
     * */
    public ArrayList<String> displayRoundResults(){
        Card lowestValuedWildCard = null;
        resultsOutput.clear();

        if (wildCardsInRound.size() > 0) {
            lowestValuedWildCard = wildCardsInRound.get(0);
            if (wildCardsInRound.size() > 1) {
                for (Card wildCard : wildCardsInRound) {
                    if (wildCard.getValue() < lowestValuedWildCard.getValue()) {
                        lowestValuedWildCard.setWildCard(true);
                    }
                }
            }
        }
        int winningPlayerIndex = 0;
        if (lowestValuedWildCard == null) {
            Card highestCardInRound = cardsPlayedInRound.get(0);
            for (Card card : cardsPlayedInRound) {
                if (card.getValue() > highestCardInRound.getValue()) {
                    highestCardInRound = card;
                }
            }
            winningPlayerIndex = cardsPlayedInRound.indexOf(highestCardInRound);
        } else {
            winningPlayerIndex = cardsPlayedInRound.indexOf(lowestValuedWildCard);
        }


        String winningPlayerName = players.get(winningPlayerIndex);
        if (currentPlayer == 0) {
            scores.put(winningPlayerName, scores.get(winningPlayerName) + 1);
        }
        resultsOutput.add("");
        for(int i = 0; i < 4; i++){
            resultsOutput.add(players.get(i) + ": " + cardsPlayedInRound.get(i).getValue() + " " + cardsPlayedInRound.get(i).isWildCard());
        }
        resultsOutput.add("");
        resultsOutput.add(winningPlayerName + " has won the hand");
        resultsOutput.add("");
        if (currentPlayer == 3){
            cardsPlayedInRound.clear();
            wildCardsInRound.clear();
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }

        return resultsOutput;
    }
    public ArrayList<String> displayGameResults(){
        resultsOutput.clear();
        resultsOutput.add("=== End of Game ===");
        resultsOutput.add("Scores");
        displayScores();

        if (currentPlayer == 0) {
            determineWinners();
        }

//        resultsOutput.add("");
        if (winners.size() > 1){
            //System.out.println("\nWinners: ");
            String outputLine = "Winners: ";
//            out.println(outputLine);
            resultsOutput.add(outputLine);
            int counter = 0;
            for (String winner: winners) {
                if (counter != winners.size() - 1) {
                    //System.out.print(winner + ", ");
                    outputLine += winner + ", ";
                } else {
                    //System.out.print(winner + "\n");
                    outputLine += winner;
                }
                counter++;
            }
            resultsOutput.add(outputLine);
        } else {
            String outputLine = "Winner: " + winners.get(0);
            resultsOutput.add(outputLine);
            //System.out.println("\nWinner: " + winners.get(0));
        }

        String outputLine = "Score: " + scores.get(winners.get(0));
        resultsOutput.add(outputLine);
        //System.out.println("Score: " + scores.get(winners.get(0)));

        displayRemainingCards();

        if (currentPlayer == 3) {
            currentPlayer = 0;
            currentRound++;
            gameState = "postGame";
        } else {
            currentPlayer++;
        }
        return resultsOutput;
    }

    /**
     * update the winner score
     * @param playerList  player list
     * @param scoreMap score of players
     */
    private static void updateWinnerScore(List<Player> playerList, Map<String, Integer> scoreMap) {
        Player wins = winnerPlayer(playerList);
        Integer winsScore = scoreMap.get(wins.getName());
        winsScore = winsScore + 1;
        scoreMap.put(wins.getName(), winsScore);
        removeCardFromHand(playerList);
        System.out.printf("Winner is %s.\n", wins.getName());
        System.out.println("Player "+wins.getName()+" Gets A point ");
    }

    /** Method used to determine the winner(s) of the game */
    public void determineWinners(){
        int winningScore = Collections.max(scores.values());
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue()==winningScore) {
                winners.add(entry.getKey());
            }
        }
    }
    public void displayRemainingCards(){
        //System.out.println("\nCards remaining in deck: ");
        String outputLine = "\nCards remaining in deck: ";
        resultsOutput.add(outputLine);
        ArrayList<String> cardsRemaining = new ArrayList<>();
        if(cardDeck.CardsRemaining(deck) > 0) {
            cardsRemaining = cardDeck.PrintDeck(deck);
            for(String card : cardsRemaining){
                resultsOutput.add(card);
            }
        } else {
            //System.out.println("None");
            outputLine = "None";
            resultsOutput.add(outputLine);
        }
    }
    public void displayScores(){
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            //System.out.println(entry.getKey() + " - " + entry.getValue());
            String outputLine = entry.getKey() + " - " + entry.getValue();
            resultsOutput.add(outputLine);
        }
        resultsOutput.add("");
    }

}
