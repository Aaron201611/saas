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
import com.yunkouan.saas.modules.sys.controller.AdminController;
import com.yunkouan.saas.modules.sys.entity.SysAdmin;
import com.yunkouan.saas.modules.sys.entity.SysAdminRole;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.service.IAdminService;
import com.yunkouan.saas.modules.sys.vo.AdminVo;

/**
 * @author tphe06 2017年2月10日
 */
public class TestAdmin extends BaseJunitTest {
	@Autowired
	private AdminController c;
	@Autowired
	private IAdminService s;

	@Test
	@Ignore
	public void testList() throws ServiceException, DaoException {
		SysAdmin obj = new SysAdmin();
		obj.setCreateTime(new Date());
		AdminVo vo = new AdminVo(obj);
		ResultModel r = c.list(vo, new BeanPropertyBindingResult(vo, AdminVo.class.getName()));
		System.out.println(r.getList().size());
	}

	@Test
	@Ignore
	public void testView() throws ServiceException, DaoException {
		ResultModel r = c.view(new AdminVo(new SysAdmin().setAdminId("75BCC60D6A494E4296EF319A91EFC17E")));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testAdd() throws ServiceException, DaoException {
		SysAdmin obj = new SysAdmin();
		obj.setAdminNo("admin1");
		obj.setAdminName("admin");
		obj.setLoginPwd("admin");
		obj.setUserId("384D873241D341B282253346CA481DCE");
		obj.setCreatePerson("tphe06");
		obj.setUpdatePerson("tphe06");
		obj.setCreateTime(new Date());
		obj.setOrgId("62E18D09DC2F4FC6A717F1E9503B40D3");
		AdminVo vo = new AdminVo(obj);
		List<SysAdminRole> list = new ArrayList<SysAdminRole>();
		for(int i=0; i<1; ++i) {
			SysAdminRole r = new SysAdminRole();
			r.setRoleId("5C939B3A13DF40A0AEA106A7006405FF");
			list.add(r);
		}
		vo.setList(list);
		ResultModel r = c.add(vo, new BeanPropertyBindingResult(vo, AdminVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testUpdate() throws ServiceException, DaoException {
		SysAdmin obj = new SysAdmin();
		obj.setAdminId("F74C57C666824D928F9B30817E7DD4E4");
		obj.setAdminStatus(2);
		obj.setCreateTime(new Date());
		AdminVo vo = new AdminVo(obj);
		List<SysAdminRole> list = new ArrayList<SysAdminRole>();
		for(int i=0; i<10; ++i) {
			SysAdminRole r = new SysAdminRole();
			r.setRoleId("394B3CF49B5E43399A5BE52BEAF28A93");
			list.add(r);
		}
		vo.setList(list);
		ResultModel r = c.update(vo, new BeanPropertyBindingResult(vo, AdminVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
//	@Ignore
	public void testQuery() throws ServiceException, DaoException {
		List<SysAuth> list = s.query("75BCC60D6A494E4296EF319A91EFC17E");
		System.out.println(list.size());
	}
}