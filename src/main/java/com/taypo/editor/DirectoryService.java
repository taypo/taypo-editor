package com.taypo.editor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api")
public class DirectoryService {

	@Autowired
	Conf conf;
	
	@RequestMapping("/tree")
	public List<Node> index(@RequestParam(defaultValue="") String relPath) {
		Path root = Paths.get(conf.getServePath());
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
	
	@RequestMapping(value="/resource", produces="text/html; charset=utf-8") 
	public String getResource(@RequestParam String path) {
		return readFile(path);
	}
	
	private boolean check(Path path) {
		if(conf.getIgonoredFileNames().contains(path.getFileName().toString()))
			return false;
		return true;
	}
	
	private Node pathToNode(Path path) {
		Path root = Paths.get(conf.getServePath());
		Node node = new Node();
		node.setName(path.getFileName().toString());
		node.setDirectory(Files.isDirectory(path));
		node.setRelativePath(root.relativize(path).toString());
		return node;
	}

	private String readFile(String path) {
		Path root = Paths.get(conf.getServePath());
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(root.resolve(path));
			return new String(encoded, StandardCharsets.UTF_8);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
		
	}
}
