package com.Y_LAB.homework;

import com.Y_LAB.homework.in.user_panel.HomePanel;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.roles.User;


public class Main {
    public static void main(String[] args) {
        User admin = new Admin("root", "root");
        User.addUserToUserSet(admin);
        HomePanel.printStartPage();
    }
}