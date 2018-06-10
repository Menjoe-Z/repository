package com.plzt.onenet.main.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.plzt.onenet.main.commmon.Constant;
import com.plzt.onenet.main.entity.User;

@Controller
public class SystemController {
	
	@Autowired
    private MongoTemplate mongoTemplate; 
	
	@RequestMapping("/")
	public String root() {
		return "index";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/main/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.USER_SESSION);
		return "redirect:/";
	}
	
	@RequestMapping("/login")
	public String login(
			@RequestParam(value = "username", required = true)String username,
			@RequestParam(value = "password", required = true)String password,
			Map<String, String> template,
			HttpSession session
		) {
		Query query = Query.query(Criteria.where("username").is(username).and("password").is(DigestUtils.md5Hex(password).toLowerCase()));
		User user = mongoTemplate.findOne(query, User.class);
		if (user == null) {
			template.put("error_msg", "登录失败，请检查账户和密码!");
			return "index";
		}
		session.setAttribute(Constant.USER_SESSION, user);
		return "redirect:/main/index";
	}
}
