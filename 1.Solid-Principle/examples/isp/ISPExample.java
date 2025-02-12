/**
 * Interface Segregation Principle (ISP) Example and Guidelines
 * 
 * Definition:
 * ISP states that "Clients should not be forced to depend upon interfaces that they do not use."
 * In other words, it's better to have multiple smaller, focused interfaces rather than one large interface.
 * 
 * Why Important?
 * 1. Reduces coupling between components
 * 2. Makes system more maintainable
 * 3. Prevents classes from implementing unnecessary methods
 * 4. Promotes focused, cohesive interfaces
 * 
 * When to Use?
 * - When interfaces have methods that aren't used by all implementing classes
 * - When you notice some implementations throwing UnsupportedOperationException
 * - When you see classes that need only a subset of interface methods
 */

// BAD EXAMPLE - Violates ISP
interface MultiFunctionPrinter {
    void print();
    void scan();
    void fax();
    void photocopy();
    void colorPrint();
    void staple();
}

// Problems with this approach:
// 1. Basic printers must implement unnecessary methods
// 2. High coupling between printer types
// 3. Changes to advanced features affect basic printer implementations
class BasicPrinter implements MultiFunctionPrinter {
    public void print() { /* Basic printing */ }
    public void scan() { throw new UnsupportedOperationException(); }
    public void fax() { throw new UnsupportedOperationException(); }
    public void photocopy() { throw new UnsupportedOperationException(); }
    public void colorPrint() { throw new UnsupportedOperationException(); }
    public void staple() { throw new UnsupportedOperationException(); }
}

// GOOD EXAMPLE - Follows ISP
// Breaking down into focused interfaces
interface BasicPrinting {
    void print();
}

interface Scanning {
    void scan();
}

interface Faxing {
    void fax();
}

interface AdvancedPrinting {
    void colorPrint();
    void staple();
}

// Now classes can implement only what they need
class SimpleHomePrinter implements BasicPrinting {
    public void print() { /* Basic printing */ }
}

class OfficePrinter implements BasicPrinting, Scanning {
    public void print() { /* Print implementation */ }
    public void scan() { /* Scan implementation */ }
}

class EnterpriseMultiFunction implements BasicPrinting, Scanning, Faxing, AdvancedPrinting {
    public void print() { /* Print implementation */ }
    public void scan() { /* Scan implementation */ }
    public void fax() { /* Fax implementation */ }
    public void colorPrint() { /* Color print implementation */ }
    public void staple() { /* Staple implementation */ }
}

/**
 * Developer's ISP Checklist:
 * 
 * 1. Interface Analysis:
 *    □ Does the interface have methods that aren't used by all implementers?
 *    □ Are there any UnsupportedOperationException implementations?
 *    □ Do different clients use different subsets of the interface?
 * 
 * 2. Client Usage:
 *    □ Do clients depend on methods they don't use?
 *    □ Would changes to one feature affect clients using unrelated features?
 * 
 * 3. Cohesion Check:
 *    □ Are all methods in the interface related to a single responsibility?
 *    □ Can the interface be split into more focused interfaces?
 * 
 * 4. Implementation Review:
 *    □ Are implementing classes using all methods?
 *    □ Is inheritance being used just to access a few methods?
 * 
 * If any answers suggest problems, consider splitting the interface.
 */

// Example of how interfaces can be combined when needed
class PhotocopierDevice implements Scanning, BasicPrinting {
    public void scan() { /* Scanning implementation */ }
    public void print() { /* Printing implementation */ }
    
    // Business logic for photocopying using scan and print
    public void photocopy() {
        scan();
        print();
    }
}