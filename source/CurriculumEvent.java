package packager;

import java.util.List;

public class CurriculumEvent implements CurriculumEventInterface {
	
	private String name;
	private String type;
	private String[] prerequisiteArray;
	private int index;
	
	public CurriculumEvent(String name, List<String> prerequisites, int index) throws Exception {
		int firstDigit = firstDigit(name);
		this.name = name;
		if (firstDigit == 0) {
			this.type = "Ground training";
		} else if (firstDigit == 1) {
			this.type = "Flight support";
		} else if ((firstDigit == 2) || (firstDigit == 3)) {
			this.type = "Sim";
		} else if (firstDigit == 4) {
			this.type = "Flight";
		} else {
			throw new Exception("type of curriculum event not supported");
		}
		if (prerequisites != null) {
			this.prerequisiteArray = prerequisites.stream().toArray(String[]::new);
		} else {
			this.prerequisiteArray = null;
		}
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}
	
	public int getIndex() {
		return this.index;
	}

	public String[] getPrerequisites() {
		return this.prerequisiteArray;
	}
	
	protected String printMe() {
		return ("Name: " + this.name + ", Type: " + this.type + ", Index: " + this.index);
	}
	
	protected int firstDigit(String name) throws Exception {
		char [] charArray = name.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((Character.isDigit(charArray[i]))) {
				int digit = Character.getNumericValue(charArray[i]);
				if ((digit == 0) || (digit == 1) || (digit == 2) || (digit == 3) || (digit == 4)) {
					return digit;
				}
			}
		}
		throw new Exception("no digit found in curriculum event name to determine type");
	}

}