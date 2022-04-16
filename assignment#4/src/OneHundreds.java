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
     * dealing the card for player
     * @param player get player name
     * @return  card for the player
     */
    public Card dealCard(String player ) throws IOException {
        while(true) {
            Card card = null;
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

    /**
     * get winner
     * @param scoreMap a score hashmap
     * @param finalMaxScore final max score
     * @return a list of final winner
     */
    public static List<String> getFinalWinner(Map<String, Integer> scoreMap, int finalMaxScore){
        List<String> finalWinners = new ArrayList<>();
        Set<String> playersName = scoreMap.keySet();
        for (String playerName: playersName
        ) {
            if(scoreMap.get(playerName) == finalMaxScore){
                finalWinners.add(playerName);
            }
        }
        return finalWinners;
    }

    /**
     * get max score
     * @param scoreMap a score hashmap
     * @return the max score
     */
    public static Integer getFinalMaxScore(Map<String, Integer> scoreMap){
        Set<String> nameOfPlayer = scoreMap.keySet();
        int maxScore = 0;
        for (String playerName: nameOfPlayer
        ) {
            if(scoreMap.get(playerName) > maxScore){
                maxScore = scoreMap.get(playerName);
            }
        }
        return maxScore;
    }

    /**
     * print the winner
     * @param finalMaxScore  max score
     * @param finalWinners winner name
     */
    private static void printFinalResult(int finalMaxScore, List<String> finalWinners) {
        StringBuilder statement = new StringBuilder("The winner is: ");
        for (String finalWinner : finalWinners) {
            statement.append(finalWinner).append(" Score: ").append(finalMaxScore);
        }
        System.out.println(statement);
        System.out.println("Thanks for Playing ");
    }

}
