package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public class AnonymizedStrategy implements GameStrategy {
    private final GameStrategy strategy;

    @Override
    public String getName() {
        return "AnonymousStrategy";
    }

    @Override
    public GameAction playRound(GameState state) {
        return strategy.playRound(state);
    }

    public AnonymizedStrategy(GameStrategy strategy) {
        this.strategy = strategy;
    }

    public AnonymizedStrategy() {
        throw new IllegalArgumentException("No strategy to anonymize was specified");
    }
}
