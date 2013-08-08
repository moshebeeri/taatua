package org.vidad.algo.bendit;

public class SoftmaxGambler extends GamblerBase {
	private double temperature;

	// / <summary>Creates a new SoftMax gambler.</summary>
	public SoftmaxGambler(double temperature) {
		this.temperature = temperature;
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		double[] weights = new double[getLeverCount()];

		// Generating the Gibs cumulated probabilities
		for (int i = 0; i < getLeverCount(); i++) {
			weights[i] = Math
					.exp(rewardSums[i]
							/ ((double) Math.max(observationCounts[i], 1) * temperature));
		}
		// random.nextInt(weights.length).;
		return StdRandom.discrete(weights);
		// return (new DiscreteGenerator(weights)).Next();
	}
}
