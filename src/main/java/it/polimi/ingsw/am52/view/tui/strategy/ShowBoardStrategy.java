package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.view.tui.TuiPrinter;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Objects;
import java.util.Scanner;

/**
 * The class from Strategy to implement the behaviour to visualize a board, yours or related to one of the opponents
 */
public class ShowBoardStrategy extends Strategy {

    //region Private Fields

    /**
     * True if the visualized board is not yours, otherwise False
     */
    private final boolean isOther;

    //endregion

    //region Constructor

    /**
     * The Constructor of the class, which is empty because there are no private fields
     */
    public ShowBoardStrategy(boolean isOther) {
        this.isOther = isOther;
    }

    //endregion

    //region Public Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseStatus executeWithNetworkCall() {
        if (!isOther) {
            ViewModelState.getInstance().setViewTypeNickname(ViewModelState.getInstance().getClientNickname());
            ViewModelState.getInstance().setViewTypeShown(ViewType.BOARD);
        } else {
            Scanner scanner = new Scanner(System.in);

            ViewModelState.getInstance().getNicknames().stream()
                    .filter(n -> !Objects.equals(n, ViewModelState.getInstance().getClientNickname()))
                    .forEach(n -> System.out.println("│ - " + n));

            System.out.print("│ - Enter nickname of the user to look: ");
            String nickname = scanner.nextLine();
            if (ViewModelState.getInstance().setViewTypeNickname(nickname.trim())) {
                ViewModelState.getInstance().setViewTypeShown(ViewType.BOARD);
            } else {
                System.out.println("│ - The given nickname does not exists!");
            }
        }

        TuiPrinter.getInstance().reprint(ViewModelState.getInstance().getViewTypeShown());
        return new ResponseStatus();
    }

    //endregion
}
