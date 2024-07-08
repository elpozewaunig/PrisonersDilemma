package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class AppeaseStrategy implements GameStrategy {
    public String getName() {
        return "Appease";
    }

    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();
        var thisPlayerActions = state.player1() == this ? state.player1Actions() : state.player2Actions();

        if (otherPlayerActions.isEmpty()) {
            return GameAction.COOPERATE;
        }

        // Repeat the last move if the opponent cooperates, else do the opposite of the last move
        if(otherPlayerActions.getLast() == GameAction.COOPERATE) {
            return thisPlayerActions.getLast();
        }
        else {
            return thisPlayerActions.getLast() == GameAction.COOPERATE ? GameAction.DEFECT : GameAction.COOPERATE;
        }
    }
}
