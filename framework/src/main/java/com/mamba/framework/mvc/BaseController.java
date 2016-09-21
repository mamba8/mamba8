package com.mamba.framework.mvc;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mamba.framework.util.SimpleObjUtil;

@Controller
@ResponseBody
public abstract class BaseController<T extends Id> {
	public abstract BaseService<T> getBaseAdminService();

	private static final SimpleObjUtil check = new SimpleObjUtil();
	/**
	 * 获取所有资源，支持分
	 * 
	 * @param paging
	 * @param example
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Permission("#get")
	public RestfulResult<T> all(Paging paging, T example) {
		List<T> list = this.getBaseAdminService().findByExample(paging, example);
		// 第一次访问才需要获取total, 后续访问total由前台带上来
		if (paging.isNeedSetTotal()) {
			paging.setTotal(this.getBaseAdminService().countByExample(example).intValue());
		}
		return new RestfulPagingResult<>(list, paging.getTotal());
	}

	/**
	 * 根据id获取某个资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
	@Permission("/{id}#get")
	public RestfulResult<T> getResourceById(@PathVariable Long id) {
		T entity = this.getBaseAdminService().find(id);

		return new RestfulResult<>(entity);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Permission("#post")
	public RestfulResult<T> post(T entity) {
		this.getBaseAdminService().persist(entity);

		return new RestfulResult<>(entity);
	}

	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
	@Permission("/{id}#put")
	public RestfulResult<T> put(@PathVariable Long id, T entity) {
		this.getBaseAdminService().merge(entity);

		return new RestfulResult<>(entity);
	}

	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
	@Permission("/{id}#delete")
	public RestfulResult<T> delete(@PathVariable Long id) {
		this.getBaseAdminService().remove(id);

		return new RestfulResult<>();
	}
}
