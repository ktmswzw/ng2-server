package com.xecoder.core.impl;



import com.xecoder.common.exception.SysException;
import com.xecoder.common.exception.factor.UserExcepFactor;
import com.xecoder.core.entity.Module;
import com.xecoder.core.entity.ModuleCriteria;
import com.xecoder.core.mapper.ModuleMapper;
import com.xecoder.core.service.ModuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleMapper mapper;
	@Override
	public void delete(Long id)  {
		if (isRoot(id)) {
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
		}
		
		Module module = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(getModuleByParentId(module.getId()).size() > 0){
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
		}
		
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Module> find(Module module) {
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(module.getParentId() != null){
			cri.andParentIdEqualTo(module.getParentId());
		}

		if(StringUtils.isNotBlank(module.getName())){
			cri.andNameEqualTo(module.getName());
		}

		if(StringUtils.isNotBlank(module.getUrl())){
			cri.andUrlEqualTo(module.getUrl());
		}

		if(StringUtils.isNotBlank(module.getSn())){
			cri.andSnEqualTo(module.getSn());
		}

		if(module.getParentId()!=null){
			cri.andParentIdEqualTo(module.getParentId());
		}

		if(module.getId()!=null){
			cri.andIdEqualTo(module.getId());
		}

		if(StringUtils.isBlank(module.getSort())){
			criteria.setOrderByClause("priority ASC");
		}
		else
			criteria.setOrderByClause(module.getSort()+" "+module.getOrder());

		List<Module> list = mapper.selectByExample(criteria);


		if(list != null && list.size() > 0){
			for(Module m:list){
				if(m.getParentId() != null){
					m.setParent(this.get(m.getParentId()));
				}
			}
		}
		return list;
	}

    @Override
	public Module get(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<Module> getModuleByParentId(Long parentId){
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(parentId != null){
			cri.andParentIdEqualTo(parentId);
		}
		
		return mapper.selectByExample(criteria);
	}

	@Override
	public List<Module> getBySn(String sn) {
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(StringUtils.isNotBlank(sn)){
			cri.andSnEqualTo(sn);
		}
		
		return mapper.selectByExample(criteria);
	}

	@Override
	public Module getTree() {
		List<Module> list = find(new Module());

		if(list != null && list.size() > 0){
			List<Module> rootList = makeTree(list);

			return rootList.get(0);
		}

		return null;
	}

	private List<Module> makeTree(List<Module> list) {
		List<Module> parent = new ArrayList<Module>();
		// get parentId = null;
		for (Module e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);

		makeChildren(parent, list);

		return parent;
	}

	private void makeChildren(List<Module> parent, List<Module> children) {
		if (children.isEmpty()) {
			return ;
		}

		List<Module> tmp = new ArrayList<Module>();
		for (Module c1 : parent) {
			for (Module c2 : children) {
				c2.setChildren(new ArrayList<Module>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}

		children.removeAll(tmp);

		makeChildren(tmp, children);
	}

	@Override
	public void save(Module module) {
		if (getBySn(module.getSn()).size()>1) {
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
		}
		mapper.insertSelective(module);
	}

	@Override
	public void update(Module module) {
        if (getBySn(module.getSn()).size()>1) {
			throw new SysException(UserExcepFactor.DELETE_SUPER_FAILED);
        }
		mapper.updateByPrimaryKeySelective(module);
	}
	
	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

}
