import java.util.*;
public class State {
    private Game game;
    public Player player;

    //Constructor
    public State(Game g, Player p) {
        game = g;
        player = p;
    }

    public void update() {
    }
    public void render() {
    }
    public void enterState() {
        game.getStateStack().push(this); //Pushing its own state on the stack
    }

    public void exitState() {
        game.getStateStack().pop();
        if (game.getStateStack().size() == 1) {
            System.exit(0);
        }
    }

    public void scroll() {
        try {
            for (int i = 0; i < 15; i++) {
                System.out.println("");
                Thread.sleep(55);
            }
        } catch (Exception e) {
            System.out.println("Error Code 1");
        }
    }

    public void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            System.out.println("Time Error");
        }
    }

    public String toString() {
        return "State";
    }

    //Setters and Getters
    public Game getGame() {
        return game;
    }
    public Player getPlayer() {return player;}
}
