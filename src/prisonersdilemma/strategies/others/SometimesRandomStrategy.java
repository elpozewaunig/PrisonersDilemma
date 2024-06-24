package prisonersdilemma.strategies.others;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

import java.util.Random;

public class SometimesRandomStrategy implements GameStrategy {

  private boolean opponentDefect = false;
  public final Random rng = new Random();
  @Override
  public String getName() {
    return "SometimesRandomStrategy";
  }

  @Override
  public GameAction playRound(GameState state) {

    int low = 1;
    int high = 100;
    int result = rng.nextInt(high-low) + low;

    if (result%3 == 0){
      return GameAction.DEFECT;
    }

    if (!state.player2Actions().isEmpty()) {
      opponentDefect = state.player2Actions().get(state.player2Actions().size() - 1) == GameAction.DEFECT;
    }

    if (opponentDefect) {
      opponentDefect = false;
      return GameAction.DEFECT;
    }

    return GameAction.COOPERATE;
  }
}
