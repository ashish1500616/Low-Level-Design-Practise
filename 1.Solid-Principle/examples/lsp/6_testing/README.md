# Chapter 6: Testing LSP Compliance - Trust but Verify

## The Art of Testing LSP

Testing LSP compliance is more than just unit testing - it requires carefully designed test suites that verify substitutability and contract adherence.

```java
// 1. Base Test Suite Pattern
abstract class ContractTest<T extends Playable> {
    protected abstract T createInstance();
    
    @Test
    void shouldHandlePlayPauseSequence() {
        T subject = createInstance();
        subject.play();
        assertEquals(PlaybackState.PLAYING, subject.getState());
        subject.pause();
        assertEquals(PlaybackState.PAUSED, subject.getState());
    }
    
    @Test
    void shouldHandleStopState() {
        T subject = createInstance();
        subject.stop();
        assertEquals(PlaybackState.STOPPED, subject.getState());
    }
}

// 2. Concrete Test Classes
class AudioPlayerTest extends ContractTest<AudioPlayer> {
    @Override
    protected AudioPlayer createInstance() {
        return new AudioPlayer();
    }
    
    // Additional audio-specific tests
}

class VideoPlayerTest extends ContractTest<VideoPlayer> {
    @Override
    protected VideoPlayer createInstance() {
        return new VideoPlayer(mock(VideoRenderer.class));
    }
    
    // Additional video-specific tests
}

// 3. Property-Based Testing
class PropertyBasedTests {
    @Property
    void speedControlShouldNeverBeNegative(
            @ForAll @Double(min = "-100.0", max = "100.0") double speed) {
        SpeedControllable player = new AudioPlayer();
        
        assumeTrue(player.supportsVariableSpeed());
        
        if (speed <= 0) {
            assertThrows(IllegalArgumentException.class, 
                () -> player.setPlaybackSpeed(speed));
        } else {
            player.setPlaybackSpeed(speed);
            assertTrue(player.getPlaybackSpeed() > 0);
        }
    }
}

// 4. Contract Testing Helpers
class ContractAssertions {
    static void assertInvariantsPreserved(Shape shape) {
        double originalArea = shape.getArea();
        // Perform operations
        shape.transform(Transform.IDENTITY);
        assertEquals(originalArea, shape.getArea(), 
            "Area should be preserved under identity transform");
    }
    
    static void assertSubstitutable(Collection<String> collection) {
        int originalSize = collection.size();
        collection.add("test");
        assertTrue(collection.size() > originalSize, 
            "Adding an element should increase size");
    }
}

// 5. State Machine Testing
class StateMachineTester {
    @Test
    void testAllValidStateTransitions() {
        Playable player = new AudioPlayer();
        
        // Define valid state transitions
        Map<PlaybackState, Set<PlaybackState>> validTransitions = Map.of(
            PlaybackState.STOPPED, Set.of(PlaybackState.PLAYING),
            PlaybackState.PLAYING, Set.of(PlaybackState.PAUSED, PlaybackState.STOPPED),
            PlaybackState.PAUSED, Set.of(PlaybackState.PLAYING, PlaybackState.STOPPED)
        );
        
        // Test each transition
        for (Map.Entry<PlaybackState, Set<PlaybackState>> entry : 
                validTransitions.entrySet()) {
            PlaybackState fromState = entry.getKey();
            for (PlaybackState toState : entry.getValue()) {
                assertTrue(canTransition(player, fromState, toState));
            }
        }
    }
    
    private boolean canTransition(Playable player, 
            PlaybackState from, PlaybackState to) {
        // Implementation of state transition testing
        return true;
    }
}

// 6. Performance Contract Testing
class PerformanceContractTest {
    @Test
    void collectionOperationsShouldMaintainTimeComplexity() {
        List<Collection<Integer>> implementations = Arrays.asList(
            new ArrayList<>(),
            new LinkedList<>(),
            new HashSet<>()
        );
        
        for (Collection<Integer> impl : implementations) {
            assertTimeComplexity(
                () -> impl.add(42),
                ComplexityBound.O_1, // Should be O(1)
                "Add operation"
            );
            
            assertTimeComplexity(
                () -> impl.contains(42),
                ComplexityBound.O_N, // Should be O(n) or better
                "Contains operation"
            );
        }
    }
    
    private void assertTimeComplexity(Runnable operation, 
            ComplexityBound expected, String description) {
        // Implementation of complexity testing
    }
}

// 7. Exception Contract Testing
class ExceptionContractTest {
    @Test
    void shouldPreserveExceptionHierarchy() {
        List<FileReader> readers = Arrays.asList(
            new BasicFileReader("test.txt"),
            new BufferedFileReader("test.txt"),
            new SecuredFileReader(new BasicFileReader("test.txt"))
        );
        
        for (FileReader reader : readers) {
            assertThrows(IOException.class, () -> {
                reader.read();
            }, "All readers should throw IOException for missing files");
        }
    }
}

// 8. Resource Management Testing
class ResourceManagementTest {
    @Test
    void shouldReleaseResourcesInCorrectOrder() {
        try (var recorder = new ResourceRecorder()) {
            AutoCloseable resource = createTestResource();
            resource.close();
            
            List<String> events = recorder.getEvents();
            assertResourceCleanup(events);
        }
    }
    
    private void assertResourceCleanup(List<String> events) {
        // Verify cleanup order
        List<String> expectedOrder = Arrays.asList(
            "release_locks",
            "flush_buffers",
            "close_handles"
        );
        
        assertTrue(events.containsAll(expectedOrder));
        // Verify relative ordering is preserved
        assertTrue(isOrderPreserved(events, expectedOrder));
    }
}

// 9. Comprehensive Test Suite
class LSPComplianceTest {
    @Test
    void fullLSPCompliance() {
        // Test behavioral substitution
        testBehavioralSubstitution();
        
        // Test invariant preservation
        testInvariantPreservation();
        
        // Test precondition weakening
        testPreconditions();
        
        // Test postcondition strengthening
        testPostconditions();
    }
    
    private void testBehavioralSubstitution() {
        List<Shape> shapes = Arrays.asList(
            new Rectangle(10, 20),
            new Square(10)
        );
        
        for (Shape shape : shapes) {
            double area = shape.getArea();
            shape.transform(Transform.IDENTITY);
            assertEquals(area, shape.getArea());
        }
    }
    
    private void testInvariantPreservation() {
        // Implementation
    }
    
    private void testPreconditions() {
        // Implementation
    }
    
    private void testPostconditions() {
        // Implementation
    }
}
```

## Key Testing Strategies

1. **Contract Test Suites**
   - Base test classes for common behavior
   - Separate test classes for specific features
   - Property-based testing for invariants

2. **State Testing**
   - Valid state transitions
   - Invalid state prevention
   - State consistency

3. **Performance Contracts**
   - Time complexity verification
   - Resource usage patterns
   - Scalability requirements

4. **Exception Handling**
   - Exception hierarchy preservation
   - Error state recovery
   - Resource cleanup

## Best Practices for LSP Testing

1. Create base test classes for common contracts
2. Use property-based testing for invariants
3. Test state transitions thoroughly
4. Verify resource management
5. Check performance constraints

## Exercise: Design a Test Suite

Create a comprehensive test suite for the multimedia player system from Chapter 4:
1. Implement contract tests for Playable
2. Add property-based tests for speed control
3. Verify state machine transitions
4. Test resource cleanup
5. Validate performance requirements

## Conclusion
Remember: LSP compliance is about trust and verification. Your tests should give you confidence that substituting any subclass will maintain the expected behavior of your system.