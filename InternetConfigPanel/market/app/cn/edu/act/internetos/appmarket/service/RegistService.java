package cn.edu.act.internetos.appmarket.service;

import java.util.*;
import models.*;

public class RegistService{

	public static void regist(String account, String password, String name) throws Exception{
		User user = new User(account, password, name);	
		UserDao userdao = new UserDao();	
		userdao.save(user);

		UserSpace userspace = new UserSpace(user.getId());
		UserSpaceDao userspacedao = new UserSpaceDao();
		userspacedao.save(userspace);
	}
   
}