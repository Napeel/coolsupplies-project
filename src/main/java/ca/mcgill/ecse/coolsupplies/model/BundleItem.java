/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;

// line 73 "../../../../../CoolSupplies.ump"
public class BundleItem
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PurchaseLevel { Mandatory, Recommended, Optional }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BundleItem Attributes
  private int quantity;
  private PurchaseLevel level;

  //BundleItem Associations
  private CoolSupplies coolSupplies;
  private GradeBundle bundle;
  private Item item;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetBundle;
  private boolean canSetItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BundleItem(int aQuantity, PurchaseLevel aLevel, CoolSupplies aCoolSupplies, GradeBundle aBundle, Item aItem)
  {
    cachedHashCode = -1;
    canSetBundle = true;
    canSetItem = true;
    quantity = aQuantity;
    level = aLevel;
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create bundleItem due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBundle = setBundle(aBundle);
    if (!didAddBundle)
    {
      throw new RuntimeException("Unable to create bundleItem due to bundle. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create bundleItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setLevel(PurchaseLevel aLevel)
  {
    boolean wasSet = false;
    level = aLevel;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public PurchaseLevel getLevel()
  {
    return level;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetOne */
  public GradeBundle getBundle()
  {
    return bundle;
  }
  /* Code from template association_GetOne */
  public Item getItem()
  {
    return item;
  }
  /* Code from template association_SetOneToManyAssociationClass */
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
      existingCoolSupplies.removeBundleItem(this);
    }
    if (!coolSupplies.addBundleItem(this))
    {
      coolSupplies = existingCoolSupplies;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setBundle(GradeBundle aBundle)
  {
    boolean wasSet = false;
    if (!canSetBundle) { return false; }
    if (aBundle == null)
    {
      return wasSet;
    }

    GradeBundle existingBundle = bundle;
    bundle = aBundle;
    if (existingBundle != null && !existingBundle.equals(aBundle))
    {
      existingBundle.removeBundleItem(this);
    }
    if (!bundle.addBundleItem(this))
    {
      bundle = existingBundle;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setItem(Item aItem)
  {
    boolean wasSet = false;
    if (!canSetItem) { return false; }
    if (aItem == null)
    {
      return wasSet;
    }

    Item existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      existingItem.removeBundleItem(this);
    }
    if (!item.addBundleItem(this))
    {
      item = existingItem;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    BundleItem compareTo = (BundleItem)obj;
  
    if (getBundle() == null && compareTo.getBundle() != null)
    {
      return false;
    }
    else if (getBundle() != null && !getBundle().equals(compareTo.getBundle()))
    {
      return false;
    }

    if (getItem() == null && compareTo.getItem() != null)
    {
      return false;
    }
    else if (getItem() != null && !getItem().equals(compareTo.getItem()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getBundle() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getBundle().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getItem() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getItem().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetBundle = false;
    canSetItem = false;
    return cachedHashCode;
  }

  public void delete()
  {
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeBundleItem(this);
    }
    GradeBundle placeholderBundle = bundle;
    this.bundle = null;
    if(placeholderBundle != null)
    {
      placeholderBundle.removeBundleItem(this);
    }
    Item placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeBundleItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "level" + "=" + (getLevel() != null ? !getLevel().equals(this)  ? getLevel().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bundle = "+(getBundle()!=null?Integer.toHexString(System.identityHashCode(getBundle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}