package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class AlternateDefectCoopStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "AlternateDefectCooperate";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

        if (otherPlayerActions.size() % 2 == 0) {
            return GameAction.DEFECT;
        }
        else {
            return GameAction.COOPERATE;
        }
    }

}
