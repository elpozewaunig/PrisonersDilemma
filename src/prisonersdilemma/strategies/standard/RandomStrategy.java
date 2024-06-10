package prisonersdilemma.strategies.standard;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

import java.util.Random;

public class RandomStrategy implements GameStrategy {

  public final Random rng = new Random();

  @Override
  public String getName() {
    return "SuperRandomStrat";
  }

  @Override
  public GameAction playRound(GameState state) {
    return rng.nextBoolean() ? GameAction.COOPERATE : GameAction.DEFECT;
  }

}
