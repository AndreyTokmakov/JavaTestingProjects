public class UserService {

    MailService mailService;

    public UserService(MailService mailService) {
        this.mailService = mailService;
    }

    public boolean register(UserDto userDto) {
        System.out.println("UserService::register() entered");
        boolean welcomeEmailSuccess = mailService.sendWelcomeEmail(userDto);
        // some other stuff here
        return welcomeEmailSuccess;
    }
}