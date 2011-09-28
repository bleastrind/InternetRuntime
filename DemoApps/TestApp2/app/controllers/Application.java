package controllers;

import play.*;
import play.mvc.*;
import play.cache.*;
import java.util.*;
import java.net.*;
import java.io.*;

import models.*;

public class Application extends Controller {

    public static void index(String userId, String appId, String requestUrl) {
        render(userId, appId, requestUrl);
    }
}