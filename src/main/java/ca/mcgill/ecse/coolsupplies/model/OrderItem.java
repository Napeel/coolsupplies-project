/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;

// line 52 "../../../../../CoolSupplies.ump"
public class OrderItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderItem Attributes
  private int quantity;

  //OrderItem Associations
  private CoolSupplies coolSupplies;
  private Order order;
  private InventoryItem item;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetOrder;
  private boolean canSetItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OrderItem(int aQuantity, CoolSupplies aCoolSupplies, Order aOrder, InventoryItem aItem)
  {
    cachedHashCode = -1;
    canSetOrder = true;
    canSetItem = true;
    quantity = aQuantity;
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create orderItem due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create orderItem due to order. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create orderItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_GetOne */
  public InventoryItem getItem()
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
      existingCoolSupplies.removeOrderItem(this);
    }
    if (!coolSupplies.addOrderItem(this))
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
  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (!canSetOrder) { return false; }
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removeOrderItem(this);
    }
    if (!order.addOrderItem(this))
    {
      order = existingOrder;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setItem(InventoryItem aItem)
  {
    boolean wasSet = false;
    if (!canSetItem) { return false; }
    if (aItem == null)
    {
      return wasSet;
    }

    InventoryItem existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      existingItem.removeOrderItem(this);
    }
    if (!item.addOrderItem(this))
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

    OrderItem compareTo = (OrderItem)obj;
  
    if (getOrder() == null && compareTo.getOrder() != null)
    {
      return false;
    }
    else if (getOrder() != null && !getOrder().equals(compareTo.getOrder()))
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
    if (getOrder() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getOrder().hashCode();
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

    canSetOrder = false;
    canSetItem = false;
    return cachedHashCode;
  }

  public void delete()
  {
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeOrderItem(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removeOrderItem(this);
    }
    InventoryItem placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeOrderItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}