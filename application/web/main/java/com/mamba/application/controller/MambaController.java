package com.mamba.application.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jztey.framework.mvc.BaseController;
import com.jztey.framework.mvc.RestfulResult;
import com.mamba.application.service.MambaServiceImpl;

@RestController
@RequestMapping("/api/mamba")
public class MambaController extends BaseController<Mamba> {

	@Inject
	private MambaServiceImpl mambaService;

	@RequestMapping(value = "/helloWord", method = RequestMethod.GET)
	public RestfulResult<Object> helloWord() {
		RestfulResult<Object> response = new RestfulResult<>();

		response.setMessage("i'm coming~~");
		response.setCode(10200);
		response.setSuccess(true);

		return response;

	}
}
