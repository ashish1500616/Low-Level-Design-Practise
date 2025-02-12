// A comprehensive example demonstrating LSP with vehicles

// Core interfaces defining capabilities
interface Movable {
    void move();
    int getSpeed();
    void setSpeed(int speed);
}

interface EngineOperable {
    void startEngine();
    void stopEngine();
    boolean isEngineRunning();
}

// Abstract base class for all vehicles
abstract class Vehicle implements Movable {
    protected int speed;
    
    @Override
    public int getSpeed() {
        return speed;
    }
    
    @Override
    public void setSpeed(int speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative");
        }
        this.speed = speed;
    }
}

// Base class for all engine-powered vehicles
abstract class MotorizedVehicle extends Vehicle implements EngineOperable {
    protected boolean engineRunning;
    
    @Override
    public void startEngine() {
        engineRunning = true;
    }
    
    @Override
    public void stopEngine() {
        engineRunning = false;
        setSpeed(0);
    }
    
    @Override
    public boolean isEngineRunning() {
        return engineRunning;
    }
    
    @Override
    public void move() {
        if (!engineRunning) {
            throw new IllegalStateException("Cannot move: Engine is not running");
        }
        // Implementation for movement
    }
}

// Specific vehicle implementations
class Car extends MotorizedVehicle {
    @Override
    public void move() {
        super.move();
        setSpeed(getSpeed() + 10);
    }
}

class ElectricCar extends MotorizedVehicle {
    private int batteryLevel;
    
    @Override
    public void startEngine() {
        if (batteryLevel > 0) {
            super.startEngine();
        } else {
            throw new IllegalStateException("Cannot start: Battery depleted");
        }
    }
    
    @Override
    public void move() {
        super.move();
        batteryLevel--;
    }
}

// Non-motorized vehicles don't implement EngineOperable
class Bicycle extends Vehicle {
    @Override
    public void move() {
        // Bicycles can move without an engine
        setSpeed(getSpeed() + 5);
    }
}

class Skateboard extends Vehicle {
    @Override
    public void move() {
        // Skateboards can move without an engine
        setSpeed(getSpeed() + 3);
    }
}

// Example usage demonstrating LSP
class TransportationSystem {
    public static void testVehicleMovement(Vehicle vehicle) {
        // This method works with ANY vehicle type
        vehicle.move();
        System.out.println("Vehicle is moving at speed: " + vehicle.getSpeed());
    }
    
    public static void testEngineVehicle(MotorizedVehicle vehicle) {
        // This method works with ANY motorized vehicle
        vehicle.startEngine();
        vehicle.move();
        vehicle.stopEngine();
    }
    
    public static void main(String[] args) {
        // All these calls are valid and maintain LSP
        Vehicle bicycle = new Bicycle();
        Vehicle car = new Car();
        MotorizedVehicle electricCar = new ElectricCar();
        
        // Works with any Vehicle
        testVehicleMovement(bicycle);
        testVehicleMovement(car);
        testVehicleMovement(electricCar);
        
        // Works with any MotorizedVehicle
        testEngineVehicle((MotorizedVehicle)car);
        testEngineVehicle(electricCar);
        
        // This would cause a compilation error - which is good!
        // testEngineVehicle(bicycle); // Bicycle is not a MotorizedVehicle
    }
}
