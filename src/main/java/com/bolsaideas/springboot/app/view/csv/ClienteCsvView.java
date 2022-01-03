package com.bolsaideas.springboot.app.view.csv;

import java.io.FileWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.bolsaideas.springboot.app.models.entity.Cliente;

//como tenemos una sola clase con nombre listart no es necesario cambiar la extension
@Component("listar")//dando nombre creamos la vista en formato de clase
public class ClienteCsvView extends AbstractView{

	
	
	public ClienteCsvView() {
		setContentType("text/csv");//de tipo csv
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;//genera contenido descargable
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\" ");
		response.setContentType(getContentType()); //ese get y set es de la clase abstractview
		
		@SuppressWarnings("unchecked")
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		
		/*TOma una clase entity y la crea en una lñinea en el archivo plano*/
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] header= {"id","nombre","apellido","email","createAt"};
		beanWriter.writeHeader(header);
		
		for(Cliente cliente: clientes) {
			beanWriter.write(cliente, header); 
		}
		beanWriter.close();
		
	} //aqui heredamos de una vista mas arriba en la herencia

}
