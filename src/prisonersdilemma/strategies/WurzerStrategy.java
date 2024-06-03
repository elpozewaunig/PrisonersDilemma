package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WurzerStrategy implements GameStrategy {
  private final int testOpponentXTimes = 10;
  private int roundCounter = 0;
  private int finalRoundCounter = 0;

  private int tournamentRound = 0;

  @Override
  public String getName() {
    return "\033[35;49;1m[Q1] CheatingWurzel\033[0m";
  }

  /**
   * Tries to cheat in different ways
   * First of all, wouldn't consider that cheating, but we are counting rounds and if it's the last round we auto defect
   * Second of all we try to cheat: we give the opponent the game state and look how it reacts x times.
   * If it defects every time, we defect to, if it cooperates, we cooperate to;
   * If it's inconsistent, we try to reset the thing by giving it an empty game state. If the reaction is consistent we react accordingly
   * If not the opponent is seen as random and we auto defect
   * If the cheating doesn't work, we play titForTat
   */
  @Override
  public GameAction playRound(GameState state) {
    roundCounter++; // new round
    var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();
    var otherPlayer = (state.player1() == this) ? state.player2() : state.player1(); // get opponent

    if (otherPlayerActions.isEmpty()) { // new tournament Round
      roundCounter = 1; // reset roundCounter
      tournamentRound++; // new tournamentRound
    }


    // If last round - defect
    else if (tournamentRound > 1 && roundCounter == finalRoundCounter)
      return GameAction.DEFECT;
    else if(tournamentRound < 2) // first tournament round - figure out how many rounds are played, in order to defect at the last one
      finalRoundCounter = roundCounter;

    // not the last round

    try { // try to cheat

      // test reactions of opponent from current game state #cheatingWurzel
        GameAction reaction = testOpponent(otherPlayer, state);
        if (reaction != null) // reaction was consistent return ideal reaction
          return reaction;

        // else: try resetting and test reactions of opponent afterward (works if opponent resets internal variables when simulating a new round)
        GameAction resetReaction = testOpponentWithReset(otherPlayer, state);  // test reactions of opponent when resetting
        // reaction was consistent return ideal reaction ||| else: inconsistent reactions -> probably random strat -> defect
        return Objects.requireNonNullElse(resetReaction, GameAction.DEFECT);

    } catch (Exception e) { // cheating didn't work, play titForTat
      otherPlayer.playRound(new GameState(state.player1(), state.player2(), new ArrayList<>(new ArrayList<>()), new ArrayList<>(new ArrayList<>())));
        return playTitForTat(otherPlayerActions);
    }
  }





  private GameAction testOpponent(GameStrategy otherPlayer, GameState state){
    // get reactions
    GameAction[] reactions = new GameAction[testOpponentXTimes];
    for (int i = 0; i < testOpponentXTimes; i++)
      reactions[i] = otherPlayer.playRound(state);

    // evaluate reactions (should work with reactions.stream...) but it's late and I don't want to think ;)
    GameAction reaction0 = reactions[0];
    boolean consistent = true;
    for (int i = 1; i < testOpponentXTimes; i++){
      if (!reactions[i].equals(reaction0)){
        consistent = false;
        break;
      }
    }


    // return reaction
    if (consistent && reaction0.equals(GameAction.COOPERATE)) // if all reactions cooperate: cooperate
      return GameAction.COOPERATE;
    else if (consistent) // if all reactions defect: defect
      return GameAction.DEFECT;
    return null;
  }


  private GameAction testOpponentWithReset(GameStrategy otherPlayer, GameState state){
    GameAction[] reactions = new GameAction[10];
    GameAction reaction0 = reactions[0];
    // reset and get reactions
    for (int i = 0; i < testOpponentXTimes; i++) {
      otherPlayer.playRound(new GameState(state.player1(), state.player2(), new ArrayList<>(new ArrayList<>()), new ArrayList<>(new ArrayList<>())));
      reactions[i] = otherPlayer.playRound(state);
    }


    // evaluate reactions (should work with reactions.stream...) but it's late and I don't want to think ;)
    boolean consistent = true;
    for (int i = 1; i < testOpponentXTimes; i++){
      if (!reactions[i].equals(reaction0)){
        consistent = false;
        break;
      }
    }

    // reset and return reaction
    if (consistent && reaction0.equals(GameAction.COOPERATE)) {// if all reactions cooperate: reset and cooperate
      otherPlayer.playRound(new GameState(state.player1(), state.player2(), new ArrayList<>(new ArrayList<>()), new ArrayList<>(new ArrayList<>())));
      return GameAction.COOPERATE;
    } else if (consistent) { // if all reactions defect: reset and defect
      otherPlayer.playRound(new GameState(state.player1(), state.player2(), new ArrayList<>(new ArrayList<>()), new ArrayList<>(new ArrayList<>())));
      return GameAction.DEFECT;
    }
    otherPlayer.playRound(new GameState(state.player1(), state.player2(), new ArrayList<>(new ArrayList<>()), new ArrayList<>(new ArrayList<>())));
    return null;
  }

  private GameAction playTitForTat(List<GameAction> otherPlayerActions){
    if (otherPlayerActions.isEmpty()) {
      return GameAction.COOPERATE;
    }
    return otherPlayerActions.getLast() == GameAction.COOPERATE ? GameAction.DEFECT : GameAction.COOPERATE;
  }
}
