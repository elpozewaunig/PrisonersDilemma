package prisonersdilemma.strategies.others;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class MaybeMeanStrategy implements GameStrategy {

  @Override
  public String getName() {
    return "maybemean";
  }

  @Override
  public GameAction playRound(GameState state) {

    var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();
    if (otherPlayerActions.isEmpty()) {
      return GameAction.DEFECT;
    }
    if (otherPlayerActions.size() == 1) {
      return otherPlayerActions.getLast();
    }
    GameAction lastAction = otherPlayerActions.getLast();
    GameAction secondLastAction = otherPlayerActions.get(otherPlayerActions.size() - 2);
    if (lastAction == secondLastAction) {
      return lastAction;
    }

    return GameAction.DEFECT;
  }

}
