package it.polimi.ingsw.am52.view.tui.state;

/**
 * The abstract class to create the skeleton of all the views TUI-type.
 * This grants that the visualized data are different for each state of the game we are in.
 */
public abstract class TuiView {

    //region Protected Fields

    /**
     * The type of this view
     */
    protected ViewType type;

    //endregion

    //region Constructor

    /**
     * The constructor sets the type of view we want to visualize
     * @param type  The type of view to be visualized
     */
    public TuiView(ViewType type) {
        this.type = type;
    }

    //endregion

    //region Utils Method

    /**
     * Method to clear the console before a new TUI is printed. Usually, this method is called before each update, obviously only if the update wants the terminal to be updated.
     */
    protected final void clearConsole(){
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            System.out.println("\033[H\033[2J");
        }
    }

    //endregion

    //region Inherited Protected Methods

    /**
     * Method to print the information useful to the player
     */
    protected abstract void printView();

    /**
     * Method to print the commands the player can perform
     */
    protected abstract void printCommands();

    //endregion

    //region Public Method

    /**
     * Generic method to update the TUI
     */
    public final void print(){
        //clear console
        clearConsole();

        printView();
        printCommands();
        System.out.print("> ");
    }

    //endregion
}
