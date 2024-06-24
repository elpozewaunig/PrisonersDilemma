package prisonersdilemma.strategies.others;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

import java.util.Random;

public class TheWinnerStrategy implements GameStrategy {
    private int roundCounter = 0;
    private final Random rng = new Random();

    @Override
    public String getName() {
        return "TheWinnerStrategie";
    }

    @Override
    public GameAction playRound(GameState state) {
        roundCounter++;
        int lastRoundIndex = state.player2Actions().size() - 1;
        GameAction lastOpponentAction = lastRoundIndex >= 0 ? state.player2Actions().get(lastRoundIndex) : GameAction.DEFECT;

        if (roundCounter == 1) {
            return GameAction.DEFECT;
        } else if (roundCounter == 2 || roundCounter == 3) {
            return lastOpponentAction;
        } else {
            roundCounter = 0; //wieder bei null anfangen für 3. runde
            return rng.nextBoolean() ? GameAction.COOPERATE : GameAction.DEFECT;
        }
    }

}