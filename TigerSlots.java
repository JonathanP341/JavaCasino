import java.util.*;
import java.io.*;
import java.math.*;
public class TigerSlots extends State {
    private double spinAmount;
    private double slotWinnings;
    private int rolls;
    public TigerSlots(Game g, Player p) {
        super(g, p);
        spinAmount = 0.2;

        try {
            File myFile = new File("TigerStats.txt");
            if (myFile.exists()) {
                Scanner reader = new Scanner(myFile);
                String data;

                //Skipping the first 2 lines
                for (int i = 0; i < 2; i++) {
                    reader.nextLine();
                }
                data = reader.nextLine(); //Reading the slotWinnings line
                slotWinnings = Double.parseDouble(data);
                data = reader.nextLine(); //Reading the rolls line
                rolls = Integer.parseInt(data);
                reader.close();
            } else {
                slotWinnings = 0;
                rolls = 0;
            }
        } catch (Exception e) {
            System.out.println("Error here");
        }


    }

    public void render() {
        scroll();
        if (getGame().getPlaying() == false) {
            System.out.println("*****************************");
            System.out.println("*        TIGER SLOTS        *");
            System.out.println("*****************************\n");
        } else {
            System.out.println("-----TIGER SLOTS-----\n");
            System.out.println("Current Balance: $" + getPlayer().getBalance());
            System.out.println("Amnt/spin: $" + spinAmount);
            System.out.println("1) Roll");
            System.out.println("2) Add Balance");
            System.out.println("3) Change Spin Amount");
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
                try {
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
                case 1: //Rolling
                    roll();
                    break;
                case 2: //Adding balance to the player
                    System.out.println("\n"); //Adding space before the balance
                    getPlayer().addBalance();
                    break;
                case 3: //Adding money
                    System.out.println("\n"); //Adding space before the balance
                    System.out.print("New Balance: ");
                    double newSpinAmount = userInput.nextDouble();
                    setSpinAmount(newSpinAmount);
                    break;
                case 4: //Exit the state
                    exitState();
                    getGame().setPlaying(false);
                    break;
            }
        }
    }

    private void roll() {
        Random rand = new Random();
        int[] randInts = new int[3];

        //Getting the random object for each slot
        for (int i = 0; i < 3; i++) {
            randInts[i] = rand.nextInt(3);
        }
        //Updating the players value and the stats
        getPlayer().loseMoney(spinAmount);
        slotWinnings -= spinAmount;
        rolls += 1;
        //Displaying the spins and getting the winnings then adding the stats to a file
        displaySpin(randInts);
        calculateReturn(randInts);
        toFile();

        //Giving some time so the user can see the rolls
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private void toFile() {
        try {
            File myFile = new File("TigerStats.txt");
            FileWriter writer = new FileWriter("TigerStats.txt");
            if (myFile.exists()) {
                writer.write("Winnings: $" + slotWinnings + "\n");
                writer.write("Rolls: " + rolls + "\n");
                writer.write(slotWinnings + "\n");
                writer.write(rolls + "\n");
                writer.close();
            } else {
                myFile.createNewFile();
                writer.write("Winnings: $" + slotWinnings + "\n");
                writer.write("Rolls: " + rolls + "\n");
                writer.write(slotWinnings + "\n");
                writer.write(rolls + "\n");
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("error here");
        }
    }

    private void displaySpin(int[] randInts) {
        String[] objects = {"Lemon", "Melon", "Seven", "Charm"};
        System.out.println("--------------------------");
        System.out.println("| " + objects[randInts[0]] + " - " + objects[randInts[1]] + " - " + objects[randInts[2]] + " |");
        System.out.println("--------------------------");
    }

    private void calculateReturn(int[] randInts) {
        double value = 0;
        if (randInts[0] == randInts[1]) {
            if (randInts[1] == randInts[2]) {
                //Since there are 4 objects and you need all 3 then its 1/64 chance each to get a jackpot then
                //you will probably roll 4/64=1/16 times costing spinAmounnt * 16 then the return is ~94% or 15
                value = 14 * spinAmount;
                value = Math.round(value * 100.0) / 100.0;
                String result = String.format("YOU WON $ %.2f!!!!!", value);
                System.out.println(result);
            }
        }
        //Addings the winnings
        getPlayer().addWinnings(value);
        slotWinnings += value;
    }

    //Setters and Getters
    public double getSpinAmount() {
        return spinAmount;
    }
    public void setSpinAmount(double newSpinAmount) {
        spinAmount = newSpinAmount;
    }

    public double getSlotWinnings() {return slotWinnings;}
    public void setSlotWinnings(double newSW) {slotWinnings = newSW;}

    public int getRolls() {return rolls;}



}
