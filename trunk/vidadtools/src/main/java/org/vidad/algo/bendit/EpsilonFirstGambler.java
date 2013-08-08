package org.vidad.algo.bendit;

public class EpsilonFirstGambler extends GamblerBase {
	// / <seealso cref="Epsilon"/>
	private double epsilon;

	// / <summary>
	// / Gets the number of remaining exploration rounds.
	// / </summary>
	private int remainingExplorationRounds;

	// / <summary>
	// / Creates a new epsilon first gambler.
	// / </summary>
	public EpsilonFirstGambler(double epsilon) {
		this.epsilon = epsilon;
	}

	// / <summary>
	// / Gets the percentage of initial exploration.
	// / </summary>
	public double getEpsilon() {
		return epsilon;
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// setting the number of initial random pulls
		if (roundIndex == 0) {
			remainingExplorationRounds = (int) (epsilon * horizon);
		}

		// initial exploration
		if (remainingExplorationRounds > 0) {
			remainingExplorationRounds--;
			return random.nextInt(getLeverCount());
		}

		// greedy exploitation
		else {
			double maxMean = Double.NEGATIVE_INFINITY;
			int maxMeanIndex = -1; // dummy initialization

			for (int i = 0; i < getLeverCount(); i++) {
				double mean = rewardSums[i] / Math.max(observationCounts[i], 1);

				if (mean > maxMean) {
					maxMean = mean;
					maxMeanIndex = i;
				}
			}

			return maxMeanIndex;
		}
	}
}
