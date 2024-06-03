package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public class GrimTriggerStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "GrimTrigger";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();
        var thisPlayerActions = state.player1() == this ? state.player1Actions() : state.player2Actions();

        if(otherPlayerActions.isEmpty()) {
            return GameAction.COOPERATE;
        }
        if(otherPlayerActions.getLast() == GameAction.DEFECT || thisPlayerActions.getLast() == GameAction.DEFECT) {
            return GameAction.DEFECT;
        }
        return GameAction.COOPERATE;
    }

}
