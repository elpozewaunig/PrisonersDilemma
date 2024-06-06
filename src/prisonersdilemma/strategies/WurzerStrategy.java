package prisonersdilemma.strategies;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;

import java.util.ArrayList;
import java.util.List;

public class WurzerStrategy implements GameStrategy{
  private final int testOpponentXTimes = 10;
  private int roundCounter = 0;
  private int finalRoundCounter = 0;

  private int tournamentRound = 0;

  private GameStrategy[] opponents = new GameStrategy[testOpponentXTimes];
  private GameStrategy[] opponentsToReset = new GameStrategy[testOpponentXTimes];

  @Override
  public String getName() {
    return "\033[35;49;1m[Q1] CheatingWurzel\033[0m";
  }

  @Override
  public GameAction playRound(GameState state) {
    roundCounter++; // new round
    var otherPlayer = (state.player1() == this) ? state.player2() : state.player1(); // get opponent
    var otherPlayerActions = state.player1() == this ? state.player2Actions() : state.player1Actions();

    //----------------------------------------------------------------------------------------------------
    if (otherPlayer.getClass() == this.getClass()) // play against self; cooperate // instance of didn't work
      return GameAction.COOPERATE;

    if (otherPlayerActions.isEmpty()) { // new tournament Round
      roundCounter = 1; // reset roundCounter
      tournamentRound++; // new tournamentRound
      resetOpponentArray(otherPlayer);
    }
    else if (tournamentRound > 1 && roundCounter == finalRoundCounter)
      // If last round - defect
      return GameAction.DEFECT;
    else if(tournamentRound < 2)
      // first tournament round - figure out how many rounds are played, in order to defect at the last one
      finalRoundCounter = roundCounter;

    // else: play against opponent, and it's not the last round

    try{ // try to cheat...
      // ...with letting the instances in the opponent array play and predict the action of the opponent by the action of its clones
      // using clones, so that it's harder to detect the cheating try (i.e. Nuclear Gandis anti cheat method)
      // test reaction using current game state
      GameAction reaction = testOpponentArray(state);
      if (reaction == GameAction.COOPERATE) // opponent cooperated cooperate
        return GameAction.COOPERATE;

      // opponent didn't cooperate consistently, try forcing him with sending him an empty game state and therefore
      // possibly resetting his internal variables
      GameAction resetReaction = resetAndTestOpponentArray(state);  // test reactions of opponent when resetting
      // reaction was consistently a Cooperation, cooperate
      if (resetReaction == GameAction.COOPERATE) {
        resetArrayBecauseOfAction(state); // reset array of running clones, to be able to make accurate predictions
        otherPlayer.playRound(emptyGameState(state)); // reset other player
        return GameAction.COOPERATE; // cooperate
      }

      // reaction was either inconsistent or a consistent deflection, deflect
      return GameAction.DEFECT;

    } catch (Exception e) { // cheating didn't work, play TFT
      return playTitForTat(otherPlayerActions);
    }
  }

  /**
   * Play TFT
   */
  private GameAction playTitForTat(List<GameAction> otherPlayerActions){
    if (otherPlayerActions.isEmpty()) {
      return GameAction.COOPERATE;
    }
    return otherPlayerActions.getLast() == GameAction.COOPERATE ? GameAction.COOPERATE : GameAction.DEFECT;
  }


  /**
   * create instances of opponent and save them in the opponents array in order to maybe get an advantage by trying
   * to disclose the reaction of the opponent beforhand
   */
  private void resetOpponentArray(GameStrategy otherPlayer){
    try { // try filling opponent Array up with instances of current opponent
      for (int i = 0; i < opponents.length; i++){
        opponents[i] = (GameStrategy) otherPlayer.getClass().getConstructors()[0].newInstance();
        opponentsToReset[i] = (GameStrategy) otherPlayer.getClass().getConstructors()[0].newInstance();
      }
    } catch (java.lang.InstantiationException | java.lang.IllegalAccessException | java.lang.reflect.InvocationTargetException e){
      // something went wrong, fill the array up with null
      opponents = new GameStrategy[opponents.length];
      opponentsToReset = new GameStrategy[opponents.length];
    }
  }

  /**
   * evaluate wheter opponents responses where consistent (if not he probably plays a random strategy)
   */
  private boolean reactionsAreConsistent(GameAction[] reactions){
    // evaluate reactions (should work with reactions.stream...) but it's late and I don't want to think ;)
    GameAction reaction0 = reactions[0];
    for (int i = 1; i < testOpponentXTimes; i++){
      if (!reactions[i].equals(reaction0)){
        return false;
      }
    }
    return true;
  }

  /**
   * return the best GameAction in response to the reactions of the opponent to the current game state
   */
  private GameAction returnBestGameAction(GameAction[] reactions){
    // evaluate reactions
    boolean consistent = reactionsAreConsistent(reactions);

    // return reaction
    if (consistent && reactions[0].equals(GameAction.COOPERATE)) // if all reactions cooperate: cooperate
      return GameAction.COOPERATE;
    else if (consistent) // if all reactions defect: defect
      return GameAction.DEFECT;
    return null;
  }

  /**
   * let the opponent Instances in the opponents array play with the current game state and evaluate the best response
   */
  private GameAction testOpponentArray(GameState state){
    // get reactions
    GameAction[] reactions = new GameAction[testOpponentXTimes];
    for (int i = 0; i < opponents.length; i++) {
      reactions[i] = opponents[i].playRound(state);
      opponentsToReset[i].playRound(state);
    }

    return returnBestGameAction(reactions);
  }


  /**
   * Reset the oppenent instances in the opponentsToReset Array and evaluate reactions
   */
  private GameAction resetAndTestOpponentArray(GameState state) {
    // get reactions
    GameAction[] reactions = new GameAction[testOpponentXTimes];
    GameState emptyState = emptyGameState(state);
    for (int i = 0; i < opponents.length; i++) {
      opponentsToReset[i].playRound(emptyGameState(emptyState));
      reactions[i] = opponentsToReset[i].playRound(state);
    }

    return returnBestGameAction(reactions);
  }

  /**
   * when reseting there was a consistent cooperation, so we will reset the Main Opponent instance
   * to be able to make accurate preditions in the future, we will also reset all the clones that should be on the
   * same state as the main opponent
   */
  private void resetArrayBecauseOfAction(GameState state){
    GameState emptyState = emptyGameState(state);
    for (GameStrategy opponent : opponents)
      opponent.playRound(emptyGameState(emptyState));
  }


  /**
   * create an empty game state
   */
  private GameState emptyGameState(GameState state){
    return new GameState(state.player1(), state.player2(), new ArrayList<>(new ArrayList<>()), new ArrayList<>(new ArrayList<>()));
  }
}
