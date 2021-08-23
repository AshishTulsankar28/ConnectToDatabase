package com.merge.pdf.demo.model;

import lombok.Data;

/**
 * Response model for pdf merge utility
 * @author Ashish Tulsankar
 *
 */
@Data
public class PdfResponse {

	private boolean hasError;
	private String description;
	private String filePath;
	private String fileSize;
	private int numberOfMergedFiles;
	private String mergeTime;
	private String writeTime;

}
