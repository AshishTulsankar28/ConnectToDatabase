package com.merge.pdf.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PdfHelper {

	private PdfHelper() { }
	
	public static List<URL> getAllFilesAsResourceUrls(String folder) throws IOException {
		List<URL> resourceUrls = new ArrayList<>();

		Resource resource = new ClassPathResource(folder);
		File reportsDirectory = resource.getFile();

		if(!reportsDirectory.isDirectory()) {
			log.warn("{} is not a directory", reportsDirectory);
			return resourceUrls;
		}
		
		File[] files = reportsDirectory.listFiles((directory, fileName) -> fileName.toLowerCase().endsWith(".pdf"));
		for (File file : files) {
			resourceUrls.add(PdfHelper.class.getResource("/"+folder+"/"+file.getName()));
		}

		return resourceUrls;
	}

	public static File[] getAllFiles(String folder) throws IOException {

		Resource resource = new ClassPathResource(folder);
		File reportsDirectory = resource.getFile();

		if(!reportsDirectory.isDirectory()) {
			log.warn("{} is not a directory", reportsDirectory);
			return null;
		}

		return reportsDirectory.listFiles((directory, fileName) -> fileName.toLowerCase().endsWith(".pdf"));
	}

}
