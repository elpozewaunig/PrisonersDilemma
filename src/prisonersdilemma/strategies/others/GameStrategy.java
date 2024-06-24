package prisonersdilemma.strategies.others;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

public interface GameStrategy {

  String getName();

  GameAction playRound(GameState state);

}
