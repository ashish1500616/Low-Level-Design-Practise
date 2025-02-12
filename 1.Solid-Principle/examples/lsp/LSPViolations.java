// Common LSP Violations Examples

// Example 1: Rectangle-Square Problem (Classic LSP Violation)
class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}

// Violates LSP because it changes the behavior of setWidth/setHeight
class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width); // Violates LSP by changing both dimensions
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height); // Violates LSP by changing both dimensions
    }
}

// Example 2: Bird Problem (Throwing Unexpected Exceptions)
abstract class Bird {
    abstract void fly();
}

class Duck extends Bird {
    @Override
    void fly() {
        System.out.println("Duck flying");
    }
}

// Violates LSP by throwing exception for expected behavior
class Ostrich extends Bird {
    @Override
    void fly() {
        throw new UnsupportedOperationException("Can't fly!"); // Violates LSP
    }
}

// Example 3: File Access Problem (Strengthening Preconditions)
class ReadOnlyFile {
    protected String content;
    
    public void read() {
        // Read file content
    }
}

// Violates LSP by adding extra preconditions
class SecuredFile extends ReadOnlyFile {
    private boolean isAuthenticated = false;
    
    @Override
    public void read() {
        if (!isAuthenticated) {
            throw new SecurityException("Authentication required"); // Violates LSP
        }
        super.read();
    }
}

// Example 4: Collection Problem (Breaking Contract)
class ArrayCollection<T> {
    protected T[] elements;
    
    public void addElement(T element) {
        // Add element logic
    }
    
    public int size() {
        return elements.length;
    }
}

// Violates LSP by limiting collection size
class FixedSizeArray<T> extends ArrayCollection<T> {
    private static final int MAX_SIZE = 10;
    
    @Override
    public void addElement(T element) {
        if (size() >= MAX_SIZE) {
            throw new IllegalStateException("Cannot add more elements"); // Violates LSP
        }
        super.addElement(element);
    }
}

// Example Usage showing how these violations break client code
class LSPViolationDemo {
    public static void main(String[] args) {
        // Rectangle-Square Problem
        testRectangle(new Rectangle()); // Works fine
        testRectangle(new Square());    // Breaks unexpectedly
        
        // Bird Problem
        Bird duck = new Duck();
        Bird ostrich = new Ostrich();
        tryToFly(duck);    // Works fine
        tryToFly(ostrich); // Throws unexpected exception
        
        // File Access Problem
        ReadOnlyFile normal = new ReadOnlyFile();
        ReadOnlyFile secured = new SecuredFile();
        readFile(normal);  // Works fine
        readFile(secured); // Throws unexpected exception
    }
    
    // This method assumes Rectangle behavior
    static void testRectangle(Rectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(4);
        // Expected area: 20
        // But with Square, both width and height become 4, area becomes 16!
        System.out.println("Area: " + rectangle.getArea());
    }
    
    static void tryToFly(Bird bird) {
        bird.fly(); // Ostrich will throw exception!
    }
    
    static void readFile(ReadOnlyFile file) {
        file.read(); // SecuredFile might throw exception!
    }
}

/* 
Key LSP Violations Demonstrated:

1. Rectangle-Square: 
   - Violates invariants of the base class
   - Changes expected behavior of setWidth/setHeight

2. Bird-Ostrich:
   - Throws unexpected exceptions
   - Breaks "is-a" relationship expectation

3. File Access:
   - Adds stricter preconditions
   - Changes contract of base class

4. Collection:
   - Adds limitations not present in base class
   - Throws unexpected exceptions

These examples show how seemingly logical inheritance can break 
substitutability and cause unexpected behavior in client code.
*/