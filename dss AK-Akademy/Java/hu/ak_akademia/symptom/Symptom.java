package hu.ak_akademia.symptom;

import java.io.Serializable;
import java.util.Objects;

public class Symptom implements Serializable {

	private static final long serialVersionUID = 4543822783406673692L;
	private final int id;
	private final String name;
	private final double tfidfScore;

	public Symptom(String description, double tfidfScore, int id) {
		this.name = description;
		this.tfidfScore = tfidfScore;
		this.id = id;
	}

	public Symptom(int id, String description) {
		this.id = id;
		this.name = description;
		this.tfidfScore = 0;
	}

	public double getTfidfScore() {
		return tfidfScore;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Symptom)) {
			return false;
		}
		Symptom other = (Symptom) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Symptom id: " + id + ", " + ", name: " + name + ", Score: " + tfidfScore;
	}
}
