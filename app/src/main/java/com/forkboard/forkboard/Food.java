package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 * Food holds the notion of what a food item is in a recipe
 */
public class Food {
    //---------------------------------------------------------------
    // States
    //---------------------------------------------------------------

    protected String  _type;
    protected Units   _units;
    private   double  _quantity;
    private   boolean _deficitAllowed;

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------

    /**
     * DEFAULT CONSTRUCTOR
     */
    public Food() {
        _type           = "(undefined food type)";
        _quantity       = 0;
        _units          = Units.item;
        _deficitAllowed = false;
    }

    /**
     * Type CONSTRUCTOR
     * @param type String specifying name of food
     */
    public Food(String type) {
        _type           = type;
        _quantity       = 0;
        _units          = Units.item;
        _deficitAllowed = false;
    }

    /**
     * Partial CONSTRUCTOR
     * @param type String specifying name of food
     * @param amount double specifying how much of the food we have
     */
    public Food(String type, double amount) {
        _type           = type;
        _quantity       = amount;
        _units          = Units.item;
        _deficitAllowed = false;
    }

    /**
     * Full CONSTRUCTOR
     * @param type String specifying name of food
     * @param count double specifying how much of the food we have
     * @param units what units the food is in
     */
    public Food(String type, double count, Units units) {
        _type           = type;
        _quantity       = count;
        _units          = units;
        _deficitAllowed = false;
    }

    //---------------------------------------------------------------
    // Modifiers
    //---------------------------------------------------------------

    /**
     * Allow the Food item to have a negative amount.
     * <p>This is used when doing food computation. Tailored for the
     * Shopping list calculations </p>
     */
    public void allowDeficit()          { _deficitAllowed = true; }


    /**
     * Allow the Food item to have a negative amount.
     * <p>
     *     This is used when doing food computation. Tailored for the
     * Shopping list calculations
     * </p>
     * <p>
     *      e.g.: foodItem.allowDeficit(false) will not allow the food
     * to be a negative value.
     * </p>
     * @param q Boolean of whether we allow for negative values.
     */
    public void allowDeficit(boolean q) {
        _deficitAllowed = q;
        if (_quantity < 0) _quantity = 0;
    }

    /**
     * Set the type for the Food
     * @param type String to set the type
     */
    public void type(String type)    { _type = type;           }

    /**
     * Set the Units for the Food
     * @param units Units to set the units for the Food
     */
    public void units(Units units)   { _units = units;         }

    /**
     * Set the quantity for the Food
     * @param amount double to set the quantity for the Food
     */
    public void quantity(double amount) {
        if (amount >= 0 || _deficitAllowed)
            _quantity = amount;
        else
            _quantity = 0;
    }

    /**
     * Add amount to the Food's current quantity
     * @param more int to add to the Food's quantity
     */
    public void add(int more)    { _quantity += more; }

    /**
     * Add amount to the Food's current quantity.
     * <p>
     *     You may add to food's together! They have to be the same
     *     type though. 2 apples + 3 apples = 5 apples ==> makes since.
     *     2 apples + 4 onions = one horrible monster ==> doesn't make since.
     * </p>
     * @param more int to add to the Food's quantity
     */
    public void add(Food more)   {
        if (isSameFoodType(more)) {
            _quantity += more._quantity;
        }
    }

    /**
     * Subtract amount from the Food's current quantity.
     * @param less int to add to the Food's quantity
     */
    public void subtract(int less)  {
        _quantity -= less;
        if (!_deficitAllowed && _quantity < 0) _quantity = 0;
    }

    /**
     * Subtract amount from the Food's current quantity.
     * <p>
     *     You may subtract food from each other! They have to be the same
     *     type though. 4 apples - 2 apples = 2 apples ==> makes since.
     *     8 apples - 4 onions = a genetic impossibility ==> doesn't make since.
     * </p>
     * @param less int to add to the Food's quantity
     */
    public void subtract(Food less) {
        if (isSameFoodType(less)) {
            _quantity -= less._quantity;
            if (!_deficitAllowed && _quantity < 0) _quantity = 0;
        }
    }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    public String type()     { return _type;     }
    public Units  units()    { return _units;    }
    public double quantity() { return _quantity; }

    public boolean isSameFoodType(Food comp)   { return _type.equals(comp._type); }
    public boolean isSameFoodType(String comp) { return _type.equals(comp);       }
    public boolean ranOut()                    { return (_quantity <= 0);         }
    public boolean canDeficit()                { return _deficitAllowed;          }

    public Food copy() {
        Food tmp = new Food (_type, _quantity, _units);
        tmp.allowDeficit(_deficitAllowed);
        return tmp;
    }

    //---------------------------------------------------------------
    // Object class overrides
    //---------------------------------------------------------------

    public String toString() {
        String printout = "";
        printout += Format.fractionize(quantity());

        if (_units != Units.item) printout += " " + units();

        printout += " " + type();

        if (canDeficit()) printout += " *";

        return printout;
    }


}