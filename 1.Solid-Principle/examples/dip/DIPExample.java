/**
 * Dependency Inversion Principle (DIP) Guide and Examples
 * 
 * Definition:
 * DIP states that:
 * 1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
 * 2. Abstractions should not depend on details. Details should depend on abstractions.
 * 
 * Why Important?
 * - Reduces coupling between components
 * - Makes code more flexible and easier to modify
 * - Enables easier testing through dependency injection
 * - Promotes modular design and reusability
 * 
 * When to Use?
 * - When you need to decouple high-level and low-level modules
 * - When you want to make your code more testable
 * - When you need to swap implementations without changing client code
 * - When building extensible frameworks or libraries
 */

 /**
 * Dependency Inversion Principle (DIP) - A Simple Guide
 * 
 * What is DIP?
 * - Like using a power outlet in your home
 * - Any appliance with a standard plug can connect to it
 * - You don't need a specific outlet for each appliance
 * 
 * Real-world example:
 * - Think of a coffee machine (high-level) and coffee beans (low-level)
 * - The coffee machine is designed to work with any type of coffee beans
 * - It doesn't care if you use Arabica or Robusta beans
 * - It just needs something that fits its definition of "coffee beans"
 * 
 * Benefits:
 * - Easy to change parts without breaking things
 * - Easy to test
 * - More flexible code
 * 
 * When to use:
 * - When you want to easily swap components
 * - When you need to test your code
 * - When building flexible systems
 */

// BAD EXAMPLE - Violates DIP
class DataReader {
    private MySQLDatabase database; // Direct dependency on concrete class

    public DataReader() {
        this.database = new MySQLDatabase(); // Hard-coded dependency
    }

    public String readData() {
        return database.query("SELECT * FROM data");
    }
}

class MySQLDatabase {
    public String query(String sql) {
        return "Data from MySQL"; // Simplified for example
    }
}

// Problems with this approach:
// 1. DataReader is tightly coupled to MySQLDatabase
// 2. Cannot easily switch to different database types
// 3. Difficult to test as we cannot mock the database
// 4. Violates DIP as high-level module (DataReader) depends on low-level module (MySQLDatabase)

// GOOD EXAMPLE - Follows DIP
interface Database {
    String query(String sql);
}

class MySQLDatabaseImpl implements Database {
    public String query(String sql) {
        return "Data from MySQL";
    }
}

class PostgreSQLDatabaseImpl implements Database {
    public String query(String sql) {
        return "Data from PostgreSQL";
    }
}

class ImprovedDataReader {
    private final Database database; // Depends on abstraction

    public ImprovedDataReader(Database database) { // Constructor injection
        this.database = database;
    }

    public String readData() {
        return database.query("SELECT * FROM data");
    }
}

// Usage example showing flexibility:
class DIPDemo {
    public static void main(String[] args) {
        // Can easily switch between database implementations
        Database mysqlDb = new MySQLDatabaseImpl();
        Database postgresDb = new PostgreSQLDatabaseImpl();

        ImprovedDataReader mysqlReader = new ImprovedDataReader(mysqlDb);
        ImprovedDataReader postgresReader = new ImprovedDataReader(postgresDb);

        System.out.println(mysqlReader.readData());
        System.out.println(postgresReader.readData());
    }
}

/**
 * Developer's DIP Checklist:
 * 
 * 1. Dependency Analysis:
 *    □ Are high-level modules depending directly on low-level modules?
 *    □ Are concrete classes being instantiated directly within classes?
 *    □ Is it difficult to swap implementations without changing code?
 * 
 * 2. Interface Design:
 *    □ Have you identified the abstractions that both high and low-level modules should depend on?
 *    □ Are interfaces focused and cohesive?
 *    □ Do interfaces represent stable abstractions?
 * 
 * 3. Dependency Injection:
 *    □ Are dependencies passed in through constructors or setters?
 *    □ Can dependencies be easily swapped for testing?
 *    □ Is the code using a DI container or framework when appropriate?
 * 
 * 4. Implementation Review:
 *    □ Does each concrete implementation properly fulfill its interface contract?
 *    □ Are there any hidden dependencies not injected?
 *    □ Is the code testable with mock objects?
 * 
 * Best Practices:
 * 1. Use constructor injection for required dependencies
 * 2. Create meaningful interfaces that represent capabilities
 * 3. Avoid 'new' keyword in classes that should be flexible
 * 4. Consider using a DI framework for complex applications
 * 5. Write unit tests to verify proper dependency injection
 */

/**
 * DIP vs Strategy Pattern - Understanding the Differences
 * 
 * Similarities:
 * 1. Both use interfaces/abstractions
 * 2. Both promote loose coupling
 * 3. Both allow switching implementations
 * 4. Both use dependency injection
 * 
 * Key Differences:
 * 1. Intent:
 *    - DIP: Architectural principle about dependency direction
 *    - Strategy: Behavioral pattern for switching algorithms
 * 
 * 2. Scope:
 *    - DIP: Broader principle affecting entire application architecture
 *    - Strategy: Focused on encapsulating interchangeable algorithms
 * 
 * 3. Usage:
 *    - DIP: Applies to all dependencies between modules
 *    - Strategy: Specifically for when you need multiple algorithms
 * 
 * Example of When NOT to Use DIP:
 */

// Case: When a class is stable and unlikely to change
class Logger {
    private final FileSystem fileSystem;  // Concrete class

    public Logger() {
        // This is acceptable if FileSystem is a stable, specific implementation
        // that won't need to change
        this.fileSystem = new FileSystem();
    }
}

// Case: When the dependency is a standard library or utility
class MathCalculator {
    private final Math math;  // Java's built-in Math class
    // This is fine because Math is a stable utility class
}

/**
 * Guidelines for Deciding Whether to Apply DIP:
 * 
 * 1. Apply DIP when:
 *    - The dependency might change (e.g., database implementations)
 *    - You need to test the class with mock objects
 *    - The dependency has multiple possible implementations
 *    - The system needs to be extensible
 * 
 * 2. Don't apply DIP when:
 *    - Working with stable utility classes (java.lang.Math, java.lang.String)
 *    - The dependency is a concrete implementation that won't change
 *    - The cost of abstraction outweighs the benefits
 *    - The class is a low-level implementation detail
 * 
 * Example of Pragmatic DIP Application:
 */

// Acceptable direct instantiation
class SimpleLogger {
    private final FileWriter writer;  // Standard Java class

    public SimpleLogger() {
        try {
            this.writer = new FileWriter("app.log");  // Acceptable as FileWriter is stable
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// DIP recommended approach
interface StorageService {
    void save(String data);
}

class CloudStorageService implements StorageService {
    private final CloudProvider provider;  // Specific to this implementation

    public CloudStorageService() {
        // OK to directly instantiate here as this is a specific implementation
        this.provider = new CloudProvider();
    }

    @Override
    public void save(String data) {
        provider.store(data);
    }
}

class LocalStorageService implements StorageService {
    private final FileSystem fileSystem;

    public LocalStorageService() {
        // OK to directly instantiate here as this is a specific implementation
        this.fileSystem = new FileSystem();
    }

    @Override
    public void save(String data) {
        fileSystem.writeToFile(data);
    }
}

// High-level module using DIP correctly
class DataManager {
    private final StorageService storage;  // Depends on abstraction

    public DataManager(StorageService storage) {
        this.storage = storage;
    }

    public void saveData(String data) {
        storage.save(data);
    }
}
// Also add a class where we show how to use the DataManager class
class DIPExample {
    public static void main(String[] args) {
        // Can easily switch between storage implementations
        StorageService cloudStorage = new CloudStorageService();
        StorageService localStorage = new LocalStorageService();

        DataManager cloudDataManager = new DataManager(cloudStorage);
        DataManager localDataManager = new DataManager(localStorage);

        cloudDataManager.saveData("Data saved in the cloud");
        localDataManager.saveData("Data saved locally");
    }
}

/**
 * Key Takeaway:
 * - DIP doesn't mean "never instantiate concrete classes"
 * - It means "high-level modules shouldn't depend on low-level modules"
 * - Concrete classes can instantiate other concrete classes if they're part
 *   of their specific implementation
 * - The important part is that the high-level modules remain decoupled
 *   through abstractions
 */