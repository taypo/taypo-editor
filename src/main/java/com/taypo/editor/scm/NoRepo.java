package com.taypo.editor.scm;

public class NoRepo extends IRepo {

	@Override
	public RepositoryType getType() {
		return RepositoryType.NONE;
	}

	@Override
	public String getCurrentBranch() {
		// TODO Auto-generated method stub
		return null;
	}

}
