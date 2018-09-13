package com.yunkouan.saas.modules.sys.controller;

import org.junit.Test;

import java.util.Date;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.DaoException;
import com.yunkouan.exception.ServiceException;
import com.yunkouan.saas.common.BaseJunitTest;
import com.yunkouan.saas.modules.sys.entity.SysLog;
import com.yunkouan.saas.modules.sys.service.ILogService;
import com.yunkouan.saas.modules.sys.vo.LogVo;

/**
 * @author tphe06 2017年2月10日
 */
public class TestLog extends BaseJunitTest {
	@Autowired
	private ILogService s;

	@Test
//	@Ignore
	public void testList() throws DaoException, ServiceException {
		SysLog obj = new SysLog();
		obj.setCreateTime(new Date());
		LogVo vo = new LogVo(obj);
		ResultModel r = s.list(vo);
		System.out.println(r.getList().size());
	}

	@Test
	@Ignore
	public void testView() throws ServiceException, DaoException {
		ResultModel r = s.view("");
		System.out.println(r.getObj());
	}

	@Test
//	@Ignore
	public void testAdd() throws ServiceException, DaoException {
		SysLog obj = new SysLog();
		obj.setCreatePerson("tphe06");
		obj.setUpdatePerson("tphe06");
		obj.setLogContent("新增ASN单");
		obj.setOpButton("");
		obj.setOpModel("");
		obj.setWarehouseId("");
		obj.setOrgId("");
		obj.setOpPerson("");
		System.out.println(s.add(obj));
	}
}