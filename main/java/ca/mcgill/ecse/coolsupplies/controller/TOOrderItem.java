/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 58 "../../../../../../model.ump"
// line 105 "../../../../../../model.ump"
public class TOOrderItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOOrderItem Attributes
  private int quantity;
  private String itemName;
  private String gradeBundleName;
  private double price;
  private double discountDeducted;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOOrderItem(int aQuantity, String aItemName, String aGradeBundleName, double aPrice, double aDiscountDeducted)
  {
    quantity = aQuantity;
    itemName = aItemName;
    gradeBundleName = aGradeBundleName;
    price = aPrice;
    discountDeducted = aDiscountDeducted;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getQuantity()
  {
    return quantity;
  }

  public String getItemName()
  {
    return itemName;
  }

  public String getGradeBundleName()
  {
    return gradeBundleName;
  }

  public double getPrice()
  {
    return price;
  }

  public double getDiscountDeducted()
  {
    return discountDeducted;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "gradeBundleName" + ":" + getGradeBundleName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "discountDeducted" + ":" + getDiscountDeducted()+ "]";
  }
}