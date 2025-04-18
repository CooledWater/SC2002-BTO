package menu;
import java.util.*;
import entity.*;
import services.*;

public interface UserMainMenu {
	public final static AccountService accountService = new AccountService();
	
	public void viewProfile();
	
    public default void changePassword(Scanner sc, User user) {
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        accountService.changePassword(user, newPassword);
    }
}
