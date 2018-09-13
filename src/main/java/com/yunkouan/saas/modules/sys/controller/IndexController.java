package com.yunkouan.saas.modules.sys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.yunkouan.saas.common.aop.log.OpLog;
import com.yunkouan.saas.common.constant.Constant;
import com.yunkouan.saas.common.constant.ErrorCode;
import com.yunkouan.saas.common.util.LoginUtil;
import com.yunkouan.saas.common.vo.Principal;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.service.IAdminService;
import com.yunkouan.saas.modules.sys.service.IAuthService;
import com.yunkouan.saas.modules.sys.vo.AdminVo;
import com.yunkouan.saas.modules.sys.vo.AuthVo;
import com.yunkouan.valid.ValidSearch;

/**
 * 限平台管理员登录，暂不支持多园区模式
 * @author tphe06 2017年3月8日
 */
@Controller
@CrossOrigin
@RequestMapping("${adminPath}/index")
public class IndexController extends BaseController {
	protected static Log log = LogFactory.getLog(IndexController.class);

	/**权限服务接口**/
	@Autowired
	private IAuthService service;
	/**帐号服务接口**/
	@Autowired
	private IAdminService accountService;

	public IndexController() {
		if(log.isDebugEnabled()) log.debug("构建【主页控制器】类实例");
	}

	/**
	 * 加载菜单数据（含一二级）
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@RequestMapping(value = "/loadMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel loadMenu(HttpServletRequest req) {
		try {
			if(log.isDebugEnabled()) log.debug("SessionId:["+req.getSession().getId()+"]，URL:"+req.getRequestURL());
			Principal p = LoginUtil.getLoginUser();
			if(p == null) throw new BizException(ErrorCode.NO_POWER);
			SysAuth entity = new SysAuth()
			.setAuthStatus(Constant.STATUS_ACTIVE)
			.setAuthType(SysAuth.AUTH_TYPE_TOP)
			.setAuthLevel(p.getAuthLevel());
			AuthVo vo = new AuthVo(entity);
			vo.setAccountId(p.getAccountId());
			List<SysAuth> list = service.query4admin(vo);
			List<AuthVo> result = new ArrayList<AuthVo>();
			if(list != null) for(int i=0; i<list.size(); ++i) {
				vo.getEntity().setParentId(list.get(i).getAuthId());
				vo.getEntity().setAuthType(SysAuth.AUTH_TYPE_SECOND);
				result.add(new AuthVo(list.get(i)).setList(chg(service.query4admin(vo))));
			}
			return new ResultModel().setList(result);
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}
	private static List<AuthVo> chg(List<SysAuth> list) {
		if(list == null) return null;
		List<AuthVo> r = new ArrayList<AuthVo>();
		for(int i=0; i<list.size(); ++i) {
			r.add(new AuthVo(list.get(i)));
		}
		return r;
	}

	/**
	 * 查询当前登录用户信息
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@RequestMapping(value = "/loadUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel loadUser(HttpServletRequest req) {
		ResultModel r = new ResultModel();
		try {
			if(log.isDebugEnabled()) log.debug("SessionId:["+req.getSession().getId()+"]，URL:"+req.getRequestURL());
			Principal p = LoginUtil.getLoginUser();
			if(p == null) throw new BizException(ErrorCode.NO_POWER);
			AdminVo n = accountService.load(p.getAccountId());
			r.setObj(n);
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		}
		return r;
	}

	/**
	 * 修改当前登录用户信息
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 * @throws ServiceException 
	 * @throws DaoException 
	 */
	@OpLog(model = "个人中心", type = OpLog.OP_TYPE_UPDATE, pos = 0)
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel updateUser(@Validated(value = { ValidSearch.class }) @RequestBody AdminVo vo, BindingResult br) {
		ResultModel r = new ResultModel();
		try {
			if ( br.hasErrors() ) {
				return super.handleValid(br);
			}
			accountService.updatePwd(vo, LoginUtil.getLoginUser());
		} catch (DaoException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		} catch (ServiceException e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			return r.setError().addMessage(ErrorCode.UNKNOW_ERROR);
		}
		return r;
	}
}