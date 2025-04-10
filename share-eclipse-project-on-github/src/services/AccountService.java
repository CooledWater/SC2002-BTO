package services;

import entity.User;

public class AccountService {
    private User user;

    public AccountService(User user) {
        this.user = user;
    }

    public boolean checkPassword(String inputPassword) {
        return user.getPassword().equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        user.changePassword(newPassword);
        System.out.println("Password changed successfully.");
    }
}

