/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 39 "../../../../../../model.ump"
// line 95 "../../../../../../model.ump"
public class TOGrade
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGrade Attributes
  private String level;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGrade(String aLevel)
  {
    level = aLevel;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getLevel()
  {
    return level;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "level" + ":" + getLevel()+ "]";
  }
}