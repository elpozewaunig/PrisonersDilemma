package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class BadStartTitForTatStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "BadStartTitForTat";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

        if (otherPlayerActions.isEmpty()) {
            return GameAction.DEFECT;
        }

        return otherPlayerActions.getLast();
    }

}
