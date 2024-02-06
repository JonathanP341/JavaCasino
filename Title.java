import java.util.*;
public class Title extends State{
    public Title(Game g, Player p) {
        super(g, p);
    }
    public void update() {
        //Getting the user input, if they type anything the game begins
        delay(1500);

        MainMenu menuState = new MainMenu(getGame(), getPlayer());
        menuState.enterState(); //Entering the menuState

    }

    public void render() {
        System.out.println("*****************************");
        System.out.println("*       CASINO ROYALE       *");
        System.out.println("*****************************\n");
    }

    public String toString() {
        return "Title State";
    }
}
