# Liskov Substitution Principle (LSP) - Complete Guide

## What is LSP?
The Liskov Substitution Principle states that objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program. In other words, derived classes must be substitutable for their base classes without changing the expected behavior.

## Why Use LSP?
1. **Maintains Behavioral Consistency**: Ensures that derived classes honor the contracts established by base classes
2. **Reduces Bugs**: Prevents unexpected behaviors when using polymorphism
3. **Improves Maintainability**: Makes code more predictable and easier to extend
4. **Enhances Reusability**: Allows for better code organization and module reuse

## When to Apply LSP?
1. When designing inheritance hierarchies
2. When implementing interfaces
3. When working with polymorphic behavior
4. When creating frameworks or libraries
5. When designing public APIs

## How to Implement LSP?
### Key Rules:
1. **Preconditions**: Cannot be strengthened in a subtype
2. **Postconditions**: Cannot be weakened in a subtype
3. **Invariants**: Must be preserved in a subtype
4. **History Constraint**: Subtypes shouldn't allow state changes that base types didn't allow

### Implementation Steps:
1. Identify the base class/interface contract
2. Ensure all subclasses can fulfill this contract
3. Test substitutability
4. Use composition over inheritance when appropriate
5. Apply interface segregation when needed

## Framework for LSP Decision Making

### 1. Contract Analysis
- What are the expectations from the base type?
- What behaviors must be preserved?
- What states are valid?

### 2. Substitutability Check
Ask these questions for each subclass:
- Can it replace the parent without breaking functionality?
- Does it throw unexpected exceptions?
- Does it add extra requirements?
- Does it violate any parent class invariants?

### 3. Design Decision Matrix

| Aspect | Questions to Ask | Red Flags |
|--------|-----------------|------------|
| Behavior | Does the subclass behave like its parent? | Throwing unexpected exceptions |
| Contract | Can the subclass fulfill all parent promises? | Need to weaken preconditions |
| State | Does state management align with parent? | Invalid state transitions |
| Methods | Are all inherited methods meaningful? | Unused or inappropriate methods |

### 4. Common Violation Patterns
1. **Type Checking Anti-pattern**
   ```java
   if (animal instanceof Dog) {
       // Special handling
   }
   ```
2. **Empty Method Anti-pattern**
   ```java
   public void someMethod() {
       // Do nothing or throw exception
   }
   ```
3. **State Violation Anti-pattern**
   ```java
   class Square extends Rectangle {
       setWidth() { /* changes both width and height */ }
   }
   ```

## Best Practices
1. Use abstract classes and interfaces wisely
2. Keep inheritance hierarchies shallow
3. Design for extension
4. Program to interfaces
5. Use composition over inheritance when in doubt
6. Apply Tell, Don't Ask principle
7. Follow Interface Segregation Principle

## Warning Signs (When LSP Might be Violated)
1. Empty method implementations
2. Throwing UnsupportedOperationException
3. Type checking in polymorphic methods
4. Overridden methods that weaken contracts
5. Subclasses that need to know about their clients

## Real-World Examples
1. Collection Framework Hierarchies
2. File System Abstractions
3. Payment Processing Systems
4. UI Component Hierarchies
5. Database Access Layers

## Testing for LSP Compliance
1. Write tests using base class references
2. Test all subclasses with the same test suite
3. Verify behavior consistency
4. Check exception handling
5. Validate state transitions

We have a complete set of examples that demonstrate:

What not to do (LSPViolations.java)
How to fix common violations (LSPFixedExamples.java)
A proper LSP-compliant implementation (Vehicle.java)
A comprehensive guide (LSP_Guide.md)