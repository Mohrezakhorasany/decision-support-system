package hu.ak_akademia.file;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FileData {

	private Map<String, Map<String, Double>> data;

	public FileData(Map<String, Map<String, Double>> data) {
		this.data = data;
	}

	public Set<String> getColumnOneDataWithoutDuplicates() {
		return data.values()
				.stream()
				.map(Map::keySet)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
	}

	public Set<String> getColumnTwoDataWithoutDuplicates() {
		return data.keySet();
	}

	public Map<String, Double> getValue(String key) {
		return data.get(key);
	}
}
