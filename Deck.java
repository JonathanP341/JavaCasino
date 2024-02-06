import java.util.*;
public class Deck {
    private ArrayList<Card> deck;

    //Creating a deck either with suits or without, depends
    public Deck() {
        deck = new ArrayList<Card>(); //Initializing the deck
        //Creating the values the card can have to loop through later
        String[] labels = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"H", "D", "S", "C"};
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                Card card = new Card(labels[i], suits[j], false);
                deck.add(card);
            }
        }
    }

    //Methods that do something
    public Card getRandomCard() {
        Random rand = new Random();
        //Gets a random card from within the size of the deck, cant count cards in this
        int intRandom = rand.nextInt(deck.size());
        return deck.get(intRandom);
    }



    //Getters
    public ArrayList<Card> getDeck() {
        return deck;
    }

}
