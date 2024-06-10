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

    private static final Random rng = new Random();

    public BetterTournamentRunner(List<GameStrategy> players) {
        this.players = players;
        for (var player : players) {
            playerNumbers.put(player, players.indexOf(player));
        }
    }

    public void run() {
        int nRounds = rng.nextInt(1, 200);

        System.out.println("\n====== TOURNAMENT BEGINS ======");
        System.out.println("Round length: " + nRounds);

        // Calibrate every player by pitting them against always defect
        System.out.println("\n====== CALIBRATION ======");
        for(int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
            if(playerIndex != 0) {
                playRound(new AlwaysDefectStrategy(), players.get(playerIndex), new ArrayList<>(), nRounds);
            }
        }

        // Pit every strategy against every other strategy
        System.out.println("\n====== MATCHES ======");
        List<StrategyResult> results = new ArrayList<>();
        for(int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
            for(int opponentIndex = 0; opponentIndex < players.size(); opponentIndex++) {
                if(playerIndex != opponentIndex) {
                    playRound(players.get(playerIndex), players.get(opponentIndex), results, nRounds);
                }
            }
        }

        System.out.println("\n====== FINAL SCORES ======");

        HashMap<GameStrategy, Integer> scores = new HashMap<>();
        for(GameStrategy strategy : players) {
            for (StrategyResult result : results) {
                if (strategy == result.strategy()) {
                    if (!scores.containsKey(strategy)) {
                        scores.put(strategy, result.points());
                    } else {
                        scores.put(strategy, scores.get(strategy) + result.points());
                    }
                }
            }
        }

        ArrayList<StrategyResult> sortedScores = new ArrayList<>();
        for(GameStrategy strategy : scores.keySet()) {
            sortedScores.add(new StrategyResult(strategy, scores.get(strategy)));
        }

        sortedScores.sort(new Comparator<StrategyResult>() {
            public int compare(StrategyResult s1, StrategyResult s2) {
                return (s1.points() - s2.points());
            }
        });

        int max = 0;
        GameStrategy winner = null;
        for(int i = 0; i < sortedScores.size(); i++) {
            StrategyResult entry = sortedScores.get(i);
            System.out.println(sortedScores.size()-i + "\t" + entry.strategy().getName() + "#" + playerNumbers.get(entry.strategy()) + ": " + scores.get(entry.strategy()));
            if(scores.get(entry.strategy()) > max) {
                winner = entry.strategy();
                max = scores.get(entry.strategy());
            }
        }


        System.out.println("\n====== ULTIMATE WINNER ======");
        if(winner != null) {
            System.out.printf("%s#%d: %d%n", winner.getName(), playerNumbers.get(winner), scores.get(winner));
        }
        else {
            System.out.println("No strategies competed!");
        }
    }

    private void playRound(GameStrategy player1, GameStrategy player2, List<StrategyResult> results, int nRounds) {

        var game = new GameRunner(
                nRounds,
                player1,
                player2
        );

        var result = game.play();

        results.add(new StrategyResult(player1, result.player1Score()));
        results.add(new StrategyResult(player2, result.player2Score()));

        System.out.printf("%s#%d vs %s#%d: %d:%d%n",
                player1.getName(),
                playerNumbers.get(player1),
                player2.getName(),
                playerNumbers.get(player2),
                result.player1Score(),
                result.player2Score()
        );
    }
}
