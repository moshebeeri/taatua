package org.vidad.algo.bendit;

public class EpsilonGreedyGambler extends GamblerBase {
	// / <seealso cref="Epsilon"/>
	private double epsilon;

	// / <summary>
	// / Creates a new epsilon-greedy gambler.
	// / </summary>
	public EpsilonGreedyGambler(double epsilon) {
		this.epsilon = epsilon;
	}

	// / <summary>
	// / Gets the <i>random pull</i> frequency.
	// / </summary>
	public double getEpsilon() {
		return epsilon;
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// randomom lever with epsilon frequency
		if (random.nextDouble() < epsilon || roundIndex == 0) {
			return random.nextInt(getLeverCount());
		}

		// greedy optimal lever
		else {
			double maxMean = Double.NEGATIVE_INFINITY;
			int maxIndex = -1; // dummy initialization

			for (int i = 0; i < getLeverCount(); i++) {
				double mean = rewardSums[i] / Math.max(observationCounts[i], 1);

				if (mean > maxMean) {
					maxMean = mean;
					maxIndex = i;
				}
			}

			return maxIndex;
		}
	}
}
