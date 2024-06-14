package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class TFTTAdvancedStrategy implements GameStrategy {
    int defectCount = 0;

    @Override
    public String getName() {
        return "TFTTwithBetterMemory";
    }

    @Override
    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

        if (otherPlayerActions.isEmpty()) {
            defectCount = 0;
            return GameAction.COOPERATE;
        }

        if(otherPlayerActions.getLast() == GameAction.DEFECT) {
            defectCount++;
        }

        if(defectCount == 2) {
            defectCount = 0;
            return GameAction.DEFECT;
        }

        return GameAction.COOPERATE;
    }

}
