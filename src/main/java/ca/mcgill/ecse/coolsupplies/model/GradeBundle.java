/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import java.util.*;

// line 68 "../../../../../CoolSupplies.ump"
public class GradeBundle extends InventoryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GradeBundle Attributes
  private int discount;

  //GradeBundle Associations
  private CoolSupplies coolSupplies;
  private Grade grade;
  private List<BundleItem> bundleItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GradeBundle(String aName, int aDiscount, CoolSupplies aCoolSupplies, Grade aGrade)
  {
    super(aName);
    discount = aDiscount;
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create bundle due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGrade = setGrade(aGrade);
    if (!didAddGrade)
    {
      throw new RuntimeException("Unable to create bundle due to grade. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bundleItems = new ArrayList<BundleItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDiscount(int aDiscount)
  {
    boolean wasSet = false;
    discount = aDiscount;
    wasSet = true;
    return wasSet;
  }

  public int getDiscount()
  {
    return discount;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetOne */
  public Grade getGrade()
  {
    return grade;
  }
  /* Code from template association_GetMany */
  public BundleItem getBundleItem(int index)
  {
    BundleItem aBundleItem = bundleItems.get(index);
    return aBundleItem;
  }

  public List<BundleItem> getBundleItems()
  {
    List<BundleItem> newBundleItems = Collections.unmodifiableList(bundleItems);
    return newBundleItems;
  }

  public int numberOfBundleItems()
  {
    int number = bundleItems.size();
    return number;
  }

  public boolean hasBundleItems()
  {
    boolean has = bundleItems.size() > 0;
    return has;
  }

  public int indexOfBundleItem(BundleItem aBundleItem)
  {
    int index = bundleItems.indexOf(aBundleItem);
    return index;
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
      existingCoolSupplies.removeBundle(this);
    }
    coolSupplies.addBundle(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setGrade(Grade aNewGrade)
  {
    boolean wasSet = false;
    if (aNewGrade == null)
    {
      //Unable to setGrade to null, as bundle must always be associated to a grade
      return wasSet;
    }
    
    GradeBundle existingBundle = aNewGrade.getBundle();
    if (existingBundle != null && !equals(existingBundle))
    {
      //Unable to setGrade, the current grade already has a bundle, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Grade anOldGrade = grade;
    grade = aNewGrade;
    grade.setBundle(this);

    if (anOldGrade != null)
    {
      anOldGrade.setBundle(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundleItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BundleItem addBundleItem(int aQuantity, BundleItem.PurchaseLevel aLevel, CoolSupplies aCoolSupplies, Item aItem)
  {
    return new BundleItem(aQuantity, aLevel, aCoolSupplies, this, aItem);
  }

  public boolean addBundleItem(BundleItem aBundleItem)
  {
    boolean wasAdded = false;
    if (bundleItems.contains(aBundleItem)) { return false; }
    GradeBundle existingBundle = aBundleItem.getBundle();
    boolean isNewBundle = existingBundle != null && !this.equals(existingBundle);
    if (isNewBundle)
    {
      aBundleItem.setBundle(this);
    }
    else
    {
      bundleItems.add(aBundleItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundleItem(BundleItem aBundleItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundleItem, as it must always have a bundle
    if (!this.equals(aBundleItem.getBundle()))
    {
      bundleItems.remove(aBundleItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleItemAt(BundleItem aBundleItem, int index)
  {  
    boolean wasAdded = false;
    if(addBundleItem(aBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleItems()) { index = numberOfBundleItems() - 1; }
      bundleItems.remove(aBundleItem);
      bundleItems.add(index, aBundleItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleItemAt(BundleItem aBundleItem, int index)
  {
    boolean wasAdded = false;
    if(bundleItems.contains(aBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleItems()) { index = numberOfBundleItems() - 1; }
      bundleItems.remove(aBundleItem);
      bundleItems.add(index, aBundleItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleItemAt(aBundleItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeBundle(this);
    }
    Grade existingGrade = grade;
    grade = null;
    if (existingGrade != null)
    {
      existingGrade.setBundle(null);
    }
    for(int i=bundleItems.size(); i > 0; i--)
    {
      BundleItem aBundleItem = bundleItems.get(i - 1);
      aBundleItem.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "discount" + ":" + getDiscount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "grade = "+(getGrade()!=null?Integer.toHexString(System.identityHashCode(getGrade())):"null");
  }
}