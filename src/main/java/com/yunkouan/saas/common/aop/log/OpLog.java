package com.yunkouan.saas.common.aop.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP日志记录，自定义注解
 * @author tphe06
 */
@Inherited
@Target({ElementType.METHOD, ElementType.PARAMETER}) //表示此注解可以使用在参数（方法：ElementType.PARAMETER）上
@Retention(RetentionPolicy.RUNTIME) //表示可以在代码运行时被发现
@Documented
public @interface OpLog {
	/**MODEL_SYSTEM_ORG:模块标识-平台管理-企业管理**/
	public static final String MODEL_SYSTEM_ORG = "企业管理";
	/**MODEL_SYSTEM_PLATFORM:模块标识-平台管理-平台帐户管理**/
	public static final String MODEL_SYSTEM_PLATFORM_ACCOUNT = "平台帐户管理";
	/**MODEL_SYSTEM_RIGHT:模块标识-平台管理-权限管理**/
	public static final String MODEL_SYSTEM_RIGHT = "权限管理";
	/**
	 * 日志标识，默认所处最小级别模块标识
	 * @return
	 */
	public String model() default "";


	/**OP_TYPE_LOGIN:操作类型-登录**/
	public static final String OP_TYPE_LOGIN = "登录";
	/**OP_TYPE_ADD:操作类型-添加**/
	public static final String OP_TYPE_ADD = "新增";
	/**OP_TYPE_UPDATE:操作类型-修改**/
	public static final String OP_TYPE_UPDATE = "修改";
	/**OP_TYPE_DELETE:操作类型-删除**/
	public static final String OP_TYPE_DELETE = "删除";
	/**OP_TYPE_QUERY:操作类型-查询**/
	public static final String OP_TYPE_QUERY = "查询";
	/**OP_TYPE_LOGOUT:操作类型-退出**/
	public static final String OP_TYPE_LOGOUT = "退出";
	/**OP_TYPE_ENABLE:操作类型-生效**/
	public static final String OP_TYPE_ENABLE = "生效";
	/**OP_TYPE_DISABLE:操作类型-失效**/
	public static final String OP_TYPE_DISABLE = "失效";
	/**OP_TYPE_CANCEL:操作类型-取消**/
	public static final String OP_TYPE_CANCEL = "取消";
	/**
	 * 操作类型，对应界面上的按钮
	 * @return
	 */
	public String type() default "查询";


	/**
	 * 保存的参数位置，如果入参有多个，只保存一个参数的时候用
	 * @return
	 */
	public int pos() default -1;
}