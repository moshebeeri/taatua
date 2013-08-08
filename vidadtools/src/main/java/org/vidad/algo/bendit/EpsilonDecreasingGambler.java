package org.vidad.algo.bendit;

import java.util.Random;

public class EpsilonDecreasingGambler extends GamblerBase {
	// / <seealso cref="EpsilonDecreasingGambler"/>
	private double epsilonZero;

	// / <summary>Creates a new epsilon decreasing gambler.</summary>
	public EpsilonDecreasingGambler(double epsilonZero) throws Exception {
		if (epsilonZero <= 0.0)
			throw new Exception("epsilonZero" + epsilonZero
					+ "epsilonZero must be in the interval (0, +infinity).");

		this.epsilonZero = epsilonZero;
	}

	// / <summary>Gets the <i>epsilon zero</i> tuning parameter.</summary>
	// / <remarks>
	// / See the <see cref="EpsilonDecreasingGambler"/> for details about this
	// / tuning parameter.
	// / </remarks>
	public double getEpsilonZero() {
		return epsilonZero;
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// computing the max mean (and the 2nd mean)
		double maxMean = Double.NEGATIVE_INFINITY;// Double.NEGATIVE_INFINITY;
		int maxIndex = -1; // dummy initialization

		for (int i = 0; i < getLeverCount(); i++) {
			double mean = rewardSums[i] / Math.max(observationCounts[i], 1);

			if (mean > maxMean) {
				maxMean = mean;
				maxIndex = i;
			}
		}

		// Computing the lever epsilonZero value
		double epsilonZeroT = Math.min(1.0,
				epsilonZero / Math.max(roundIndex, 1));

		if (Math.random() < epsilonZeroT) {
			Random randomGenerator = new Random();

			// randomom lever with epsilonZero frequency
			return randomGenerator.nextInt(getLeverCount());
		} else {
			// greedy optimal lever
			return maxIndex;
		}
	}
}
