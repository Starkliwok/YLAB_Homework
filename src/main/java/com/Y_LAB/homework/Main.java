package com.Y_LAB.homework;

import com.Y_LAB.homework.in.user_panel.HomePanel;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import com.Y_LAB.homework.util.init.LiquibaseConfig;

public class Main {
    public static void main(String[] args) {
        LiquibaseConfig.initMigration(ConnectionToDatabase.getConnection());
        HomePanel.printStartPage();
    }
}