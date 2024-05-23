package it.polimi.ingsw.am52.view.tui.state;

public abstract class TuiView {
    /**
     * The type of this view
     */
    protected ViewType type;

    public TuiView(ViewType type) {
        this.type = type;
    }

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

    protected abstract void printView();

    protected abstract void printCommands();

    public void print(){
        //clear console
        clearConsole();

        printView();
        printCommands();
    }
}
