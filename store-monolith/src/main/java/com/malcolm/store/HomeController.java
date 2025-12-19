package com.malcolm.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {
	
	@GetMapping("path")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
	@SuppressWarnings("static-method")
	@RequestMapping("/")
	public String index() {
		String viewName = getViewName();
		return viewName;
	}

	private String getViewName() {
		return "index.html";
	}
}
