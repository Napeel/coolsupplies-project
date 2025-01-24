/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 12 "../../../../../../model.ump"
// line 75 "../../../../../../model.ump"
public class TOStudent
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOStudent Attributes
  private String name;
  private String gradeLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOStudent(String aName, String aGradeLevel)
  {
    name = aName;
    gradeLevel = aGradeLevel;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
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
            "gradeLevel" + ":" + getGradeLevel()+ "]";
  }
}