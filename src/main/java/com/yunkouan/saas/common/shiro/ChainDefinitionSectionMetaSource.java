package com.yunkouan.saas.common.shiro;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunkouan.saas.common.constant.Constant;
import com.yunkouan.saas.modules.sys.entity.SysAuth;
import com.yunkouan.saas.modules.sys.service.IAuthService;

/**
* @Description: shiro权限初始化
* @author tphe06
* @date 2017年3月11日
*/
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
	protected static Log log = LogFactory.getLog(ChainDefinitionSectionMetaSource.class);

    /**权限服务接口**/
    @Autowired
    private IAuthService service;
//    @Autowired
//    private IAuthDao dao;
	private String basePath;
    private String filterChainDefinitions;
    private static final String PREMISSION_STRING="perms[\"{0}\"]";

    public ChainDefinitionSectionMetaSource() {
    	if(log.isDebugEnabled()) log.debug("构建【权限初始化服务】类实例");
    }

    /**
    * @Description: 加载系统中所有权限URL地址【注意：必须是所有权限URL】
    * @return
    * @throws BeansException
     */
    public Section getObject() throws BeansException {
    	if(log.isDebugEnabled()) log.debug("进入权限初始化方法");
        /**加载默认的url**/
    	Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        /**从数据库查询所有权限【菜单数据】*/
        SysAuth entity = new SysAuth();
        entity.setAuthStatus(Constant.STATUS_ACTIVE);
        entity.setAuthLevel(SysAuth.AUTH_LEVEL_PLATFORM_ADMIN);
//        List<SysAuth> list = dao.select(entity);
    	List<SysAuth> list = service.query(entity);
        if(list != null) for (Iterator<SysAuth> it = list.iterator(); it.hasNext();) {
        	SysAuth resource = it.next();
            if(StringUtils.isNotEmpty(resource.getAuthUrl()) && StringUtils.isNotEmpty(resource.getAuthNo())) {
                /**所有【权限URL地址】与 【权限编号】的对应关系
                ${adminPath}/bill/search = perms[bill:search]**/
                section.put(resource.getAuthUrl(),  MessageFormat.format(PREMISSION_STRING,resource.getAuthNo()));
            }
        }
        section.put("/", "anon");
//        section.put(new StringBuffer("/static/js").append("/**").toString(), "authc");
//        section.put(new StringBuffer(basePath).append("/**").toString(), "authc");
        return section;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Class<?> getObjectType() {
        return this.getClass();
    }

    public boolean isSingleton() {
        return true;
    }
}