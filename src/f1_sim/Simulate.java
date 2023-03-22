package f1_sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Anja Tanovic
 */
public class Simulate {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        //Keyboard input
        Scanner sc = new Scanner(System.in);

        String drivers_txt;
        File drivers_file;
        String venues_txt;
        File venues_file;

        do {
            System.out.print("Insert the absolute path to the DRIVER text document: ");
            drivers_txt = sc.nextLine();
            drivers_file = new File(drivers_txt);
            if (!drivers_file.exists() || !drivers_file.isFile()) {
                System.out.println("File is not found.");
            }
        } while (!drivers_file.exists() || !drivers_file.isFile());

        do {
            System.out.print("Insert the absolute path to the VENUE text document: ");
            venues_txt = sc.nextLine();
            venues_file = new File(venues_txt);
            if (!venues_file.exists() || !venues_file.isFile()) {
                System.out.println("File is not found.");
            }
        } while (!venues_file.exists() || !venues_file.isFile());

        int numRaces = 0;
        String readString;
        do {
            System.out.println("How many races will be run in this championship? "
                    + "(3-5 is possible)");
            readString = sc.nextLine();
            try {
                numRaces = Integer.parseInt(readString);
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormat Exception: invalid input. "
                        + "Insert has to be a number.");
            }
            if (numRaces > 5 || numRaces < 3) {
                System.out.println("Number is not inside the expected range.");
            }
        } while (numRaces > 5 || numRaces < 3);

        Championship chmp = new Championship(drivers_txt, venues_txt, numRaces);

        int chosenRace = 0;
        int[] races = new int[numRaces]; //array of chosen races, example: 2 5 3 are chosen

        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("RACING CHAMPIONSHIP IS ABOUT TO BEGIN!");
        System.out.println("---------------------------------------");
        System.out.println();

        for (int i = 0; i < numRaces; i++) {
            System.out.println("Choose venue for race number " + (i + 1) + ":");
            for (int j = 1; j <= chmp.getNumberOfVenues(); j++) {
                if (!chmp.isChosenRace(j - 1)) {
                    System.out.println(j + ") " + chmp.getVenuesName(j - 1));
                }
            }
            do {
                readString = sc.nextLine();
                try {
                    chosenRace = Integer.parseInt(readString);
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormat Exception: invalid input. "
                            + "Number of venue should be inserted.");
                }
                if (chosenRace > chmp.getNumberOfVenues() || chosenRace < 1 || chmp.isChosenRace(chosenRace - 1)) {
                    System.out.println("Inserted number does not exist on a list. Try again.");
                }
            } while (chosenRace > chmp.getNumberOfVenues() || chosenRace < 1 || chmp.isChosenRace(chosenRace - 1));

            chmp.setChosenRace(chosenRace - 1);
            races[i] = chosenRace - 1;
            
            System.out.println();
            chmp.prepareForTheRace();
            System.out.println("---------------------------------------");
            System.out.println("* RACE NUMBER " + (i + 1) + " IS STARTING *");
            System.out.println("---------------------------------------");

            for (int j = 0; j < chmp.getNumberOfLaps(races[i]); j++) {
                System.out.println("Drivers are ready to start lap number " + (j + 1)
                        + " on vanue " + chmp.getVenuesName(races[i]) + ".");
                if (j == 1) {
                    chmp.pneumaticsDecision();
                }

                chmp.driveAverageLapTime(races[i]);
                chmp.applySpecialSkills();
                chmp.checkRainOccurence(races[i]);
                chmp.checkMechanicalProblem();

                System.out.println("Lap number " + (j + 1) + " is finished.");
                System.out.println("Press enter to see results in lap " + (j + 1) + ".");
                readString = sc.nextLine();
                chmp.sortDriversByTime();

                chmp.printLeader(j);

                System.out.println("Press enter to continue.");
                readString = sc.nextLine();
            }
            chmp.updateAccumulatedPoints();
            
            chmp.printWinnersAfterRace(chmp.getVenuesName(races[i]));
            System.out.println("---------------------------------------");
            System.out.println();
        }
        
        System.out.println("---------------------------------------");
        System.out.println("CHAMPIONSHIP IS FINISHED.");
        System.out.println("---------------------------------------");
        System.out.println("\nFinal results:");
        System.out.println("---------------------------------------");
        chmp.sortDriversByPoints();
        System.out.println("***************************************");
        chmp.printChampion();
        System.out.println("***************************************");
        System.out.println();

    }
}
