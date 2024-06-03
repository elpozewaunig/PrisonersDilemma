package prisonersdilemma;

import prisonersdilemma.strategies.GameStrategy;

public record GameResult(int player1Score, int player2Score, GameStrategy winner, GameStrategy loser) {
}
