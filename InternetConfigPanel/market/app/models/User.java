package models;
/*
 * User is a class of the User and for UserDao;
 */
import java.io.*;
import java.util.UUID;
public class User implements Serializable{
	//Fields
	
	private String id;
	private String account;
	private String password;
	private String name;

	
	//Constructors
	public User(String id, String account, String password, String name){
		this.id = id;
		this.account = account;
		this.password = password;
		this.name = name;
	}
	public User(String account, String password, String name){
		this.id = "accesstoken:" + account;
		this.account = account;
		this.password = password;
		this.name = name;
	}
	
	//Property Accessors
	public String getId(){
		return this.id;
	}
	public String getAccount(){
		return this.account;
	}
	public void setAccount(String account){
		this.account = account;
	}
	
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
}
