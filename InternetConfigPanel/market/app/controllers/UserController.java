package controllers;

import play.*;
import play.mvc.*;
import play.cache.*;
import java.util.*;

import models.*;

public class UserController extends Controller {

    public static void welcome() {
		User user = Cache.get(session.getId() + "-users", User.class);
        render("UserService/welcome.html", user);
    }
	
}