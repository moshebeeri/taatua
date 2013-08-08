package org.vidad.algo.bendit;

public class GaussMatchGambler extends GamblerBase {
	private double skew;

	// / <summary>Index of the last pulled lever.</summary>
	private int lastPulledLever = 0;

	// private NormalGenerator generator = new NormalGenerator();

	// / <summary>Creates a new <i>interval draw</i> gambler.</summary>
	public GaussMatchGambler(double skew) {
		this.skew = skew;
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

		// draw a deviate from each lever
		double maxDeviate = Double.NEGATIVE_INFINITY;
		int maxDeviateIndex = -1;

		for (int i = 0; i < getLeverCount(); i++) {
			double mean = 0;
			if (observationCounts[i] > 0)
				mean = LeverMean(i);
			else
				mean = leverMeanSum / observedLeverCount;

			double sigma = 0;
			if (observationCounts[i] > 1)
				sigma = LeverSigma(i);
			else
				sigma = leverSigmaSum / twiceObservedLeverCount;

			// recycling the normal generator instead of creating a new one each
			// time
			// generator.Mean = mean;
			// generator.Sigma = sigma * skew // notice the "skew" factor
			// / Math.sqrt(Math.max(observationCounts[i], 1));
			// double deviate = generator.Next();
			double std = sigma * skew // notice the "skew" factor
					/ Math.sqrt(Math.max(observationCounts[i], 1));
			double deviate = (random.nextGaussian() * std) + mean;

			if (deviate > maxDeviate) {
				maxDeviate = deviate;
				maxDeviateIndex = i;
			}
		}

		// pulling the lever of highest deviate
		lastPulledLever = maxDeviateIndex;
		return maxDeviateIndex;
	}
}
