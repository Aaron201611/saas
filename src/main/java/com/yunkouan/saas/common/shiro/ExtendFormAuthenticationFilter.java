package com.yunkouan.saas.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.yunkouan.saas.common.constant.ErrorCode;
import com.yunkouan.saas.common.util.LoginUtil;
import com.yunkouan.util.MessageUtil;

/**
* @Description: 表单验证（包含验证码）过滤类
* @author tphe06
* @date 2017年3月11日
*/
@Service
public class ExtendFormAuthenticationFilter extends FormAuthenticationFilter {
	protected static Log log = LogFactory.getLog(ExtendFormAuthenticationFilter.class);

	public ExtendFormAuthenticationFilter() {
		if(log.isDebugEnabled()) log.debug("构建【权限表单验证服务】类实例");
	}

	/**
     * 表示当访问拒绝时
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	HttpServletRequest r = (HttpServletRequest)request;
    	if(log.isDebugEnabled()) log.debug("SessionId:["+r.getSession().getId()+"]，URL:"+r.getRequestURL());
    	/**判断是否登录页面地址loginUrl**/
        if(this.isLoginRequest(request, response)) {
        	/**判断是否POST请求**/
            if(this.isLoginSubmission(request, response)) {
                /**执行后续createToken,onLoginSuccess,onLoginFailure方法**/
                return this.executeLogin(request, response);
            } else {
            	if(log.isWarnEnabled()) log.warn("非post方式访问系统登录接口");
                /**非法访问，输出错误信息**/
            	if(LoginUtil.isAjax(request)) {
	                Token t = new Token();
	                t.setStatus("-1");
		            String message = MessageUtil.getMessage(r, ErrorCode.NO_POWER);
		            t.setMessage(message);
	                LoginUtil.printAjax(response, JSON.toJSONString(t));
            	} else {
            		LoginUtil.redirect(request, response, "/#!/login");
            	}
                return false;
            }
        } else {
        	if(log.isWarnEnabled()) log.warn("无权访问该URL地址：["+r.getRequestURL()+"]");
            /**非法访问，输出错误信息**/
        	if(LoginUtil.isAjax(request)) {
        		if(log.isDebugEnabled()) log.debug("ajax请求");
	            Token t = new Token();
	            t.setStatus("-1");
	            String message = MessageUtil.getMessage(r, ErrorCode.NO_POWER);
	            t.setMessage(message);
	            LoginUtil.printAjax(response, JSON.toJSONString(t));
//	            throw new BizException("valid_common_nopower_error");
	            return false;
        	} else {
        		LoginUtil.redirect(request, response, "/#!/login");
        	}
//        	this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
	 * 读取form表单参数值
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);//username
		String password = getPassword(request);//password
		if (password == null) {
			password = "";
		}
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(username);
		token.setPassword(password.toCharArray());
		return token;
	}
//	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
//		if(log.isDebugEnabled()) log.debug(request.getRemoteAddr()+" 访问系统登录接口");
//		UsernamePasswordToken token = new UsernamePasswordToken();
//		try {
//			/**json数据存放在http协议的body里面，而非head或者parameter部分 **/
//			byte[] b = new byte[request.getContentLength()];
//			request.getInputStream().read(b);
//			String json = new String(b, "UTF-8");
//			if(log.isInfoEnabled()) log.info(json);
//			/**把json格式字符串数据转化成相对应的类实例，使用阿里巴巴的fastjson，速度较快**/
//			token = JSON.parseObject(json, UsernamePasswordToken.class);
//		} catch (Exception e) {
//			if(log.isErrorEnabled()) log.error(e.getMessage());
//		}
//		boolean rememberMe = isRememberMe(request);//rememberMe
//		token.setRememberMe(rememberMe);
//		String host = StringUtil.getRemoteAddr((HttpServletRequest) request);
//		token.setHost(host);
//		return token;
//	}

	/**
	 * 登录成功回调事件
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		if(!LoginUtil.isAjax(request)) {
//			boolean p = SecurityUtils.getSubject().isPermitted("warehouse:get");
//			if(p) {
				if(log.isDebugEnabled()) log.debug("有主页权限，重定向到主页");
				LoginUtil.redirect(request, response, "/#!/warehouse/home");
//			} else {
//				if(log.isErrorEnabled()) log.error("无主页权限，重定向到登录页面");
//				redirect(request, response, "/#!/login");
//			}
			return false;
		}
		/** TODO 清空登录失败次数**/
		/**向浏览器输出结果**/
        Token r = new Token();
        r.setStatus("1");
        LoginUtil.printAjax(response, JSON.toJSONString(r));
        return false;
	}

//	/**
//	 * 登录成功之后跳转URL
//	 */
//	@Override
//	public String getSuccessUrl() {
//		return super.getSuccessUrl();
//	}
//	/**
//	 * 登录成功回调事件
//	 */
//	@Override
//	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
//		Principal u = LoginUtil.getLoginUser();
//		if(log.isDebugEnabled()) log.debug(u+" 重定向到主页");
//		String url = getSuccessUrl();
//		if(log.isDebugEnabled()) log.debug("主页URL地址："+url);
//		WebUtils.issueRedirect(request, response, url, null, true);
//	}

	/**
	 * 登录失败回调事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
        	if(!LoginUtil.isAjax(request)) {
        		setFailureAttribute(request, e);
        		LoginUtil.redirect(request, response, "/#!/login");
        		return false;
        	}
        	/** TODO 增加登录失败次数**/
        	/**向浏览器输出结果**/
            Token t = new Token();
            t.setStatus("0");
            t.setMessage(e.getMessage());
            LoginUtil.printAjax(response, JSON.toJSONString(t));
        } catch (Exception e1) {
            if(log.isErrorEnabled()) log.error(e1.getMessage());
        }
        return false;
	}

	public static class Token {
		/**登录结果，0失败，1成功**/
		private String status;
		/**错误信息**/
		private String message;

		public String getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}