package com.yunkouan.saas.modules.sys.controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;

import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.DaoException;
import com.yunkouan.exception.ServiceException;
import com.yunkouan.saas.common.BaseJunitTest;
import com.yunkouan.saas.common.util.IdUtil;
import com.yunkouan.saas.modules.sys.controller.OrgController;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.entity.SysOrg;
import com.yunkouan.saas.modules.sys.vo.OrgVo;

/**
 * @author tphe06 2017年2月10日
 */
public class TestOrg extends BaseJunitTest {
	@Autowired
	private OrgController c;

	@Test
	@Ignore
	public void testList() throws DaoException, ServiceException {
		SysOrg obj = new SysOrg();
		OrgVo vo = new OrgVo(obj);
		ResultModel r = c.list(vo, new BeanPropertyBindingResult(vo, OrgVo.class.getName()));
		System.out.println(r.getList().size());
	}

	@Test
	@Ignore
	public void testView() throws ServiceException, DaoException {
		ResultModel r = c.view(new OrgVo(new SysOrg().setOrgId("62E18D09DC2F4FC6A717F1E9503B40D3")));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testAdd() throws DaoException, ServiceException {
		SysOrg obj = new SysOrg();
		obj.setOrgNo(IdUtil.getUUID());
		obj.setOrgName("test");
		obj.setOrgShortName("test");
		obj.setBank("61000000");
		obj.setCreatePerson("tphe06");
		obj.setUpdatePerson("tphe06");
		OrgVo vo = new OrgVo(obj);
		List<SysAuth> list = new ArrayList<SysAuth>();
		for(int i=0; i<1; ++i) {
			SysAuth e = new SysAuth();
			e.setAuthId("942DF0F95F924B00BB9DC2DF384C77A0");
			list.add(e);
		}
		vo.setList(list);
		ResultModel r = c.add(vo, new BeanPropertyBindingResult(vo, OrgVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
//	@Ignore
	public void testUpdate() throws DaoException, ServiceException {
		SysOrg obj = new SysOrg();
		obj.setOrgId("0FDF52B8EC5E4E04BE10510F4DD3327E");
		obj.setOrgNo("11111111");
		obj.setOrgName("顺丰快递1111");
		OrgVo vo = new OrgVo(obj);
		List<SysAuth> list = new ArrayList<SysAuth>();
		for(int i=0; i<1; ++i) {
			SysAuth auth = new SysAuth();
			auth.setAuthId("15C8112A93EA4FA1BD052E22478E154E");
			list.add(auth);
		}
		vo.setList(list);
		ResultModel r = c.update(vo, new BeanPropertyBindingResult(vo, OrgVo.class.getName()));
		System.out.println(r.getObj());
	}
}