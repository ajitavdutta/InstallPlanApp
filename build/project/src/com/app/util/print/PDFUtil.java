package com.app.util.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.app.util.AppUtility;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.html.Tags;

public class PDFUtil implements PdfPCellEvent {

	private String fldName;
	
	public PDFUtil() {
		super();
		this.fldName = new String();
	}

	public PDFUtil(String fldName) {
		super();
		this.fldName = fldName;
	}

	public void CreateTemplatePDF() {
		try {
			// Step 1 - Create the required file folders.
			File file = new File(AppUtility.getProperty("TPLT-FILE"));
			file.getParentFile().mkdirs();

			// Step 2 - Open the document
			Document document = new Document(PageSize.A4, 36, 36, 54, 36);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			writer.setInitialLeading(5);
			document.open();

			// Step 3 - Add the Header Table
			PdfPTable headerTable = new PdfPTable(2);
			headerTable.setWidths(new int[] { 24, 24 });
			headerTable.setTotalWidth(document.getPageSize().getWidth() - 60);
			headerTable.setLockedWidth(true);

			Image logo = Image.getInstance("file:resources/images/Header_Image1.png");
			logo.setAlignment(Element.ALIGN_CENTER);
			logo.setAlignment(Image.MIDDLE);
			logo.scaleAbsoluteHeight(20);
			logo.scaleAbsoluteWidth(20);
			logo.scalePercent(20);
			Chunk chunk = new Chunk(logo, 0, -14);

			PdfPCell cell = new PdfPCell(new Phrase(chunk));
			cell.setFixedHeight(20);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell);

			logo = Image.getInstance("file:resources/images/ANZ_Logo.png");
			logo.setAlignment(Element.ALIGN_CENTER);
			logo.setAlignment(Image.MIDDLE);
			logo.scaleAbsoluteHeight(10);
			logo.scaleAbsoluteWidth(20);
			logo.scalePercent(5);
			chunk = new Chunk(logo, 0, -14);

			cell = new PdfPCell(new Phrase(chunk));
			cell.setFixedHeight(20);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			headerTable.addCell(cell);

			headerTable.writeSelectedRows(0, -1, document.leftMargin(), document.top() + 20, writer.getDirectContent());

			// Step 4 - Add the Body textField
			Rectangle rect = new Rectangle(document.left(), document.bottom() + 10, document.right() + 11,
					document.top() - 10);
			TextField tf = new TextField(writer, rect, "body");
			tf.setOptions(TextField.MULTILINE);
			writer.addAnnotation(tf.getTextField());

			// Step 5 - Add the Footer table
			PdfPTable footerTable = new PdfPTable(2);
			footerTable.setWidths(new int[] { 2, 1 });
			footerTable.setTotalWidth(document.getPageSize().getWidth() - 60);
			footerTable.setLockedWidth(true);

			cell = new PdfPCell();
			cell.setFixedHeight(15);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.TOP);
			cell.setCellEvent(new PDFUtil("from"));
			footerTable.addCell(cell);

			cell = new PdfPCell();
			cell.setFixedHeight(15);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.TOP);
			cell.setCellEvent(new PDFUtil("footer"));
			footerTable.addCell(cell);

			footerTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottom(), writer.getDirectContent());

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
		PdfWriter writer = canvases[0].getPdfWriter();
		TextField txtFld = new TextField(writer, position, fldName);
		txtFld.setFontSize(8);
		txtFld.setOptions(TextField.MULTILINE);
		txtFld.setAlignment(Element.ALIGN_LEFT);
		txtFld.setMaxCharacterLength(100);
		try {
			writer.addAnnotation(txtFld.getTextField());
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}

	}

	public void createPDFInstallPlan(File pdfFileName, String htmlFileName, String sdmNo, String sdmSummary)
			throws IOException, DocumentException {
		FillTemplateHelper template = new FillTemplateHelper(AppUtility.getProperty("TPLT-FILE"));
		template.setSender(
				"Version: 0.1 | %s | Classification: Confidential.\n" + sdmNo + " Install Plan | " + sdmSummary);

		// step 1
		Document document = new Document(template.getPageSize(), template.getmLeft(), template.getmRight(),
				template.getmTop(), template.getmBottom());

		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
		writer.setPageEvent(template);

		// step 3
		document.open();

		// step 4
		ElementList elements = FillTemplateHelper.parseHtml(htmlFileName, AppUtility.getProperty("CSS-FILE"),
				Tags.getHtmlTagProcessorFactory());
		for (Element e : elements) {
			document.add(e);
		}

		// step 5
		document.close();
	}

}
