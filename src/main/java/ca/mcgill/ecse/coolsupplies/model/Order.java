/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import java.util.*;
import java.sql.Date;

// line 1 "../../../../../CoolSuppliesStates.ump"
// line 32 "../../../../../CoolSuppliesPersistence.ump"
// line 41 "../../../../../CoolSupplies.ump"
public class Order
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Order> ordersByNumber = new HashMap<Integer, Order>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int number;
  private Date date;
  private PurchaseLevel level;
  private String authorizationCode;
  private String penaltyAuthorizationCode;

  //Order State Machines
  public enum Status { Started, Penalized, Paid, Cancelled, Prepared, PickedUp }
  private Status status;

  //Order Associations
  private Parent parent;
  private Student student;
  private CoolSupplies coolSupplies;
  private List<OrderItem> orderItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aNumber, Date aDate, PurchaseLevel aLevel, Parent aParent, Student aStudent, CoolSupplies aCoolSupplies)
  {
    date = aDate;
    level = aLevel;
    authorizationCode = null;
    penaltyAuthorizationCode = null;
    if (!setNumber(aNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate number. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddParent = setParent(aParent);
    if (!didAddParent)
    {
      throw new RuntimeException("Unable to create order due to parent. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create order due to student. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create order due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderItems = new ArrayList<OrderItem>();
    setStatus(Status.Started);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    Integer anOldNumber = getNumber();
    if (anOldNumber != null && anOldNumber.equals(aNumber)) {
      return true;
    }
    if (hasWithNumber(aNumber)) {
      return wasSet;
    }
    number = aNumber;
    wasSet = true;
    if (anOldNumber != null) {
      ordersByNumber.remove(anOldNumber);
    }
    ordersByNumber.put(aNumber, this);
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
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

  public boolean setAuthorizationCode(String aAuthorizationCode)
  {
    boolean wasSet = false;
    authorizationCode = aAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPenaltyAuthorizationCode(String aPenaltyAuthorizationCode)
  {
    boolean wasSet = false;
    penaltyAuthorizationCode = aPenaltyAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithNumber(int aNumber)
  {
    return ordersByNumber.get(aNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithNumber(int aNumber)
  {
    return getWithNumber(aNumber) != null;
  }

  public Date getDate()
  {
    return date;
  }

  public PurchaseLevel getLevel()
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

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public boolean updateOrder(String purchaseLevelName,String studentName)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 5 "../../../../../CoolSuppliesStates.ump"
        doUpdateOrder(purchaseLevelName, studentName);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 88 "../../../../../CoolSuppliesStates.ump"
        doUpdateOrderError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 124 "../../../../../CoolSuppliesStates.ump"
        doUpdateOrderError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 167 "../../../../../CoolSuppliesStates.ump"
        doUpdateOrderError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 207 "../../../../../CoolSuppliesStates.ump"
        doUpdateOrderError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addItem(String aItem,int aQuantity)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 9 "../../../../../CoolSuppliesStates.ump"
        doAddItem(aItem, aQuantity);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 76 "../../../../../CoolSuppliesStates.ump"
        doAddItemError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 108 "../../../../../CoolSuppliesStates.ump"
        doAddItemError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 147 "../../../../../CoolSuppliesStates.ump"
        doAddItemError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 183 "../../../../../CoolSuppliesStates.ump"
        doAddItemError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateItem(String itemName,int newQuantity)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 13 "../../../../../CoolSuppliesStates.ump"
        doUpdateItem(itemName, newQuantity);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 72 "../../../../../CoolSuppliesStates.ump"
        doUpdateItemError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 112 "../../../../../CoolSuppliesStates.ump"
        doUpdateItemError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 151 "../../../../../CoolSuppliesStates.ump"
        doUpdateItemError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 187 "../../../../../CoolSuppliesStates.ump"
        doUpdateItemError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean removeItem(String itemName)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 17 "../../../../../CoolSuppliesStates.ump"
        doRemoveItem(itemName);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 80 "../../../../../CoolSuppliesStates.ump"
        doRemoveItemError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 116 "../../../../../CoolSuppliesStates.ump"
        doRemoveItemError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 155 "../../../../../CoolSuppliesStates.ump"
        doRemoveItemError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 191 "../../../../../CoolSuppliesStates.ump"
        doRemoveItemError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean paymentForOrder(String authorizationCode)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (validateAuthorizationCode(authorizationCode)&&validateOrderHasItems())
        {
        // line 21 "../../../../../CoolSuppliesStates.ump"
          doPaymentForOrder(authorizationCode);
          setStatus(Status.Paid);
          wasEventProcessed = true;
          break;
        }
        if (!(validateAuthorizationCode(authorizationCode)))
        {
        // line 25 "../../../../../CoolSuppliesStates.ump"
          doPaymentAuthError();
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        if (!(validateOrderHasItems()))
        {
        // line 29 "../../../../../CoolSuppliesStates.ump"
          doNoItemPaidError();
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      case Penalized:
        // line 96 "../../../../../CoolSuppliesStates.ump"
        doPaymentForOrderError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 128 "../../../../../CoolSuppliesStates.ump"
        doPaymentForOrderError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 171 "../../../../../CoolSuppliesStates.ump"
        doPaymentForOrderError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 211 "../../../../../CoolSuppliesStates.ump"
        doPaymentForOrderError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 33 "../../../../../CoolSuppliesStates.ump"
        doCancel();
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 92 "../../../../../CoolSuppliesStates.ump"
        doCancelError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 104 "../../../../../CoolSuppliesStates.ump"
        doCancel();
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 159 "../../../../../CoolSuppliesStates.ump"
        doCancelError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 195 "../../../../../CoolSuppliesStates.ump"
        doCancelError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean schoolYearStarts()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 38 "../../../../../CoolSuppliesStates.ump"
        doSchoolYearStart();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 68 "../../../../../CoolSuppliesStates.ump"
        doSchoolYearStartError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 136 "../../../../../CoolSuppliesStates.ump"
        doSchoolYearStart();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 163 "../../../../../CoolSuppliesStates.ump"
        doSchoolYearStartError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 203 "../../../../../CoolSuppliesStates.ump"
        doSchoolYearStartError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pickedUpByStudent()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 42 "../../../../../CoolSuppliesStates.ump"
        doPickedUpError();
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 84 "../../../../../CoolSuppliesStates.ump"
        doPickedUpError();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 120 "../../../../../CoolSuppliesStates.ump"
        doPickedUpError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 199 "../../../../../CoolSuppliesStates.ump"
        doPickedUpPickedUp();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean paymentForPenalty(String penaltyAuthorizationCode,String orderAuthorizationCode)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 46 "../../../../../CoolSuppliesStates.ump"
        doPaymentPenaltyError();
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Penalized:
        if (validatePenaltyPayment(penaltyAuthorizationCode)&&validateAuthorizationCode(orderAuthorizationCode))
        {
        // line 54 "../../../../../CoolSuppliesStates.ump"
          doPaymentPenalty(penaltyAuthorizationCode, orderAuthorizationCode);
          setStatus(Status.Prepared);
          wasEventProcessed = true;
          break;
        }
        if (!(validatePenaltyPayment(penaltyAuthorizationCode)))
        {
        // line 59 "../../../../../CoolSuppliesStates.ump"
          doPaymentPenaltyAuthError();
          setStatus(Status.Penalized);
          wasEventProcessed = true;
          break;
        }
        if (!(validateAuthorizationCode(orderAuthorizationCode)))
        {
        // line 64 "../../../../../CoolSuppliesStates.ump"
          doPaymentAuthError();
          setStatus(Status.Penalized);
          wasEventProcessed = true;
          break;
        }
        break;
      case Paid:
        // line 132 "../../../../../CoolSuppliesStates.ump"
        doPaymentPenaltyError();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 175 "../../../../../CoolSuppliesStates.ump"
        doPaymentPenaltyError();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 215 "../../../../../CoolSuppliesStates.ump"
        doPaymentPenaltyError();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;

    // entry actions and do activities
    switch(status)
    {
      case Cancelled:
        delete();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Parent getParent()
  {
    return parent;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetMany */
  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setParent(Parent aParent)
  {
    boolean wasSet = false;
    if (aParent == null)
    {
      return wasSet;
    }

    Parent existingParent = parent;
    parent = aParent;
    if (existingParent != null && !existingParent.equals(aParent))
    {
      existingParent.removeOrder(this);
    }
    parent.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeOrder(this);
    }
    student.addOrder(this);
    wasSet = true;
    return wasSet;
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
      existingCoolSupplies.removeOrder(this);
    }
    coolSupplies.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, CoolSupplies aCoolSupplies, InventoryItem aItem)
  {
    return new OrderItem(aQuantity, aCoolSupplies, this, aItem);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    Order existingOrder = aOrderItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aOrderItem.setOrder(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a order
    if (!this.equals(aOrderItem.getOrder()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ordersByNumber.remove(getNumber());
    Parent placeholderParent = parent;
    this.parent = null;
    if(placeholderParent != null)
    {
      placeholderParent.removeOrder(this);
    }
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeOrder(this);
    }
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeOrder(this);
    }
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
  }


  /**
   * 
   * Validates if the provided authorization code is valid
   * 
   * @param code The authorization code to validate
   * @return True if code is valid, false otherwise
   * @author Jeffrey Lim
   * 
   */
  // line 230 "../../../../../CoolSuppliesStates.ump"
   private boolean validateAuthorizationCode(String code){
    return code != null && code.trim().length() == 4;
  }


  /**
   * 
   * Validates if the provided penalty payment code is valid
   * 
   * @param penaltyCode The penalty authorization code to validate
   * @return True if penalty code is valid, false otherwise
   * @author Jeffrey Lim
   * 
   */
  // line 241 "../../../../../CoolSuppliesStates.ump"
   private boolean validatePenaltyPayment(String penaltyCode){
    return validateAuthorizationCode(penaltyCode);
  }


  /**
   * 
   * Validates if the provided order has items
   * 
   * 
   * @return True if order has items
   * @author Matthew
   * 
   */
  // line 253 "../../../../../CoolSuppliesStates.ump"
   private boolean validateOrderHasItems(){
    return this.hasOrderItems();
  }


  /**
   * 
   * Returns a code if the order has no items
   * 
   * 
   * @return Returns error message
   * @author Bryan Vu
   * 
   */
  // line 265 "../../../../../CoolSuppliesStates.ump"
   private void doNoItemPaidError(){
    throw new RuntimeException("Order " + this.getNumber() + " has no items");
  }


  /**
   * 
   * Updates the order with new purchase level and student
   * 
   * @param purchaseLevelName The name of the purchase level to update to
   * @param studentName The name of the student to update to
   * @throws RuntimeException if purchase level or student is invalid
   * @author Jeffrey Lim
   * 
   */
  // line 278 "../../../../../CoolSuppliesStates.ump"
   private void doUpdateOrder(String purchaseLevelName, String studentName){
    PurchaseLevel currentPurchaseLevel = null;
    CoolSupplies coolSupplies = this.getCoolSupplies();

    for (PurchaseLevel level : PurchaseLevel.values()) {
      if (level.name().equalsIgnoreCase(purchaseLevelName)) {
        currentPurchaseLevel = level;
      }
    }

    if (currentPurchaseLevel == null) {
      throw new RuntimeException("Purchase level " + purchaseLevelName + " does not exist.");
    }

    Parent parent = this.getParent();
    if (parent == null) {
      throw new RuntimeException("Parent does not exist.");
    }
    if (Student.getWithName(studentName) == null) {
      throw new RuntimeException("Student " + studentName +" does not exist.");
    }
    if (Student.getWithName(studentName).getParent() != parent) {
      throw new RuntimeException("Student " + studentName + " is not a child of the parent " + parent.getEmail() + "." );
    }
    
    this.setLevel(currentPurchaseLevel);
    this.setStudent(Student.getWithName(studentName));
  }


  /**
   * 
   * Throws error when order can not be updated due to the state
   * 
   * @throws RuntimeException with message indicating items cannot be added
   * @author Jeffrey Lim
   * 
   */
  // line 313 "../../../../../CoolSuppliesStates.ump"
   private void doUpdateOrderError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot update a picked up order");
    }
    throw new RuntimeException("Cannot update a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Adds an item to the order
   * 
   * @param itemName The name of the item to add
   * @param quantity The quantity of the item to add
   * @throws RuntimeException if item doesn't exist or quantity is invalid
   * @author Jeffrey Lim
   * 
   */
  // line 329 "../../../../../CoolSuppliesStates.ump"
   private void doAddItem(String itemName, int quantity){
    // Logic for adding an item

    CoolSupplies coolSupplies = this.getCoolSupplies();
    InventoryItem itemToBeAdded = InventoryItem.getWithName(itemName);

    if (itemToBeAdded == null){
      throw new RuntimeException("Item " + itemName + " does not exist.");
    }
    
    for (OrderItem o: this.getOrderItems()){
      if (o.getItem().getName().equals(itemName)){
        throw new RuntimeException("Item " + itemName + " already exists in the order " + this.getNumber() + ".");
      }
    }


    if (quantity <= 0){
      throw new RuntimeException("Quantity must be greater than 0.");
    }

    new OrderItem(quantity, coolSupplies, this, itemToBeAdded);
  }


  /**
   * 
   * Throws error when item cannot be added to order
   * 
   * @throws RuntimeException with message indicating items cannot be added
   * @author Jeffrey Lim
   * 
   */
  // line 359 "../../../../../CoolSuppliesStates.ump"
   private void doAddItemError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot add items to a picked up order");
    }
    throw new RuntimeException("Cannot add items to a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Updates the quantity of an existing item in the order
   * 
   * @param itemName The name of the item to update
   * @param newQuantity The new quantity for the item
   * @throws RuntimeException if item doesn't exist or quantity is invalid
   * @author Jeffrey Lim
   * 
   */
  // line 374 "../../../../../CoolSuppliesStates.ump"
   private void doUpdateItem(String itemName, int newQuantity){
    // Logic for updating an item
    
    // make sure quantity is > 0
    if (newQuantity <= 0) {
      throw new RuntimeException("Quantity must be greater than 0.");
    }

    // get items in the order
    List<OrderItem> items = this.getOrderItems();

    

    InventoryItem inventoryItemSeeked = InventoryItem.getWithName(itemName);
    if(inventoryItemSeeked == null){
      throw new RuntimeException("Item " + itemName + " does not exist.");
    }
    boolean itemExists = false;
    // check that the item exists in the order
    OrderItem item = null;

  
    for (OrderItem i : items) {
      if (i.getItem().getName().equals(itemName)) {
        item = i;
        itemExists = true;
        break;
      }
    }
    if (!itemExists){
      throw new RuntimeException("Item " + itemName + " does not exist in the order " +this.getNumber() +".");
    }

    // update item
    item.setQuantity(newQuantity);
  }


  /**
   * 
   * Throws error when item cannot be updated in order
   * 
   * @throws RuntimeException with message indicating items cannot be updated
   * @author Jeffrey Lim
   * 
   */
  // line 417 "../../../../../CoolSuppliesStates.ump"
   private void doUpdateItemError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot update items in a picked up order");
    }
    throw new RuntimeException("Cannot update items in a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Removes an item from the order
   * 
   * @param itemName The name of the item to remove
   * @throws RuntimeException if item doesn't exist in order
   * @author Jeffrey Lim
   * 
   */
  // line 431 "../../../../../CoolSuppliesStates.ump"
   private void doRemoveItem(String itemName){
    // Logic for removing an item

    //get all items in general
    InventoryItem itemToBeRemoved = InventoryItem.getWithName(itemName);
    
    if(itemToBeRemoved == null){
      throw new RuntimeException("Item " + itemName + " does not exist.");
    }

    // get items in the order
    List<OrderItem> items = this.getOrderItems();

    // check that the item exists in the order
    OrderItem item = null;
    for (OrderItem i : items) {
      if (i.getItem().getName().equals(itemName)) {
        item = i;
        break;
      }
    }

    if (item == null) {
      throw new RuntimeException("Item " + itemName + " does not exist in the order " + this.number + ".");
    }

    // remove item
    item.delete();
  }


  /**
   * 
   * Throws error when item cannot be removed from order
   * 
   * @throws RuntimeException with message indicating items cannot be removed
   * @author Jeffrey Lim
   * 
   */
  // line 467 "../../../../../CoolSuppliesStates.ump"
   private void doRemoveItemError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot delete items from a picked up order");
    }
    throw new RuntimeException("Cannot delete items from a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Cancels the order by deleting it
   * 
   * @author Jeffrey Lim
   * 
   */
  // line 479 "../../../../../CoolSuppliesStates.ump"
   private void doCancel(){
    // Logic for cancelling when in Started state
    this.delete();
  }


  /**
   * 
   * Throws error when order cannot be cancelled
   * 
   * @throws RuntimeException with message indicating order cannot be cancelled
   * @author Jeffrey Lim
   * 
   */
  // line 490 "../../../../../CoolSuppliesStates.ump"
   private void doCancelError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot cancel a picked up order");
    }
    throw new RuntimeException("Cannot cancel a " + this.getStatus().name().toLowerCase() + " order");
  }

  // line 497 "../../../../../CoolSuppliesStates.ump"
   private void doSchoolYearStart(){
    /****
    Date currentDate = new Date(System.currentTimeMillis());
    this.setDate(currentDate);
    ***/
  }


  /**
   * 
   * Throws error when attempting to pick up an already picked up order
   * 
   * @throws RuntimeException indicating order is already picked up
   * @author Jeffrey Lim
   * 
   */
  // line 510 "../../../../../CoolSuppliesStates.ump"
   private void doPickedUpPickedUp(){
    throw new RuntimeException("The order is already picked up");
  }


  /**
   * 
   * Throws error when order cannot be picked up
   * 
   * @throws RuntimeException with message indicating order cannot be picked up
   * @author Jeffrey Lim
   * 
   */
  // line 520 "../../../../../CoolSuppliesStates.ump"
   private void doPickedUpError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot pickup a picked up order");
    }
    throw new RuntimeException("Cannot pickup a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Processes payment for an order
   * 
   * @param authorizationCode The payment authorization code
   * @throws RuntimeException if authorization code is invalid
   * @author Jeffrey Lim
   * 
   */
  // line 535 "../../../../../CoolSuppliesStates.ump"
   private void doPaymentForOrder(String authorizationCode){
    this.setAuthorizationCode(authorizationCode);
    this.setStatus(Status.Paid);
  }


  /**
   * 
   * Applies penalty effect to the order
   * 
   * @author Jeffrey Lim
   * 
   */
  // line 545 "../../../../../CoolSuppliesStates.ump"
   private void doPenaltyEffect(){
    // Logic for applying a penalty effect
    
    // Dont need to set a penalty effect orders are do not store the auth code for verification
    // They just store it to express that they have been paid for using that code
    //This is a place holder
  }


  /**
   * 
   * Processes penalty payment for an order
   * 
   * @param penaltyAuthorizationCode The penalty payment authorization code
   * @param orderAuthorizationCode The order payment authorization code
   * @author Jeffrey Lim
   * 
   */
  // line 560 "../../../../../CoolSuppliesStates.ump"
   private void doPaymentPenalty(String penaltyAuthorizationCode, String orderAuthorizationCode){
    this.setPenaltyAuthorizationCode(penaltyAuthorizationCode);
    this.setAuthorizationCode(orderAuthorizationCode);
    // this.setPaymentDate(new Date());
  }


  /**
   * 
   * Throws error when penalty authorization code is invalid
   * 
   * @throws RuntimeException indicating invalid penalty authorization code
   * @author Jeffrey Lim
   * 
   */
  // line 572 "../../../../../CoolSuppliesStates.ump"
   private void doPaymentPenaltyAuthError(){
    throw new RuntimeException("Penalty authorization code is invalid");
  }


  /**
   * 
   * Returns an error for bad payment penalty cases 
   * 
   * 
   * @return Returns a penalty error code
   * @author Jeffrey Lim
   * 
   */
  // line 583 "../../../../../CoolSuppliesStates.ump"
   private void doPaymentPenaltyError(){
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot pay penalty for a picked up order");
    }
    throw new RuntimeException("Cannot pay penalty for a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Throws error when authorization code is invalid
   * 
   * @throws RuntimeException indicating invalid authorization code
   * @author Jeffrey Lim
   * 
   */
  // line 599 "../../../../../CoolSuppliesStates.ump"
   private void doPaymentAuthError(){
    throw new RuntimeException("Authorization code is invalid");
  }


  /**
   * 
   * Returns error code for bad error states
   * 
   * @return Error message describing how payment is not possilbe in this state
   * @author Jeffrey Lim
   * 
   */
  // line 609 "../../../../../CoolSuppliesStates.ump"
   private void doPaymentForOrderError(){
    if(this.getStatus().name().toLowerCase().equals("paid")){
      throw new RuntimeException("The order is already paid");
    }
    if(this.getStatus().name().toLowerCase().equals("pickedup")){
      throw new RuntimeException("Cannot pay for a picked up order");
    }
    throw new RuntimeException("Cannot pay for a " + this.getStatus().name().toLowerCase() + " order");
  }


  /**
   * 
   * Throws error when school year has already started
   * 
   * @throws RuntimeException indicating school year has already started
   * @author Jeffrey Lim
   * 
   */
  // line 625 "../../../../../CoolSuppliesStates.ump"
  public void doSchoolYearStartError(){
    throw new RuntimeException("The school year has already been started");
  }

  // line 34 "../../../../../CoolSuppliesPersistence.ump"
   public static  void reinitializeUniqueNumber(List<Order> orders){
    ordersByNumber = new HashMap<Integer, Order>();
    for (Order o : orders) {
      ordersByNumber.put(o.getNumber(), o);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "penaltyAuthorizationCode" + ":" + getPenaltyAuthorizationCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "level" + "=" + (getLevel() != null ? !getLevel().equals(this)  ? getLevel().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "parent = "+(getParent()!=null?Integer.toHexString(System.identityHashCode(getParent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null");
  }
}