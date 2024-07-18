package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public class AnonymousStrategy implements GameStrategy {

    @Override
    public String getName() {
        return "AnonymousStrategy";
    }

    @Override
    public GameAction playRound(GameState state) {
        throw new IllegalStateException("A strategy tried tampering with its opponent");
    }
}
