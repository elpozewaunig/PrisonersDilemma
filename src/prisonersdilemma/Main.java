package prisonersdilemma;

import prisonersdilemma.strategies.others.*;
import prisonersdilemma.strategies.q1.*;
import prisonersdilemma.strategies.standard.*;
import prisonersdilemma.strategies.standard.AlwaysCooperateStrategy;
import prisonersdilemma.strategies.standard.AlwaysDefectStrategy;
import prisonersdilemma.strategies.standard.RandomStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    var players = List.of(
            // Classic/given strategies
            new CopyOppositeStrategy(),
            new AlwaysCooperateStrategy(),
            new RandomStrategy(),
            new PureTitForTatStrategy(),
            new BadStartTitForTatStrategy(),
            new AlwaysDefectStrategy(),
            new GrimTriggerStrategy(),
            new AlternateDefectCoopStrategy(),
            new AlternateCoopDefectStrategy(),
            new TwoTitsForTatStrategy(),
            new TitForTwoTatsStrategy(),
            new TFTTAdvancedStrategy(),

            // Complex/student-contributed strategies
            new SchwaigerStrategy(),
            new WolfgerStrategy(),
            new PozewaunigStrategy(),
            new MuellauerStrategy(),
            new WurzerStrategy(),
            new StuhlpfarrerStrategy(),
            new Agent47(),
            new Agent48(),
            //new CloneStrat3000Strategy(), -> Erzeugt StackOverflow
            new DarknessDestroyer2000Strategy(),
            new DolorStrategy(),
            new IpsumStrategy(),
            new LoremStrategy(),
            new MaybeMeanStrategy(),
            new NiceDayStrategy(),
            new PItifulAttemptToBeatMeStrategy(),
            new SneakyCopiesStrategy(),
            new SometimesRandomStrategy(),
            new TheWinnerStrategy()
    );

    // new TournamentRunner(new ArrayList<>(players)).run();
    new BetterTournamentRunner(new ArrayList<>(players)).run();
  }
}
