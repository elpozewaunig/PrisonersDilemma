package prisonersdilemma;

import prisonersdilemma.strategies.standard.AlwaysDefectStrategy;
import prisonersdilemma.strategies.GameStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BetterTournamentRunner {
    private final List<GameStrategy> players;
    private final Map<GameStrategy, Integer> playerNumbers = new HashMap<>();
    private boolean anticheat = false;

    private static final Random rng = new Random();

    private int errorCount = 0;

    public BetterTournamentRunner(List<GameStrategy> players) {
        this.players = players;
        for (var player : players) {
            playerNumbers.put(player, players.indexOf(player));
        }
    }

    public BetterTournamentRunner(List<GameStrategy> players, boolean anticheat) {
        this(players);
        this.anticheat = anticheat;
    }

    public void run() {
        run(rng.nextInt(1, 200));
    }

    public void run(int nRounds) {
        System.out.println("\n====== TOURNAMENT BEGINS ======");
        System.out.println("Round length: " + nRounds);

        // Calibrate every player by pitting them against always defect
        System.out.println("\n====== CALIBRATION ======");
        for (GameStrategy player : players) {
            playRound(new AlwaysDefectStrategy(), player, new ArrayList<>(), nRounds);
        }

        // Pit every strategy against every other strategy
        System.out.println("\n====== MATCHES ======");
        errorCount = 0;
        List<StrategyMatchResult> results = new ArrayList<>();
        for(int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
            for(int opponentIndex = 0; opponentIndex < players.size(); opponentIndex++) {
                if(playerIndex != opponentIndex) {
                    playRound(players.get(playerIndex), players.get(opponentIndex), results, nRounds);
                }
            }
        }

        int eliminationRound = 0;
        ArrayList<GameStrategy> winners = new ArrayList<>();
        HashMap<GameStrategy, Integer> scores = new HashMap<>();

        // Calculate scores for a round and eliminate low-scoring strategies, repeat
        do {
            if(eliminationRound == 0) {
                System.out.println("\n====== TOTAL SCORES ======");
            }
            else {
                System.out.println("\n====== ELIMINATION ROUND " + eliminationRound + " ======");
            }

            // Populate a HashMap summing up the total points of each strategy
            scores.clear();
            for (GameStrategy strategy : players) {
                for (StrategyMatchResult result : results) {
                    if (strategy == result.strategy() && players.contains(result.opponent())) {
                        if (!scores.containsKey(strategy)) {
                            scores.put(strategy, result.points());
                        } else {
                            scores.put(strategy, scores.get(strategy) + result.points());
                        }
                    }
                }
            }

            // Create an ArrayList out of the HashMap so the scores can be sorted
            ArrayList<StrategyResult> sortedScores = new ArrayList<>();
            for(GameStrategy strategy : scores.keySet()) {
                sortedScores.add(new StrategyResult(strategy, scores.get(strategy)));
            }

            sortedScores.sort(Comparator.comparingInt(StrategyResult::points));

            // Print out the scores in order and determine the winner(s), strategies may be tied
            winners.clear();
            for (int i = 0; i < sortedScores.size(); i++) {
                StrategyResult entry = sortedScores.get(i);

                // When a strategy is tied, lower its rank to match the best-ranking strategy with the same score
                int rank = sortedScores.size() - i;
                for(int posCompare = i+1;
                    posCompare < sortedScores.size() && entry.points() == sortedScores.get(posCompare).points();
                    posCompare++) {
                    rank--;
                }

                if (rank == 1) {
                    winners.add(entry.strategy());
                }

                System.out.println(rank + "\t" + entry.strategy().getName() + "#" + playerNumbers.get(entry.strategy()) + ": " + scores.get(entry.strategy()));
            }

            // Eliminate bottom half of strategies, if strategies are tied with the cutoff score, they persist
            int cutoffScore = sortedScores.get(sortedScores.size()/2).points();
            int lastPlayerCount = players.size();
            players.removeIf(strategy -> scores.get(strategy) < cutoffScore);
            int newPlayerCount = players.size();

            // If no strategies were eliminated, they are all tied and no more eliminations can take place
            if(lastPlayerCount == newPlayerCount) {
                break;
            }

            eliminationRound++;

        } while(players.size() > 2);
        // Abort if only 2 strategies remain, as such a match produces inconclusive results

        System.out.println("\n====== ULTIMATE WINNER" + (winners.size() > 1 ? "S" : "") + " ======");
        if(!winners.isEmpty()) {
            for(GameStrategy winner : winners) {
                System.out.printf("%s#%d: %d%n", winner.getName(), playerNumbers.get(winner), scores.get(winner));
            }
        }
        else {
            System.out.println("No strategies competed!");
        }

        System.out.println("\nStats:");
        System.out.println(playerNumbers.size() + " strategies competed");
        System.out.println("A match consisted of " + nRounds + " rounds");
        System.out.println(results.size()/2 + " matches were played");
        System.out.println("Anticheat was " + (anticheat ? "enabled" : "disabled"));
        if(errorCount > 0) {
            System.out.print("\033[31m");
        }
        System.out.println(errorCount + " of them resulted in an error or exception \033[0m");
    }

    private void playRound(GameStrategy player1, GameStrategy player2, List<StrategyMatchResult> results, int nRounds) {

        var game = new GameRunner(
                nRounds,
                anticheat,
                player1,
                player2
        );

        try {
            GameResult result = game.play();

            results.add(new StrategyMatchResult(player1, result.player1Score(), player2));
            results.add(new StrategyMatchResult(player2, result.player2Score(), player1));

            printMatchResult(player1, player2, result.player1Score(), result.player2Score());
            System.out.println();
        }
        catch(Throwable e) {
            results.add(new StrategyMatchResult(player1, 0, player2));
            results.add(new StrategyMatchResult(player2, 0, player1));
            errorCount++;

            printMatchResult(player1, player2, 0, 0);
            System.out.print("\033[1m -> \033[41m " + e.getClass().getSimpleName() + " \033[0m");
            System.out.println();
        }
    }

    public void printMatchResult(GameStrategy player1, GameStrategy player2, int player1Score, int player2Score) {
        System.out.printf("%s#%d vs %s#%d: %d:%d",
                player1.getName(),
                playerNumbers.get(player1),
                player2.getName(),
                playerNumbers.get(player2),
                player1Score,
                player2Score
        );
    }

    public record StrategyMatchResult(GameStrategy strategy, int points, GameStrategy opponent) {
    }
}
