/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 24 "../../../../../../model.ump"
// line 85 "../../../../../../model.ump"
public class TOGradeBundle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGradeBundle Attributes
  private String name;
  private int discount;
  private String gradeLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGradeBundle(String aName, int aDiscount, String aGradeLevel)
  {
    name = aName;
    discount = aDiscount;
    gradeLevel = aGradeLevel;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
  }

  public int getDiscount()
  {
    return discount;
  }

  public String getGradeLevel()
  {
    return gradeLevel;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "discount" + ":" + getDiscount()+ "," +
            "gradeLevel" + ":" + getGradeLevel()+ "]";
  }
}