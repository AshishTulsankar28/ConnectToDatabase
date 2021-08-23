## pdf-merger-demo

POC - Merge multiple files

###### REST endpoints:
```
1. Merge using Apache PDFBox

 - POST | http://localhost:8080/mergePdf/{input folder name}/{output file name}
 where
 input folder name is mandatory, name of folder under resources from which PDF files will be considered for merging.
 output file name is optional, name of merged file which will be stored under target folder. If not specified, current time stamp will be used as filename
 
 For example - http://localhost:8080/mergePdf/reports/my-merged-file
		
2. Merge using iText

 - POST | http://localhost:8080/mergeFiles/{input folder name}/{output file name}
 where
 input folder name is mandatory, name of folder under resources from which PDF files will be considered for merging.
 output file name is optional, name of merged file which will be stored under target folder. If not specified, current time stamp will be used as filename
 
 For example - http://localhost:8080/mergeFiles/reports/my-merged-file
```

###### JMeter Test Plan:
```
pdf-merger-demo.jmx is the executable JMeter test plan included in project directory
```

## See

* [Apache PDFBox](https://pdfbox.apache.org/)
* [iText](https://itextpdf.com/)