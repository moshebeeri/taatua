package org.vidad.algo.bendit;

import java.util.Random;


public class SoftMixGambler extends Gambler {
	private static Random random = new Random();

	private double d;

	private double[] s;

	private int roundCount;

	// / <summary>
	// / Used as a cache for the <see cref="Observe"/> method implementation.
	// / </summary>
	private double gamma;

	// / <summary>
	// / Used as a cache for the <see cref="Observe"/> method implementation.
	// / </summary>
	private int maxIndex;

	// / <summary>Creates a new SoftMix gambler.</summary>
	public SoftMixGambler(double d) throws Exception {
		if (d <= 0.0 || d >= 1.0)
			throw new Exception("d=" + d
					+ " #E00: d should be in the interval (0,1).");

		this.d = d;
	}

	// / <summary>Reset the counters of the SoftMix gambler.</summary>
	public void Reset() {
		super.Reset();

		// zero initialization
		s = new double[getLeverCount()];
		roundCount = 0;
	}

	// / <summary>Returns the index of the pulled levers.</summary>
	public int Play(int horizon) {
		// computing gamma_t
		gamma = 1.0;

		if (roundCount > 2) {
			gamma = Math.min(1.0,
					5 * getLeverCount() * Math.log(roundCount - 1)
							/ (d * d * (roundCount - 1)));
		}

		// computing the maximal index
		double maxS = Double.NEGATIVE_INFINITY;
		maxIndex = -1; // dummy initialization

		for (int i = 0; i < getLeverCount(); i++) {
			if (s[i] > maxS) {
				maxS = s[i];
				maxIndex = i;
			}
		}

		// randomom lever with epsilon frequency
		if (random.nextDouble() < gamma) {
			return random.nextInt(getLeverCount());
		}
		// greedy optimal lever
		else {
			return maxIndex;
		}
	}

	// / <summary>Records of the reward brough by the specified lever.</summary>
	public void Observe(int index, double value) {
		// The base method MUST be called
		super.Observe(index, value);

		roundCount++;

		if (index == maxIndex) {
			s[index] += value / (1 - gamma + (gamma / getLeverCount()));
		} else {
			s[index] += value / (gamma / getLeverCount());
		}
	}
}
