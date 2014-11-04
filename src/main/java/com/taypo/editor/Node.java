package com.taypo.editor;

public class Node implements Comparable<Node> {
	private String name;
	private String relativePath;
	private boolean isDirectory;

	public Node() {
	}

	public Node(String name, String relativePath, boolean isDirectory) {
		super();
		this.name = name;
		this.relativePath = relativePath;
		this.isDirectory = isDirectory;
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

	@Override
	public int compareTo(Node o) {
		if (isDirectory && !o.isDirectory)
			return -1;
		if (!isDirectory && o.isDirectory)
			return 1;
		return name.compareTo(o.name);
	}

}
