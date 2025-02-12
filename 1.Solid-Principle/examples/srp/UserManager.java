// Bad Example - Violates SRP
class UserManager {
    public void registerUser(User user) {
        // This class violates SRP by handling multiple responsibilities:
        // 1. Database operations
        // 2. Email sending
        // 3. Validation
        // 4. Logging
        // Each of these should be separate classes
    }
}

// Good Example - Follows SRP
class UserValidator {
    public boolean validate(User user) {
        // Validation rules
        return user != null 
            && user.getEmail() != null 
            && user.getPassword() != null;
    }
}

class UserRepository {
    public void save(User user) {
        try {
            // Database specific operations
            // Connection conn = DatabaseConnection.getConnection();
            // Save user to database
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }
}

class EmailService {
    public void sendWelcomeEmail(User user) {
        // Email configuration and sending
        String subject = "Welcome to our platform";
        String body = "Dear " + user.getName() + ",\nWelcome aboard!";
        // Send email logic
    }
}

class Logger {
    public void log(String message) {
        System.out.println("[" + LocalDateTime.now() + "] " + message);
    }
}

class UserService {
    private final UserValidator validator;
    private final UserRepository repository;
    private final EmailService emailService;
    private final Logger logger;

    public UserService(UserValidator validator, UserRepository repository,
                      EmailService emailService, Logger logger) {
        this.validator = validator;
        this.repository = repository;
        this.emailService = emailService;
        this.logger = logger;
    }

    public void registerUser(User user) {
        if (!validator.validate(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }

        repository.save(user);
        emailService.sendWelcomeEmail(user);
        logger.log("User registered: " + user.getEmail());
    }
}
