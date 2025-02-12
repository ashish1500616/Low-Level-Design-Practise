# Chapter 3: Fixing LSP Violations - A Journey to Better Design

## The Art of Fixing LSP Violations

Remember our troubled examples from Chapter 2? Let's transform them into well-designed, LSP-compliant solutions. We'll learn that sometimes the best way to fix inheritance is to avoid it altogether.

## The Shape Saga: A Better Approach

Instead of forcing Square to be a Rectangle, let's rethink our hierarchy:

```java
// 1. Start with a common interface that defines shape behavior
interface Shape {
    double getArea();
    void draw();
}

// 2. Each shape implements the interface independently
class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing rectangle");
    }
}

class Square implements Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public void draw() {
        System.out.println("Drawing square");
    }
}
```

## The Bird Solution: Interface Segregation to the Rescue

Instead of assuming all birds can fly, let's separate capabilities:

```java
// 1. Base interface for all birds
interface Bird {
    void move();
    void makeSound();
}

// 2. Additional capability interface
interface Flyable {
    void fly();
    double getFlightSpeed();
}

// 3. A flying bird implements both interfaces
class Duck implements Bird, Flyable {
    @Override
    public void move() {
        System.out.println("Duck walking/swimming");
    }

    @Override
    public void makeSound() {
        System.out.println("Quack!");
    }

    @Override
    public void fly() {
        System.out.println("Duck flying");
    }

    @Override
    public double getFlightSpeed() {
        return 50.0; // km/h
    }
}

// 4. A non-flying bird only implements Bird
class Ostrich implements Bird {
    @Override
    public void move() {
        System.out.println("Ostrich running");
    }

    @Override
    public void makeSound() {
        System.out.println("Boom!");
    }
}
```

## The Vehicle Refactoring: Using Composition

Let's fix our electric car problem using composition:

```java
// 1. Define clear interfaces for capabilities
interface Vehicle {
    void move();
    void stop();
}

interface PowerSource {
    void replenish();
    double getEnergyLevel();
}

// 2. Implement specific power sources
class FuelTank implements PowerSource {
    private double fuelLevel;

    @Override
    public void replenish() {
        System.out.println("Refueling with gas");
        fuelLevel = 100;
    }

    @Override
    public double getEnergyLevel() {
        return fuelLevel;
    }
}

class Battery implements PowerSource {
    private double charge;

    @Override
    public void replenish() {
        System.out.println("Charging battery");
        charge = 100;
    }

    @Override
    public double getEnergyLevel() {
        return charge;
    }
}

// 3. Use composition to create different vehicle types
class Car implements Vehicle {
    private final PowerSource powerSource;

    public Car(PowerSource powerSource) {
        this.powerSource = powerSource;
    }

    @Override
    public void move() {
        if (powerSource.getEnergyLevel() > 0) {
            System.out.println("Car moving");
        }
    }

    @Override
    public void stop() {
        System.out.println("Car stopped");
    }

    public void refill() {
        powerSource.replenish();
    }
}
```

## Using the Improved Designs

```java
public class ImprovedDesignDemo {
    public static void main(String[] args) {
        // Shapes example
        Shape rectangle = new Rectangle(5, 4);
        Shape square = new Square(5);
        printArea(rectangle);  // Works perfectly
        printArea(square);     // Works perfectly

        // Birds example
        List<Bird> birds = Arrays.asList(new Duck(), new Ostrich());
        List<Flyable> flyingBirds = Arrays.asList(new Duck());
        
        // All birds can move
        birds.forEach(bird -> bird.move());
        
        // Only flying birds will be in this list
        flyingBirds.forEach(bird -> bird.fly());

        // Vehicles example
        Car gasCar = new Car(new FuelTank());
        Car electricCar = new Car(new Battery());
        
        // Both cars work the same way
        gasCar.refill();    // "Refueling with gas"
        electricCar.refill(); // "Charging battery"
    }

    private static void printArea(Shape shape) {
        System.out.println("Area: " + shape.getArea());
    }
}
```

## Key Lessons in Fixing LSP Violations

1. **Favor Composition**: When inheritance causes LSP violations, composition often provides a better solution
2. **Interface Segregation**: Break down large interfaces into smaller, focused ones
3. **Clear Contracts**: Make interfaces explicit and focused on behavior
4. **Avoid Forced Inheritance**: Don't use inheritance just because of real-world classification

## Exercise: Apply These Patterns

Try to fix this LSP violation using the patterns we've learned:

```java
class Document {
    void save() { /* saves to disk */ }
}

class ReadOnlyDocument extends Document {
    @Override
    void save() {
        throw new UnsupportedOperationException("Cannot save read-only document");
    }
}
```

## Coming Up in Chapter 4...
We'll explore a complete real-world example, building a multimedia player system that properly handles different types of media while maintaining LSP compliance!