package com.merge.pdf.demo.service;

import com.merge.pdf.demo.model.PdfResponse;

/**
 * PDF utility service used to merge multiple files.
 * @author Ashish Tulsankar
 *
 */
public interface PdfService {

	public PdfResponse mergeFiles(String folderName, String fileName);

}
