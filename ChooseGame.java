import java.util.Scanner;

public class ChooseGame extends State {
    public ChooseGame(Game g, Player p) {
        super(g, p);
    }

    public void render() {
        scroll();
        System.out.println("-----CHOOSE GAME-----\n");
        System.out.println("1) Slots");
        System.out.println("2) Blackjack");
        System.out.println("3) Crash");
        System.out.println("4) Back to Menu");
    }

    public void update() {
        Scanner userInput = new Scanner(System.in);
        int choice = 0;
        boolean done = false;
        while (done == false) { //Goes until it breaks
            try {
                String val = userInput.nextLine();
                choice = Integer.parseInt(val);

                if (choice > 0 && choice < 5) { //5 is the number of games
                    done = true;
                } else {
                    System.out.println("Value Error - Enter valid integer");
                }
            } catch (Exception e) {
                System.out.println("Type Error - Enter integer");
            }
        }
        //Resetting playing
        getGame().setPlaying(false);
        switch (choice) {
            case 1:
                TigerSlots ts = new TigerSlots(getGame(), getPlayer());
                ts.enterState();
                break;
            case 2:
                Blackjack bj = new Blackjack(getGame(), getPlayer());
                bj.enterState();
                break;
            case 3:
                //Play crash
                break;
            case 4:
                //Returning to the menu
                this.exitState();
                break;
        }
    }

    public String toString() {
        return "ChooseGame State";
    }
}
