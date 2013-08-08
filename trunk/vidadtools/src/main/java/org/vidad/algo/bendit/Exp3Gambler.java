package org.vidad.algo.bendit;


public class Exp3Gambler extends Gambler {

	private double gamma;

	// / <summary>Cumulated weights associated to each variable.</summary>
	private double[] w;

	// / <summary>Sum of the cumulated weights.</summary>
	private double wSum;

	// / <summary>Action probabilities in the last play.</summary>
	private double[] p;

	// / <summary>Creates a new exp3 gambler.</summary>
	public Exp3Gambler(double gamma) throws Exception {
		super();
		if (gamma <= 0.0 || gamma > 1.0)
			throw new Exception("gamma " + gamma
					+ " The gamma parameter should be in (0, 1]");

		this.gamma = gamma;
	}

	// / <summary>Resets the counters.</summary>
	public void Reset() {
		super.Reset();

		// Wieights initialization
		w = new double[getLeverCount()];
		for (int i = 0; i < w.length; i++)
			w[i] = 1.0;

		// Weight sum
		wSum = (double) getLeverCount();

		// Just any non-initialized array for now
		p = new double[getLeverCount()];
	}

	// / <summary>Returns the index of the pulled lever.</summary>
	public int Play(int horizon) {
		// Computing the probabilities
		for (int i = 0; i < getLeverCount(); i++)
			p[i] = (1.0 - gamma) * w[i] / wSum + gamma
					/ (double) getLeverCount();

		// Transformation into cumulated probabilities
		double[] cumP = new double[getLeverCount()];
		cumP[0] = p[0];
		for (int i = 1; i < getLeverCount(); i++)
			cumP[i] = p[i] + cumP[i - 1];

		/*
		 * assert(Math.abs(cumP[getLeverCount() - 1] - 1.0) < 0.0000001,
		 * "The sum of the cumulated probabilities should be equal to one (value = "
		 * + cumP[getLeverCount() - 1].toString() + ").");
		 */
		// selecting the variable
		double threshold = random.nextDouble();
		int index = 0;
		while (cumP[index] < threshold)
			index++;

		return index;
	}

	// / <summary>Records of the reward brough by the specified lever.</summary>
	public void Observe(int index, double reward) {
		super.Observe(index, reward);

		wSum -= w[index];
		w[index] *= Math.exp(gamma * reward
				/ (p[index] * (double) getLeverCount()));

		// brutal handling of numeric overflow
		if (Double.isNaN(w[index]) || Double.isInfinite(w[index])
				|| Math.abs(w[index]) > 10e+9) {
			w[index] = 1.0; // dummy arbitrary weight
		}

		wSum += w[index];
	}

}
