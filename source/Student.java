package packager;

import java.util.ArrayList;
import java.util.List;

import packager.SyllabusEvent.eventStatus;

public class Student {
	
	private String name;
	private SyllabusEvent[] SyllabusEventArray;
	private String curriculumError;

	public Student(String name, SyllabusEvent[] SyllabusEventArray) {
		this.name = name;
		this.SyllabusEventArray = SyllabusEventArray;
	}
	
	public String getName() {
		return this.name;
	}
	
	public SyllabusEvent[] getSyllabusEventArray() {
		return this.SyllabusEventArray;
	}

	//defines logic that identifies which events a student is eligible for and display them in a string format with newlines
	public String getEligibleEvents() throws Throwable {
		List<String> eligibleEvents = new ArrayList<String>();
		for (int i = 0; i < SyllabusEventArray.length; i++) {
			if (!isEventComplete(SyllabusEventArray[i])) {
				if (SyllabusEventArray[i].getPrerequisites() != null) {
					if (arePrerequisitesMet(SyllabusEventArray[i])) {
						eligibleEvents.add(SyllabusEventArray[i].getName());
					}
				}
				else {
					eligibleEvents.add(SyllabusEventArray[i].getName());
				}
			}
		}
		String[] eligibleStrings = eligibleEvents.stream().toArray(String[]::new);
		String returner = "";
		for (int j = 0; j < eligibleStrings.length; j++) {
			returner += eligibleStrings[j] + "\n";
		}
		return returner;
	}
	
	private boolean arePrerequisitesMet(SyllabusEvent syllabusEvent) throws Throwable {
		if (syllabusEvent.getPrerequisites() == null) {
			return true;
		}
		String[] prerequisiteArray = syllabusEvent.getPrerequisites();
		SyllabusEvent[] prerequisiteSyllabusEvents = null;
		try {
			prerequisiteSyllabusEvents = convertToEvent(prerequisiteArray);
		} catch (Throwable t) {
			throw new Exception(curriculumError + "and was listed as a prerequisite of " + syllabusEvent.getName() + "\n");
		}
		for (int i = 0; i < prerequisiteSyllabusEvents.length; i++) {
			if (!isEventComplete(prerequisiteSyllabusEvents[i])) {
				return false;
			}
		}
		return true;
	}

	protected SyllabusEvent[] convertToEvent(String[] prerequisiteArray) throws Throwable { //TODO: change to private
		SyllabusEvent[] prerequisiteSyllabusEvents = new SyllabusEvent[prerequisiteArray.length];
		boolean debugger = false;
		for (int i = 0; i < prerequisiteArray.length; i++) {
			//find index of match and add it to returner array
			for (int j = 0; j < SyllabusEventArray.length; j++) {
				if (SyllabusEventArray[j].getName().equals(prerequisiteArray[i])) {
					prerequisiteSyllabusEvents[i] = SyllabusEventArray[j];
					j = SyllabusEventArray.length;
					debugger = true;
				}
			}
			if (!debugger) {
				curriculumError = "Syallabus event " + prerequisiteArray[i] + " is not found in the tSharp export ";
				Throwable noStackTrace = new Throwable();
				noStackTrace.setStackTrace(new StackTraceElement[0]);
				throw noStackTrace;
			} else {
				debugger = false;
			}
		}
		for (int i = 0; i < prerequisiteSyllabusEvents.length; i++) {
		}
		return prerequisiteSyllabusEvents;
	}

	private boolean isEventComplete(SyllabusEvent syllabusEvent) {
		eventStatus status = syllabusEvent.getEventStatus();
		boolean isComplete = false;
		switch (status) {
			case Pass_Unsigned: isComplete = true;
			break;
			case Pass_Signed: isComplete = true;
			break;
			case Pass_Reviewed: isComplete = true;
			break;
			case Incomplete_Unsigned: isComplete = false;
			break;
			case Incomplete_Signed: isComplete = false;
			break;
			case Incomplete_Reviewed: isComplete = false;
			break;
			case Conditional_Pass_Unsigned: isComplete = true;
			break;
			case Conditional_Pass_Signed: isComplete = true;
			break;
			case Conditional_Pass_Reviewed: isComplete = true;
			break;
			case Unsat_Unsigned: isComplete = false;
			break;
			case Unsat_Signed: isComplete = false;
			break;
			case Unsat_Reviewed: isComplete = false;
			break;
			case Ready_Room_Unsat_Unsigned: isComplete = false;
			break;
			case Ready_Room_Unsat_Signed: isComplete = false;
			break;
			case Ready_Room_Unsat_Reviewed: isComplete = false;
			break;
			case Eligible: isComplete = false;
			break;
			case Ineligible: isComplete = false;
			break;
			case Waived: isComplete = false;
			break;
			case Proficiency_Advanced: isComplete = false;
			break;
			case CANX: isComplete = false;
			break;
			case Deferred: isComplete = false;
			break;
			case Executing: isComplete = false;
			break;
			case Scheduled: isComplete = false;
			break;
			case Active: isComplete = false;
			break;
			case Blank: isComplete = false;
			break;
		}
		return isComplete;
	}

}