package packager;

import java.util.List;

public class DocumentRefEvent {
	
	private String name;
	private List<String> prerequisites;

	public DocumentRefEvent(String name, List<String> prerequisites) {
		this.name = name;
		this.prerequisites = prerequisites;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getPrerequisites() {
		return this.prerequisites;
	}
	
}