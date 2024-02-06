import java.util.*;
public class Game {
    private boolean playing;
    private boolean running;
    private Stack<State> stateStack;

    //Constructor
    public Game() {
        //Initializing the game
        playing = false;
        running = true;
        stateStack = new Stack<State>();
        loadStates(); //Method to set the title state to the stack
    }

    //Methods of consequence
    public void loadStates() {
        Player p = new Player();
        Title titleState = new Title(this, p);
        stateStack.push(titleState);
    }

    public void gameLoop() { //The main loop of the game
        //Renders then updates and repeat
        while (stateStack.isEmpty() == false) {
            stateStack.peek().render();
            stateStack.peek().update();
        }
    }

    public void update() {
        stateStack.peek().update();
    }

    public void render() {
        stateStack.peek().render();
    }

    //Running the Main Game
    public static void main(String[] args) {
        Game g = new Game();
        g.gameLoop();
    }

    //Setters and Getters
    public boolean getPlaying() {
        return playing;
    }
    public boolean getRunning() {
        return running;
    }
    public Stack<State> getStateStack() {
        return stateStack;
    }

    public void setPlaying(boolean newPlaying) {
        playing = newPlaying;
    }
    public void setRunning(boolean newRunning) {
        running = newRunning;
    }
    public void setStateStack(Stack<State> newStack) {
        stateStack = newStack;
    }
}
