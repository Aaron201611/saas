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
import com.yunkouan.saas.modules.sys.entity.SysUser;
import com.yunkouan.saas.modules.sys.service.IUserService;
import com.yunkouan.saas.modules.sys.vo.UserVo;
import com.yunkouan.valid.ValidSave;
import com.yunkouan.valid.ValidSearch;
import com.yunkouan.valid.ValidUpdate;

/**
 * 用户控制类
 * @author tphe06 2017年2月14日
 */
@Controller
@CrossOrigin
@RequiresPermissions(value = {"people.view"})
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController {
	protected static Log log = LogFactory.getLog(UserController.class);

	/**用户服务接口*/
	@Autowired
    private IUserService service;

	public UserController() {
		if(log.isDebugEnabled()) log.debug("构建【用户控制器】类实例");
	}

	/**
     * 用户列表数据查询
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResultModel list(@Validated(value = { ValidSearch.class }) @RequestBody UserVo vo, BindingResult br)  {
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
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
	}

    /**
     * 查看用户详情
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel view(@RequestBody UserVo vo)  {
//		ResultModel r = new ResultModel();
        try {
        	return service.view(vo.getEntity().getUserId());
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
     * 添加用户
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel add( @Validated(value = { ValidSave.class }) @RequestBody UserVo vo, BindingResult br)  {
//		ResultModel r = new ResultModel();
        try {
    		if ( br.hasErrors() ) {
    			return super.handleValid(br);
    		}
			if (vo.getEntity() == null) {
				vo.setEntity(new SysUser());
			}
    		vo.getEntity().setIsEmployee(1);
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
     * 添加并生效用户
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/saveAndEnable", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel saveAndEnable( @Validated(value = { ValidSave.class }) @RequestBody UserVo vo, BindingResult br)  {
//		ResultModel r = new ResultModel();
        try {
    		if ( br.hasErrors() ) {
    			return super.handleValid(br);
    		}
			if (vo.getEntity() == null) {
				vo.setEntity(new SysUser());
			}
    		vo.getEntity().setIsEmployee(1);
			return service.saveAndEnable(vo);
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
		} catch (BizException e) {
			throw e;
//			return r.setError().addMessage(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR);
//			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
    }
    /**
     * 修改用户
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel update(@Validated(value = { ValidUpdate.class }) @RequestBody UserVo vo, BindingResult br)  {
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
     * 生效用户
     * @throws DaoException 
     * @throws ServiceException 
     */
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel enable(@RequestBody UserVo vo)  {
//		ResultModel r = new ResultModel();
		try {
			return service.enable(vo.getEntity().getUserId());
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
     * 失效用户
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel disable(@RequestBody UserVo vo)  {
//		ResultModel r = new ResultModel();
		try {
			return service.disable(vo.getEntity().getUserId());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
//			return r.setError().addMessage(e.getMessage());
			throw new BizException(e.getMessage());
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
     * 取消用户
     * @throws ServiceException 
     * @throws DaoException 
     */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel cancel(@RequestBody UserVo vo)  {
//		ResultModel r = new ResultModel();
		try {
			return service.cancel(vo.getEntity().getUserId());
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