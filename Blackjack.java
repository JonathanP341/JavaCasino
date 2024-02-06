import java.util.*;
import java.io.*;
import java.math.*;
public class Blackjack extends State {
    private double bet;
    private double winnings;
    private int games;

    //Constructor
    public Blackjack(Game g, Player p) {
        super(g,p);
        bet = 2.0; //Will eventually have a way to hold this
        winnings = 0.0;
        games = 0;
    }

    public void render() {
        scroll();
        if (getGame().getPlaying() == false) {
            System.out.println("****************************");
            System.out.println("*        BLACK JACK        *");
            System.out.println("****************************\n");

        } else {
            //Have to actually render it here/display the options
            System.out.println("-----BLACK JACK-----\n");
            System.out.println("Current Balance: $" + getPlayer().getBalance());
            System.out.println("Bet: $" + bet);
            System.out.println("1) Play Blackjack");
            System.out.println("2) Add Balance");
            System.out.println("3) Change Bet");
            System.out.println("4) Exit Game");
        }
    }

    public void update() {
        Scanner userInput = new Scanner(System.in);
        boolean done = false;
        int choice = 0;
        if (getGame().getPlaying() == false) {
            delay(1500);
            getGame().setPlaying(true);
        } else {
            while (done == false) {
                try { //Getting the input of the user
                    String text = userInput.nextLine();
                    choice = Integer.parseInt(text);

                    if (choice > 0 && choice < 5) {
                        done = true;
                    } else {
                        System.out.println("ERROR 6 - ENTER VALID VALUE");
                    }
                } catch(Exception e) {
                    System.out.println("ERROR 5 - ENTER PROPER VALUE");
                }
            }

            //The control switch to determine what the user does
            switch(choice) {
                case 1: //Playing blackjack
                    play();
                    break;
                case 2: //Adding balance to the player
                    System.out.println("\n"); //Adding space before the balance
                    getPlayer().addBalance();
                    break;
                case 3: //Adding money
                    System.out.println("\n"); //Adding space before the balance
                    System.out.print("New Bet: ");
                    double newBet = userInput.nextDouble();
                    setBet(newBet);
                    break;
                case 4: //Exit the state
                    exitState();
                    getGame().setPlaying(false);
                    break;
            }
        }
    }

    private void play() {
        //Have to make the playing loop here
        Deck deck = new Deck(); //Making a deck
        //Game loop from here, always assume its just you vs dealer
        ArrayList<Card> dealerHand = new ArrayList<Card>();
        ArrayList<Card> userHand = new ArrayList<Card>();
        //Holding the value of the players cards
        int dealerValue = 0;
        int userValue = 0;

        //Removing the bet from the player
        getPlayer().loseMoney(bet);
        winnings -= bet;
        games += 1;

        //Getting the deck for the dealer and user
        for (int i = 0; i < 2; i++) {
            userHand.add(deck.getRandomCard());
            dealerHand.add(deck.getRandomCard());
        }
        //Flipping the cards so we can see the required cards
        userHand = flip(userHand);
        dealerHand.get(0).flip();

        //Counting the value of each players respective hand
        dealerValue = countCards(dealerHand);
        userValue = countCards(userHand);

        //Displaying each respective hand
        System.out.println("Dealer's Hand: " + displayHand(dealerHand));
        System.out.println("User's Hand: " + displayHand(userHand));

        //Checking for the special conditions
        boolean split = false;
        boolean doubleDown = false;
        boolean insurance = false;
        if (split(userHand)) { //user choose split
            split = true;
            //Continue from here with split
            //Ignoring for now
        } else if (doubleDown(userHand)) { //User choose doubleDown
            doubleDown = true;
            userHand = doubleDownPlay(userHand); //Doubling down, gives the user 1 more card
        } else if (insurance(dealerHand)) { //user choose insurance
            insurance = true;
            //Getting cards normally
            userHand = normalPlay(userHand);
            //Bet half on insurance
            insurancePlay(dealerHand);
        } else { //Normal game
            userHand = normalPlay(userHand);
        }
        userValue = countCards(userHand);
        //Checking for loss
        if (userValue > 21) {
            System.out.println("You got over 21, you lost!!");
        } else {
            //Getting the dealers hand
            dealerHand.get(1).flip(); //Flipping the
            dealerHand = dealerPlay(dealerHand);
            findWinner(userHand, dealerHand, doubleDown);
        }
        delay(1500);
    }

    private void findWinner(ArrayList<Card> userHand, ArrayList<Card> dealerHand, boolean doubleDown) {
        int userValue = countCards(userHand);
        int dealerValue = countCards(dealerHand);
        //Displaying the hand of the user and dealer
        System.out.println("\n\nUser's Hand: " + displayHand(userHand));
        System.out.println("Dealer's Hand: " + displayHand(dealerHand));

        if (doubleDown) { //If the user doubled down
            if (dealerValue > 21 || userValue > dealerValue) {
                System.out.println("You win $" + (bet * 4) + "!!!!");
                winnings += 4 * bet;
                getPlayer().addWinnings(bet * 4);
            } else if (dealerValue > userValue) {
                System.out.println("You lost! Better luck next round");
            } else {
                System.out.println("You tie! You get your money back");
                winnings += bet * 2;
                getPlayer().addWinnings(bet * 2);
            }
        } else { //Otherwise, not doubled down
            if (dealerValue > 21 || userValue > dealerValue) {
                System.out.println("You win $" + (bet * 2) + "!!!!");
                winnings += 2 * bet;
                getPlayer().addWinnings(bet * 2);
            } else if (dealerValue > userValue) {
                System.out.println("You lost! Better luck next round");
            } else {
                System.out.println("You tie! You get your money back");
                winnings += bet;
                getPlayer().addWinnings(bet);
            }
        }
    }
    private ArrayList<Card> dealerPlay(ArrayList<Card> dealerHand) {
        Deck deck = new Deck();
        int dealerValue = countCards(dealerHand);

        System.out.println("\n\n---Dealer's Turn---");

        while (dealerValue < 17) {
            System.out.println(displayHand(dealerHand));
            Card c = deck.getRandomCard();
            c.flip();
            dealerHand.add(c);
            dealerValue = countCards(dealerHand);
            delay(750);

        }
        System.out.println(displayHand(dealerHand));
        System.out.println("");
        delay(750);
        return dealerHand;
    }

    private void insurancePlay(ArrayList<Card> dealerHand) {
        winnings -= bet / 2;
        getPlayer().loseMoney(bet/2);
        if (dealerHand.get(1).BJValue() == 10) { //If the second card is a face card
            System.out.println("You won insurance!");
            System.out.println("You win $" + bet);
            //
            winnings += bet;
            getPlayer().addWinnings(bet);
        } else {
            System.out.println("You lost the insurance side bet.");
        }

    }

    private ArrayList<Card> doubleDownPlay(ArrayList<Card> hand) {
        Deck deck = new Deck();
        //Removing the bet from the player because they doubled down
        getPlayer().loseMoney(bet);
        winnings -= bet;
        //Adding one new card to the users hand then displaying it
        hand.add(deck.getRandomCard());
        System.out.println(displayHand(hand));
        hand.get(2).flip();
        System.out.println(displayHand(hand));
        return hand;
    }

    private ArrayList<Card> normalPlay(ArrayList<Card> hand) {
        boolean stand = false;
        int choice = 0;
        int userValue = countCards(hand);
        Deck deck = new Deck();

        while (stand == false && userValue < 22) {
            choice = gameOptions();
            switch (choice) {
                case 1:
                    Card c = deck.getRandomCard();
                    c.flip();
                    hand.add(c);
                    userValue = countCards(hand);
                    break;
                case 2:
                    stand = true;
                    break;
            }
            System.out.println(displayHand(hand));
        }
        return hand;
    }

    private boolean insurance(ArrayList<Card> hand) {
        Scanner userInput = new Scanner(System.in);
        if (hand.get(0).getLabel().equals("A")) {
            System.out.print("Use Insurance?(Y/N): ");
            String str = userInput.nextLine();
            if (str.equalsIgnoreCase("y")) {
                return true;
            }
        }
        return false;
    }
    private boolean split(ArrayList<Card> hand) {
        Scanner userInput = new Scanner(System.in);
        if (hand.get(0).getLabel().equals(hand.get(1).getLabel())) {
            System.out.print("Split the deck?(Y/N): ");
            String str = userInput.nextLine();
            if (str.equalsIgnoreCase("y")) {
                return true;
            }
        }
        return false;
    }

    //Supposed to just get one card when you double down, but ill add that later
    private boolean doubleDown(ArrayList<Card> hand) {
        Scanner userInput = new Scanner(System.in);
        if (countCards(hand) > 8 && countCards(hand) < 12) {
            System.out.print("Double Down?(Y/N): ");
            String str = userInput.nextLine();
            if (str.equalsIgnoreCase("y")) {
                return true;
            }
        }
        return false;
    }

    private int gameOptions() {
        System.out.println("\n------------------------");
        System.out.println("1) Hit");
        System.out.println("2) Stand");
        delay(1000);

        Scanner userInput = new Scanner(System.in);
        int choice = 0;
        boolean done = false;
        while (done == false) { //Getting the proper value
            try { //Getting the input of the user
                String text = userInput.nextLine();
                choice = Integer.parseInt(text);

                if (choice > 0 && choice < 3) {
                    done = true;
                } else {
                    System.out.println("ERROR 6 - ENTER VALID VALUE");
                }
            } catch(Exception e) {
                System.out.println("ERROR 5 - ENTER PROPER VALUE");
            }
        }
        return choice;
    }

    private String displayHand(ArrayList<Card> hand) {
        String str = "";
        for (int i = 0; i < hand.size(); i++) {
            str += hand.get(i).toString();
            str += " ";
        }
        str += "\nDeck Value: " + countCards(hand);
        return str;
    }

    private ArrayList<Card> flip(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).flip();
        }
        return hand;
    }
    private int countCards(ArrayList<Card> hand) {
        int count = 0;
        int aceCt = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getFlipped() == true) {
                count += hand.get(i).BJValue();
                if (hand.get(i).getLabel().equals("A")) { //Checking for the amount of aces in the hand
                    aceCt += 1;
                }
            }
        }
        //Checking for the amount of aces
        while (aceCt > 0 && count > 21) {
            aceCt -= 1;
            count -= 10;
        }

        return count;
    }

    public void setBet(double newBet) {
        bet = newBet;
    }
    public double getBet() {
        return bet;
    }

    public void addWinnings(double newWinnings) {
        winnings += newWinnings;
    }
    public double getWinnings() {
        return winnings;
    }

    public int getGames() {return games;}
}
