package com.merge.pdf.demo.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merge.pdf.demo.model.PdfResponse;
import com.merge.pdf.demo.service.PdfService;

@RestController
public class PdfRestController {

	@Autowired
	private PdfService pdfBoxService;
	
	@Autowired
	private PdfService iTextService;

	@PostMapping(value = {"/mergePdf/{directory}", "/mergePdf/{directory}/{fileName}"})
	public PdfResponse mergeFilesUsingPDFBox(@PathVariable String directory, @PathVariable Optional<String> fileName) {

		String targetFileName;
		if (fileName.isPresent()) {
			targetFileName = fileName.get();
		} else {
			// set current time-stamp as target file name
			targetFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		}

		return pdfBoxService.mergeFiles(directory, targetFileName);
	}
	
	@PostMapping(value = {"/mergeFiles/{directory}", "/mergeFiles/{directory}/{fileName}"})
	public PdfResponse mergeFilesUsingiText(@PathVariable String directory, @PathVariable Optional<String> fileName) {

		String targetFileName;
		if (fileName.isPresent()) {
			targetFileName = fileName.get();
		} else {
			// set current time-stamp as target file name
			targetFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		}

		return iTextService.mergeFiles(directory, targetFileName);
	}

}
