package prisonersdilemma;

import prisonersdilemma.strategies.others.*;
import prisonersdilemma.strategies.q1.*;
import prisonersdilemma.strategies.standard.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    var players = List.of(
            // Classic/given strategies
            new CopyOppositeStrategy(),
            new AlwaysCooperateStrategy(),
            new RandomStrategy(),
            new TitForTatStrategy(),
            new BadStartTitForTatStrategy(),
            new AlwaysDefectStrategy(),
            new CopyAverageStrategy(),
            new GrimTriggerStrategy(),
            new AppeaseStrategy(),
            new AngryAppeaseStrategy(),
            new AlternateDefectCoopStrategy(),
            new AlternateCoopDefectStrategy(),
            new TwoTitsForTatStrategy(),
            new TitForTwoTatsStrategy(),
            new TitForTwoTatsMemoryStrategy(),
            new ProbabilityCopyStrategy(),

            // Other student's strategies
            new Agent47(),
            new Agent48(),
            new CloneStrat3000Strategy(),
            new DarknessDestroyer2000Strategy(),
            new DolorStrategy(),
            new IpsumStrategy(),
            new LoremStrategy(),
            new MaybeMeanStrategy(),
            new NiceDayStrategy(),
            new PItifulAttemptToBeatMeStrategy(),
            new SneakyCopiesStrategy(),
            new SometimesRandomStrategy(),
            new TheWinnerStrategy(),
            new TheArtfulWolveStrategy(),
            new ForgivingTitForTatAlsoHasRabiesStrategy(),

            // Team-contributed strategies
            new SchwaigerStrategy(),
            new WolfgerStrategy(),
            new PozewaunigStrategy(),
            new MuellauerStrategy(),
            new WurzerStrategy(),
            new StuhlpfarrerStrategy()
    );

    // new TournamentRunner(new ArrayList<>(players)).run();
    new BetterTournamentRunner(new ArrayList<>(players)).run();
  }
}
