/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;
import java.sql.Date;
import java.util.*;

// line 44 "../../../../../../model.ump"
// line 100 "../../../../../../model.ump"
public class TOOrder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOOrder Attributes
  private String parentEmail;
  private String studentName;
  private String status;
  private int number;
  private Date date;
  private String level;
  private String authorizationCode;
  private String penaltyAuthorizationCode;
  private double totalPrice;

  //TOOrder Associations
  private List<TOOrderItem> TOOrderItems;

  //Helper Variables
  private boolean canSetTOOrderItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOOrder(String aParentEmail, String aStudentName, String aStatus, int aNumber, Date aDate, String aLevel, String aAuthorizationCode, String aPenaltyAuthorizationCode, double aTotalPrice, TOOrderItem... allTOOrderItems)
  {
    parentEmail = aParentEmail;
    studentName = aStudentName;
    status = aStatus;
    number = aNumber;
    date = aDate;
    level = aLevel;
    authorizationCode = aAuthorizationCode;
    penaltyAuthorizationCode = aPenaltyAuthorizationCode;
    totalPrice = aTotalPrice;
    canSetTOOrderItems = true;
    TOOrderItems = new ArrayList<TOOrderItem>();
    boolean didAddTOOrderItems = setTOOrderItems(allTOOrderItems);
    if (!didAddTOOrderItems)
    {
      throw new RuntimeException("Unable to create TOOrder, must not have duplicate TOOrderItems. See https://manual.umple.org?RE001ViolationofImmutability.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getParentEmail()
  {
    return parentEmail;
  }

  public String getStudentName()
  {
    return studentName;
  }

  public String getStatus()
  {
    return status;
  }

  public int getNumber()
  {
    return number;
  }

  public Date getDate()
  {
    return date;
  }

  public String getLevel()
  {
    return level;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public String getPenaltyAuthorizationCode()
  {
    return penaltyAuthorizationCode;
  }

  public double getTotalPrice()
  {
    return totalPrice;
  }
  /* Code from template association_GetMany */
  public TOOrderItem getTOOrderItem(int index)
  {
    TOOrderItem aTOOrderItem = TOOrderItems.get(index);
    return aTOOrderItem;
  }

  public List<TOOrderItem> getTOOrderItems()
  {
    List<TOOrderItem> newTOOrderItems = Collections.unmodifiableList(TOOrderItems);
    return newTOOrderItems;
  }

  public int numberOfTOOrderItems()
  {
    int number = TOOrderItems.size();
    return number;
  }

  public boolean hasTOOrderItems()
  {
    boolean has = TOOrderItems.size() > 0;
    return has;
  }

  public int indexOfTOOrderItem(TOOrderItem aTOOrderItem)
  {
    int index = TOOrderItems.indexOf(aTOOrderItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOOrderItems()
  {
    return 0;
  }
  /* Code from template association_SetUnidirectionalMany */
  private boolean setTOOrderItems(TOOrderItem... newTOOrderItems)
  {
    boolean wasSet = false;
    if (!canSetTOOrderItems) { return false; }
    canSetTOOrderItems = false;
    ArrayList<TOOrderItem> verifiedTOOrderItems = new ArrayList<TOOrderItem>();
    for (TOOrderItem aTOOrderItem : newTOOrderItems)
    {
      if (verifiedTOOrderItems.contains(aTOOrderItem))
      {
        continue;
      }
      verifiedTOOrderItems.add(aTOOrderItem);
    }

    if (verifiedTOOrderItems.size() != newTOOrderItems.length)
    {
      return wasSet;
    }

    TOOrderItems.clear();
    TOOrderItems.addAll(verifiedTOOrderItems);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "parentEmail" + ":" + getParentEmail()+ "," +
            "studentName" + ":" + getStudentName()+ "," +
            "status" + ":" + getStatus()+ "," +
            "number" + ":" + getNumber()+ "," +
            "level" + ":" + getLevel()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "penaltyAuthorizationCode" + ":" + getPenaltyAuthorizationCode()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}