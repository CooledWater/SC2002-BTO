package services;

import user.User;

public class accountService {
    private User user;

    public accountService(User user) {
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

