package it.polimi.ingsw.am52.view.tui.state;

public abstract class TuiView {
    /**
     * The type of this view
     */
    protected ViewType type;

    public TuiView(ViewType type) {
        this.type = type;
    }

    protected abstract void printView();

    protected abstract void printCommands();

    public void print(){
        //clear console
        System.out.println("\033[H\033[2J");

        printView();
        printCommands();
    }
}
