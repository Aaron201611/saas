package com.yunkouan.saas.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yunkouan.base.BaseController;
import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.BizException;
import com.yunkouan.saas.common.constant.ErrorCode;
import com.yunkouan.saas.common.shiro.UsernamePasswordToken;
import com.yunkouan.saas.common.util.LoginUtil;
import com.yunkouan.saas.common.vo.Principal;
import com.yunkouan.saas.common.aop.log.OpLog;

@Controller
@CrossOrigin
@RequestMapping("${adminPath}/adminlogin")
public class LoginController extends BaseController {
	protected static Log log = LogFactory.getLog(LoginController.class);

	public LoginController() {
		if(log.isDebugEnabled()) log.debug("构建【登录控制器】类实例");
	}

	/**
	 * 重定向到的登录页面
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
	public void toLogin(HttpServletRequest req, HttpServletResponse rsp) {
		try {
			if(log.isWarnEnabled()) log.warn("重定向到登录页面");
			SecurityUtils.getSubject().logout();
//			rsp.sendRedirect(req.getRequestURL()+"/#!/login");
			LoginUtil.redirect(req, rsp, "/#!/login");
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	/**
	 * 登录功能
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@OpLog(model = OpLog.OP_TYPE_LOGIN, type = OpLog.OP_TYPE_LOGIN, pos=0)
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel doLogin(@RequestBody UsernamePasswordToken token, HttpServletRequest req) {
		ResultModel r = new ResultModel();
		try {
			if(log.isDebugEnabled()) log.debug("SessionId:["+req.getSession().getId()+"]，URL:"+req.getRequestURL());
			SecurityUtils.getSubject().logout();
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			r.setObj("1");
			return r;
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	/**
	 * 登录成功，重定向到主页
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public void success(HttpServletRequest req, HttpServletResponse rsp) {
		try {
			if(log.isWarnEnabled()) log.warn("登录成功，重定向到主页");
			LoginUtil.redirect(req, rsp, "/#!/warehouse/home");
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	/**
	 * 退出功能
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@OpLog(model = OpLog.OP_TYPE_LOGOUT, type = OpLog.OP_TYPE_LOGOUT)
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel logout() {
		try {
			SecurityUtils.getSubject().logout();
			if(log.isInfoEnabled()) log.info("您已经成功退出");
			return new ResultModel();
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.UNKNOW_ERROR); 
		}
	}

	/**
	 * 检查是否登录或者超时
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@RequestMapping(value = "/isTimeout", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel isTimeout(HttpServletRequest req) {
		try {
			if(log.isDebugEnabled()) log.debug("SessionId:["+req.getSession().getId()+"]，URL:"+req.getRequestURL());
			Principal p = LoginUtil.getLoginUser();
			if(p == null) throw new BizException(ErrorCode.TIMEOUT_OR_NOPOWER);
			return new ResultModel();
		} catch (Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage());
			throw new BizException(ErrorCode.TIMEOUT_OR_NOPOWER);
		}
	}

	/**
	 * 检查是否有权限
	 * @author tphe06
	 * @date 2016年11月25日 下午1:12:20
	 * @return 
	 */
	@RequestMapping(value = "/isPermitted", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel isPermitted(@RequestBody String perm) {
		ResultModel r = new ResultModel();
		try {
			Principal p1 = LoginUtil.getLoginUser();
			if(p1 != null && p1.getLoginName() != null) {
				if(log.isDebugEnabled()) log.debug("当前登录帐号：["+p1.getLoginName()+"]");
			} else {
				if(log.isErrorEnabled()) log.error("当前登录帐号为空");
			}
			perm = perm.replaceAll("=", "");
			boolean p = SecurityUtils.getSubject().isPermitted(perm);
			if(!p) {
				if(log.isErrorEnabled()) log.error("["+perm+"]无权限");
				throw new BizException(ErrorCode.NO_POWER);
			}
			if(log.isDebugEnabled()) log.debug(perm+"有权限");
			return r;
		} catch (Throwable e) {
			throw new BizException(e.getMessage());
		}
	}
}