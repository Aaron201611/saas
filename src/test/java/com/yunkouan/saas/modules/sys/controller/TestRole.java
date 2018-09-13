package com.yunkouan.saas.modules.sys.controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;

import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.DaoException;
import com.yunkouan.exception.ServiceException;
import com.yunkouan.saas.common.BaseJunitTest;
import com.yunkouan.saas.common.util.IdUtil;
import com.yunkouan.saas.modules.sys.controller.AdminRoleController;
import com.yunkouan.saas.modules.sys.entity.SysAdminRole;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.vo.AdminRoleVo;

/**
 * @author tphe06 2017年2月10日
 */
public class TestRole extends BaseJunitTest {
	@Autowired
	private AdminRoleController c;

	@Test
//	@Ignore
	public void testList() throws DaoException, ServiceException {
		SysAdminRole obj = new SysAdminRole();
		obj.setCreateTime(new Date());
		AdminRoleVo vo = new AdminRoleVo(obj);
		ResultModel r = c.list(vo, new BeanPropertyBindingResult(vo, AdminRoleVo.class.getName()));
		System.out.println(r.getList().size());
	}

	@Test
	@Ignore
	public void testView() throws ServiceException, DaoException {
		ResultModel r = c.view(new AdminRoleVo(new SysAdminRole().setRoleId("7F3594A115BB4DE8A7EAE6A62CEE4603")));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testAdd() throws DaoException, ServiceException {
		SysAdminRole obj = new SysAdminRole();
		obj.setRoleNo(IdUtil.getUUID());
		obj.setRoleName("test");
		obj.setCreatePerson("tphe06");
		obj.setUpdatePerson("tphe06");
		obj.setCreateTime(new Date());
		AdminRoleVo vo = new AdminRoleVo(obj);
		List<SysAuth> list = new ArrayList<SysAuth>();
		for(int i=0; i<1; ++i) {
			SysAuth auth = new SysAuth();
			auth.setAuthId("8D0F9E4EB300479EB2482479082EFE41");
			list.add(auth);
//			list.add(new RoleAuthVo().setEntity(auth));
		}
		vo.setList(list);
		ResultModel r = c.add(vo, new BeanPropertyBindingResult(vo, AdminRoleVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testUpdate() throws DaoException, ServiceException {
		SysAdminRole obj = new SysAdminRole();
		obj.setRoleId("01760E74AD714393892310834BD0D32A");
		obj.setRoleStatus(20);
		obj.setCreateTime(new Date());
		AdminRoleVo vo = new AdminRoleVo(obj);
		List<SysAuth> list = new ArrayList<SysAuth>();
		for(int i=0; i<1; ++i) {
			SysAuth auth = new SysAuth();
			auth.setAuthId("3514BB390F4942349154DAB814FD88AF");
			list.add(auth);
			SysAuth auth2 = new SysAuth();
			auth2.setAuthId("93230F37DA714D49A554453263FBE9EB");
			list.add(auth2);
			SysAuth auth3 = new SysAuth();
			auth3.setAuthId("EF8715040AFC4AD99055AFD0BB13FCAD");
			list.add(auth3);
			SysAuth auth4 = new SysAuth();
			auth4.setAuthId("FB29CCB89C2342F49CED7B40FECE4708");
			list.add(auth4);
		}
		vo.setList(list);
		ResultModel r = c.update(vo, new BeanPropertyBindingResult(vo, AdminRoleVo.class.getName()));
		System.out.println(r.getObj());
	}
}