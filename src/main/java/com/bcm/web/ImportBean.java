package com.bcm.web;

import java.io.IOException;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.ImportService;

@ManagedBean(name = "importBean")
@ViewScoped
@SuppressWarnings("serial")
public class ImportBean implements Serializable {

	private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup()
			.lookupClass());

	public void doImport(FileUploadEvent event) throws IOException, ParseException {
		Path dest = null;
		try {
			log.info("Upload customer data");
			String fileName = event.getFile().getFileName();
			dest = Paths.get("/tmp", fileName);
			// Remove previous if exists
			if (Files.exists(dest)) {
				Files.delete(dest);
			}

			Files.copy(event.getFile().getInputstream(), dest);

			getImportService().importData(dest);

			Messages.create("successImport").add();
		} finally {
			Files.delete(dest);
		}
	}

	private ImportService getImportService() {
		return SpringApplicationContext.getBean("ImportService",
				ImportService.class);
	}
}