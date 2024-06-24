package prisonersdilemma.strategies.others;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

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
