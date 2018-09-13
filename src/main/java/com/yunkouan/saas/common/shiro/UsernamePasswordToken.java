package com.yunkouan.saas.common.shiro;

/**
* @Description: 用户和密码（包含验证码）令牌类
* @author tphe06
* @date 2017年3月11日
*/
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {
	private static final long serialVersionUID = -7593630445121174421L;

	private String validateCode;
	private String mobileLogin;
	private String orgId;

	public String getValidateCode() {
		return validateCode;
	}
	public String getMobileLogin() {
		return mobileLogin;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public void setMobileLogin(String mobileLogin) {
		this.mobileLogin = mobileLogin;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}