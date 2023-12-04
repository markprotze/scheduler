package packager;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Curriculum {
	
	private CurriculumEvent[] CurriculumEventArray;
	private DocumentRefEvent[] DocumentRefEventArray;
	@SuppressWarnings("rawtypes")
	private DynamicArrayList TSharpData;
	private int numOfStudents;
	private int numOfEvents;
	private Student[] StudentArray;
	private String syllabusVersion;
	private String dataTime;
	private String curriculumFilePath;
	
	public Curriculum(String curriculumFilePath, String tSharpExcelPath) throws Exception {
		this.curriculumFilePath = curriculumFilePath;
		readCirriculum(curriculumFilePath);
		createCurriculumEvents(tSharpExcelPath);
		matchCurriculumEvents();
		createStudents();
	}
	
	public CurriculumEvent[] getCurriculumEventArray() {
		return this.CurriculumEventArray;
	}
	
	public String getCurriculumFilePath() {
		return this.curriculumFilePath;
	}
	
	public int getNumOfEvents() {
		return this.numOfEvents;
	}
	
	public int getNumOfStudents() {
		return this.numOfStudents;
	}
	
	public Student[] getStudents() {
		return this.StudentArray;
	}
	
	public String getSyllabusVersion() {
		return this.syllabusVersion;
	}
	
	public String getDataTime() {
		return this.dataTime;
	}

	private void readCirriculum(String filepath) {
	      JSONParser parser = new JSONParser();
	      try {
	         Object obj = parser.parse(new FileReader(filepath));
	         JSONObject superObject = (JSONObject)obj;
	         syllabusVersion =  (String) superObject.get("syllabusVersion");
	         JSONArray eventArray = (JSONArray) superObject.get("events");
	         DocumentRefEventArray = new DocumentRefEvent[eventArray.size()];
	         for (int j = 0; j < eventArray.size(); j++) {
		         JSONObject eventObject = (JSONObject) eventArray.get(j);
		         String name = (String) eventObject.get("Name");
		         JSONArray prerequisiteArray = (JSONArray) eventObject.get("Prerequisites");
		         List<String> prerequisites = new ArrayList<String>();
		         if (prerequisiteArray != null) {
			         for(int i = 0; i < prerequisiteArray.size(); i++){
			        	 prerequisites.add((String) prerequisiteArray.get(i));
			         }
		         } else {
		        	 prerequisites = null;
		         }
		         DocumentRefEventArray[j] = new DocumentRefEvent(name, prerequisites);
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	  }
	
	@SuppressWarnings("unchecked")
	private void createCurriculumEvents(String filepath) throws Exception {
		ExcelParser Excel = new ExcelParser(filepath);
		dataTime = Excel.getDataTime();
		TSharpData = Excel.getArray();
		this.numOfStudents = Excel.getNumStudents();
		ArrayList<String> eventTitlesList = (ArrayList<String>) Excel.getArray().get(5);
		String[] eventTitles = new String[eventTitlesList.size()];
		eventTitles = eventTitlesList.toArray(eventTitles);
		CurriculumEventArray = new CurriculumEvent[eventTitles.length - 4];
		int incrementer = 0;
		for (int i = 3; i < eventTitles.length - 1; i++) {
			if (hasFourDigits(eventTitles[i])) {
				//TODO: hey! I never implemented referencing the documentrefeventarray to determine prerequisites!
				List<String> prerequisites = new ArrayList<String>();
				for (int j = 0; j < DocumentRefEventArray.length; j++) {
					if (DocumentRefEventArray[j].getName().equals(eventTitles[i])) {
						if (DocumentRefEventArray[j].getPrerequisites() != null) {
							prerequisites = DocumentRefEventArray[j].getPrerequisites();
						} else {
							prerequisites = null;
						}
					}
				}
				CurriculumEventArray[incrementer] = new CurriculumEvent(eventTitles[i], prerequisites, i);
				incrementer++;
			}
		}
		this.numOfEvents = incrementer;
	}
	
	//consolidates curriculum event array to only include events that have prerequisite data and are sought to be displayed
	private void matchCurriculumEvents() {
		for (int i = 0; i < this.numOfEvents; i++) {
			if (!matchesDocRefEvent(CurriculumEventArray[i].getName())) {
				CurriculumEventArray[i] = null;
			}
		}
		//remove null spaces
		CurriculumEventArray = Arrays.stream(CurriculumEventArray).filter(Objects::nonNull).toArray(CurriculumEvent[]::new);
		int numOfEvents = 0;
		for (int i = 0; i < CurriculumEventArray.length; i++) {
			if (CurriculumEventArray[i] != null) {
				numOfEvents++;
			}
		}
		this.numOfEvents = numOfEvents;
	}
	
	private boolean matchesDocRefEvent(String eventName) {
		for (int i = 0; i < DocumentRefEventArray.length; i++) {
			if (DocumentRefEventArray[i].getName().equals(eventName)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createStudents() throws Exception {
	StudentArray = new Student[this.numOfStudents];
	int incrementer = 0;
		for (int i = 6; i < this.numOfStudents + 6; i++) {
			SyllabusEvent[] currentSyllabusEventArray = new SyllabusEvent[this.numOfEvents];
			String[] lineData = new String[((ArrayList) TSharpData.get(i)).size()];
			for (int j = 0; j < this.numOfEvents; j++) {
				List<String> lineDataList = (List<String>) TSharpData.get(i);
				lineData = lineDataList.stream().toArray(String[]::new);
				currentSyllabusEventArray[j] = new SyllabusEvent(CurriculumEventArray[j], lineData[CurriculumEventArray[j].getIndex()]);
			}
			StudentArray[incrementer] = new Student(lineData[0], currentSyllabusEventArray);
			incrementer++;
		}
	}
	
	private boolean hasFourDigits(String input) {
		int digitIncremeter = 0;
		char[] charArray = input.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (Character.isDigit(charArray[i])) {
				digitIncremeter++;
			}
		}
		if (digitIncremeter == 4) {
			return true;
		} else {
			return false;
		}
	}

}