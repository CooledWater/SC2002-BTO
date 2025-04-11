package services;

import entity.User;

public class AccountService {
    public boolean checkPassword(User user, String inputPassword) {
        return user.getPassword().equals(inputPassword);
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        System.out.println("Password changed successfully.");
    }
}

