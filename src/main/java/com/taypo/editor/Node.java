package com.taypo.editor;

public class Node implements Comparable<Node> {
	private String name;
	private String relativePath;
	private boolean isDirectory;
	private boolean binary;

	public Node() {
	}

	public Node(String name, String relativePath, boolean isDirectory, boolean binary) {
		super();
		this.name = name;
		this.relativePath = relativePath;
		this.isDirectory = isDirectory;
		this.binary = binary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public boolean isBinary() {
		return binary;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
	}

	@Override
	public int compareTo(Node o) {
		if (isDirectory && !o.isDirectory)
			return -1;
		if (!isDirectory && o.isDirectory)
			return 1;
		return name.compareTo(o.name);
	}

}
