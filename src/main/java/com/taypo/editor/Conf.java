package com.taypo.editor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {
	@Value("${serve-path:.}")
	private String servePath;
	
	@Value("#{'${ignore-filenames}'.split(',')}")
	private List<String> igonoredFileNames = new ArrayList<>();

	public String getServePath() {
		return servePath;
	}

	public void setServePath(String servePath) {
		this.servePath = servePath;
	}

	public List<String> getIgonoredFileNames() {
		return igonoredFileNames;
	}

	public void setIgonoredFileNames(List<String> igonoredFileNames) {
		this.igonoredFileNames = igonoredFileNames;
	}
}
