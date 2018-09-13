package com.yunkouan.saas.modules.sys.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunkouan.base.BaseController;
import com.yunkouan.entity.ResultModel;
import com.yunkouan.exception.BizException;
import com.yunkouan.saas.common.vo.Principal;
import com.yunkouan.saas.modules.sys.service.IStrategyImplService;
import com.yunkouan.saas.modules.sys.vo.StrategyImplVo;
import com.yunkouan.saas.common.constant.ErrorCode;
import com.yunkouan.saas.common.util.LoginUtil;
import com.yunkouan.valid.ValidSave;
import com.yunkouan.valid.ValidSearch;
import com.yunkouan.valid.ValidUpdate;

/** 
* @Description: 策略controller（使用VO类，不直接出现实体类）
* @author tphe06
* @date 2017年10月17日 下午1:57:50  
*/
@Controller
@RequestMapping("${adminPath}/strategyImpl")
public class StrategyImplController extends BaseController {
	private static Log log = LogFactory.getLog(StrategyImplController.class);

	@Autowired
	private IStrategyImplService service;

	/**
	 * 页面列表查询（分页）
	 * @param vo
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResultModel list(@Validated(value = { ValidSearch.class }) @RequestBody StrategyImplVo vo, BindingResult br) {
		try {
			if ( br.hasErrors() ) {
				return super.handleValid(br);
			}
			Principal p = LoginUtil.getLoginUser();
			List<StrategyImplVo> list = service.list(vo, p);
			return new ResultModel().setList(list).setPageCount(vo.getPageCount()).setTotalCount(vo.getTotalCount());
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
	}

	/**
	 * 页面列表查询（不分页）
	 * @param vo
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	@ResponseBody
    public ResultModel select(@Validated(value = { ValidSearch.class }) @RequestBody StrategyImplVo vo, BindingResult br) {
		try {
			if ( br.hasErrors() ) {
				return super.handleValid(br);
			}
			Principal p = LoginUtil.getLoginUser();
			List<StrategyImplVo> list = service.select(vo, p);
			return new ResultModel().setList(list);
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
	}

	/**
	 * 页面查看详情
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel view(@RequestBody StrategyImplVo vo)  {
       try {
    	   StrategyImplVo v = service.view(vo.getEntity().getStrategyId());
		   return new ResultModel().setObj(v);
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
    }

	/**
	 * 查询数量
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel count(@Validated(value = { ValidSearch.class }) @RequestBody StrategyImplVo vo, BindingResult br) {
       try {
		   if ( br.hasErrors() ) {
			   return super.handleValid(br);
		   }
    	   Principal p = LoginUtil.getLoginUser();
    	   long count = service.count(vo, p);
		   return new ResultModel().setObj(count);
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
    }

	/**
	 * 新增方法
	 * @param vo
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel insert( @Validated(value = { ValidSave.class }) @RequestBody StrategyImplVo vo, BindingResult br) {
		try {
			if ( br.hasErrors() ) {
				return super.handleValid(br);
			}
			Principal p = LoginUtil.getLoginUser();
			service.insert(vo, p);
			return this.view(vo);
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
    }

	/**
	 * 修改方法
	 * @param vo
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel update(@Validated(value = { ValidUpdate.class }) @RequestBody StrategyImplVo vo, BindingResult br) {
		try {
			if ( br.hasErrors() ) {
				return super.handleValid(br);
			}
			Principal p = LoginUtil.getLoginUser();
			service.update(vo, p);
			return this.view(vo);
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
    }

	/**
	 * 删除方法
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel delete(@RequestBody StrategyImplVo vo) {
		try {
			int result = service.delete(vo.getEntity().getStrategyId());
			return new ResultModel().setObj(result);
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
    }

	/** 
	* @Title: deleteByIds 
	* @Description: 批量删除
	* @auth tphe06
	* @time 2018 2018年8月24日 上午9:54:33
	* @param ids
	* @return
	* ResultModel
	*/
	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel deleteByIds(@RequestBody String[] ids) {
		try {
			service.delete(ids);
			return new ResultModel();
		} catch(BizException e) {
			throw e;
		} catch(Throwable e) {
			if(log.isErrorEnabled()) log.error(e.getMessage(), e);
			throw new BizException(ErrorCode.UNKNOW_ERROR);
		}
    }
}