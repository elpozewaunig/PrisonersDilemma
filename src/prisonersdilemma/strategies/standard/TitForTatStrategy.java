package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class TitForTatStrategy implements GameStrategy {

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
