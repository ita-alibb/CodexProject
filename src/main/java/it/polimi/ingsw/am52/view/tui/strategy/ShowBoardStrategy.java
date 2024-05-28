package it.polimi.ingsw.am52.view.tui.strategy;

import it.polimi.ingsw.am52.json.response.ResponseStatus;
import it.polimi.ingsw.am52.view.tui.TuiPrinter;
import it.polimi.ingsw.am52.view.tui.state.ViewType;
import it.polimi.ingsw.am52.view.viewModel.ViewModelState;

import java.util.Scanner;

public class ShowBoardStrategy extends Strategy {
    //region Constructor
    private final boolean isOther;
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
        ViewModelState.getInstance().setViewTypeShown(ViewType.BOARD);

        if (!isOther) {
            ViewModelState.getInstance().setViewTypeNickname(ViewModelState.getInstance().getClientNickname());
        } else {
            Scanner scanner = new Scanner(System.in);

            String nickname;
            while (true) {
                System.out.print("          │ - Enter nickname of the user to look: ");
                nickname = scanner.nextLine();
                if (ViewModelState.getInstance().setViewTypeNickname(nickname.trim())) {
                    break;
                }
                System.out.println("         │ The given nickname doesn't exist");
            }
        }

        TuiPrinter.getInstance().update();
        return new ResponseStatus();
    }

    //endregion
}
