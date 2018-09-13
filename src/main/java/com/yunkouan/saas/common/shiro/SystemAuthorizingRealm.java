package com.yunkouan.saas.common.shiro;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunkouan.saas.common.constant.Constant;
import com.yunkouan.saas.common.vo.Principal;
import com.yunkouan.saas.modules.sys.entity.SysAdmin;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.entity.SysOrg;
import com.yunkouan.saas.modules.sys.entity.SysUser;
import com.yunkouan.saas.modules.sys.service.IAdminService;
import com.yunkouan.saas.modules.sys.service.IOrgService;
import com.yunkouan.saas.modules.sys.vo.AdminVo;
import com.yunkouan.util.UserUtil;

/**
* @Description: 系统安全认证实现类
* @author tphe06
* @date 2017年3月11日
*/
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {
	protected static Log log = LogFactory.getLog(SystemAuthorizingRealm.class);

	/**帐号服务接口**/
	@Autowired
	private IAdminService accountService;
//	/**企业帐号数据层接口*/
//	@Autowired
//	private IAdminDao adminDao;
//	/**企业用户数据层接口**/
//	@Autowired
//	private IUserDao userDao;
//	/**企业帐号角色授权数据层接口**/
//	@Autowired
//	private IAdministratorRoleDao accountRoleDao;
//    /**企业数据层接口*/
//	@Autowired
//    private IOrgDao orgDao;
	@Autowired
	private IOrgService orgService;

	public SystemAuthorizingRealm() {
		super.setCachingEnabled(false);
		if(log.isDebugEnabled()) log.debug("构建【权限认证服务】类实例");
	}

	/**
	 * 认证回调函数【验证用户合法性/校验登录帐号密码是否正确】
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		if(log.isDebugEnabled()) log.debug("进入认证回调函数");
		// 参数值是否合法校验
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		if(token == null) throw new AuthenticationException("valid_login_pwd_error");
		if(StringUtils.isBlank(token.getUsername())) throw new AuthenticationException("valid_login_account_not_empty");
		if(token.getPassword() == null || StringUtils.isBlank(new String(token.getPassword()))) throw new AuthenticationException("valid_login_pwd_not_empty");
		String pwd = new String(token.getPassword());
		// 校验组织
//		SysOrg org = orgDao.selectOne(new SysOrg().setOrgNo(token.getOrgId()));
		SysOrg org = orgService.query(new SysOrg().setOrgNo(token.getOrgId()));
		if(org == null) throw new AuthenticationException("valid_login_orgid_not_exists");
		// 校验用户名
		AdminVo vo;
		try {
//			SysAdmin entity = new SysAdmin().setAdminNo(token.getUsername()).setOrgId(org.getOrgId());
//			SysAdmin a = adminDao.selectOne(entity);
//			if(a == null) throw new ServiceException(ErrorCode.DATA_EMPTY);
//			SysUser user = userDao.selectByPrimaryKey(a.getUserId());
//			List<SysAdminRole> list = accountRoleDao.list(new SysAdministratorRole().setAdminId(a.getAdminId()));
//			vo = new AdminVo(a).setUser(user).setList(list);
			vo = accountService.queryRole(new SysAdmin().setAdminNo(token.getUsername()).setOrgId(org.getOrgId()));
		} catch (Exception e) {
			throw new AuthenticationException("valid_login_account_not_exists");
		}
		if(vo == null || vo.getEntity() == null || StringUtils.isBlank(vo.getEntity().getAdminId()) 
			|| vo.getUser() == null) throw new AuthenticationException("valid_login_account_not_exists");
		if(vo.getList() == null || vo.getList().size() != 1
			|| !Constant.ROLE_PLATFORM.equals(vo.getList().get(0).getRoleNo())) throw new AuthenticationException("valid_login_account_error_right");
		// 检查帐号状态
		if(Constant.STATUS_ACTIVE != vo.getEntity().getAdminStatus()) throw new AuthenticationException("valid_login_account_locked");
		if(Constant.STATUS_ACTIVE != vo.getUser().getUserStatus()) throw new AuthenticationException("valid_login_account_locked");
		/** TODO 检查登录失败次数是否达到上限，如果是则置帐号状态为【生效】 **/
		// 校验帐号密码，密码为【帐号uuid+密码】明文做SHA1加密
		SysAdmin account = vo.getEntity();
		SysUser user = vo.getUser();
		String password =  UserUtil.entryptPassword(account.getAdminId().concat(pwd));
		if(!password.equals(account.getLoginPwd())) throw new AuthenticationException("valid_login_pwd_error");
		if(log.isInfoEnabled()) log.info(account.getAdminNo()+"登录成功");
		// 封装登录用户信息
		Principal principal = new Principal(user.getUserId(), token.getUsername(), user.getUserNo(), user.getUserName(),
			user.getEmail(), user.getUserStatus(), user.getIsEmployee(), user.getPhone(), false, SysAuth.AUTH_LEVEL_PLATFORM_ADMIN, account.getAdminId(), org.getOrgId());
		return new SimpleAuthenticationInfo(principal, pwd, null, getName());
	}

	/**
	 * 授权回调函数【进行鉴权但缓存中无用户的授权信息时调用/权限检查（使用权限检查标签或者手工调用接口）的时候才调用】
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(log.isDebugEnabled()) log.debug("进入授权回调函数");
		Principal principal = (Principal) getAvailablePrincipal(principals);
		if(log.isInfoEnabled()) log.info(principal + " 进行鉴权");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		/**登录用户所拥有的所有权限编号【平台管理员限1级权限，企业管理员限2级权限，企业用户限3级权限】**/
//		List<SysAuth> auths = adminDao.query(new SysAdmin().setAdminId(principal.getAccountId()));
		List<SysAuth> auths = accountService.query(principal.getAccountId());
		List<String> list = new ArrayList<String>();
		if(auths != null) for(int i=0; i<auths.size(); ++i) {
			list.add(auths.get(i).getAuthNo());
		}
		info.addStringPermissions(list);
		if(log.isInfoEnabled()) log.info(principal + " 权限列表：["+info.getStringPermissions()+"]");
		return info;
	}

}