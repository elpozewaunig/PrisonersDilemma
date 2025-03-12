package prisonersdilemma.strategies.omi_s24;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class SneakyCopiesStrategy implements GameStrategy {
  int countRound = 0;

  @Override
  public String getName() {
    return "SneakyCopies";
  }

  // startet mit defect, kopiert dann was der Gegner vorher gemacht hat und defected jedes 10. Mal
  @Override
  public GameAction playRound(GameState state) {
    var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

    countRound++;

    if (otherPlayerActions.isEmpty()) {
      return GameAction.DEFECT;
    }

    if (countRound % 10 == 0) {
      return GameAction.DEFECT;
    }

    return otherPlayerActions.getLast() == GameAction.COOPERATE ? GameAction.COOPERATE : GameAction.DEFECT;
  }

}
