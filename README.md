# PrisonersDilemma

This is a Java project created as part of the Object-oriented modelling and implementation (OMI) course at the University of Klagenfurt during the summer semester 2024. It collects submissions for an assignment to demonstrate the strategy design pattern. Students implemented various strategies to compete in an iterative [Prisoner's Dilemma](https://en.wikipedia.org/wiki/Prisoner%27s_dilemma) game. In addition to the original code provided by the instructors and student strategies, this project features a greatly improved tournament runner (compared to the initially provided one) making the game both more fair and more fun in addition to providing greater insight into every strategy's performance.

## Tournament rules
As played by the `BetterTournamentRunner`class

* Every match of the tournament has a **finite** but initially unknown (randomized) **number of rounds** per match
* To keep things fair, the **number of rounds does not change** throughout one tournament, since otherwise some strategies might unfairly gain more points than others by playing more rounds
  * This means that strategies may count the rounds to gain an advantage in subsequent rounds
  * In order not to give the first competitor of any strategy an advantage (since no strategy knows the number of rounds in the first match and therefore can't change its behaviour accordingly, but can in the second match), all strategies are granted a "calibration" round against an always defecting strategy
* Multiple instances of the same strategy may compete, the **results are on a per-instance basis**, not a per-class basis as this might give strategies with multiple instances in the tournament an advantage
* All strategies **play against every other strategy** twice (once as player 1, once as player 2)
* Every round of a match grants the strategy either 0, 1, 3 or 5 points depending on the opponent's decision (see table)

  | Points | Decision  | Opponent's decision |
  |--------|-----------|---------------------|
  | 0      | Cooperate | Defect              |
  | 1      | Defect    | Defect              |
  | 3      | Cooperate | Cooperate           |
  | 5      | Defect    | Cooperate           |

* The scores of a match are summed up for both strategies and stored
* The final score for a strategy is the **sum of its points** across all matches
* As some strategies may perform very poorly and boost specific strategies with their behaviour, the **bottom half of the scoreboard is eliminated**
  * If strategies are tied at the elimination cutoff score, they persist
* The scores are then summed up again for the remaining contestants, repeating the elimination process
* If no contestants could be eliminated due to ties or if less than 3 strategies are remaining (since a match between 2 strategies would heavily favour an always-defect strategy, while 3 strategies allows for gaining an advantage through cooperation), no more eliminations take place and the final winners are determined

## Credits

The initial tournament code was provided by Manuel Rasinger ([@herrytcu](https://github.com/herrytco)) - thank you!
Most student strategies are anonymized for privacy (except for our team contributions) but they are used with consent of the participating students. Credits go to the respective authors.
