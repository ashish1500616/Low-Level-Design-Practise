# Single Responsibility Principle (SRP)

## What is SRP?
A class should have only one reason to change, meaning it should have only one job or responsibility.

## Why use SRP?
- Easier maintenance
- Better testability
- Reduced complexity
- Improved code reusability
- Less coupling between components
- Better code organization

## When to use SRP?
Use SRP when:
1. A class handles multiple unrelated tasks
2. Changes in one functionality force changes in unrelated code
3. The class is growing too large and complex
4. You find it difficult to test the class
5. You notice that changes in one part affect other parts unexpectedly

## How to implement SRP?
1. Identify different responsibilities in your class
2. Extract each responsibility into its own class
3. Create focused interfaces
4. Use composition to combine functionalities
5. Ensure each class has a single, well-defined purpose

## Framework for Decision Making
Ask these questions when designing a class:
1. What is the primary responsibility of this class?
2. Does this method belong to the core responsibility?
3. If requirements change, would multiple parts of the class need to change?
4. Can you explain the class's purpose in a single sentence?
5. Are all methods using the same class-level data?
