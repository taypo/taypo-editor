package com.taypo.editor;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class DirectoryService {

	@Autowired
	Conf conf;

	// TODO: for all methods here, decide if it accepts absolute or relative
	// path, make all same.

	@RequestMapping("/tree")
	public List<Node> index(@RequestParam(defaultValue = "") String relPath) {
		Path root = Paths.get(conf.getServePath());
		Path absPath = root.resolve(decodeURL(relPath));

		List<Node> nodes = new ArrayList<>();
		// if not root directory, add a parent node
		if (!absPath.equals(root))
			nodes.add(new Node("..", root.relativize(absPath.getParent()).toString(), true, false));
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(absPath)) {
			for (Path path : directoryStream) {
				if (check(path))
					nodes.add(pathToNode(path));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Collections.sort(nodes);
		return nodes;
	}

	@RequestMapping("/node")
	public Node getNode(@RequestParam(defaultValue = "") String relPath) {
		Path root = Paths.get(conf.getServePath());
		Path absPath = root.resolve(decodeURL(relPath));
		return pathToNode(absPath);
	}

	@RequestMapping(value = "/resource", produces = "text/html; charset=utf-8")
	public String getResource(@RequestParam String path) {
		return readFile(path);
	}

	@RequestMapping(value = "/resource", method = RequestMethod.PUT)
	public void setResource(@RequestParam String path, @RequestBody String content) {
		saveFile(path, content);
	}

	private void saveFile(String path, String content) {
		Path root = Paths.get(conf.getServePath());
		try {
			Files.write(root.resolve(path), content.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean check(Path path) {
		if (conf.getIgonoredFileNames().contains(path.getFileName().toString()))
			return false;
		return true;
	}

	private Node pathToNode(Path path) {
		Path root = Paths.get(conf.getServePath());
		Node node = new Node();
		node.setName(path.getFileName().toString());
		node.setDirectory(Files.isDirectory(path));
		node.setRelativePath(root.relativize(path).toString());
		if (node.isDirectory())
			node.setBinary(false);
		else
			node.setBinary(isBinaryFile(path));
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

	private String decodeURL(String url) {
		try {
			return URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Guess whether given file is binary. Just checks for anything under 0x09.
	 * TODO either this or whole Nodes should be cached
	 */
	private boolean isBinaryFile(Path path) {
		try {
			Path absPath = Paths.get(conf.getServePath()).resolve(path);
			long size = Files.size(absPath);
			int readLen = (size > 1024) ? 1024 : (int) size;

			byte[] data = new byte[readLen];
			InputStream in = Files.newInputStream(absPath);
			in.read(data);
			in.close();

			int ascii = 0;
			int other = 0;

			for (int i = 0; i < data.length; i++) {
				byte b = data[i];
				if (b < 0x09)
					return true;

				if (b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D)
					ascii++;
				else if (b >= 0x20 && b <= 0x7E)
					ascii++;
				else
					other++;
			}

			if (other == 0)
				return false;

			return 100 * other / (ascii + other) > 95;
		} catch (IOException ex) {
			return true;
		}
	}
}
