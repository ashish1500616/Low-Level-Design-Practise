# Chapter 5: Advanced LSP Concepts - Going Deeper

## Type Parameters and LSP: The Generics Story

When working with generics, LSP becomes even more interesting. Let's explore through examples.

```java
// 1. Covariance and Contravariance
interface AnimalShelter<T extends Animal> {
    void accept(T animal);              // Contravariant position
    T release();                        // Covariant position
    boolean hasSpace();
}

// 2. Generic Bounds and LSP
class CatShelter implements AnimalShelter<Cat> {
    private List<Cat> cats = new ArrayList<>();
    
    @Override
    public void accept(Cat cat) {
        cats.add(cat);
    }
    
    @Override
    public Cat release() {
        if (cats.isEmpty()) return null;
        return cats.remove(0);
    }
    
    @Override
    public boolean hasSpace() {
        return cats.size() < 100;
    }
}

// 3. The Collection Hierarchy Example
interface ReadOnlyCollection<T> {
    boolean contains(T item);
    int size();
    Iterator<T> iterator();
}

interface Collection<T> extends ReadOnlyCollection<T> {
    boolean add(T item);
    boolean remove(T item);
}

// 4. Advanced Variance Example
class ImmutableList<T> implements ReadOnlyCollection<T> {
    private final List<T> items;
    
    public ImmutableList(List<T> items) {
        this.items = new ArrayList<>(items);
    }
    
    @Override
    public boolean contains(T item) {
        return items.contains(item);
    }
    
    @Override
    public int size() {
        return items.size();
    }
    
    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(items).iterator();
    }
}

// 5. Protocol Inheritance
interface DataSource<T> {
    void connect();
    void disconnect();
    T fetch();
}

interface TransactionalDataSource<T> extends DataSource<T> {
    void beginTransaction();
    void commit();
    void rollback();
}

// 6. Advanced Contract Example
class Result<T, E extends Exception> {
    private final T value;
    private final E error;
    
    private Result(T value, E error) {
        this.value = value;
        this.error = error;
    }
    
    public static <T, E extends Exception> Result<T, E> success(T value) {
        return new Result<>(value, null);
    }
    
    public static <T, E extends Exception> Result<T, E> error(E error) {
        return new Result<>(null, error);
    }
    
    public <R> Result<R, E> map(Function<T, R> mapper) {
        if (error != null) return Result.error(error);
        return Result.success(mapper.apply(value));
    }
}

// 7. Practical Application
class DatabaseOperation<T> {
    private final TransactionalDataSource<T> dataSource;
    
    public DatabaseOperation(TransactionalDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }
    
    public Result<T, DatabaseException> execute() {
        try {
            dataSource.beginTransaction();
            T result = dataSource.fetch();
            dataSource.commit();
            return Result.success(result);
        } catch (DatabaseException e) {
            dataSource.rollback();
            return Result.error(e);
        }
    }
}

// 8. Type-Safe Builder Pattern with LSP
interface Builder<T> {
    T build();
}

abstract class AbstractBuilder<T, SELF extends AbstractBuilder<T, SELF>> 
        implements Builder<T> {
    
    @SuppressWarnings("unchecked")
    protected final SELF self() {
        return (SELF) this;
    }
}

class QueryBuilder extends AbstractBuilder<Query, QueryBuilder> {
    private String select;
    private String from;
    private String where;
    
    public QueryBuilder select(String select) {
        this.select = select;
        return self();
    }
    
    public QueryBuilder from(String from) {
        this.from = from;
        return self();
    }
    
    public QueryBuilder where(String where) {
        this.where = where;
        return self();
    }
    
    @Override
    public Query build() {
        return new Query(select, from, where);
    }
}

// Usage Examples
class AdvancedLSPDemo {
    public void demonstrateVariance() {
        // Covariance example
        List<Cat> cats = new ArrayList<>();
        List<? extends Animal> animals = cats; // OK - covariant
        
        // Contravariance example
        Comparator<Animal> animalComparator = (a1, a2) -> 0;
        Comparator<? super Cat> catComparator = animalComparator; // OK - contravariant
        
        // Immutable collection example
        List<String> mutableList = new ArrayList<>();
        ReadOnlyCollection<String> immutableView = new ImmutableList<>(mutableList);
        
        // Builder pattern example
        Query query = new QueryBuilder()
            .select("*")
            .from("users")
            .where("age > 18")
            .build();
    }
}

// 9. Testing Advanced LSP Compliance
class AdvancedLSPTest {
    @Test
    void testGenericConstraints() {
        // Test that CatShelter works wherever AnimalShelter<Cat> is expected
        AnimalShelter<Cat> shelter = new CatShelter();
        assert shelter.hasSpace();
        
        Cat cat = new Cat();
        shelter.accept(cat);
        assert shelter.release() == cat;
    }
    
    @Test
    void testImmutabilityContract() {
        List<String> original = Arrays.asList("a", "b", "c");
        ReadOnlyCollection<String> immutable = new ImmutableList<>(original);
        
        // Should not affect immutable view
        original.add("d");
        assert immutable.size() == 3;
    }
}
```

## Key Advanced Concepts

1. **Variance in Generic Types**
   - Covariance: `? extends T`
   - Contravariance: `? super T`
   - Invariance: exact type match

2. **Type Parameter Bounds**
   - Upper bounds: `<T extends Animal>`
   - Multiple bounds: `<T extends Animal & Serializable>`
   - Recursive type bounds: `<T extends Comparable<T>>`

3. **Protocol Inheritance**
   - Interface hierarchies
   - Contract extensions
   - Capability inheritance

4. **Advanced Design Patterns with LSP**
   - Builder pattern with type safety
   - Result types for error handling
   - Immutable views of mutable data

## Common Advanced LSP Violations to Avoid

1. Breaking variance expectations
2. Violating immutability contracts
3. Incorrect generic bounds
4. Protocol inconsistencies

## Best Practices for Advanced LSP

1. Use bounded wildcards appropriately
2. Design immutable interfaces carefully
3. Consider generic type parameter implications
4. Test substitutability with generic types

## Exercise: Advanced LSP Challenge

Implement a generic event handling system that:
1. Supports different event types
2. Maintains type safety
3. Allows for both synchronous and asynchronous handlers
4. Preserves LSP throughout the hierarchy

## Coming Up in Chapter 6...
We'll explore testing strategies specifically designed to catch LSP violations in complex systems!