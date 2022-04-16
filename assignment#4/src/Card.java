/**
 * simple Card class
 * @author mohamed
 */
public class Card {
    private final int value;
    private boolean wildCard;

    /**
     * Constructor creates card value and status
     * @param value card value
     * @param wildCard card status
     */
    public Card(int value, boolean wildCard) {
        this.value = value;
        this.wildCard = wildCard;
    }

    /**
     * Get card value
     * @return card value
     */
    public int getValue() {
        return value;
    }

    /**
     * Get card status
     * @return card status
     */
    public boolean isWildCard() {
        return wildCard;
    }

    /**
     * Set card status
     * @param wildCard card status
     */
    public void setWildCard(boolean wildCard) {
        this.wildCard = wildCard;
    }

    /**
     * compares two cards.
     * @param card card object to compare
     * @return the wining card
     */
    public int compareTo(Card card){

        if (this.wildCard && card.wildCard){
            return -(Integer.compare(this.value, card.value));
        }else if(this.wildCard){
            return 1;
        }else if(card.wildCard){
            return -1;
        }else{
            return Integer.compare(this.value, card.value);
        }
    }

}
