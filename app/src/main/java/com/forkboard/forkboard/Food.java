package com.forkboard.forkboard;

/**
 * Created by Kyle on 6/8/2016.
 */
public class Food {
    //---------------------------------------------------------------
    // States
    //---------------------------------------------------------------

    protected String  _type;
    protected Units   _units;
    private   int     _quantity;
    private   boolean _deficitAllowed;

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------

    public Food() {
        _type           = "(undifined food type)";
        _quantity       = 0;
        _units          = Units.item;
        _deficitAllowed = false;
    }

    public Food(String type) {
        _type           = type;
        _quantity       = 0;
        _units          = Units.item;
        _deficitAllowed = false;
    }

    public Food(String type, int amount) {
        _type           = type;
        _quantity       = amount;
        _units          = Units.item;
        _deficitAllowed = false;
    }

    public Food(String type, int count, Units units) {
        _type           = type;
        _quantity       = count;
        _units          = units;
        _deficitAllowed = false;
    }

    //---------------------------------------------------------------
    // Modifiers
    //---------------------------------------------------------------

    public void allowDeficit()          { _deficitAllowed = true; }
    public void allowDeficit(Boolean q) {
        _deficitAllowed = q;
        if (_quantity < 0) _quantity = 0;
    }

    public void type(String type)    { _type = type;           }
    public void units(Units units)   { _units = units;         }
    public void quantity(int amount) {
        if (amount >= 0)
            _quantity = amount;
        else
            _quantity = 0;
    }

    public void add(int more)    { _quantity += more; }
    public void add(Food more)   {
        if (isSameFoodType(more)) {
            _quantity += more._quantity;
        }
    }

    public void subtract(int less)  {
        _quantity -= less;
        if (!_deficitAllowed && _quantity < 0) _quantity = 0;
    }

    public void subtract(Food less) {
        if (isSameFoodType(less)) {
            _quantity -= less._quantity;
            if (!_deficitAllowed && _quantity < 0) _quantity = 0;
        }
    }

    //---------------------------------------------------------------
    // Accessors
    //---------------------------------------------------------------

    public String type()  { return _type;     }
    public Units  units() { return _units;    }
    public int quantity() { return _quantity; }

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
        printout += quantity();

        if (_units != Units.item) printout += " " + units();

        printout += " " + type();

        if (quantity() != 1 && _units == Units.item)
            printout += "s";

        if (canDeficit()) printout += " *";

        return printout;
    }
}
