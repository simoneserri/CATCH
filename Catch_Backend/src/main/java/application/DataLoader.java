package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.FotoRepository;
import application.GattoRepository;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class DataLoader implements org.springframework.boot.CommandLineRunner {
	
	@Autowired
    private FotoRepository fotoRepository;
	
	@Autowired
    private GattoRepository gattoRepository;
	 @Override
    public void run(String... args) throws Exception {
		 if(gattoRepository.count()==0) {
			fotoRepository.deleteAll(); 
			caricaGatti();
    		caricaFoto();
		 }
		 System.out.println("Dati caricati");
    }
    
    
    
	 private void caricaGatti() throws Exception {
		 ObjectMapper mapper = new ObjectMapper();

		    ClassPathResource resource = new ClassPathResource("data/gatti.json");

		    try (InputStream is = resource.getInputStream()) {
		        List<Gatto> gatti = Arrays.asList(mapper.readValue(is, Gatto[].class));
		        gattoRepository.saveAll(gatti);
		        System.out.println(gatti.size() + " gatti caricati dal JSON nel database.");
		    } catch (IOException e) {
		        System.err.println("Errore nel caricamento di data/gatti.json: " + e.getMessage());
		    }
		}

		
    
    
    private void caricaFoto() throws Exception {
    	        List<Gatto> gatti = gattoRepository.findAll();
    	        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    	        for (Gatto gatto : gatti) {
    	            int id = gatto.getID();
    	            String pattern = "classpath:/images/" + id + "_*.jpg";
    	            Resource[] resources = resolver.getResources(pattern);
    	            for (Resource resource : resources) {
    	                try (InputStream is = resource.getInputStream()) {
    	                    byte[] bytes = is.readAllBytes();
    	                    Foto foto = new Foto();
    	                    foto.setFoto(bytes);
    	                    foto.setGatto(gatto);
    	                    fotoRepository.save(foto);
    	                    System.out.println("Foto salvata per gatto ID " + id + ": " + resource.getFilename());
    	                } catch (IOException e) {
    	                    System.err.println("Errore nel leggere l'immagine " + resource.getFilename() + ": " + e.getMessage());
    	                }
    	            }
    	        }
    	}
}