// Good Example - Follows DIP
interface MessageService {
    void sendMessage(String message, String recipient);
}

class EmailService implements MessageService {
    @Override
    public void sendMessage(String message, String recipient) {
        // Email-specific implementation
        System.out.println("Sending email to " + recipient + ": " + message);
    }
}

class SMSService implements MessageService {
    @Override
    public void sendMessage(String message, String recipient) {
        // SMS-specific implementation
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}

// High-level module depending on abstraction (MessageService)
class NotificationService {
    private final MessageService messageService;
    
    // Constructor injection - depends on abstraction
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }
    
    public void notify(String recipient, String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        messageService.sendMessage(message, recipient);
    }
}

// Usage example:
class Example {
    public static void main(String[] args) {
        // Can easily switch between email and SMS
        MessageService emailService = new EmailService();
        MessageService smsService = new SMSService();
        
        NotificationService emailNotifier = new NotificationService(emailService);
        NotificationService smsNotifier = new NotificationService(smsService);
        
        emailNotifier.notify("user@example.com", "Hello via email!");
        smsNotifier.notify("+1234567890", "Hello via SMS!");
    }
}