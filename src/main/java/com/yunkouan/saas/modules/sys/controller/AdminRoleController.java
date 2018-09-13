package com.yunkouan.saas.modules.sys.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunkouan.base.BaseController;
import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.BizException;
import com.yunkouan.exception.DaoException;
import com.yunkouan.exception.ServiceException;
import com.yunkouan.saas.common.constant.ErrorCode;
import com.yunkouan.saas.common.util.LoginUtil;
import com.yunkouan.saas.modules.sys.service.IAdminRoleService;
import com.yunkouan.saas.modules.sys.vo.AdminRoleVo;
import com.yunkouan.valid.ValidSave;
import com.yunkouan.valid.ValidSearch;
import com.yunkouan.valid.ValidUpdate;

/**
 * 管理员角色控制类
 * @author tphe06 2017年2月14日
 */
@Controller
@CrossOrigin
@RequiresPermissions(value = {"people.view"})
@RequestMapping("${adminPath}/adminrole")
public class AdminRoleController extends BaseController {
	protected static Log log = LogFactory.getLog(AdminRoleController.class);

	/**管理员角色服务接口*/
	@Autowired
    private IAdminRoleService service;

	public AdminRoleController() {
		if(log.isDebugEnabled()) log.debug("构建【管理员角色控制器】类实例");
	}

	/**
     * 管理员角色列表数据查询
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResultModel list(@Validated(value = { ValidSearch.class }) @RequestBody AdminRoleVo vo, BindingResult br)  {
//		ResultModel r = new ResultModel();
		try {
			if ( br.hasErrors() ) {
				return super.handleValid(br);
			}
			return service.list(vo);
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
	}

    /**
     * 查看管理员角色详情
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel view(@RequestBody AdminRoleVo vo)  {
//		ResultModel r = new ResultModel();
        try {
        	return service.view(vo.getEntity().getRoleId());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }

    /**
     * 添加管理员角色
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel add( @Validated(value = { ValidSave.class }) @RequestBody AdminRoleVo vo, BindingResult br)  {
//		ResultModel r = new ResultModel();
        try {
    		if ( br.hasErrors() ) {
    			return super.handleValid(br);
    		}
			return service.add(vo, LoginUtil.getLoginUser());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }

    /**
     * 修改管理员角色
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel update(@Validated(value = { ValidUpdate.class }) @RequestBody AdminRoleVo vo, BindingResult br)  {
//		ResultModel r = new ResultModel();
        try {
    		if ( br.hasErrors() ) {
    			return super.handleValid(br);
    		}
			return service.update(vo, LoginUtil.getLoginUser());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }

    /**
     * 生效管理员角色
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel enable(@RequestBody AdminRoleVo vo)  {
//		ResultModel r = new ResultModel();
		try {
			return service.enable(vo.getEntity().getRoleId());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }

    /**
     * 失效管理员角色
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel disable(@RequestBody AdminRoleVo vo)  {
//		ResultModel r = new ResultModel();
		try {
			return service.disable(vo.getEntity().getRoleId());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }

    /**
     * 取消管理员角色
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel cancel(@RequestBody AdminRoleVo vo)  {
//		ResultModel r = new ResultModel();
		try {
			return service.cancel(vo.getEntity().getRoleId());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }
}