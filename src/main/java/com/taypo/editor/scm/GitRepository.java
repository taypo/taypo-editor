package com.taypo.editor.scm;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

class GitRepository extends IRepo {
	
	Git git;
	
	public GitRepository(Git git) {
		this.git = git;
		//repo.get
	}

	@Override
	public RepositoryType getType() {
		return RepositoryType.GIT;
	}

	@Override
	public String getCurrentBranch() {
		try {
			return git.getRepository().getBranch();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
