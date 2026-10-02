package com.ptms.app;

import com.ptms.app.controller.LoginController;
import com.ptms.app.controller.RoleController;
import com.ptms.app.model.User;

public class Main {

    private static final LoginController loginController =
            new LoginController();

    private static final RoleController roleController =
            new RoleController();

    public static void main(String[] args) {

        User loggedInUser = loginController.login();

        if (loggedInUser == null) {
            return;
        }

        roleController.showMenu(loggedInUser);
    }
}