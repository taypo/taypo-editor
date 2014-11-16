package com.taypo.editor.scm;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class IRepo {
	
	private static Logger log = LoggerFactory.getLogger(IRepo.class);

	public static IRepo from(String path) {
		Git git = checkGit(path);
		if(git != null) {
			return new GitRepository(git);
		}
		
		return new NoRepo();
	}
	
	private static Git checkGit(String path) {
		try {
			return Git.open(new File(path));
		} catch (IOException e) {
			log.info("Path: " + path + " is not a git repository");
			return null;
		}
	}
	
	public abstract RepositoryType getType();
	public abstract String getCurrentBranch();
}
