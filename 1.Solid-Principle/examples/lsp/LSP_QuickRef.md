# LSP Quick Reference Card

## Common Violations and Solutions

| Violation Pattern | Example | Solution | Key Principle |
|------------------|---------|----------|---------------|
| Method Throwing Unexpected Exception | `class Ostrich extends Bird { void fly() { throw new UnsupportedOperationException(); } }` | Use interface segregation: `interface Flyable` | Don't promise what you can't deliver |
| Strengthening Preconditions | `class SecuredFile extends File { @Override void read() { if(!authenticated) throw...; } }` | Use composition and explicit interfaces | Keep contracts consistent |
| Weakening Postconditions | `class LazyCache extends Cache { @Override T get() { return null; } }` | Use Optional or explicit contract interfaces | Maintain guarantees |
| Breaking Invariants | `class Square extends Rectangle { setWidth() { /* changes height too */ } }` | Use composition or separate interfaces | Preserve behavior expectations |
| Type Checking | `if (animal instanceof Dog) { ... }` | Use polymorphism and interface segregation | Design for behavior, not types |

## Quick Decision Guide

1. Should I use inheritance? Ask:
   - Can the subclass fully substitute the base class?
   - Are all base class methods meaningful?
   - Can invariants be preserved?
   → If NO to any, use composition instead

2. Is my interface too big? Ask:
   - Do some implementations throw UnsupportedOperationException?
   - Are there groups of related methods?
   - Do clients use only parts of the interface?
   → If YES to any, split the interface

3. Found a violation? Quick fixes:
   - Unexpected Exceptions → Split interface
   - State Violations → Use composition
   - Precondition Issues → Create explicit contract
   - Postcondition Issues → Use Optional/Result types

## Code Smells Checklist

❌ instanceof checks in business logic
❌ Empty method implementations
❌ UnsupportedOperationException
❌ Subclass knowing about clients
❌ Overridden methods changing invariants

✅ Interface segregation
✅ Composition over inheritance
✅ Explicit contracts
✅ Behavior-based design
✅ Clear error handling

## Testing Checklist

- [ ] Base class contract tests
- [ ] State transition tests
- [ ] Resource management tests
- [ ] Exception handling tests
- [ ] Performance contract tests
- [ ] Invariant preservation tests

## Emergency Fixes

1. **Quick Fix for Unexpected Exceptions**:
```java
// Before
class Bird { void fly() }
class Ostrich extends Bird { void fly() { throw new Error(); } }

// After
interface Flyable { void fly(); }
class Bird { void move(); }
class Ostrich extends Bird { }
class Duck extends Bird implements Flyable { }
```

2. **Quick Fix for Precondition Violation**:
```java
// Before
class File { void save() }
class ReadOnlyFile extends File { void save() { throw new Error(); } }

// After
interface FileOperations { boolean canSave(); void save(); }
class ReadOnlyFile implements FileOperations { boolean canSave() { return false; } }
```

3. **Quick Fix for State Violation**:
```java
// Before
class Rectangle { setWidth(w) { this.w = w; } }
class Square extends Rectangle { setWidth(w) { this.w = w; this.h = w; } }

// After
interface Shape { double getArea(); }
class Rectangle implements Shape { }
class Square implements Shape { }
```

## Remember
- LSP is about behavior, not structure
- If in doubt, use composition
- Make contracts explicit
- Test at the base class level
- Design for substitutability