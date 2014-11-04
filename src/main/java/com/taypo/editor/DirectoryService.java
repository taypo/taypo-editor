package com.taypo.editor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api")
public class DirectoryService {
	//TODO move these to config class
	@Value("${serve-path:.}")
	private String servePath;
	
	@Value("#{'${ignore-filenames}'.split(',')}")
	//@Value("${ignore.filenames}")
	private List<String> igonoredFileNames = new ArrayList<>();
	
	private Path root;

	@RequestMapping("/tree")
	public List<Node> index(@RequestParam(defaultValue="") String relPath) {
		root = Paths.get(servePath);
		Path absPath = root.resolve(relPath);

		List<Node> nodes = new ArrayList<>();
		//if not root directory, add a parent node
		if(!absPath.equals(root))
			nodes.add(new Node("..", root.relativize(absPath.getParent()).toString(), true));
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(absPath)) {
			for (Path path : directoryStream) {
				if(check(path))
					nodes.add(pathToNode(path));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Collections.sort(nodes);
		return nodes;
	}
	
	private boolean check(Path path) {
		if(igonoredFileNames.contains(path.getFileName().toString()))
			return false;
		return true;
	}
	
	private Node pathToNode(Path path) {
		Node node = new Node();
		node.setName(path.getFileName().toString());
		node.setDirectory(Files.isDirectory(path));
		node.setRelativePath(root.relativize(path).toString());
		return node;
	}


}
