package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

import java.util.Random;

public class ProbabilityCopyStrategy implements GameStrategy {
    public String getName() {
        return "ProbabilityCopy";
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

        Random r = new Random();

        // Make a random choice based on the probability of the opponent's actions
        int result = r.nextInt(cooperateCount + defectCount);
        if(result < cooperateCount) {
            return GameAction.COOPERATE;
        }
        else {
            return GameAction.DEFECT;
        }
    }
}
