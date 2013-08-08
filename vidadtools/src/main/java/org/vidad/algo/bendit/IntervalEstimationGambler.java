package org.vidad.algo.bendit;

import org.apache.commons.math3.distribution.NormalDistribution;

public class IntervalEstimationGambler extends GamblerBase {
	private double alpha;

	// / <summary>Index of the last pulled lever.</summary>
	private int lastPulledLever = 0;

	// / <param name="alpha">Upper confidence parameter.</param>
	public IntervalEstimationGambler(double alpha) throws Exception {
		if (alpha <= 0.0 || alpha >= 1.0)
			throw new Exception("alpha" + alpha
					+ "alpha should be in the interval (0,1).");

		this.alpha = alpha;
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// initialization: observing at least two levers twice
		if (observedLeverCount < 1 || twiceObservedLeverCount < 1) {
			// playing twice the same lever
			if (observationCounts[lastPulledLever] == 1) {
				return lastPulledLever;
			}

			return random.nextInt(getLeverCount());
		}

		double maxPrice = Double.NEGATIVE_INFINITY;
		int maxPriceIndex = -1; // dummy initialization

		// computing the prices of the observed levers
		for (int i = 0; i < getLeverCount(); i++) {
			if (observationCounts[i] > 0) {
				double price = 0;

				// two or more observations
				if (observationCounts[i] > 1) {
					price = Price(LeverMean(i), LeverSigma(i),
							observationCounts[i]);
				}
				// only one observation
				else {
					price = Price(LeverMean(i), leverSigmaSum
							/ twiceObservedLeverCount, 1);
				}

				if (price > maxPrice) {
					maxPrice = price;
					maxPriceIndex = i;
				}
			}
		}

		// computing the price of an unobserved lever
		if (observedLeverCount < getLeverCount()) {
			double uPrice = Price(leverMeanSum / observedLeverCount,
					leverSigmaSum / twiceObservedLeverCount, 1);

			if (uPrice > maxPrice) {
				maxPrice = uPrice;

				// Choosing randomly an unobserved lever
				int uIndex = random.nextInt(getLeverCount()
						- observedLeverCount);
				int uCount = 0;
				for (int i = 0; i < getLeverCount(); i++) {
					if (observationCounts[i] == 0) {
						if (uCount == uIndex) {
							maxPriceIndex = i;
							break;
						} else
							uCount++;
					}
				}
			}
		}

		lastPulledLever = maxPriceIndex;
		return maxPriceIndex;
	}

	private double Price(double mean, double sigma, int count) {
		NormalDistribution icdf = new NormalDistribution(mean, sigma
				/ Math.sqrt(Math.max(count, 1)));
		return icdf.cumulativeProbability(1 - alpha);
		// InvCumulativeNormalDistribution icdf =
		// new InvCumulativeNormalDistribution(mean, sigma /
		// Math.sqrt(Math.max(count, 1)));
		//
		// return icdf.ValueOf(1 - alpha);
	}
}
