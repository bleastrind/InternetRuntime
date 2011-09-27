package controllers;

import play.*;
import play.mvc.*;
import play.cache.*;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void login() {
        render();
    }

    public static void getAccount(String account, String password) {
        if (account.equals("admin") && password.equals("admin"))
            AdminController.welcome();
	    else
	    {        
            UserDao userdao = new UserDao();
            User user = userdao.findByAccount(account);
			Cache.set(session.getId() + "-users", user, "30mn");
            UserController.welcome();
        }
    }
}