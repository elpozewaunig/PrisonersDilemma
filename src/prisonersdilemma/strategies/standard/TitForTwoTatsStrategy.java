package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class TitForTwoTatsStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "TitForTwoTats";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();
        int playedTurns = state.player1Actions().size();

        if (playedTurns <= 1) {
            return GameAction.COOPERATE;
        }

        if(otherPlayerActions.get(playedTurns-1) == GameAction.DEFECT &&
                otherPlayerActions.get(playedTurns-2) == GameAction.DEFECT) {
            return GameAction.DEFECT;
        }

        return GameAction.COOPERATE;
    }

}
