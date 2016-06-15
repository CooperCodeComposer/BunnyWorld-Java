//*************************-=-=-=-=-=-=-=-=-=-=-=-=-**************************// 
//********************<           BUNNY WORLD             >*******************//
//*************************-=-=-=-=  V 1.0  -=-=-==-**************************//
//**************                     AUTHOR                     **************//
//---------------<_>------->>>   ALISTAIR COOPER   <<<-------<_>--------------//
//*****************<_>         CREATED: 06/09/2016          <_>***************//
//**************************-=-=-=-=-=-=-=-=-=-=-=-=-*************************//
//                                                                            //


import java.util.*;
import bunnyworld.*;

/**
 * Simulator is the driver class to simulate the colony
 *
 * @author alistaircooper
 */
public class Simulator {

    /**
     * main method
     */
    // Constants
    private static final int BREEDING_AGE = 2;
    private static final int MAX_SIM_YEARS = 20;
    private static final int INITIAL_BUNNIES = 5;
    private static final int FAMINE_THRESHOLD = 100;

    public static void main(String[] args) {

        // Instance variables
        Set<Bunny> bunnies = new TreeSet<>();

        // If there's 1 male and 1 female 2 years or older all females 2 years older give birth 
        
        // initialize colony
        for (int i = 0; i < INITIAL_BUNNIES; i++) {
            bunnies.add(new Bunny());
        }

        // increment year
        int year = 0;

        // print initial birth report 
        printAnnualReport(bunnies, year);

        while ( (year < MAX_SIM_YEARS) && (bunnies.size() > 0)) {

            year++;  // increment year 

            System.out.println("************************************************");
            System.out.println("* BIRTHS + DEATHS");
            System.out.println("* YEAR: " + year);

            age(bunnies);
            dieOfOldAge(bunnies);
            procreate(bunnies);
            checkForFamine(bunnies);
            printAnnualReport(bunnies, year);

        }

    }

    /**
     * age method checks if the bunny is 6 years old
     *
     * @param bunnies
     */
    public static void age(Set<Bunny> bunnies) {

        Iterator<Bunny> iter = bunnies.iterator();
        while (iter.hasNext()) {
            Bunny b = iter.next();
            b.age();  // call method to age by 1 year
        }
    }

    /**
     * dieOfOldAge method kills at age 6
     *
     * @param bunnies
     */
    public static void dieOfOldAge(Set<Bunny> bunnies) {

        Iterator<Bunny> iter = bunnies.iterator();
        while (iter.hasNext()) {
            Bunny b = iter.next();

            if (b.reachedLifeExpectancy()) {
                System.out.println("* Sadly " + b.getGender() + " bunny [ID " 
                        + b.getId() + "] " + "has passed away");
                iter.remove();
            }
        }
    }

    /**
     * procreate method checks for breeding couples and adds baby bunnies to set
     *
     * @param bunnies
     */
    public static void procreate(Set<Bunny> bunnies) {

        Set<Bunny> babyBunnies = new TreeSet<>();  // Set to hold baby bunnies

        boolean spermSource = false; // flag for male bunny 2 or older in set

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

                    Bunny babyBunny = Bunny.giveBirth();

                    babyBunnies.add(babyBunny);  // FIX ME pass in mother

                    System.out.println("* A baby bunny is born! [ID "
                            + babyBunny.getId() + "]");
                }
            }
        }

        // Merge babyBunnies Set with bunnies Set
        bunnies.addAll(babyBunnies);
    }

    /**
     * checkForFamine method that kills 50% of bunnies if pop exceeds 100
     *
     * @param bunnies
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

            //    System.out.println("TEST: Current id array " + bunnyIdArray);

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
     * @param bunnies
     * @param year is the current year
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
     * @param bunnies
     */
    public static void print(Set<Bunny> bunnies) {
        System.out.println("Colony:");
        for (Bunny bunny : bunnies) {
            System.out.println(bunny);
        }

    }

}
