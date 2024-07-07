package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class CopyAverageStrategy implements GameStrategy {
    public String getName() {
        return "CopyAverage";
    }

    public GameAction playRound(GameState state) {
        var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

        if (otherPlayerActions.isEmpty()) {
            return GameAction.COOPERATE;
        }

        int cooperateCount = 0;
        int defectCount = 0;
        for(GameAction act : otherPlayerActions) {
            if(act == GameAction.COOPERATE) {
                cooperateCount++;
            }
            else {
                defectCount++;
            }
        }

        if(cooperateCount > defectCount) {
            return GameAction.COOPERATE;
        }
        else {
            return GameAction.DEFECT;
        }
    }
}
