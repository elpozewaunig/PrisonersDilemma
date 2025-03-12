package prisonersdilemma.strategies.omi_s24;

import prisonersdilemma.GameAction;
import prisonersdilemma.GameState;
import prisonersdilemma.strategies.GameStrategy;

public class Agent48 implements GameStrategy {  // Tit-for-Tat

    @Override
    public String getName() {
        return "Agent48";
    }

    @Override
    public GameAction playRound(GameState state) {

        if (state.player1Actions().isEmpty()) { // Erste Runde? Start mit Cooperate
            return GameAction.COOPERATE;
        }

        GameAction opponentLastAction = state.player2Actions().getLast();   //Vergleich mit gegner Strategie

        if (Math.random() < 0.25) { // Letzter Gegner Befehl wird wiederholt mit 25% random defect chance.
            return GameAction.DEFECT;
        }

        return opponentLastAction;
    }
}