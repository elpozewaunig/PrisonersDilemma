package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public class PureTitForTatStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "VanillaTitForTat";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

        if (otherPlayerActions.isEmpty()) {
            return GameAction.COOPERATE;
        }

        return otherPlayerActions.getLast();
    }

}
