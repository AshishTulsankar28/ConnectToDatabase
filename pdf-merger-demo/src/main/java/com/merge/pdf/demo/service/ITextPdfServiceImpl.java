package com.merge.pdf.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.merge.pdf.demo.model.PdfResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Refer {@link https://itextpdf.com/}
 * @author Ashish Tulsankar
 *
 */
@Slf4j
@Service("iTextService")
public class ITextPdfServiceImpl implements PdfService {

	private String outputFolder = System.getProperty("user.dir") + "/target/";

	@Override
	public PdfResponse mergeFiles(String folderName, String fileName) {

		PdfResponse response = new PdfResponse();
		long startTime, endTime, duration;

		try {

			fileName = outputFolder + fileName + ".pdf";
			Document document = new Document();
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(fileName));
			copy.setCompressionLevel(9);

			List<URL> fileUrls = PdfHelper.getAllFilesAsResourceUrls(folderName);

			if(fileUrls.isEmpty()) {
				log.warn("{} directory dont have valid pdfs", folderName);
				return response;
			}

			log.info("Started merging files...");
			startTime = System.nanoTime();
			document.open();
			
			for (URL file : fileUrls){
				PdfReader reader = new PdfReader(file);
				copy.addDocument(reader);
				copy.freeReader(reader);
				reader.close();
			}
			
			endTime = System.nanoTime();
			duration = ((endTime - startTime) / 1000000);
			log.info("Finished merging files in {} ms", duration);
			response.setMergeTime(duration + " milliseconds");

			startTime = System.nanoTime();
			document.close();
			endTime = System.nanoTime();
			duration = ((endTime - startTime) / 1000000);
			log.info("Saved file in {} ms", duration);
			response.setWriteTime(duration + " milliseconds");
			

			File mergedPdf = new File(fileName);
			response.setFileSize(Math.round(((double)mergedPdf.length() / (1024 * 1024)) * 100.0) / 100.0 + " megabytes");
			response.setFilePath(fileName);
			response.setNumberOfMergedFiles(fileUrls.size());
			response.setDescription("Files merged using iText");

		} catch (DocumentException | IOException e) {
			response.setHasError(true);
			log.error("Unable to merge files. Details {} ", e);
		} 

		return response;
	}

}
