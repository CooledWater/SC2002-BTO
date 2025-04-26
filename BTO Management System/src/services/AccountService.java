package services;

import entity.User;
import menu.LoginMenu;
import java.util.Scanner;

public class AccountService {
    public boolean checkPassword(User user, String inputPassword) {
        return user.getPassword().equals(inputPassword);
    }

    public void changePassword(User user, String newPassword, LoginMenu loginMenu, Scanner sc) {
        user.setPassword(newPassword);
        System.out.println("Password changed successfully.");
        
        System.out.println("Please re-login to continue.");
        User reLoggedInUser = loginMenu.reLogin(sc, user);

        if (reLoggedInUser != null && reLoggedInUser.getNRIC().equals(user.getNRIC())) {
            System.out.println("Re-login successful.");
        } else {
            System.out.println("Re-login failed or different user.");
        }
    }
}

