package bunnyworld;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * DownloadNames class handles downloading names from text file and includes
 * method to return a name
 *
 * @author alistaircooper
 */
public class DownloadNames {

    // Class variables 
    private static ArrayList<String> namesArray = new ArrayList<>();

    /**
     * DownloadNamesArray method downloads names from url and creates an
     * ArrayList
     *
     * @return namesArray   array of bunny names
     */
    public static ArrayList<String> DownloadNamesArray() {
        String url = "https://s3-us-west-2.amazonaws.com/uclaiosclass/bunny-names.txt";
        Scanner in = null;

        try {
            URL pageLocation = new URL(url);
            in = new Scanner(pageLocation.openStream());
//            in.useDelimiter("(<a href=\"|\">)");
            while (in.hasNext()) {
                String name = in.nextLine();

                namesArray.add(name);
            }

        } catch (IOException e) {
            System.out.println("error: " + e.toString());
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return namesArray;
    }

    /**
     * NameBunny randomly selects (and removes) name from bunny name array
     *
     * @return String
     */
    public static String NameBunny() {

        String name = ""; // name for new bunny

        try {
            // get total size of name array  
            int sizeOfNamesArray = namesArray.size();

            // generate random index position
            Random rand = new Random();
            int bunnyNameIndex = rand.nextInt(sizeOfNamesArray);

            // get name
            name = namesArray.get(bunnyNameIndex);

            // remove name from array
            namesArray.remove(bunnyNameIndex);

        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.toString());
            System.exit(1);
        } 

        return name;
    }

}
