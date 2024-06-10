package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class AlwaysCooperateStrategy implements GameStrategy {

  @Override
  public String getName() {
    return "AlwaysCooperate";
  }

  @Override
  public GameAction playRound(GameState state) {
    return GameAction.COOPERATE;
  }
}
