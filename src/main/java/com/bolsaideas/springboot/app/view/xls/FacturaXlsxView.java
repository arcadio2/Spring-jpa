package com.bolsaideas.springboot.app.view.xls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.SheetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;

@Component("factura/ver.xlsx") //no puede haber un componente co nel mismo nombre
public class FacturaXlsxView  extends AbstractXlsxView{

	@Autowired
	private MessageSource messageSource; 
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\" ");
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		Factura factura = (Factura) model.get("factura");
		Sheet sheet = workbook.createSheet("Factura Spring"); 

		Row row = sheet.createRow(0); 
		Cell cell = row.createCell(0); 
		cell.setCellValue(mensajes.getMessage("text.cliente.detalle.titulo"));
		row = sheet.createRow(1);
		cell = row.createCell(1); 
		cell.setCellValue(factura.getCliente().getNombre()+" "+factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio")+factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion")+factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha")+factura.getCreateAt());
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);//le da el color gold 
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //si es solido, etc
		
		CellStyle theaderBody = workbook.createCellStyle();
		theaderBody.setBorderBottom(BorderStyle.THIN);
		theaderBody.setBorderTop(BorderStyle.THIN);
		theaderBody.setBorderRight(BorderStyle.THIN);
		theaderBody.setBorderLeft(BorderStyle.THIN);
	
		
		
		Row header =sheet.createRow(9); 
		header.createCell(0).setCellValue(mensajes.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.factura.form.item.total"));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int i=10;//despues de los datos, fila 10
		for(ItemFactura item:factura.getItems()) {
			Row fila = sheet.createRow(i++);//primero asigna y luego incrementa
			cell = fila.createCell(0);
			fila.createCell(0).setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(theaderBody);
		
			
			cell = fila.createCell(1);
			fila.createCell(1).setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(theaderBody);
			
			
			cell = fila.createCell(2);
			fila.createCell(2).setCellValue(item.getCantidad());
			cell.setCellStyle(theaderBody);
			
			
			cell = fila.createCell(3);
			fila.createCell(3).setCellValue(item.calcularImporte());
			cell.setCellStyle(theaderBody);
			
		}
		
		theaderStyle.setFillForegroundColor(IndexedColors.AQUA.index);
		Row filatotal = sheet.createRow(i); 
		cell = filatotal.createCell(2);
		filatotal.createCell(2).setCellValue("TOTAL: ");
		cell.setCellStyle(theaderStyle);
		cell = filatotal.createCell(3);
		filatotal.createCell(3).setCellValue(factura.getTotal());
		cell.setCellStyle(theaderStyle);
	}

}
