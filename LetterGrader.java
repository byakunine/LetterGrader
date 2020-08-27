package firstJavaPackage;

import java.util.*;
import java.io.*;
class ScoreStats{
	//class ScoreStats to store new score data
	//and calculate max, min and avg scores
	//of an exam, e.g. quiz1, quiz2, mid1, etc.
	double totalScore;
	double maxScore;
	double minScore;
	
	public ScoreStats() {
	//constructor
	//initialize totalScore = 0, maxScore = 0, and minScore = 100
		totalScore = 0;
		maxScore = 0;
		minScore = 100;
	}
	
	public void addNewScore(double newScore) {
	//when a student's data is loaded, new score will be added
	//totalScore 
		totalScore += newScore;
		maxScore = Math.max(maxScore, newScore);
		minScore = Math.min(minScore, newScore);
	}
	
	public double getAvg(int totalStudents) {
	//calculate avg score
		return totalScore/totalStudents;
	}
}

class Student{
	//class Student to store students' information
	String name;
	double quiz1;
	double quiz2;
	double quiz3;
	double quiz4;
	double mid1;
	double mid2;
	double finalExam;
	double finalScore;
	char grade;

	public Student(String name, double quiz1, double quiz2, double quiz3, double quiz4, double mid1, double mid2, double finalExam){
	//constructor	
	//calculates finalScore and letterGrade
		this.name = name;
		this.quiz1 = quiz1;
		this.quiz2 = quiz2;
		this.quiz3 = quiz3;
		this.quiz4 = quiz4;
		this.mid1 = mid1;
		this.mid2 = mid2;
		this.finalExam = finalExam;
		
		//calculate letter grade
		this.finalScore = quiz1 * 0.10 + quiz2 * 0.10 + quiz3 * 0.10 + quiz4 * 0.10 + mid1 * 0.20 + mid2 * 0.15 + finalExam * 0.25;
		if (this.finalScore >= 0.9) {
			this.grade = 'A';
		}
		else if(this.finalScore >= 0.8) {
			this.grade = 'B';
		}
		else if(this.finalScore >= 0.7) {
			this.grade = 'C';
		}
		else if(this.finalScore >= 0.6) {
			this.grade = 'D';
		}
		else {
			this.grade = 'F';
		}
	}
	
	public String getName() {
		return this.name;
	}
}


public class LetterGrader {
//main class
	
	String inputFileName;
	String outputFileName;
	ScoreStats quizOne = new ScoreStats();
	ScoreStats quizTwo = new ScoreStats();
	ScoreStats quizThree = new ScoreStats();
	ScoreStats quizFour = new ScoreStats();
	ScoreStats midOne = new ScoreStats();
	ScoreStats midTwo = new ScoreStats();
	ScoreStats finalExam = new ScoreStats();
	ArrayList<Student> arrayStudentInfo = new ArrayList <Student>();


	public LetterGrader(String inputFileName, String outputFileName){
	//constructor 
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
	}
	
	public void examInputOutputFiles() {

			File inputFile = new File (inputFileName);
			File outputFile = new File (outputFileName);
			if (!inputFile.exists()) {
				System.out.println("Input file '" + inputFileName + "' does not exist.");
				System.exit(2);
				}
			else if(!outputFile.exists()) {
				System.out.println("Output file '" + outputFileName + "' does not exist.");
				System.exit(2);
				}
			}
	
	public void processInputOutputFiles() {
		//Read data in input file line by line
		//1. Store data to Array list arrayStudentInfo
		//2. 
		File inputFile = new File (inputFileName);
		File outputFile = new File (outputFileName);
		try {
			Scanner input = new Scanner(inputFile);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				
				// this line has one student's data,
				String[] studentInfo = line.split(",");
				
				//transfer data type to double
				double quiz1 = Double.parseDouble(studentInfo[1].trim());
				double quiz2 = Double.parseDouble(studentInfo[2].trim());
				double quiz3 = Double.parseDouble(studentInfo[3].trim());
				double quiz4 = Double.parseDouble(studentInfo[4].trim());
				double mid1 = Double.parseDouble(studentInfo[5].trim());
				double mid2 = Double.parseDouble(studentInfo[6].trim());
				double finalScore = Double.parseDouble(studentInfo[7].trim());
				
				//add this student's scores to each exam's ScoreStats class
				quizOne.addNewScore(quiz1);
				quizTwo.addNewScore(quiz2);
				quizThree.addNewScore(quiz3);
				quizFour.addNewScore(quiz4);
				midOne.addNewScore(mid1);
				midTwo.addNewScore(mid2);
				finalExam.addNewScore(finalScore);
				
				//add this student's information and scores to Student class
				Student s = new Student(studentInfo[0],quiz1,quiz2,quiz3,quiz4,mid1,mid2,finalScore);
				arrayStudentInfo.add(s);
			}
			input.close();

		}catch (IOException e) {
			System.out.println("Error reading from input file:" + inputFileName);
		}
	
	
		//student letter grades print to output file
		try {
			PrintWriter output = new PrintWriter(outputFile);
			int totalStudents = arrayStudentInfo.size();
			output.print("Letter grade for " + totalStudents + " students given in '"+ inputFileName +"' file is:\n");
			
			//sort student name 
			Comparator<Student> compareByName = (Student o1, Student o2) -> o1.getName().compareTo( o2.getName() );
			Collections.sort(arrayStudentInfo, compareByName);
			
			//print out student's letter grades
			   for(Student str: arrayStudentInfo){
					output.println(str.name + ":\t" + str.grade);
			   }
			
			
			output.println("");
			output.close();
		}catch (IOException e) {
			System.out.println("Error writing to output file: " + outputFileName);
		}
	}
	
	
	public void printScoreStats() {
	//print out class score statistics on the screen
	//should show class average, minimum, and maximum
		
			System.out.print("Letter grade has been calculated for students listed in input file '" + inputFileName +"' and written to output file '"+ outputFileName +"'\n");
			System.out.println("Here are the class averages, minimums, and maximums:");
			System.out.println("\t\tQ1\tQ2\tQ3\tQ4\tMidI\tMidII\tFinal");
			
			int totalStudents = arrayStudentInfo.size();
			double quiz1avg = quizOne.getAvg(totalStudents);
			double quiz2avg = quizTwo.getAvg(totalStudents);
			double quiz3avg = quizThree.getAvg(totalStudents);
			double quiz4avg = quizFour.getAvg(totalStudents);
			double mid1avg = midOne.getAvg(totalStudents);
			double mid2avg = midTwo.getAvg(totalStudents);
			double finalavg = finalExam.getAvg(totalStudents);
			
			System.out.println("Average:\t"+ quiz1avg +"\t"+ quiz2avg + '\t'+ quiz3avg +'\t' + quiz4avg + '\t' + mid1avg + '\t' + mid2avg + '\t' + finalavg);
			System.out.println("Minimum:\t"+ quizOne.minScore +"\t"+ quizTwo.minScore + '\t'+ quizThree.minScore +'\t' + quizFour.minScore + '\t' + midOne.minScore + '\t' + midTwo.minScore + '\t' + finalExam.minScore);
			System.out.println("Maximum:\t"+ quizOne.maxScore +"\t"+ quizTwo.maxScore + '\t'+ quizThree.maxScore +'\t' + quizFour.maxScore + '\t' + midOne.maxScore + '\t' + midTwo.maxScore + '\t' + finalExam.maxScore);

	}
	
	public static String pressAnyKeyToContinue() {
	//read user input
	//if user press any key, it returns the value and continue the process
		String toContinue = null;
		System.out.println("Press ENTER key to continue..");
		BufferedReader read = new BufferedReader (new InputStreamReader(System.in));
		try {
			toContinue = read.readLine();
			
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return toContinue;
	}
	
}
