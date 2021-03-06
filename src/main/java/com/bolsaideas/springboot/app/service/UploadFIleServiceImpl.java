package com.bolsaideas.springboot.app.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFIleServiceImpl implements IUploadFileService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private final static String UPLOADS_FOLDER = "uploads"; 
	private IClienteService clienteService;
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = this.getPath(filename); 
		Resource recurso = null; 
		recurso = new UrlResource(pathFoto.toUri());
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cambiar la imagen : "+pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException{
		/*Parta evitar tener mismo nombre*/
		String uniqueFilename = UUID.randomUUID().toString() + "_" +file.getOriginalFilename();
		Path rootPath = this.getPath(uniqueFilename);
			//alternativa
		Files.copy(file.getInputStream(), rootPath);
			/*byte[] bytes = foto.getBytes();
			Path rutaCompleta = Paths.get(roothPath + "//"+ foto.getOriginalFilename()); 
			Files.write(rutaCompleta, bytes); */
			
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = this.getPath(filename);
		File archivo = rootPath.toFile();
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				
				return true;
			}
		}
		return false; 
	}
	
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		// TODO Auto-generated method stub
		Files.createDirectories(Paths.get(UPLOADS_FOLDER));
	}

}
