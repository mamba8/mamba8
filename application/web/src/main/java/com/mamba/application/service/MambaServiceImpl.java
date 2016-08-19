package com.mamba.application.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.mamba.application.entity.Mamba;
import com.mamba.framework.mvc.BaseDao;
import com.mamba.framework.mvc.BaseService;

@Named
public class MambaServiceImpl extends BaseService<Mamba> {

	@Inject
	private BaseDao<Mamba> mambaDao;

	@Override
	public BaseDao<Mamba> getDao() {
		// TODO Auto-generated method stub
		return mambaDao;
	}

}
