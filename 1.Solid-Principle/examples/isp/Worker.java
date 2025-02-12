// Good Example - Follows ISP
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Payable {
    void getPaid();
}

// Classes can implement only what they need
class Human implements Workable, Eatable, Payable {
    public void work() { /* ... */ }
    public void eat() { /* ... */ }
    public void getPaid() { /* ... */ }
}

class Robot implements Workable {
    public void work() { /* ... */ }
    // Robot doesn't need to eat or get paid
}
