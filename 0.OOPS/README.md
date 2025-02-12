# Object-Oriented Programming (OOP) Concepts Guide

## 1. What is Object-Oriented Programming?

Object-Oriented Programming is a programming paradigm that organizes code into objects that contain both data and behavior. It provides a clear modular structure for programs, making it excellent for large, complex, and actively maintained software systems.

### Benefits of OOP:
- **Maintainability**: Changes to one part of the code have minimal impact on other parts
- **Reusability**: Code can be reused through inheritance
- **Scalability**: OOP makes it easier to manage and modify existing code as projects scale
- **Security**: Implementation details can be hidden through encapsulation

## 2. Core OOP Principles

### 2.1 Encapsulation
Encapsulation is the bundling of data and the methods that operate on that data within a single unit (class), restricting direct access to some of an object's components.

```java
// Bad Example (No Encapsulation)
class BankAccount {
    public double balance;  // Directly accessible
}

// Good Example (With Encapsulation)
class BankAccount {
    private double balance;  // Data hiding
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}
```

### 2.2 Abstraction
Abstraction is the process of hiding complex implementation details and showing only the necessary features of an object.

```java
// Abstract class example
abstract class Shape {
    abstract double calculateArea();
    
    // Common functionality
    public void display() {
        System.out.println("Area: " + calculateArea());
    }
}

class Circle extends Shape {
    private final double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}
```

### 2.3 Inheritance
Inheritance allows a class to inherit attributes and methods from another class, promoting code reuse and establishing relationships between classes.

```java
class Animal {
    protected String name;
    
    public void eat() {
        System.out.println(name + " is eating");
    }
}

class Dog extends Animal {
    public Dog(String name) {
        this.name = name;
    }
    
    public void bark() {
        System.out.println(name + " is barking");
    }
}
```

### 2.4 Polymorphism
Polymorphism allows objects to be treated as instances of their parent class while maintaining their own unique behavior.

```java
// Method Overloading (Compile-time Polymorphism)
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
}

// Method Overriding (Runtime Polymorphism)
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }
}
```

## 3. Access Modifiers and Keywords

### 3.1 Private Access Modifier
- Restricts access to class members
- Only accessible within the same class
- Helps achieve encapsulation

```java
public class Employee {
    private String name;
    private double salary;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```

### 3.2 Final Keyword
- **Final Class**: Cannot be inherited
- **Final Method**: Cannot be overridden
- **Final Variable**: Cannot be modified after initialization

```java
final class ImmutableClass {
    private final String value;
    
    public ImmutableClass(String value) {
        this.value = value;
    }
    
    public final String getValue() {
        return value;
    }
}
```

## 4. Bad Code vs. Good Code Example

### Bad Code
```java
class UserData {
    public String name;
    public int age;
    public String[] addresses;
    
    public void saveUser() {
        // Direct database operations
        System.out.println("Saving user " + name);
    }
}
```

### Good Code (Refactored)
```java
class User {
    private final String name;
    private final int age;
    private final List<Address> addresses;
    
    public User(String name, int age) {
        this.name = name;
        this.age = age;
        this.addresses = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addAddress(Address address) {
        addresses.add(address);
    }
    
    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }
}

class Address {
    private final String street;
    private final String city;
    
    // Constructor and getters
}
```

## 5. Interview Questions and Answers

### Q1: What is encapsulation and why is it important?
**Answer**: Encapsulation is the bundling of data and methods that operate on that data within a single unit (class), while restricting direct access to some of an object's components. It's important because it:
- Protects data from unauthorized access
- Makes code maintenance easier
- Reduces complexity by hiding implementation details
- Allows changes to implementation without affecting code that uses the class

### Q2: Difference between abstraction and encapsulation?
**Answer**: While both help in hiding complexity:
- Abstraction focuses on hiding complex implementation details and showing only functionality to the user
- Encapsulation focuses on wrapping data and methods together and restricting access to internal details
- Abstraction is about "what" an object does, encapsulation is about "how" it does it

### Q3: How does inheritance contribute to code reusability?
**Answer**: Inheritance allows:
- Reuse of common code across multiple classes
- Extension of existing classes with new functionality
- Creation of hierarchical relationships between classes
- Reduction in code duplication and maintenance efforts

### Q4: When should you use the final keyword?
**Answer**: Use final:
- For classes that shouldn't be inherited (e.g., utility classes, immutable classes)
- For methods that shouldn't be overridden to preserve behavior
- For variables that should be constants
- For method parameters that shouldn't be modified within the method
- To improve performance (in some cases) and thread safety

### Q5: What is polymorphism and when to use it?
**Answer**: Polymorphism allows objects to take multiple forms:
- Use method overloading when you need similar operations with different parameters
- Use method overriding when you want specific implementations in child classes
- Use interface polymorphism when you want to define a contract that multiple classes can implement differently
- Helps in writing flexible and reusable code

Remember: These concepts are best learned through practice. Try to implement these patterns in your own code and understand their practical applications.

## 6. Composition over Inheritance

### 6.1 Downsides of Inheritance

1. **Tight Coupling**: Inheritance creates a tight coupling between parent and child classes, making changes difficult
2. **Fragile Base Class Problem**: Changes in the parent class can break child classes unexpectedly
3. **Inflexibility**: Cannot change behavior at runtime as inheritance is static
4. **Limited to Single Inheritance**: In languages like Java, a class can only inherit from one class
5. **Deep Hierarchies**: Can lead to complex, deep inheritance hierarchies that are hard to understand
6. **Breaks Encapsulation**: Child classes can access parent's internal details, violating encapsulation

### 6.2 Why Favor Composition?

1. **Loose Coupling**: Classes remain independent and easier to modify
2. **Flexible**: Can change behavior at runtime by swapping components
3. **Better Testability**: Components can be easily mocked or replaced for testing
4. **Avoid Deep Hierarchies**: Flat structure is easier to understand
5. **Better Encapsulation**: Implementation details remain hidden

### 6.3 Example: Violation of Composition Over Inheritance

```java
// Bad Example using Inheritance
class Animal {
    protected String name;
    protected void eat() { System.out.println("Eating"); }
    protected void sleep() { System.out.println("Sleeping"); }
}

class Bird extends Animal {
    public void fly() { System.out.println("Flying"); }
}

class Penguin extends Bird { // Problem: Penguins can't fly!
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}
```

### 6.4 Example: Following Composition

```java
// Good Example using Composition
interface MovementBehavior {
    void move();
}

class FlyingBehavior implements MovementBehavior {
    public void move() { System.out.println("Flying"); }
}

class WalkingBehavior implements MovementBehavior {
    public void move() { System.out.println("Walking"); }
}

class Animal {
    protected String name;
    protected MovementBehavior movementBehavior;

    public Animal(MovementBehavior behavior) {
        this.movementBehavior = behavior;
    }

    public void move() {
        movementBehavior.move();
    }
}

class Bird extends Animal {
    public Bird() {
        super(new FlyingBehavior());
    }
}

class Penguin extends Animal {
    public Penguin() {
        super(new WalkingBehavior());
    }
}
```

### 6.5 Framework for Using Composition

1. **Identify Behaviors**:
   - Break down the functionality into distinct behaviors
   - Create interfaces for each behavior
   - Implement different variations of these behaviors

2. **Component Structure**:
   ```java
   interface Behavior {
       void execute();
   }
   
   class Component {
       private Behavior behavior;
       
       public Component(Behavior behavior) {
           this.behavior = behavior;
       }
       
       public void setBehavior(Behavior newBehavior) {
           this.behavior = newBehavior;
       }
   }
   ```

3. **Decision Framework**:
   - Use inheritance when there's a true "IS-A" relationship
   - Use composition when:
     * Behavior needs to change at runtime
     * Multiple variations of behavior exist
     * Code reuse is the primary goal
     * Deep inheritance hierarchies would result

4. **Implementation Steps**:
   a. Identify common behaviors
   b. Create interfaces for each behavior
   c. Implement behavior variants
   d. Compose objects with needed behaviors
   e. Allow behavior modification through setters

### 6.6 Real-World Application Example

```java
// UI Component System
interface Renderer {
    void render();
}

interface EventHandler {
    void handleEvent();
}

class WebRenderer implements Renderer {
    public void render() { System.out.println("Rendering for web"); }
}

class MobileRenderer implements Renderer {
    public void render() { System.out.println("Rendering for mobile"); }
}

class Button {
    private Renderer renderer;
    private EventHandler eventHandler;

    public Button(Renderer renderer, EventHandler eventHandler) {
        this.renderer = renderer;
        this.eventHandler = eventHandler;
    }

    public void display() {
        renderer.render();
    }

    public void click() {
        eventHandler.handleEvent();
    }

    // Can change behavior at runtime
    public void setRenderer(Renderer newRenderer) {
        this.renderer = newRenderer;
    }
}
```

Remember: When in doubt, start with composition. It's easier to change from composition to inheritance later than vice versa.

## 7. Interface vs Abstract Class

### 7.1 Problem Scenario: Building a Payment System

Consider building a payment system that needs to handle different payment methods. Each payment method has some common behaviors but also unique implementations.

❌ **Bad Approach (No Abstraction)**:
```java
class CreditCardPayment {
    public void processPayment() { /* credit card logic */ }
    public void validatePayment() { /* validation logic */ }
    public PaymentStatus getStatus() { /* status logic */ }
}

class PayPalPayment {
    public void processPayment() { /* paypal logic */ }
    public void validatePayment() { /* validation logic */ }
    public PaymentStatus getStatus() { /* status logic */ }
}
// Duplicate code, no standardization, hard to maintain
```

### 7.2 Solution Using Interface

✅ When you need a contract that multiple unrelated classes will implement:

```java
interface PaymentProcessor {
    void processPayment();
    void validatePayment();
    PaymentStatus getStatus();
}

class CreditCardPayment implements PaymentProcessor {
    @Override
    public void processPayment() {
        // Credit card specific implementation
    }

    @Override
    public void validatePayment() {
        // Credit card validation
    }

    @Override
    public PaymentStatus getStatus() {
        // Credit card status check
    }
}

class PayPalPayment implements PaymentProcessor {
    // PayPal specific implementations
}
```

### 7.3 Solution Using Abstract Class

✅ When you have common functionality that multiple related classes will share:

```java
abstract class AbstractPaymentProcessor {
    // Common functionality
    protected PaymentStatus status;

    // Common implementation shared by all payment methods
    public final void updateStatus(PaymentStatus status) {
        this.status = status;
        notifyStatusChange();
    }

    public PaymentStatus getStatus() {
        return status;
    }

    // Common private helper method
    private void notifyStatusChange() {
        // Notification logic
    }

    // Abstract methods that must be implemented
    abstract void processPayment();
    abstract void validatePayment();
}

class CreditCardPayment extends AbstractPaymentProcessor {
    @Override
    public void processPayment() {
        // Credit card specific logic
    }

    @Override
    public void validatePayment() {
        // Credit card validation
    }
}
```

### 7.4 Key Differences

| Feature | Interface | Abstract Class |
|---------|-----------|----------------|
| Multiple Inheritance | Yes (can implement multiple) | No (can extend only one) |
| State | No state (constants only) | Can have state |
| Method Implementation | Only default methods (Java 8+) | Can have concrete methods |
| Constructor | No constructors | Can have constructors |
| Access Modifiers | Public only | All access modifiers |
| Purpose | Define contract | Share common code |

### 7.5 Decision Framework

Use this flowchart to decide between Interface and Abstract Class:

```
Start
  ▼
Do you need multiple inheritance?
  ├── Yes → Use Interface
  ▼
Do you have common code to share?
  ├── No → Use Interface
  ▼
Do you need to maintain state?
  ├── Yes → Use Abstract Class
  ▼
Are the classes closely related?
  ├── Yes → Use Abstract Class
  ├── No → Use Interface
  ▼
Do you need to enforce a contract only?
  ├── Yes → Use Interface
  └── No → Use Abstract Class
```

### 7.6 Example: Combined Usage

Sometimes you might need both. Here's a real-world example:

```java
// Interface defines the contract
interface PaymentProcessor {
    void processPayment();
    PaymentStatus getStatus();
}

// Abstract class provides common functionality
abstract class AbstractOnlinePayment implements PaymentProcessor {
    protected String transactionId;
    protected PaymentStatus status;

    // Common functionality
    protected final void generateTransactionId() {
        this.transactionId = UUID.randomUUID().toString();
    }

    // Common implementation
    @Override
    public PaymentStatus getStatus() {
        return status;
    }

    // Abstract method for specific validation
    abstract protected boolean validateTransaction();
}

// Concrete implementation
class StripePayment extends AbstractOnlinePayment {
    @Override
    public void processPayment() {
        generateTransactionId();
        if (validateTransaction()) {
            // Stripe-specific payment logic
        }
    }

    @Override
    protected boolean validateTransaction() {
        // Stripe-specific validation
        return true;
    }
}
```

### 7.7 Best Practices

1. **Use Interface when**:
   - You want to define a contract
   - Multiple classes need to share the same contract
   - Classes are unrelated but need similar functionality
   - You need multiple inheritance

2. **Use Abstract Class when**:
   - You have common code to share
   - You need to maintain state
   - You want to provide a base implementation
   - The classes are closely related
   - You need protected or private members
   
3. **Consider both when**:
   - You need to combine contract definition with shared functionality
   - You're building a framework that others will extend
   - You need to ensure both flexibility and code reuse

Remember: Choose based on your specific needs and the relationships between your classes. Don't be afraid to use both when it makes sense.

## 8. Understanding Related vs Unrelated Classes

### 8.1 Related Classes

Related classes share a clear "IS-A" relationship and common characteristics. They represent specializations of a common concept.

✅ **Example of Related Classes**:
```java
// These classes are related because they all ARE-A Vehicle
abstract class Vehicle {
    protected String make;
    protected String model;
    protected int year;
    
    // Common behavior for all vehicles
    abstract void startEngine();
    
    // Shared implementation
    public void displayInfo() {
        System.out.println(year + " " + make + " " + model);
    }
}

class Car extends Vehicle {
    private int numberOfDoors;
    
    @Override
    void startEngine() {
        System.out.println("Car engine starts with key");
    }
}

class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    
    @Override
    void startEngine() {
        System.out.println("Motorcycle engine starts with kickstart");
    }
}
```

Key Characteristics of Related Classes:
- Share common attributes (make, model, year)
- Share common behaviors (startEngine, displayInfo)
- Make sense in an inheritance hierarchy
- Follow "IS-A" relationship (Car IS-A Vehicle)
- Often share implementation details

### 8.2 Unrelated Classes

Unrelated classes don't share a common parent but might need to implement similar capabilities or behaviors.

✅ **Example of Unrelated Classes**:
```java
// These classes are unrelated but all need to be "printable"
interface Printable {
    void print();
    int getNumberOfPages();
}

class Document implements Printable {
    private String content;
    
    @Override
    public void print() {
        // Print document content
    }
    
    @Override
    public int getNumberOfPages() {
        return content.length() / 1000; // Simplified
    }
}

class Photo implements Printable {
    private byte[] imageData;
    
    @Override
    public void print() {
        // Print photo
    }
    
    @Override
    public int getNumberOfPages() {
        return 1; // Photos are always one page
    }
}

class Invoice implements Printable {
    private List<Item> items;
    
    @Override
    public void print() {
        // Print invoice
    }
    
    @Override
    public int getNumberOfPages() {
        return items.size() / 10 + 1; // One page per 10 items
    }
}
```

Key Characteristics of Unrelated Classes:
- Don't share common attributes
- May share capabilities but not implementation
- Don't make sense in an inheritance hierarchy
- No "IS-A" relationship
- Independent of each other

### 8.3 Decision Framework

Use this checklist to determine if classes are related:

1. Ask "IS-A" Questions:
   - Does ClassA IS-A ClassB make sense?
   - Would inheritance feel natural?
   
2. Check Common Attributes:
   - Do the classes share common fields?
   - Do they need similar state management?
   
3. Evaluate Behavior Sharing:
   - Do they share implementation details?
   - Could they benefit from common helper methods?

4. Consider Future Changes:
   - Will changes to one class likely affect others?
   - Do they evolve together?

Example Analysis:
```java
// Related Classes (Use Abstract Class)
Dog IS-A Animal ✅
Cat IS-A Animal ✅
- Share attributes (name, age)
- Share behavior implementation (eat, sleep)
- Changes to Animal affect all pets

// Unrelated Classes (Use Interface)
Printer IS-A Logger ❌
Database IS-A Logger ❌
File IS-A Logger ❌
- Don't share attributes
- Only share the need to log
- Can change independently
```

### 8.4 Real-World Example

```java
// Related Classes Example (Abstract Class)
abstract class Employee {
    protected String name;
    protected double baseSalary;
    
    public abstract double calculateSalary();
    
    // Shared implementation
    public void displayInfo() {
        System.out.println("Employee: " + name);
    }
}

class FullTimeEmployee extends Employee {
    private double bonus;
    
    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
}

class ContractEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    
    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

// Unrelated Classes Example (Interface)
interface Exportable {
    byte[] exportData();
    String getFileType();
}

class UserProfile implements Exportable {
    private String userData;
    
    @Override
    public byte[] exportData() {
        return userData.getBytes();
    }
    
    @Override
    public String getFileType() {
        return "JSON";
    }
}

class ReportGenerator implements Exportable {
    private List<String> reportData;
    
    @Override
    public byte[] exportData() {
        return // Convert report to PDF bytes
    }
    
    @Override
    public String getFileType() {
        return "PDF";
    }
}
```

Remember: If classes share both a contract AND implementation details, you can use both interface and abstract class together. The interface defines the contract, while the abstract class provides the common implementation.

## 9. Class Relationships in OOP

### 9.1 Types of Class Relationships Overview

| Relationship | Strength | Lifecycle Dependency | Implementation | Example |
|--------------|----------|---------------------|----------------|---------|
| Inheritance (IS-A) | Strongest | Type-level | extends keyword | Car IS-A Vehicle |
| Composition (Strong HAS-A) | Very Strong | Child dies with parent | Parent creates child | ParkingLot HAS-A ParkingSpot |
| Aggregation (Weak HAS-A) | Moderate | Independent lifecycles | Parent references child | ParkingLot HAS-A Vehicle |
| Association (USES-A) | Weakest | Temporary interaction | Method parameter/local variable | PaymentSystem USES-A Ticket |

### 9.2 Detailed Relationship Analysis

#### 1. Inheritance (IS-A)
- Permanent type-level relationship
- Child is a specialized version of parent
- Strongest coupling

```java
// Good Inheritance Example
class Vehicle {
    protected String licensePlate;
    protected String make;
    protected String model;
}

class Car extends Vehicle {
    private int numberOfDoors;
    private boolean isConvertible;
}

// BAD: Inheritance just for code reuse
❌ class Rectangle {
    int width, height;
    void draw() { /* drawing logic */ }
}

class Button extends Rectangle {  // Bad! Button isn't really a Rectangle
    void click() { /* click logic */ }
}
```

#### 2. Composition (Strong HAS-A)
- Parent owns child completely
- Child cannot exist without parent
- Parent controls child's lifecycle

```java
// Good Composition Example
class ParkingLot {
    private final List<ParkingSpot> spots;  // Spots can't exist without lot
    
    public ParkingLot(int capacity) {
        spots = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            spots.add(new ParkingSpot(i));  // Parent creates child
        }
    }
}
class ParkingSpot {
  boolean isOccupied;
}

Key Points:
- Parent (ParkingLot) creates child (ParkingSpot) in its constructor.
- Child cannot exist without parent (spots die when the lot closes).
---

// BAD: Using Composition where Aggregation is appropriate
❌ class University {
    private final List<Student> students;  // Wrong! Students can exist without university
    
    public University() {
        students = new ArrayList<>();
    }
}
```

#### 3. Aggregation (Weak HAS-A)
- Parent uses child but doesn't own it
- Child can exist independently
- Child's lifecycle is managed externally

```java
// Good Aggregation Example
class ParkingLot {
    private List<Vehicle> parkedVehicles;  // Vehicles exist independently

    public void parkVehicle(Vehicle vehicle) {  // Vehicle created elsewhere
        parkedVehicles.add(vehicle);
    }
    
    public void removeVehicle(Vehicle vehicle) {
        parkedVehicles.remove(vehicle);  // Vehicle continues to exist
    }
}

Key Points:
- Child (Vehicle) is created externally and passed to parent (ParkingLot).
- Child’s lifecycle is independent (vehicles can park in other lots).

// BAD: Using Aggregation where Composition is needed
❌ class Car {
    private Engine engine;  // Wrong! Engine cannot exist meaningfully without car
    
    public void setEngine(Engine e) {  // Should be created in constructor
        this.engine = e;
    }
}
```

#### 4. Association (USES-A)
- Temporary interaction
- No ownership involved
- Typically through method parameters

```java
// Good Association Example
class PaymentSystem {
    public double calculateFee(Ticket ticket) {  // Temporary usage
        return ticket.getRate() * ticket.getDuration();
    }
}

// BAD: Using Association where Aggregation is needed
❌ class ShoppingCart {
    public void checkout(List<Item> items) {  // Wrong! Cart should maintain items
        // Process items
    }
}
```

### 9.3 Decision Framework for Choosing Relationships

1. **Main Decision Questions**:
   ```
   1. Is it a type relationship? (IS-A)
      └── Yes → Consider Inheritance
   
   2. Does child depend on parent's lifecycle?
      ├── Yes → Use Composition
      └── No → Continue to next question
   
   3. Does parent need to track child long-term?
      ├── Yes → Use Aggregation
      └── No → Use Association
   ```

2. **Lifecycle Questions**:
   - Can the child exist without the parent?
   - Who creates/destroys the child?
   - Is the relationship permanent or temporary?

### 9.4 Real-World Example: Parking System

```java
// Core entity using composition
class ParkingLot {
    private final List<ParkingSpot> spots;         // Composition
    private List<Vehicle> parkedVehicles;          // Aggregation
    private final PaymentProcessor processor;       // Composition
    
    public ParkingLot(int capacity) {
        // Composition: Create spots with the lot
        spots = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            spots.add(new ParkingSpot(i));
        }
        processor = new PaymentProcessor();
    }
    
    // Association: Temporary ticket processing
    public double processTicket(ParkingTicket ticket) {
        return processor.calculateFee(ticket);
    }
}

// Inheritance hierarchy
abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;
}

class Car extends Vehicle {
    private int numberOfDoors;
}

class Truck extends Vehicle {
    private double cargoCapacity;
}

// Independent entity for aggregation
class ParkingTicket {
    private final String ticketId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    
    // Methods...
}
```

### **2. Key Differences**  

| **Aspect**          | **Inheritance**     | **Composition**        | **Aggregation**        | **Association**      |  
|----------------------|----------------------|------------------------|------------------------|----------------------|  
| **Keyword**          | `extends`           | Constructor creates child | Method takes child   | Method parameter     |  
| **Ownership**        | Not applicable      | Parent owns child      | Parent reuses child   | No ownership         |  
| **Lifecycle**        | Child tied to type  | Child dies with parent | Child exists independently | Temporary usage |  
| **Implementation**  | Hierarchical        | Controlled by parent  | Externally managed    | Method parameter     |  

---

## **Critical Distinctions**  
### **1. Composition vs. Aggregation**  
- **Composition**:  
  ```java
  // ParkingLot CONTROLS ParkingSpot lifecycle
  public ParkingLot() {
    spots = new ParkingSpot(); // Created inside parent
  }
  ```  
- **Aggregation**:  
  ```java
  // Vehicle exists externally
  ParkingLot lot = new ParkingLot();
  Vehicle car = new Vehicle();
  lot.addVehicle(car); // Passed to parent
  ```  

### **2. Association vs. Aggregation**  
- **Association**:  
  ```java
  // Short-term interaction: Ticket is a parameter
  payment.calculateFee(ticket); 
  ```  
- **Aggregation**:  
  ```java
  // Long-term tracking: Vehicle stored in list
  parkedVehicles.add(vehicle);
  ```  

---

## **Summary**  
- **Inheritance**: Permanent "is-a" (**`Car`** → **`Vehicle`**).  
- **Composition**: Parent owns child (**`ParkingLot`** → **`ParkingSpot`**).  
- **Aggregation**: Parent borrows child (**`ParkingLot`** → **`Vehicle`**).  
- **Association**: Temporary use (**`PaymentSystem`** → **`Ticket`**).  

### 9.5 Best Practices Summary

1. **Inheritance**:
   - Use only for true "IS-A" relationships
   - Keep inheritance hierarchies shallow
   - Consider interfaces for behavior sharing

2. **Composition**:
   - Use when child components are essential parts
   - Create child objects in parent's constructor
   - Make child references final when possible

3. **Aggregation**:
   - Use when objects can exist independently
   - Accept objects through methods/constructors
   - Consider weak references if needed

4. **Association**:
   - Use for temporary interactions
   - Pass objects as method parameters
   - Don't store long-term references

Remember: 
- Prefer composition over inheritance
- Use the weakest relationship that solves the problem
- Consider object lifecycles when choosing relationships

## 10. Inheritance vs Abstract Classes: A Comprehensive Guide

### 10.1 Understanding the Basics

#### Inheritance
- A fundamental OOP mechanism that allows a class to inherit properties and methods from another class
- Represents an "IS-A" relationship
- Supports code reuse and establishes a class hierarchy

#### Abstract Class
- A class that cannot be instantiated and may have abstract methods
- Provides a common base class implementation
- Can have both concrete and abstract methods

### 10.2 Key Differences

| Feature | Inheritance | Abstract Class |
|---------|------------|----------------|
| Purpose | Code reuse and type relationship | Template and partial implementation |
| Instantiation | Concrete classes can be instantiated | Cannot be instantiated |
| Method Implementation | All methods must be implemented | Can have both abstract and concrete methods |
| Multiple Inheritance | Not supported in many languages (e.g., Java) | Not supported, but can implement multiple interfaces |
| Constructor | Has constructor | Has constructor but can't be used to create objects |
| Access Modifiers | All access modifiers allowed | All access modifiers allowed |

### 10.3 When to Use What

#### Use Inheritance When:
- You have a clear "IS-A" relationship
- Code reuse is the primary goal
- Subclasses are specific versions of the parent
- Changes to parent should affect all children

```java
// Good use of inheritance
class Vehicle {
    protected String brand;
    public void start() { /* common start logic */ }
}

class Car extends Vehicle {
    private int doors;
    @Override
    public void start() {
        super.start();
        // Car-specific start logic
    }
}
```

#### Use Abstract Class When:
- You want to provide a template
- Some common implementation is shared
- You need to enforce certain methods
- You have common state or behavior

```java
// Good use of abstract class
abstract class PaymentProcessor {
    protected String transactionId;
    
    // Common implementation
    public final void generateId() {
        this.transactionId = UUID.randomUUID().toString();
    }
    
    // Must be implemented by subclasses
    abstract void processPayment();
}
```

### 10.4 Common Pitfalls and Solutions

#### Inheritance Pitfalls:
1. **Deep Hierarchy**
   ```java
   // Bad: Too deep
   class Transport → Vehicle → Car → SportsCar → RaceCar
   
   // Better: Flatter hierarchy with interfaces
   interface Vehicle { /* common contract */ }
   class SportsCar implements Vehicle { /* implementation */ }
   class RaceCar implements Vehicle { /* implementation */ }
   ```

2. **Tight Coupling**
   ```java
   // Bad: Tightly coupled
   class Bird extends Animal {
       @Override
       void move() { fly(); }  // Assumes all birds fly
   }
   
   // Better: Use composition
   class Bird {
       private MovementStrategy movement;
       void move() { movement.execute(); }
   }
   ```

#### Abstract Class Pitfalls:
1. **Too Many Abstract Methods**
   ```java
   // Bad: Should be an interface
   abstract class Logger {
       abstract void log();
       abstract void format();
       abstract void write();
   }
   
   // Better: Split into interface and abstract class
   interface Loggable {
       void log();
   }
   abstract class BaseLogger implements Loggable {
       // Common implementation
   }
   ```

2. **Concrete Methods Without Default Behavior**
   ```java
   // Bad: Empty concrete method
   abstract class Processor {
       public void process() { }  // No default behavior
   }
   
   // Better: Make it abstract
   abstract class Processor {
       abstract public void process();
   }
   ```

### 10.5 Design Best Practices

1. **Favor Composition Over Inheritance**
   ```java
   // Instead of inheritance
   class Rectangle extends Shape { }
   
   // Use composition
   class Rectangle {
       private Shape shape;
       private Dimensions dimensions;
   }
   ```

2. **Use Abstract Classes for Template Method Pattern**
   ```java
   abstract class DataMiner {
       // Template method
       public final void mine() {
           openFile();
           extractData();
           parseData();
           closeFile();
       }
       
       abstract void extractData();
       abstract void parseData();
   }
   ```

3. **Combine with Interfaces**
   ```java
   interface Payable {
       void pay();
   }
   
   abstract class Employee implements Payable {
       protected double salary;
       
       // Common implementation
       public void calculateTax() {
           return salary * 0.2;
       }
       
       // Let subclasses implement
       abstract public void pay();
   }
   ```

### 10.6 Interview Questions

1. **Q: What is the difference between inheritance and abstraction?**
   - Inheritance is about reusing code and establishing relationships
   - Abstraction is about hiding implementation details
   - Abstract classes can use both concepts together

2. **Q: Can we have a constructor in an abstract class?**
   - Yes, but it can only be called from subclass constructors
   - Used to initialize common state
   - Cannot be used to create instances directly

3. **Q: Why can't we instantiate an abstract class?**
   - It may have incomplete implementations (abstract methods)
   - It's meant to be a template for other classes
   - It may represent an abstract concept

4. **Q: When should we make a method abstract?**
   - When there's no meaningful default implementation
   - When subclasses must provide their own implementation
   - When the behavior varies significantly between subclasses

5. **Q: Can an abstract class have all concrete methods?**
   - Yes, it's possible but uncommon
   - Usually done to prevent instantiation
   - Better to use regular class with private constructor in this case

### 10.7 Real-World Examples

1. **Document Processing System**
```java
abstract class Document {
    protected String title;
    protected String content;
    
    // Common implementation
    public void save() {
        validate();
        beforeSave();
        // Save logic
        afterSave();
    }
    
    // Template methods
    abstract void validate();
    abstract void beforeSave();
    abstract void afterSave();
}

class PDFDocument extends Document {
    @Override
    void validate() {
        // PDF-specific validation
    }
    
    @Override
    void beforeSave() {
        // PDF preprocessing
    }
    
    @Override
    void afterSave() {
        // PDF post-processing
    }
}
```

2. **Game Character System**
```java
abstract class GameCharacter {
    protected int health;
    protected int position;
    
    // Common behavior
    public void move(int x, int y) {
        position += calculateMovement(x, y);
        afterMove();
    }
    
    // Must be implemented by specific characters
    abstract int calculateMovement(int x, int y);
    abstract void afterMove();
}

class Player extends GameCharacter {
    @Override
    int calculateMovement(int x, int y) {
        // Player-specific movement calculation
    }
    
    @Override
    void afterMove() {
        // Update player state after movement
    }
}
```

Remember:
- Choose inheritance for clear "IS-A" relationships
- Use abstract classes for template method pattern
- Combine with interfaces for flexible design
- Keep hierarchies shallow and focused
- Consider composition as an alternative
- Use abstract methods when behavior varies significantly
- Provide meaningful default implementations when possible