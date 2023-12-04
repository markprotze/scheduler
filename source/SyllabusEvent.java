package packager;

import java.util.Arrays;
import java.util.List;

public class SyllabusEvent extends CurriculumEvent {
	
	static enum eventStatus {
		Pass_Unsigned,
		Pass_Signed,
		Pass_Reviewed,
		Incomplete_Unsigned,
		Incomplete_Signed,
		Incomplete_Reviewed,
		Conditional_Pass_Unsigned,
		Conditional_Pass_Signed,
		Conditional_Pass_Reviewed,
		Unsat_Unsigned,
		Unsat_Signed,
		Unsat_Reviewed,
		Ready_Room_Unsat_Unsigned,
		Ready_Room_Unsat_Signed,
		Ready_Room_Unsat_Reviewed,
		Eligible,
		Ineligible,
		Waived,
		Proficiency_Advanced,
		CANX,
		Deferred,
		Executing,
		Scheduled,
		Active,
		Blank
	}
	
	private eventStatus status;
	
	public SyllabusEvent(CurriculumEvent curriculumEvent, String status) throws Exception {
		
		super(curriculumEvent.getName(), (List<String>) asList(curriculumEvent.getPrerequisites()), curriculumEvent.getIndex());
		
		if (status.equals("Pass Unsigned")) {
			this.status = eventStatus.Pass_Unsigned;
		} else if (status.equals("Pass Signed")) {
			this.status = eventStatus.Pass_Signed;
		} else if (status.equals("Pass Reviewed")) {
			this.status = eventStatus.Pass_Reviewed;
		} else if (status.equals("Incomp. Unsigned")) {
			this.status = eventStatus.Incomplete_Unsigned;
		} else if (status.equals("Incomp. Signed")) {
			this.status = eventStatus.Incomplete_Signed;
		} else if (status.equals("Incomplete_Reviewed")) {
			this.status = eventStatus.Incomplete_Reviewed;
		} else if (status.equals("Conditional Pass Unsigned")) {
			this.status = eventStatus.Conditional_Pass_Unsigned;
		} else if (status.equals("Conditional Pass Signed")) {
			this.status = eventStatus.Conditional_Pass_Signed;
		} else if (status.equals("Conditional Pass Reviewed")) {
			this.status = eventStatus.Conditional_Pass_Reviewed;
		} else if (status.equals("Unsat Unsigned")) {
			this.status = eventStatus.Unsat_Unsigned;
		} else if (status.equals("Unsat Reviewed")) {
			this.status = eventStatus.Unsat_Reviewed;
		} else if (status.equals("Ready Room Unsat Unsigned")) {
			this.status = eventStatus.Ready_Room_Unsat_Unsigned;
		} else if (status.equals("Ready Room Unsat Signed")) {
			this.status = eventStatus.Ready_Room_Unsat_Signed;
		} else if (status.equals("Ready Room Unsat Reviewed")) {
			this.status = eventStatus.Ready_Room_Unsat_Reviewed;
		} else if (status.equals("Eligible")) {
			this.status = eventStatus.Eligible;
		} else if (status.equals("Ineligible")) {
			this.status = eventStatus.Ineligible;
		} else if (status.equals("Waived")) {
			this.status = eventStatus.Waived;
		} else if (status.equals("Prof. Advanced")) {
			this.status = eventStatus.Proficiency_Advanced;
		} else if (status.equals("CANX")) {
			this.status = eventStatus.CANX;
		} else if (status.equals("Deferred")) {
			this.status = eventStatus.Deferred;
		} else if (status.equals("Executing")) {
			this.status = eventStatus.Executing;
		} else if (status.equals("Scheduled")) {
			this.status = eventStatus.Scheduled;
		} else if (status.equals("Active")) {
			this.status = eventStatus.Active;
		} else if (status.equals("")) {
			this.status = eventStatus.Blank;
		} else {
			throw new Exception("Student status of event is unsupported: " + status);
		}
	}
	
	public eventStatus getEventStatus() {
		return status;
	}
	
	private static List<String> asList(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			return Arrays.asList(stringArray);
		}
	}
	
	protected String printMe() {
		return ("Name: " + super.getName() + ", Type: " + super.getType() + ", Index: " + super.getIndex() + ", Status: " + this.status);
	}

}