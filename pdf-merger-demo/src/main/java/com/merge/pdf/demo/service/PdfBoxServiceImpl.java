package com.merge.pdf.demo.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import com.merge.pdf.demo.model.PdfResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Refer {@link https://pdfbox.apache.org/} 
 * @author Ashish Tulsankar
 *
 */

@Slf4j
@Service("pdfBoxService")
public class PdfBoxServiceImpl implements PdfService {

	private String outputFolder = System.getProperty("user.dir") + "/target/";

	@Override
	public PdfResponse mergeFiles(String folderName, String fileName) {

		PdfResponse response = new PdfResponse();
		PDDocument desPDDoc = null;
		long startTime, endTime, duration;
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		pdfMerger.setDocumentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);

		try {

			fileName = outputFolder + fileName + ".pdf";
			File[] pdfs = PdfHelper.getAllFiles(folderName);

			if(null == pdfs || pdfs.length == 0) {
				log.warn("{} directory dont have valid pdfs", folderName);
				return response;
			}

			
			log.info("Started merging files...");
			startTime = System.nanoTime();
			desPDDoc = readAndMerge(desPDDoc, pdfMerger, pdfs);
			endTime = System.nanoTime();	
			duration = ((endTime - startTime) / 1000000);
			log.info("Finished merging files in {} ms", duration);
			response.setMergeTime(duration + " milliseconds");

			startTime = System.nanoTime();
			if (null != desPDDoc) desPDDoc.save(fileName);
			endTime = System.nanoTime();	
			duration = ((endTime - startTime) / 1000000);
			log.info("Saved PDF to its destination in {} ms", duration);
			response.setWriteTime(duration + " milliseconds");

			File mergedPdf = new File(fileName);
			response.setFileSize(Math.round(((double)mergedPdf.length() / (1024 * 1024)) * 100.0) / 100.0 + " megabytes");
			response.setFilePath(fileName);
			response.setNumberOfMergedFiles(pdfs.length);
			response.setDescription("Files merged by appending files with unrestricted main memory using Apache PDFBox");

		} catch (IOException e) {
			log.error("Exception merging files {} ", e);
			response.setHasError(true);
		} finally {
			try {
				if (desPDDoc != null) {
					desPDDoc.close();
				}
			} catch (IOException ioe) {
				log.error("Exception closing merged file {}", ioe);
			}
		}

		return response;
	}

	private PDDocument readAndMerge(PDDocument desPDDoc, PDFMergerUtility pdfMerger, File[] pdfs) throws IOException {
		boolean isFirstDocMerged = false;
		for (File file :pdfs) {

			PDDocument doc = null;

			try {
				if (isFirstDocMerged) {
					doc = PDDocument.load(file);
					pdfMerger.appendDocument(desPDDoc, doc);
				} else {
					desPDDoc = PDDocument.load(file);
					isFirstDocMerged = true;
				}
			} catch (IOException ioe) {
				log.warn("Invalid PDF detected: {} {}", file.getName(), ioe);
			} finally {
				if (doc != null) {
					doc.close();
				}
			}
		}
		return desPDDoc;
	}

}
