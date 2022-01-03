package com.bolsaideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


/*Hay un parametro que nos renderiza ya sea pdf, pagina, excel, etc*/
@Component("factura/ver")
public class FacturaPdfView  extends AbstractPdfView{
	
	@Autowired
	private MessageSource messageSource; 
	@Autowired
	private LocaleResolver localeResolver; 
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		Locale locale = localeResolver.resolveLocale(request);
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		Factura factura = (Factura) model.get("factura"); 
		PdfPTable tabla = new PdfPTable(1); 
		tabla.setSpacingAfter(20);
		PdfPCell cell = null;
		cell =  new PdfPCell(new Phrase(messageSource.getMessage("text.cliente.detalle.titulo", null,locale) ));
		
		cell.setBackgroundColor(new Color(184,218,255) );
		cell.setPadding(8f);
		
		tabla.addCell(cell);
		tabla.addCell(factura.getCliente().getNombre() + " "+factura.getCliente());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1); 
		tabla2.setSpacingAfter(20);
		
		cell =  new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null,locale) ));
		cell.setBackgroundColor(new Color(195,230,203) );
		cell.setPadding(8f);
		
		tabla2.addCell(cell);
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.folio")+factura.getId());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.descripcion")+factura.getDescripcion());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.fecha")+factura.getCreateAt());
		
		PdfPTable tabla3 = new PdfPTable(4); 
		tabla3.setWidths(new float [] {2.5f,1,1,1}); //medidas columnas en relacion
		
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.total"));
		
		for(ItemFactura p: factura.getItems()) {
			tabla3.addCell(p.getProducto().getNombre());
			tabla3.addCell(p.getProducto().getPrecio().toString());
			
			cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null,locale) ));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			
			tabla3.addCell(p.calcularImporte().toString());
		}
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(factura.getTotal().toString());
		
		document.addTitle("Factura");
		document.add(tabla); 
		document.add(tabla2);
		document.add(tabla3);
		
		
	}

	
	
}
