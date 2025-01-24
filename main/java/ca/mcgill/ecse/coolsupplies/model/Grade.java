/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 53 "../../../../../CoolSuppliesPersistence.ump"
// line 81 "../../../../../CoolSupplies.ump"
public class Grade
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Grade> gradesByLevel = new HashMap<String, Grade>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Grade Attributes
  private String level;

  //Grade Associations
  private GradeBundle bundle;
  private List<Student> students;
  private CoolSupplies coolSupplies;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Grade(String aLevel, CoolSupplies aCoolSupplies)
  {
    if (!setLevel(aLevel))
    {
      throw new RuntimeException("Cannot create due to duplicate level. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    students = new ArrayList<Student>();
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create grade due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLevel(String aLevel)
  {
    boolean wasSet = false;
    String anOldLevel = getLevel();
    if (anOldLevel != null && anOldLevel.equals(aLevel)) {
      return true;
    }
    if (hasWithLevel(aLevel)) {
      return wasSet;
    }
    level = aLevel;
    wasSet = true;
    if (anOldLevel != null) {
      gradesByLevel.remove(anOldLevel);
    }
    gradesByLevel.put(aLevel, this);
    return wasSet;
  }

  public String getLevel()
  {
    return level;
  }
  /* Code from template attribute_GetUnique */
  public static Grade getWithLevel(String aLevel)
  {
    return gradesByLevel.get(aLevel);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithLevel(String aLevel)
  {
    return getWithLevel(aLevel) != null;
  }
  /* Code from template association_GetOne */
  public GradeBundle getBundle()
  {
    return bundle;
  }

  public boolean hasBundle()
  {
    boolean has = bundle != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Student getStudent(int index)
  {
    Student aStudent = students.get(index);
    return aStudent;
  }

  public List<Student> getStudents()
  {
    List<Student> newStudents = Collections.unmodifiableList(students);
    return newStudents;
  }

  public int numberOfStudents()
  {
    int number = students.size();
    return number;
  }

  public boolean hasStudents()
  {
    boolean has = students.size() > 0;
    return has;
  }

  public int indexOfStudent(Student aStudent)
  {
    int index = students.indexOf(aStudent);
    return index;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setBundle(GradeBundle aNewBundle)
  {
    boolean wasSet = false;
    if (bundle != null && !bundle.equals(aNewBundle) && equals(bundle.getGrade()))
    {
      //Unable to setBundle, as existing bundle would become an orphan
      return wasSet;
    }

    bundle = aNewBundle;
    Grade anOldGrade = aNewBundle != null ? aNewBundle.getGrade() : null;

    if (!this.equals(anOldGrade))
    {
      if (anOldGrade != null)
      {
        anOldGrade.bundle = null;
      }
      if (bundle != null)
      {
        bundle.setGrade(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStudents()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Student addStudent(String aName, CoolSupplies aCoolSupplies)
  {
    return new Student(aName, aCoolSupplies, this);
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (students.contains(aStudent)) { return false; }
    Grade existingGrade = aStudent.getGrade();
    boolean isNewGrade = existingGrade != null && !this.equals(existingGrade);
    if (isNewGrade)
    {
      aStudent.setGrade(this);
    }
    else
    {
      students.add(aStudent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudent(Student aStudent)
  {
    boolean wasRemoved = false;
    //Unable to remove aStudent, as it must always have a grade
    if (!this.equals(aStudent.getGrade()))
    {
      students.remove(aStudent);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStudentAt(Student aStudent, int index)
  {  
    boolean wasAdded = false;
    if(addStudent(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentAt(Student aStudent, int index)
  {
    boolean wasAdded = false;
    if(students.contains(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentAt(aStudent, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCoolSupplies(CoolSupplies aCoolSupplies)
  {
    boolean wasSet = false;
    if (aCoolSupplies == null)
    {
      return wasSet;
    }

    CoolSupplies existingCoolSupplies = coolSupplies;
    coolSupplies = aCoolSupplies;
    if (existingCoolSupplies != null && !existingCoolSupplies.equals(aCoolSupplies))
    {
      existingCoolSupplies.removeGrade(this);
    }
    coolSupplies.addGrade(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    gradesByLevel.remove(getLevel());
    GradeBundle existingBundle = bundle;
    bundle = null;
    if (existingBundle != null)
    {
      existingBundle.delete();
    }
    for(int i=students.size(); i > 0; i--)
    {
      Student aStudent = students.get(i - 1);
      aStudent.delete();
    }
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeGrade(this);
    }
  }

  // line 55 "../../../../../CoolSuppliesPersistence.ump"
   public static  void reinitializeUniqueLevel(List<Grade> grades){
    gradesByLevel = new HashMap<String, Grade>();
    for (Grade g : grades) {
      gradesByLevel.put(g.getLevel(), g);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "level" + ":" + getLevel()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "bundle = "+(getBundle()!=null?Integer.toHexString(System.identityHashCode(getBundle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null");
  }
}