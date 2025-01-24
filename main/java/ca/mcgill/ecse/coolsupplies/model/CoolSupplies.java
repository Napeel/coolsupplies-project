/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.model;
import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import java.util.*;
import java.sql.Date;

// line 1 "../../../../../CoolSuppliesPersistence.ump"
// line 5 "../../../../../CoolSupplies.ump"
public class CoolSupplies
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CoolSupplies Associations
  private SchoolAdmin admin;
  private List<Student> students;
  private List<Parent> parents;
  private List<Item> items;
  private List<GradeBundle> bundles;
  private List<BundleItem> bundleItems;
  private List<Order> orders;
  private List<OrderItem> orderItems;
  private List<Grade> grades;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CoolSupplies()
  {
    students = new ArrayList<Student>();
    parents = new ArrayList<Parent>();
    items = new ArrayList<Item>();
    bundles = new ArrayList<GradeBundle>();
    bundleItems = new ArrayList<BundleItem>();
    orders = new ArrayList<Order>();
    orderItems = new ArrayList<OrderItem>();
    grades = new ArrayList<Grade>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public SchoolAdmin getAdmin()
  {
    return admin;
  }

  public boolean hasAdmin()
  {
    boolean has = admin != null;
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
  /* Code from template association_GetMany */
  public Parent getParent(int index)
  {
    Parent aParent = parents.get(index);
    return aParent;
  }

  public List<Parent> getParents()
  {
    List<Parent> newParents = Collections.unmodifiableList(parents);
    return newParents;
  }

  public int numberOfParents()
  {
    int number = parents.size();
    return number;
  }

  public boolean hasParents()
  {
    boolean has = parents.size() > 0;
    return has;
  }

  public int indexOfParent(Parent aParent)
  {
    int index = parents.indexOf(aParent);
    return index;
  }
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetMany */
  public GradeBundle getBundle(int index)
  {
    GradeBundle aBundle = bundles.get(index);
    return aBundle;
  }

  public List<GradeBundle> getBundles()
  {
    List<GradeBundle> newBundles = Collections.unmodifiableList(bundles);
    return newBundles;
  }

  public int numberOfBundles()
  {
    int number = bundles.size();
    return number;
  }

  public boolean hasBundles()
  {
    boolean has = bundles.size() > 0;
    return has;
  }

  public int indexOfBundle(GradeBundle aBundle)
  {
    int index = bundles.indexOf(aBundle);
    return index;
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
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
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
  /* Code from template association_GetMany */
  public Grade getGrade(int index)
  {
    Grade aGrade = grades.get(index);
    return aGrade;
  }

  public List<Grade> getGrades()
  {
    List<Grade> newGrades = Collections.unmodifiableList(grades);
    return newGrades;
  }

  public int numberOfGrades()
  {
    int number = grades.size();
    return number;
  }

  public boolean hasGrades()
  {
    boolean has = grades.size() > 0;
    return has;
  }

  public int indexOfGrade(Grade aGrade)
  {
    int index = grades.indexOf(aGrade);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAdmin(SchoolAdmin aNewAdmin)
  {
    boolean wasSet = false;
    if (admin != null && !admin.equals(aNewAdmin) && equals(admin.getCoolSupplies()))
    {
      //Unable to setAdmin, as existing admin would become an orphan
      return wasSet;
    }

    admin = aNewAdmin;
    CoolSupplies anOldCoolSupplies = aNewAdmin != null ? aNewAdmin.getCoolSupplies() : null;

    if (!this.equals(anOldCoolSupplies))
    {
      if (anOldCoolSupplies != null)
      {
        anOldCoolSupplies.admin = null;
      }
      if (admin != null)
      {
        admin.setCoolSupplies(this);
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
  public Student addStudent(String aName, Grade aGrade)
  {
    return new Student(aName, this, aGrade);
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (students.contains(aStudent)) { return false; }
    CoolSupplies existingCoolSupplies = aStudent.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aStudent.setCoolSupplies(this);
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
    //Unable to remove aStudent, as it must always have a coolSupplies
    if (!this.equals(aStudent.getCoolSupplies()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfParents()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Parent addParent(String aEmail, String aPassword, String aName, int aPhoneNumber)
  {
    return new Parent(aEmail, aPassword, aName, aPhoneNumber, this);
  }

  public boolean addParent(Parent aParent)
  {
    boolean wasAdded = false;
    if (parents.contains(aParent)) { return false; }
    CoolSupplies existingCoolSupplies = aParent.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aParent.setCoolSupplies(this);
    }
    else
    {
      parents.add(aParent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeParent(Parent aParent)
  {
    boolean wasRemoved = false;
    //Unable to remove aParent, as it must always have a coolSupplies
    if (!this.equals(aParent.getCoolSupplies()))
    {
      parents.remove(aParent);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addParentAt(Parent aParent, int index)
  {  
    boolean wasAdded = false;
    if(addParent(aParent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfParents()) { index = numberOfParents() - 1; }
      parents.remove(aParent);
      parents.add(index, aParent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveParentAt(Parent aParent, int index)
  {
    boolean wasAdded = false;
    if(parents.contains(aParent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfParents()) { index = numberOfParents() - 1; }
      parents.remove(aParent);
      parents.add(index, aParent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addParentAt(aParent, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Item addItem(String aName, int aPrice)
  {
    return new Item(aName, aPrice, this);
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    CoolSupplies existingCoolSupplies = aItem.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aItem.setCoolSupplies(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a coolSupplies
    if (!this.equals(aItem.getCoolSupplies()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GradeBundle addBundle(String aName, int aDiscount, Grade aGrade)
  {
    return new GradeBundle(aName, aDiscount, this, aGrade);
  }

  public boolean addBundle(GradeBundle aBundle)
  {
    boolean wasAdded = false;
    if (bundles.contains(aBundle)) { return false; }
    CoolSupplies existingCoolSupplies = aBundle.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aBundle.setCoolSupplies(this);
    }
    else
    {
      bundles.add(aBundle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundle(GradeBundle aBundle)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundle, as it must always have a coolSupplies
    if (!this.equals(aBundle.getCoolSupplies()))
    {
      bundles.remove(aBundle);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleAt(GradeBundle aBundle, int index)
  {  
    boolean wasAdded = false;
    if(addBundle(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleAt(GradeBundle aBundle, int index)
  {
    boolean wasAdded = false;
    if(bundles.contains(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleAt(aBundle, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundleItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BundleItem addBundleItem(int aQuantity, BundleItem.PurchaseLevel aLevel, GradeBundle aBundle, Item aItem)
  {
    return new BundleItem(aQuantity, aLevel, this, aBundle, aItem);
  }

  public boolean addBundleItem(BundleItem aBundleItem)
  {
    boolean wasAdded = false;
    if (bundleItems.contains(aBundleItem)) { return false; }
    CoolSupplies existingCoolSupplies = aBundleItem.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aBundleItem.setCoolSupplies(this);
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
    //Unable to remove aBundleItem, as it must always have a coolSupplies
    if (!this.equals(aBundleItem.getCoolSupplies()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(int aNumber, Date aDate, PurchaseLevel aLevel, Parent aParent, Student aStudent)
  {
    return new Order(aNumber, aDate, aLevel, aParent, aStudent, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    CoolSupplies existingCoolSupplies = aOrder.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aOrder.setCoolSupplies(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a coolSupplies
    if (!this.equals(aOrder.getCoolSupplies()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, Order aOrder, InventoryItem aItem)
  {
    return new OrderItem(aQuantity, this, aOrder, aItem);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    CoolSupplies existingCoolSupplies = aOrderItem.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aOrderItem.setCoolSupplies(this);
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
    //Unable to remove aOrderItem, as it must always have a coolSupplies
    if (!this.equals(aOrderItem.getCoolSupplies()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGrades()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Grade addGrade(String aLevel)
  {
    return new Grade(aLevel, this);
  }

  public boolean addGrade(Grade aGrade)
  {
    boolean wasAdded = false;
    if (grades.contains(aGrade)) { return false; }
    CoolSupplies existingCoolSupplies = aGrade.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aGrade.setCoolSupplies(this);
    }
    else
    {
      grades.add(aGrade);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGrade(Grade aGrade)
  {
    boolean wasRemoved = false;
    //Unable to remove aGrade, as it must always have a coolSupplies
    if (!this.equals(aGrade.getCoolSupplies()))
    {
      grades.remove(aGrade);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGradeAt(Grade aGrade, int index)
  {  
    boolean wasAdded = false;
    if(addGrade(aGrade))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGrades()) { index = numberOfGrades() - 1; }
      grades.remove(aGrade);
      grades.add(index, aGrade);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGradeAt(Grade aGrade, int index)
  {
    boolean wasAdded = false;
    if(grades.contains(aGrade))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGrades()) { index = numberOfGrades() - 1; }
      grades.remove(aGrade);
      grades.add(index, aGrade);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGradeAt(aGrade, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    SchoolAdmin existingAdmin = admin;
    admin = null;
    if (existingAdmin != null)
    {
      existingAdmin.delete();
      existingAdmin.setCoolSupplies(null);
    }
    while (students.size() > 0)
    {
      Student aStudent = students.get(students.size() - 1);
      aStudent.delete();
      students.remove(aStudent);
    }
    
    while (parents.size() > 0)
    {
      Parent aParent = parents.get(parents.size() - 1);
      aParent.delete();
      parents.remove(aParent);
    }
    
    while (items.size() > 0)
    {
      Item aItem = items.get(items.size() - 1);
      aItem.delete();
      items.remove(aItem);
    }
    
    while (bundles.size() > 0)
    {
      GradeBundle aBundle = bundles.get(bundles.size() - 1);
      aBundle.delete();
      bundles.remove(aBundle);
    }
    
    while (bundleItems.size() > 0)
    {
      BundleItem aBundleItem = bundleItems.get(bundleItems.size() - 1);
      aBundleItem.delete();
      bundleItems.remove(aBundleItem);
    }
    
    while (orders.size() > 0)
    {
      Order aOrder = orders.get(orders.size() - 1);
      aOrder.delete();
      orders.remove(aOrder);
    }
    
    while (orderItems.size() > 0)
    {
      OrderItem aOrderItem = orderItems.get(orderItems.size() - 1);
      aOrderItem.delete();
      orderItems.remove(aOrderItem);
    }
    
    while (grades.size() > 0)
    {
      Grade aGrade = grades.get(grades.size() - 1);
      aGrade.delete();
      grades.remove(aGrade);
    }
    
  }

  // line 3 "../../../../../CoolSuppliesPersistence.ump"
   public void reinitialize(){
    User.reinitializeUniqueEmail(this.getAdmin(), this.getParents());
    Student.reinitializeUniqueName(this.getStudents());
    Order.reinitializeUniqueNumber(this.getOrders());
    InventoryItem.reinitializeUniqueName(this.getItems(), this.getBundles());
    Grade.reinitializeUniqueLevel(this.getGrades());
  }

}