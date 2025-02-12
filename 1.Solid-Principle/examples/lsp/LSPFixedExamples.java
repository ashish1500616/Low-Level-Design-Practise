// Corrected Examples - Following LSP

// Example 1: Rectangle-Square Fix using Composition
interface Shape {
    int getArea();
}

class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}

class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}

// Example 2: Bird Problem Fix using Interface Segregation
interface Animal {
    void move();
}

interface Flyable {
    void fly();
}

class Duck implements Animal, Flyable {
    @Override
    public void move() {
        System.out.println("Duck walking");
    }

    @Override
    public void fly() {
        System.out.println("Duck flying");
    }
}

class Ostrich implements Animal {
    @Override
    public void move() {
        System.out.println("Ostrich running");
    }
}

// Example 3: File Access Fix using Role Interface
interface FileReader {
    String read() throws IOException;
}

class BasicFileReader implements FileReader {
    private String filePath;

    public BasicFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String read() throws IOException {
        return "File content";  // Simplified for example
    }
}

class SecuredFileReader implements FileReader {
    private final FileReader delegate;
    private final AuthenticationService authService;

    public SecuredFileReader(FileReader delegate, AuthenticationService authService) {
        this.delegate = delegate;
        this.authService = authService;
    }

    @Override
    public String read() throws IOException {
        if (authService.isAuthenticated()) {
            return delegate.read();
        }
        throw new SecurityException("Authentication required");
    }
}

// Example 4: Collection Fix using Clear Contracts
interface Collection<T> {
    boolean add(T element);
    int size();
    boolean isFull();
}

class DynamicArray<T> implements Collection<T> {
    private T[] elements;
    private int size;

    @Override
    public boolean add(T element) {
        ensureCapacity();
        elements[size++] = element;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return false;  // Never full, can always grow
    }

    private void ensureCapacity() {
        // Implementation to grow array when needed
    }
}

class FixedArray<T> implements Collection<T> {
    private final T[] elements;
    private int size;
    private final int capacity;

    public FixedArray(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
    }

    @Override
    public boolean add(T element) {
        if (isFull()) {
            return false;  // Clear contract: returns false when full
        }
        elements[size++] = element;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size >= capacity;
    }
}

// Example Usage demonstrating LSP compliance
class LSPCompliantDemo {
    public static void main(String[] args) {
        // Shape example
        testArea(new Rectangle(5, 4));  // Works as expected
        testArea(new Square(5));        // Works as expected

        // Animal example
        testMovement(new Duck());     // Works for all animals
        testMovement(new Ostrich());  // Works for all animals
        testFlight(new Duck());       // Works only for flyable animals

        // Collection example
        testCollection(new DynamicArray<>());  // Works for all collections
        testCollection(new FixedArray<>(10));  // Works for all collections
    }

    static void testArea(Shape shape) {
        System.out.println("Area: " + shape.getArea());
    }

    static void testMovement(Animal animal) {
        animal.move();  // Works for all animals
    }

    static void testFlight(Flyable flyable) {
        flyable.fly();  // Only called for flying-capable objects
    }

    static void testCollection(Collection<String> collection) {
        if (!collection.isFull()) {
            collection.add("test");
        }
    }
}

/* 
Key LSP Fixes Demonstrated:

1. Shape Hierarchy:
   - Separated Square and Rectangle implementations
   - Each implements their own natural behavior
   - No unexpected side effects

2. Bird Hierarchy:
   - Used interface segregation
   - Separated flying capability from basic animal behavior
   - No unexpected exceptions

3. File Access:
   - Used composition instead of inheritance
   - Clear separation of authentication concern
   - Explicit contract through interfaces

4. Collection:
   - Clear contracts through interface
   - Explicit capacity checking
   - No unexpected exceptions
   - Consistent behavior across implementations

These solutions maintain substitutability while providing
type-safe and predictable behavior.
*/