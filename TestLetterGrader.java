package firstJavaPackage;
import java.util.*;


public class TestLetterGrader {

	public static void main(String args[]) {
		//test if there are two valid arguments then, create the object
		//if not give right message and exit

		String toContinue = null;
		Scanner readInput = new Scanner (System.in);
		do {
			LetterGrader letterGrader = new LetterGrader(args[0], args[1]);
			letterGrader.examInputOutputFiles();
			letterGrader.processInputOutputFiles();
			letterGrader.printScoreStats();
			toContinue = letterGrader.pressAnyKeyToContinue();
		}while (toContinue != null);
		System.out.print("Thank you for using.");
		readInput.close();
	}
		
}
