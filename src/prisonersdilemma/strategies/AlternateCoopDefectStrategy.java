package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public class AlternateCoopDefectStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "AlternateCooperateDefect";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

        if (otherPlayerActions.size() % 2 == 1) {
            return GameAction.DEFECT;
        }
        else {
            return GameAction.COOPERATE;
        }
    }

}
