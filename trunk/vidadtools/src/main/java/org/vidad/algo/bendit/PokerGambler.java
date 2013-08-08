package org.vidad.algo.bendit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;

public class PokerGambler extends GamblerBase {
	// / <summary>Index of the last pulled lever.</summary>
	private int lastPulledLever = 0;

	// / <summary>Creates a new POKER gambler.</summary>
	public PokerGambler() {
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// initialization: observing at least two levers twice
		if (observedLeverCount < 1 || leverSigmaSum == 0) // FIX: the default
															// sigma must not be
															// null
		{
			// playing twice the same lever
			if (observationCounts[lastPulledLever] == 1) {
				return lastPulledLever;
			}

			lastPulledLever = random.nextInt(getLeverCount());
			return lastPulledLever;
		}

		// computing the delta
		List<Double> means = new ArrayList<Double>(observedLeverCount);
		for (int i = 0; i < getLeverCount(); i++) {

			if (observationCounts[i] > 0)
				means.add(rewardSums[i] / observationCounts[i]);
		}
		Collections.sort(means);
		int k = (int) Math.ceil(Math.sqrt(means.size()));
		double delta = means.get(means.size() - 1)
				- means.get(means.size() - k);

		double maxMean = means.get(means.size() - 1);

		// if k equals 1, then just play randomly (delta could not be estimated)
		if (k <= 1) {
			return random.nextInt(getLeverCount());
		}

		delta /= (k - 1);

		// computing the prices of the observed levers
		double maxPrice = Double.NEGATIVE_INFINITY;
		int maxPriceIndex = -1; // dummy initialization

		for (int i = 0; i < getLeverCount(); i++) {
			if (observationCounts[i] > 0) {
				double mean = rewardSums[i] / observationCounts[i];

				// empirical estimate of the standard deviation is avaiblable
				double sigma = 0;
				if (observationCounts[i] > 1) {
					sigma = LeverSigma(i);

					// FIX: sigma must not be null
					if (sigma == 0) {
						sigma = leverSigmaSum / twiceObservedLeverCount;
					}
				}
				// using the avg standard deviation amoung the levers
				else {
					sigma = leverSigmaSum / twiceObservedLeverCount;
				}

				// computing an estimate of the lever optimality probability
				// double proba =
				// Gaussian(mean, maxMean + delta, sigma /
				// Math.sqrt(observationCounts[i]))
				// / Gaussian(mean, mean, sigma /
				// Math.sqrt(observationCounts[i]));

				/*
				 * CumulativeNormalDistribution cnd = new
				 * CumulativeNormalDistribution( mean, sigma /
				 * Math.sqrt(observationCounts[i]));
				 * 
				 * double proba = (1 - cnd.ValueOf(maxMean + delta));
				 */

				NormalDistribution cnd = new NormalDistribution(mean, sigma
						/ Math.sqrt(observationCounts[i]));
				double proba = (1 - cnd.cumulativeProbability(maxMean + delta));

				// price = empirical mean + estimated long term gain
				double price = mean + horizon * delta * proba;

				if (maxPrice < price) {
					maxPrice = price;
					maxPriceIndex = i;
				}
			}
		}

		// computing the price for the unobserved levers
		if (observedLeverCount < getLeverCount()) {
			double unobservedPrice = leverMeanSum / observedLeverCount
					+ horizon * delta / observedLeverCount;

			if (unobservedPrice > maxPrice) {
				maxPrice = unobservedPrice;

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

	// / <param name="x">Value</param>
	// / <param name="mean">Gaussian mean</param>
	// / <param name="sigma">Gaussian sigma</param>
	// / <returns>Probability density</returns>
	@SuppressWarnings("unused")
	private static double Gaussian(double x, double mean, double sigma) {
		// Debug.Assert(sigma > 0.0);
		return 1 / (Math.sqrt(2 * Math.PI) * sigma)
				* Math.exp(-Math.pow((x - mean) / sigma, 2.0) / 2);
	}
}
