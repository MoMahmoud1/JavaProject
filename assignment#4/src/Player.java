import java.io.Serializable;
import java.util.LinkedList;

/**
 * simple player class
 * @author mohamed
 */
public class Player implements Serializable {
    private String name;
    private LinkedList<Card> hand;

    /**
     * player contracture
     * @param name add player name
     * @param hand add player hand list of card
     */
    public Player(String name, LinkedList<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name has been set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get hand
     * @return hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * set hand
     * @param hand has been set
     */
    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    /**
     * print cards in player hand
     */
    public void printHand(){
        hand.forEach(System.out::println);
    }
}
