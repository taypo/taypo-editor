package com.taypo.editor.scm;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taypo.editor.Conf;

@Component
public class LocalRepository {

	@Autowired
	Conf conf;
	
	private IRepo repo;
	
	@PostConstruct
	private void detect() {
		repo = IRepo.from(conf.getServePath());
	}

	public RepoInfo getInfo() {
		RepoInfo info = new RepoInfo();
		info.branch = repo.getCurrentBranch();
		info.type = repo.getType();
		return info;
	}
	
}
