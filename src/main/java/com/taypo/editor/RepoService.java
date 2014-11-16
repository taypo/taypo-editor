package com.taypo.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taypo.editor.scm.LocalRepository;
import com.taypo.editor.scm.RepoInfo;

@RestController()
@RequestMapping("/api/repo")
public class RepoService {

	@Autowired
	LocalRepository repo;

	@RequestMapping("/info")
	public RepoInfo getInfo() {
		return repo.getInfo();
	}
}
