# Chapter 1: The First Steps into LSP

## The Story of Shapes

Imagine you're building a drawing application. You start simple - just dealing with shapes. Every shape needs to be drawn and must have an area. Sounds straightforward, right? Let's see how this simple requirement leads us to understand LSP.

## The First Attempt

```java
public abstract class Shape {
    abstract double getArea();
    abstract void draw();
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    void draw() {
        System.out.println("Drawing a circle");
    }
}
```

This looks good! But wait... what happens when we try to use these shapes in our application?

## The First LSP Lesson

```java
public class DrawingApp {
    public void drawShape(Shape shape) {
        // This method trusts that ANY shape will work correctly
        shape.draw();
        System.out.println("Area: " + shape.getArea());
    }
}
```

The key insight here is that `DrawingApp` doesn't care what specific type of shape it gets. It trusts that:
1. The shape can be drawn
2. The shape has a valid area
3. No unexpected exceptions will occur

This is the essence of LSP - the ability to trust that derived classes will behave as expected.

## Adding More Shapes

```java
class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    double getArea() {
        return width * height;
    }
    
    @Override
    void draw() {
        System.out.println("Drawing a rectangle");
    }
}
```

## The Plot Twist: A Negative Area?

What if someone creates a shape that returns a negative area?

```java
// 🚫 Bad Example - Violates LSP
class WeirdShape extends Shape {
    @Override
    double getArea() {
        return -1; // Violates the implicit contract that area should be positive
    }
    
    @Override
    void draw() {
        System.out.println("Drawing a weird shape");
    }
}
```

This is our first encounter with an LSP violation! Even though the code compiles, it breaks an implicit contract: areas should never be negative.

## The Solution: Making Contracts Explicit

```java
public abstract class Shape {
    abstract double getArea();
    abstract void draw();
    
    // Making the contract explicit
    protected final void validateArea(double area) {
        if (area < 0) {
            throw new IllegalStateException("Area cannot be negative");
        }
    }
}

class SafeShape extends Shape {
    @Override
    double getArea() {
        double area = calculateArea();
        validateArea(area); // Enforcing the contract
        return area;
    }
    
    private double calculateArea() {
        // Area calculation logic
        return 0;
    }
    
    @Override
    void draw() {
        System.out.println("Drawing a safe shape");
    }
}
```

## Key Takeaways from Chapter 1

1. LSP is about trust - clients should be able to trust that derived classes will behave correctly
2. Contracts can be implicit or explicit
3. Breaking contracts, even if the code compiles, violates LSP
4. Making contracts explicit helps prevent violations

## Exercises for Chapter 1

1. Try creating a Triangle shape that maintains LSP
2. Think about what other implicit contracts might exist in our Shape hierarchy
3. Can you think of a way to enforce that all shapes must be drawable within positive coordinates?

## Next Chapter Preview
In Chapter 2, we'll explore more complex scenarios where LSP violations are less obvious and potentially more dangerous. Get ready to dive into the classic Rectangle-Square problem!