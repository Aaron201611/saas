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
import com.yunkouan.saas.modules.sys.controller.AuthController;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.service.IAuthService;
import com.yunkouan.saas.modules.sys.vo.AuthVo;

/**
 * @author tphe06 2017年2月10日
 */
public class TestAuth extends BaseJunitTest {
	@Autowired
	private AuthController c;
	@Autowired
	private IAuthService s;

	@Test
//	@Ignore
	public void testTree() throws Exception {
		AuthVo vo = new AuthVo();
		List<SysAuth> list = s.query(new SysAuth().setParentId("0"));
		vo.setList(chg(list));
		tree(vo);

		ResultModel r = new ResultModel();
		r.setList(vo.getList());
		super.toJson(r);
	}

	private void tree(AuthVo vo) {
		if(vo.getList() == null || vo.getList().size() == 0) return;
		for(int i=0; i<vo.getList().size(); ++i) {
			AuthVo c = vo.getList().get(i);
			List<SysAuth> list = s.query(new SysAuth().setParentId(c.getEntity().getAuthId()));
			c.setList(chg(list));
			tree(c);
		}
	}
	private List<AuthVo> chg(List<SysAuth> list) {
		if(list == null) return null;
		List<AuthVo> r = new ArrayList<AuthVo>();
		for(int i=0; i<list.size(); ++i) {
			r.add(new AuthVo(list.get(i)));
		}
		return r;
	}

	@Test
	@Ignore
	public void testList1() throws DaoException, ServiceException {
		SysAuth entity = new SysAuth();
		entity.setAuthStatus(1).setAuthType(1).setParentId("1");
		AuthVo vo = new AuthVo(entity);
		vo.setAccountId("1");
		vo.addTypes(1);
		vo.addTypes(2);
		List<SysAuth> list = s.query4admin(vo);
		System.out.println(list.size());
	}

	@Test
	@Ignore
	public void testList() throws DaoException, ServiceException {
		AuthVo vo = new AuthVo();
		ResultModel r = c.list(vo, new BeanPropertyBindingResult(vo, AuthVo.class.getName()));
		System.out.println(r.getList().size());
	}

	@Test
	@Ignore
	public void testView() throws DaoException, ServiceException {
		ResultModel r = c.view(new AuthVo(new SysAuth().setAuthId("53A2DEAB02854A28AE6D9915344C807A")));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testAdd() throws DaoException, ServiceException {
		SysAuth auth = new SysAuth();
		auth.setAuthNo("account.system.menu");
		auth.setAuthName("帐号管理");
		auth.setAuthShortname("帐号管理");
		auth.setAuthLevel(1);
		auth.setAuthType(2);
		auth.setAuthUrl("/account/list");
		auth.setCreatePerson("tphe06");
		auth.setUpdatePerson("tphe06");
		auth.setParentId("5DB86899447D4D198E6F1225D643158D");
		AuthVo vo = new AuthVo(auth);
		ResultModel r = c.add(vo, new BeanPropertyBindingResult(vo, AuthVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
	@Ignore
	public void testUpdate() throws DaoException, ServiceException {
		SysAuth auth = new SysAuth();
		auth.setAuthId("567435572B4E4FC1AD5F49CE7370B4BF");
		auth.setAuthStatus(20);
		AuthVo vo = new AuthVo(auth);
		ResultModel r = c.update(vo, new BeanPropertyBindingResult(vo, AuthVo.class.getName()));
		System.out.println(r.getObj());
	}

	@Test
//	@Ignore
	public void testQuery() throws DaoException, ServiceException {
		List<SysAuth> list = s.query(new SysAuth().setAuthStatus(20));
		System.out.println(list.size());
	}
}