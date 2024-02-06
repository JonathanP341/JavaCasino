public class Card {
    private String label; //ie A(ace), 2, 3, 4, 5... etc
    private String suit; //S(spade), D(diamond), H(Heart), C(club)
    private boolean flipped;

    //Constructors
    public Card(String l, String s, boolean f) {
        label = l;
        suit = s;
        flipped = f;
    }

    //Methods that do something

    //Getting the value of each of the cards, assuming that A is 11 for now
    public int BJValue() {
        if (label.equals("J") || label.equals("Q") || label.equals("K")) {
            return 10;
        } else if (label.equals("A")) { //Assuming that ace is 11 points
            return 11;
        } else {
            return Integer.parseInt(label);
        }
    }

    //Displaying the card in a string
    public String toString() {
        if (flipped == true) {
            return label + " of " + suit;
        } else {
            return "Unflipped";
        }
    }

    public boolean equals(Card other) {
        if (label.equals(other.getLabel()) && suit.equals(other.getSuit())) {
            return true;
        }
        return false;
    }

    public void flip() {
        if (flipped == false) {
            flipped = true;
        } else {
            flipped = false;
        }
    }

    //Getters and Setters
    public String getSuit() {
        return suit;
    }
    public String getLabel() {
        return label;
    }
    public boolean getFlipped() {return flipped;}
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public void setLabel(String label) {
        this.label = label;
    }

}
