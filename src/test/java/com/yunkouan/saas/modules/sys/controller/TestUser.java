package com.yunkouan.saas.modules.sys.controller;

import org.junit.Test;

import java.util.Date;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;

import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.DaoException;
import com.yunkouan.exception.ServiceException;
import com.yunkouan.saas.common.BaseJunitTest;
import com.yunkouan.saas.common.util.IdUtil;
import com.yunkouan.saas.modules.sys.controller.UserController;
import com.yunkouan.saas.modules.sys.entity.SysUser;
import com.yunkouan.saas.modules.sys.vo.UserVo;

/**
 * @author tphe06 2017年2月10日
 */
public class TestUser extends BaseJunitTest {
	@Autowired
	private UserController c;

	@Test
//	@Ignore
	public void testList() throws DaoException, ServiceException {
		SysUser obj = new SysUser();
		obj.setCreateTime(new Date());
		UserVo vo = new UserVo(obj);
		ResultModel r = c.list(vo, new BeanPropertyBindingResult(vo, UserVo.class.getName()));
		System.out.println(r.getList().size());
	}

	@Test
	@Ignore
	public void testView() throws ServiceException, DaoException {
		ResultModel r = c.view(new UserVo(new SysUser().setUserId("384D873241D341B282253346CA481DCE")));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testAdd() throws ServiceException, DaoException {
		SysUser obj = new SysUser();
		obj.setUserNo(IdUtil.getUUID());
		obj.setUserName("test");
		obj.setCreatePerson("tphe06");
		obj.setUpdatePerson("tphe06");
		obj.setPhone("13642668655");
		obj.setEmail("tphe06@qq.com");
		obj.setCreateTime(new Date());
		UserVo vo = new UserVo(obj);
		ResultModel r = c.add(vo, new BeanPropertyBindingResult(vo, UserVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testUpdate() throws ServiceException, DaoException {
		SysUser obj = new SysUser();
		obj.setUserId("384D873241D341B282253346CA481DCE");
		obj.setUserStatus(20);
		obj.setCreateTime(new Date());
		UserVo vo = new UserVo(obj);
		ResultModel r = c.update(vo, new BeanPropertyBindingResult(vo, UserVo.class.getName()));
		System.out.println(r.getObj());
	}
}