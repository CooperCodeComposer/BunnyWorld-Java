//*************************-=-=-=-=-=-=-=-=-=-=-=-=-**************************// 
//********************<           BUNNY WORLD             >*******************//
//*************************-=-=-=-=  V 1.2  -=-=-==-**************************//
//**************                     AUTHOR                     **************//
//---------------<_>------->>>   ALISTAIR COOPER   <<<-------<_>--------------//
//*****************<_>         CREATED: 06/09/2016          <_>***************//
//**************************-=-=-=-=-=-=-=-=-=-=-=-=-*************************//
//                                                                            //


import java.util.*;
import bunnyworld.*;

/**
 * simulator is the driver class to simulate the colony
 *
 * @author alistaircooper
 */
public class simulator {
 
    // Static variables 
    private static final int BREEDING_AGE = 2;       // age at which bunnies can breed
    private static final int FAMINE_THRESHOLD = 100; // threshold at which famine occurs
    
    private static int initialBunnies;  // initial number of bunnies in colony
    private static int maxSimYears;     // maximum year the simulation will run   
    
    /**
     * main method
     * @param args   parameters from the command line
     */
    public static void main(String[] args) {
        
        // call method to validate command line arguments
        if (!ValidateArguments(args)) {
            return;  // exit program
        }
        
        // Assign values from command line arguments
        initialBunnies = Integer.parseInt(args[0]);
        maxSimYears = Integer.parseInt(args[1]);
        
        // Instance variables
        Set<Bunny> bunnies = new TreeSet<>();   // set representing bunny colony
        int year = 0;                           // start year number
          
        // download names from url 
        DownloadNames.DownloadNamesArray();
        
        // populate bunnies Set with first generation
        bunnies = StartColony(); 
        
        // print initial birth report 
        printAnnualReport(bunnies, year);
        
        // main while loop for each year 
        while ( (year < maxSimYears) && (bunnies.size() > 0)) {

            year++;  // increment year 

            System.out.println("************************************************");
            System.out.println("* BIRTHS + DEATHS");
            System.out.println("* YEAR: " + year);

            age(bunnies);                      // age bunnies 1 year
            dieOfOldAge(bunnies);              // check for bunnies past life expectancy
            procreate(bunnies);                // give birth to new bunnies
            checkForFamine(bunnies);           // check population size for potential famine
            printAnnualReport(bunnies, year);  // print annual report

        }

    }
    
    /**
     * ValidateArguments method validates if user has entered 2 arguments in the 
     *                   command line
     * @param args  array of parameters from the command line
     * @return Boolean
     */
    public static Boolean ValidateArguments(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Need to enter 2 arguments, initial number of bunnies "
                            + "and number of years to run simulation");
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * StartColony method populate bunnies Set with first generation of bunnies
     *
     * @return bunnies          the Set of bunnies in colony
     */
    public static Set<Bunny> StartColony() {
        
        // Instance variables
        Set<Bunny> bunnies = new TreeSet<>();
        
        // initialize colony
        for (int i = 0; i < initialBunnies; i++) {
            
            Bunny.COLOR color = AssignColor();   // Generate random color 
            
            Bunny baby = new Bunny(color);       // instantiate bunny
            
            bunnies.add(baby);
            
            System.out.println("First generation " + baby.getGender() + " bunny called "
                              + baby.getName() + " [ID " + baby.getId() + "] " 
                              + baby.getColor() + " color is born\n" );
        }
        return bunnies;
    }
    
    /**
     * AssignColor method to randomly assign color to first generation of bunnies
     *
     * @return COLOR
     */
    public static Bunny.COLOR AssignColor() {
        Random rand = new Random();
        int n = rand.nextInt(5) + 1;  // randomly generates number between 1 and 5
        Bunny.COLOR color;            // color to return
        
        switch (n) {
            case 1: color = Bunny.COLOR.BLACK;
                    break;
            case 2: color = Bunny.COLOR.BLUE;
                    break;
            case 3: color = Bunny.COLOR.BROWN;
                    break;
            case 4: color = Bunny.COLOR.GRAY;
                    break;
            case 5: color = Bunny.COLOR.WHITE;
                    break;
            default: color = Bunny.COLOR.BLACK;
                    break;
        }
        return color;
    }

    /**
     * age method adds 1 year to eat bunny's age 
     *
     * @param bunnies   Set containing bunnies in colony
     */
    public static void age(Set<Bunny> bunnies) {

        Iterator<Bunny> iter = bunnies.iterator();
        while (iter.hasNext()) {
            Bunny b = iter.next();
            b.age();  // call method to age by 1 year
        }
    }

    /**
     * dieOfOldAge method kills bunny when reaches life expectancy 
     *
     * @param bunnies   Set containing bunnies in colony
     */
    public static void dieOfOldAge(Set<Bunny> bunnies) {

        Iterator<Bunny> iter = bunnies.iterator();
        while (iter.hasNext()) {
            Bunny b = iter.next();

            if (b.reachedLifeExpectancy()) {
                System.out.println("* Sadly " + b.getName() + " " + b.getGender() 
                        + " bunny [ID " + b.getId() + "] " + "has passed away");
                iter.remove();
            }
        }
    }

    /**
     * procreate method checks for breeding couples and adds baby bunnies to set
     *
     * @param bunnies   Set containing bunnies in colony
     */
    public static void procreate(Set<Bunny> bunnies) {

        Set<Bunny> babyBunnies = new TreeSet<>();  // Set to hold baby bunnies
        
        // flag for if there's 1 male bunny 2 or older in set
        boolean spermSource = false; 

        Iterator<Bunny> iter = bunnies.iterator();

        // check set for male bunny 2 years or older
        while (iter.hasNext()) {
            Bunny b = iter.next();

            if ((b.getGender() == Bunny.GENDER.MALE)
                    && (b.getAge() >= BREEDING_AGE)) {
                spermSource = true;

            }
        }

        iter = bunnies.iterator();  // reset iterator 

        // if true iterate through and for every female give birth 
        if (spermSource) {
            while (iter.hasNext()) {
                Bunny b = iter.next();

                if ((b.getGender() == Bunny.GENDER.FEMALE)
                        && (b.getAge() >= BREEDING_AGE)) {
                    
                    // pass in the mother as parameter to giveBirth method
                    Bunny babyBunny = Bunny.giveBirth(b);

                    babyBunnies.add(babyBunny);  // FIX ME pass in mother

                    System.out.println("* A baby bunny called " + babyBunny.getName() 
                            + " is born! \n* [ID " + babyBunny.getId() + "] " 
                            + babyBunny.getColor() + " color" );
                }
            }
        }

        // Merge babyBunnies Set with bunnies Set
        bunnies.addAll(babyBunnies);
    }

    /**
     * checkForFamine method that kills 50% of bunnies if pop exceeds 100
     *
     * @param bunnies   Set containing bunnies in colony
     */
    public static void checkForFamine(Set<Bunny> bunnies) {

        int currentColonySize = bunnies.size();    // set current size of colony

        // if colony is over the famine threshold randomly kill 50%
        if (currentColonySize >= FAMINE_THRESHOLD) {
            
            System.out.println("************************************************");
            System.out.println("* F A M I N E  A L E R T");
            System.out.println("* Bunny colony size greater than "
                                + FAMINE_THRESHOLD + " 50% will now starve");

            /* while the set is larger than 50% of the current (pre famine) size 
             * keep killing bunnies
             */
            while (bunnies.size() > (currentColonySize / 2)) {

                // build array of all bunny ids in the set (each time through kill process)
                ArrayList<Integer> bunnyIdArray = new ArrayList<>();

                Iterator<Bunny> iter = bunnies.iterator();

                // iterate through set to add bunny ids to array
                while (iter.hasNext()) {
                    Bunny b = iter.next();

                    Integer bunnyId = b.getId();

                    bunnyIdArray.add(bunnyId);

                }

                // get total size of id array 
                int sizeOfIdArray = bunnyIdArray.size();

                // generate random bunny index to kill
                Random rand = new Random();
                int bunnyIndexToKill = rand.nextInt(sizeOfIdArray);

                // gets id of bunny at kill index
                int bunnyToKillId = bunnyIdArray.get(bunnyIndexToKill);

                // iterate through bunny set and remove bunny with kill id
                iter = bunnies.iterator();  // reset iterator

                while (iter.hasNext()) {
                    Bunny b = iter.next();

                    if (b.getId() == bunnyToKillId) {
                        iter.remove();
                    }
                }
            }
            System.out.println("* Post-famine colony size = " + bunnies.size());
        }
    }

    /**
     * printAnnualReport method that prints a report for the bunny colony
     *
     * @param bunnies   Set containing bunnies in colony
     * @param year      is the current year
     */
    public static void printAnnualReport(Set<Bunny> bunnies, int year) {

        Iterator<Bunny> iter = bunnies.iterator();

        int numberMale = 0;     // count of male bunnies
        int numberFemale = 0;   // count of female bunnies 
        int bunniesBaby = 0;    // count of babies just born
        int bunniesAge1 = 0;    // count of bunnies age 1
        int bunniesAge2 = 0;    // count of bunnies age 2
        int bunniesAge3 = 0;    // count of bunnies age 3
        int bunniesAge4 = 0;    // count of bunnies age 4
        int bunniesAge5 = 0;    // count of bunnies age 5

        while (iter.hasNext()) {
            Bunny b = iter.next();

            if (b.getGender() == Bunny.GENDER.MALE) {
                numberMale++;
            } else {
                numberFemale++;
            }

            switch (b.getAge()) {
                case 0:
                    bunniesBaby++;
                    break;
                case 1:
                    bunniesAge1++;
                    break;
                case 2:
                    bunniesAge2++;
                    break;
                case 3:
                    bunniesAge3++;
                    break;
                case 4:
                    bunniesAge4++;
                    break;
                case 5:
                    bunniesAge5++;
                    break;
                default:
                    break;
            }
        }

        System.out.println("************************************************");
        System.out.println("*");
        System.out.println("* A N N U A L  R E P O R T");
        System.out.println("* YEAR: " + year);
        System.out.println("*");
        System.out.println("* MALE BUNNIES: " + numberMale);
        System.out.println("* FEMALE BUNNIES: " + numberFemale);
        System.out.println("*");
        System.out.println("* BABY BUNNIES: " + bunniesBaby);
        System.out.println("* BUNNIES AGE 1: " + bunniesAge1);
        System.out.println("* BUNNIES AGE 2: " + bunniesAge2);
        System.out.println("* BUNNIES AGE 3: " + bunniesAge3);
        System.out.println("* BUNNIES AGE 4: " + bunniesAge4);
        System.out.println("* BUNNIES AGE 5: " + bunniesAge5);
        System.out.println("");
    }

    /**
     * print method only use in testing to print bunnies Set
     *
     * @param bunnies   Set containing bunnies in colony
     */
    public static void print(Set<Bunny> bunnies) {
        System.out.println("Colony:");
        for (Bunny bunny : bunnies) {
            System.out.println(bunny);
        }
    }

}
