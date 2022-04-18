import java.io.Serializable;
import java.util.*;

/**
 * simple card deck class
 * @author mohamed
 */
public class CardDeck implements Serializable {
    public ArrayList<Card> GenerateDeck(){
        //array list for cards
        ArrayList<Card> cards = new ArrayList<>();
        Random random = new Random();
        // array for 4 cards
        int[] numbers = new int [4];
        for (int i = 0; i < 4 ; i++) {
            numbers[i] = random.nextInt(100)+1;
        }
        for (int k = 1; k <=100 ; k++) {
            Card card ;
            // check if the card in array numbers will be Wild card
            if (k==numbers[0]||k ==numbers[1]||k ==numbers[2]||k ==numbers[3]){
                card = new Card(k, true);
            }
            else {
                card = new Card(k, false);
            }
            cards.add(card);
        }
        return cards;
    }

    /**
     * shuffle the deck
     * @param cards cards to be shuffled
     * @param number of shuffle round
     */
    public void ShuffleDeck(ArrayList<Card> cards, int number){
        for(int i = 1; i <= number ; i++) {
            Collections.shuffle(cards);}
    }

    /**
     * @param deck card object in array list
     * @return
     */
    public ArrayList<String> PrintDeck(ArrayList<Card> deck){
        ListIterator<Card> iterator = deck.listIterator();
        ArrayList<String> cardsRemaining = new ArrayList<>();
        iterator.forEachRemaining((card) -> {
            cardsRemaining.add(card.getValue() + " " + card.isWildCard());
        });
        return cardsRemaining;
    }
    /**
     * the remaining cards in deck
     * @param deck get deck to check
     * @return size of the deck
     */
    public int CardsRemaining(ArrayList<Card> deck){
        return deck.size();
    }

}
