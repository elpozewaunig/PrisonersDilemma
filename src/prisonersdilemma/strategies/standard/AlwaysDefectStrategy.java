package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class AlwaysDefectStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "TheKlemensWibmer";
    }

    @Override
    public GameAction playRound(GameState state) {
        return GameAction.DEFECT;
    }
}
