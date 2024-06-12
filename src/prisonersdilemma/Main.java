package prisonersdilemma;

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
            new PureTitForTatStrategy(),
            new BadStartTitForTatStrategy(),
            new AlwaysDefectStrategy(),
            new GrimTriggerStrategy(),
            new AlternateDefectCoopStrategy(),
            new AlternateCoopDefectStrategy(),

            // Complex/student-contributed strategies
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
