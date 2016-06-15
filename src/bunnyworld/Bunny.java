package bunnyworld;

import java.util.Random;

/**
 * Bunny class represents bunnies within the colony
 *
 * @author alistaircooper
 */
public class Bunny implements Comparable<Bunny> {

    // Instance variables
    private final int LIFE_EXPECTANCY = 6;

    protected GENDER gender;
    protected COLOR color;  // currently unused!
    protected String name;
    protected int age;
    protected int id;

    // Class variables
    public static enum GENDER {
        MALE, FEMALE
    }
    
    public static enum COLOR {
        BLACK, BLUE, GRAY, WHITE, BROWN
    }

    private static int lastAsignedId = 1;   // last id assigned to a bunny

    // Instance methods 
    // Constructors
    public Bunny() {
        this.id = lastAsignedId;    // assign incremental unique id
        lastAsignedId++;
        age = 0;                    // initialize age at birth
        name = "John";
        gender = AssignGender();    // assign random gender at birth
        
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    /**
     * age method increases bunny's age by 1 year
     *
     */
    public void age() {
        age++;  // increment age 1 year
    }
    
    /**
     * reachedLifeExpectancy method checks if the bunny has reached life expectancy
     * @return boolean
     */
    public boolean reachedLifeExpectancy() {
        return (age >= LIFE_EXPECTANCY);   // true if age greater than 6
    }
    
    /**
     * giveBirth method creates a new bunny
     * @return Bunny
     */
    public static Bunny giveBirth() {
        Bunny b = new Bunny();
        return b;
    }

    // Compare To method (required by the comparable interface)
    @Override
    public int compareTo(Bunny that) {

        Integer obj1 = that.id;
        Integer obj2 = this.id;
        return obj1.compareTo(obj2);

    }

    // toString method 
    @Override
    public String toString() {
        return "[ID: " + id + "] " + gender + " (AGE: " + age + ")";
    }
    
    // Static Methods
    
    /**
     * AssignGender method randomly assigns gender when initializing a bunny
     *
     * @return GENDER
     */
    public static GENDER AssignGender() {
        Random rand = new Random();
        int n = rand.nextInt(2) + 1;   // randomly generates either 1 or 2
        
        if (n == 1) {
            return GENDER.MALE;
        } else {
            return GENDER.FEMALE;
        }
    }

}
