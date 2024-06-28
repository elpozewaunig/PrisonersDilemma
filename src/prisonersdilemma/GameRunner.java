package prisonersdilemma;

import prisonersdilemma.strategies.AnonymousStrategy;
import prisonersdilemma.strategies.GameStrategy;
import java.util.ArrayList;
import java.util.List;

public class GameRunner {

  // not constant for each competition
  private final int nRuns;
  private boolean anticheat = false;

  private final GameStrategy player1;
  private final GameStrategy player2;

  private int points1, points2;

  private final List<GameAction> player1Actions = new ArrayList<>();
  private final List<GameAction> player2Actions = new ArrayList<>();

  public GameRunner(int nRuns, GameStrategy player1, GameStrategy player2) {
    this.nRuns = nRuns;
    this.player1 = player1;
    this.player2 = player2;
  }

  public GameRunner(int nRuns, boolean anticheat, GameStrategy player1, GameStrategy player2) {
    this(nRuns, player1, player2);
    this.anticheat = anticheat;
  }

  public GameResult play() {
    for (int i = 0; i < nRuns; i++) {
      var gameState = new GameState(
              player1,
              player2,
              new ArrayList<>(player1Actions),
              new ArrayList<>(player2Actions)
      );
      var gameState1 = new GameState(
              player1,
              new AnonymousStrategy(),
              new ArrayList<>(player1Actions),
              new ArrayList<>(player2Actions)
      );
      var gameState2 = new GameState(
              new AnonymousStrategy(),
              player2,
              new ArrayList<>(player1Actions),
              new ArrayList<>(player2Actions)
      );

      GameAction nextResult1;
      GameAction nextResult2;
      
      if(anticheat) {
        nextResult1 = player1.playRound(gameState1);
        nextResult2 = player2.playRound(gameState2);
      }
      else {
        nextResult1 = player1.playRound(gameState);
        nextResult2 = player2.playRound(gameState);
      }

      player1Actions.add(nextResult1);
      player2Actions.add(nextResult2);

      gradePoints(nextResult1, nextResult2);
    }

    return new GameResult(
        points1,
        points2,
        points1 < points2 ? player2 : player1,
        points2 < points1 ? player2 : player1
    );
  }

  private void gradePoints(GameAction nextResult1, GameAction nextResult2) {
    if (nextResult1 == GameAction.COOPERATE) {
      if (nextResult2 == GameAction.COOPERATE) {
        points1 += 3;
        points2 += 3;
      } else {
        // no points for player1
        points2 += 5;
      }
    } else {
      if (nextResult2 == GameAction.COOPERATE) {
        points1 += 5;
        // no points for player2
      } else {
        points1 += 1;
        points2 += 1;
      }
    }
  }
}
