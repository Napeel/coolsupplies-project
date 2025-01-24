/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 18 "../../../../../../model.ump"
// line 80 "../../../../../../model.ump"
public class TOItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOItem Attributes
  private String name;
  private int price;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOItem(String aName, int aPrice)
  {
    name = aName;
    price = aPrice;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
  }

  public int getPrice()
  {
    return price;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "]";
  }
}