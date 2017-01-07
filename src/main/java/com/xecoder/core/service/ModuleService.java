package com.xecoder.core.service;

import com.xecoder.core.entity.Module;

import java.util.List;


public interface ModuleService {

	void save(Module module);
	
	Module get(Long id);

    List<Module> getBySn(String sn);
	
	void update(Module module);
	
	void delete(Long id);
	
	Module getTree();

    List<Module> find(Module module);
	
	/**
	 * @param parentId 根据父类查询
	 * @return
	 */
	public List<Module> getModuleByParentId(Long parentId);
	
}
