package interfaces;
import entity.*;
import java.util.*;
public interface UserMainMenu {
	
    public default void changePassword(Scanner sc, User user) {
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        accountService.changePassword(user, newPassword);
    }
}
