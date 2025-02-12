# Chapter 2: When Good Intentions Go Wrong

## The Tale of Two Shapes: Rectangle and Square

Every developer's journey through LSP inevitably encounters the famous Rectangle-Square problem. Let's explore this classic pitfall through a story.

## The Seemingly Logical Design

Picture yourself as a developer at a CAD software company. Your team is building a system for architectural drawings. You have a `Rectangle` class that works perfectly:

```java
class Rectangle {
    private double width;
    private double height;
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    public double getArea() {
        return width * height;
    }
}
```

Then comes a new requirement: support squares. You remember from mathematics that a square is a special type of rectangle. Perfect! This is inheritance 101, right?

```java
class Square extends Rectangle {
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        super.setHeight(width);  // Maintain square property
    }
    
    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        super.setWidth(height);  // Maintain square property
    }
}
```

## The Hidden Trap

Everything seems fine until you get this bug report:

```java
public class ArchitecturalDrawing {
    public void resizeRectangle(Rectangle rect) {
        // Common operation: double the width
        rect.setWidth(rect.getWidth() * 2);
        // Assumption: Area should also double
        assert rect.getArea() == originalArea * 2; // FAILS for Square!
    }
    
    public void demonstrateBug() {
        Rectangle rectangle = new Square();
        rectangle.setWidth(2);
        rectangle.setHeight(4);
        // Expected: width=2, height=4
        // Actual: width=4, height=4 (Square enforced its invariant)
    }
}
```

## Why This Violates LSP

1. **Contract Violation**: The Rectangle contract implies that width and height can be set independently
2. **Unexpected Behavior**: Code that works with Rectangle breaks when given a Square
3. **Invariant Violation**: Rectangle's invariant (width and height are independent) is broken

## The Bird Hierarchy: Another Common Trap

Let's look at another classic example: the bird hierarchy.

```java
abstract class Bird {
    abstract void fly();
}

class Duck extends Bird {
    @Override
    void fly() {
        System.out.println("Duck flying");
    }
}

class Ostrich extends Bird {
    @Override
    void fly() {
        // What do we do here? Ostrich can't fly!
        throw new UnsupportedOperationException("Can't fly!");
    }
}
```

## Why This Also Violates LSP

1. **Broken Expectations**: Client code expecting birds to fly will break
2. **Exception Surprises**: Unexpected runtime exceptions
3. **False Hierarchy**: Just because Ostrich is a bird biologically doesn't mean it should inherit from a flying bird class

## Real-World Impact

These violations have real consequences:

```java
public class BirdMigrationSimulator {
    public void simulateMigration(List<Bird> birds) {
        for (Bird bird : birds) {
            try {
                bird.fly();  // BOOM! RuntimeException for Ostrich
            } catch (UnsupportedOperationException e) {
                // Now we need special handling...
                // This breaks the whole point of polymorphism!
            }
        }
    }
}
```

## Key Lessons from These Mistakes

1. **Is-A isn't enough**: Just because X is a Y in the real world doesn't mean X should inherit from Y in code
2. **Behavior over Classification**: Focus on behavior compatibility, not classification hierarchy
3. **Watch for Hidden Assumptions**: Client code makes assumptions about behavior that must be preserved
4. **Beware of Invariants**: Subclasses must maintain the invariants of their base classes

## Exercise: Spot the Violation

Try to identify LSP violations in this code:

```java
class ElectricCar extends Car {
    @Override
    public void refuel() {
        throw new UnsupportedOperationException("Electric cars don't use fuel!");
    }
}
```

## Coming Up in Chapter 3...
We'll learn how to fix these problems using interface segregation and composition. Stay tuned to discover how to turn these problematic designs into clean, LSP-compliant solutions!