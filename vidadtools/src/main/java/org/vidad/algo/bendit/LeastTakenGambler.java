package org.vidad.algo.bendit;

public class LeastTakenGambler extends GamblerBase {
	// / <seealso cref="LeastTakenGambler"/>
	private double epsilonZero;

	// / <summary>Creates a new epsilon decreasing gambler.</summary>
	public LeastTakenGambler(double epsilonZero) throws Exception {
		if (epsilonZero <= 0.0)
			throw new Exception("epsilonZero" + epsilonZero
					+ " epsilonZero must be in the interval (0, +infinity).");

		this.epsilonZero = epsilonZero;
	}

	// / <summary>Gets the <i>epsilon zero</i> tuning parameter.</summary>
	// / <remarks>
	// / See the <see cref="LeastTakenGambler"/> for details about this
	// / tuning parameter.
	// / </remarks>
	public double getEpsilonZero() {
		return epsilonZero;
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// computing the max mean
		double maxMean = Double.NEGATIVE_INFINITY;
		int maxMeanIndex = -1;
		int minObsCount = Integer.MAX_VALUE;
		int minObsIndex = -1;

		for (int i = 0; i < getLeverCount(); i++) {
			double mean = rewardSums[i] / Math.max(observationCounts[i], 1);

			if (mean > maxMean) {
				maxMean = mean;
				maxMeanIndex = i;
			}

			if (observationCounts[i] < minObsCount) {
				minObsCount = observationCounts[i];
				minObsIndex = i;
			}
		}

		// Computing the lever epsilonZero value
		double epsilonZeroT = epsilonZero * 4 / (4 + minObsCount * minObsCount);

		if (random.nextDouble() < epsilonZeroT) {
			return minObsIndex;
		} else {
			// greedy optimal lever
			return maxMeanIndex;
		}
	}
}
