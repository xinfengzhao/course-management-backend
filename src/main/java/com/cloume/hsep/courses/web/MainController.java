package com.cloume.hsep.courses.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloume.hsep.controller.base.AbstractController;

@Controller
public class MainController extends AbstractController {
	org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint a;
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(){
		return "index";
	}
}
