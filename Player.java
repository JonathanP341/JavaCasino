import java.util.*;
public class Player {
    private double balance;
    private double winnings;

    //Constructor
    public Player() {
        //Need to get this info from a file in the future
        balance = 20;
        winnings = 0;
    }

    public void addBalance() {
        Scanner userInput = new Scanner(System.in);
        double newBal = 0;
        String input = "";
        while (true) { //Keeping going while getting input
            try {
                System.out.println("Current Balance - $" + balance);
                System.out.print("Add Balance: ");

                input = userInput.nextLine();
                newBal = Double.parseDouble(input);

                //Add to balance
                balance += newBal;

                //Display new balance
                System.out.print("New Balance: $" + balance);
                Thread.sleep(500);

                break;
            } catch (Exception e) {
                System.out.println("Error 4 - Invalid value");
            }
        }
    }

    public void addWinnings(double add) {
        winnings += add;
        balance += add;
    }

    public void loseMoney(double loss) {
        balance -= loss;
        winnings -= loss;
    }


    //Getters and Setters
    public double getBalance() {
        return balance;
    }
    public double getWinnings() {
        return winnings;
    }

    public void setBalance(int newBalance) {
        balance = newBalance;
    }
    public void setWinnings(int newWinnings) {
        winnings = newWinnings;
    }

}
