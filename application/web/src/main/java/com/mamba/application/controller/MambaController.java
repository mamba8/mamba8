package com.mamba.application.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mamba.application.service.MambaServiceImpl;
import com.mamba.framework.mvc.RestfulResult;

@RestController
@RequestMapping("/api/mamba")
public class MambaController {

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
