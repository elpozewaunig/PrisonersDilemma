package prisonersdilemma;

import prisonersdilemma.strategies.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    var players = List.of(
        new CopyOtherPlayerStrategy(),
        new PureTitForTatStrategy(),
        new AlwaysDefectStrategy(),
        new AlwaysCooperateStrategy(),
        new RandomStrategy(),
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
