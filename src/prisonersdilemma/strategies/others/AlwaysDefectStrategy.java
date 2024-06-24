package prisonersdilemma.strategies.others;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public class AlwaysDefectStrategy implements GameStrategy {

  @Override
  public String getName() {
    return "AlwaysDefect";
  }

  @Override
  public GameAction playRound(GameState state) {
    return GameAction.DEFECT;
  }

}
