package com.app.util.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.webfirmframework.wffweb.io.OutputBuffer;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.H1;
import com.webfirmframework.wffweb.tag.html.H2;
import com.webfirmframework.wffweb.tag.html.H3;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.P;
import com.webfirmframework.wffweb.tag.html.attribute.Align;
import com.webfirmframework.wffweb.tag.html.attribute.Width;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Option;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Select;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.tables.Table;
import com.webfirmframework.wffweb.tag.html.tables.Td;
import com.webfirmframework.wffweb.tag.html.tables.Tr;
import com.webfirmframework.wffweb.tag.htmlwff.Blank;
import com.webfirmframework.wffweb.view.AbstractHtmlView;

@SuppressWarnings({ "unused", "serial" })
public class InstallPlanHTMLView extends AbstractHtmlView {

	private static final long serialVersionUID = 5440664708215363551L;

	private DeviceType deviceType;
	private Html doc;

	public static enum DeviceType {
		MOBILE, MOBILE_5_INCH, MOBILE_5_5_INCH, TABLET, BROWSER;
		private DeviceType() {
		}
	}

	public InstallPlanHTMLView(DeviceType deviceType) {
		this.setDeviceType(deviceType);
	}

	
	@Override
	public void develop(OutputBuffer out) {
		Tr tr = null;
		Td td = null;
		
		doc = new Html(null) {
			Head head = new Head(this);
			Body body = new Body(this) {
				H1 h1 = new H1(this, new Align("center")) {
					Blank content = new Blank(this, "SDM $sdm-number INSTALLATION PLAN");
				};
				
				Table dTable = descrTable(this);
				
				Div div1 = new Div(this, new ClassAttribute("pagebreak"));
				
				H2 h21 = new H2(this){
					Blank content = new Blank(this, "1.	PRIOR TO INSTALL");
				};
				
				
				Div div2 = new Div(this, new ClassAttribute("pagebreak"));
				H2 h22 = new H2(this){
					Blank content = new Blank(this, "2.	INSTALLATION");
				};
				
				Div div3 = new Div(this, new ClassAttribute("pagebreak"));
				H2 h23 = new H2(this){
					Blank content = new Blank(this, "3.	VERIFICATION");
				};
				
				Div div4 = new Div(this, new ClassAttribute("pagebreak"));
				H2 h24 = new H2(this){
					Blank content = new Blank(this, "4.	 BACK-OUT");
				};
				
				Select select = new Select(this){
					Option opt1 = new Option(this, new CustomAttribute("value", "Yes")){ 
						Blank content = new Blank(this, "Yes");
					};
					
					Option opt2 = new Option(this, new CustomAttribute("value", "No")){ 
						Blank content = new Blank(this, "No");
					};
					
					Option opt3 = new Option(this, new CustomAttribute("value", "N/A")){ 
						Blank content = new Blank(this, "N/A");
					};
				};

			};

		};
		doc.setPrependDocType(true);
		out.append(doc);
	}
	
	
	/**
	 * Will create the main SDM Description Table which will be added to the 1st page.
	 * 
	 * @param html - AbstractHtml element where the table will be added.
	 * @return descrTable - The SDM Description Table.
	 */
	public Table descrTable(AbstractHtml html){
		Table descrTable = new Table(html, new Align("center"), new CustomAttribute("border", "1px")){
			Tr tr1 = new Tr(this){
				Td td11 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "SDM Number"); 
				};
				
				Td td12 = new Td(this ){
					Blank content = new Blank(this, "$sdm-number"); 
				};
				
				Td td13 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "PrimeCode/Sub-Vol Ref"); 
				};
				
				Td td14 = new Td(this ){
					Blank content = new Blank(this, "$primecode-userchange"); 
				};
			}; 
			
			Tr tr4 = new Tr(this){
				Td td41 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "Install Date"); 
				};
				
				Td td42 = new Td(this ){
					Blank content = new Blank(this, "$install-date"); 
				};
				
				Td td43 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "Install Time"); 
				};
				
				Td td44 = new Td(this ){
					Blank content = new Blank(this, "$install-time"); 
				};
			};
			
			Tr tr5 = new Tr(this){						
				Td td41 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "Prod Sys"); 
				};
				
				Td td42 = new Td(this ){
					Blank content = new Blank(this, "$prod-sys"); 
				};
				
				Td td43 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "DR Sys"); 
				};
				
				Td td44 = new Td(this ){
					Blank content = new Blank(this, "$dr-sys"); 
				};
			};
			
			Tr tr2 = new Tr(this){
				Td td21 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "SDM Summary"); 
				};
				
				Td td22 = new Td(this , new CustomAttribute("colspan", "3")){
					Blank content = new Blank(this, "$sdm-summary"); 
				};
			}; 
			
			Tr tr3 = new Tr(this){
				Td td31 = new Td(this , new ClassAttribute("textHighlightCenter")){
					Blank content = new Blank(this, "Work Request"); 
				};
				
				Td td32 = new Td(this , new CustomAttribute("colspan", "3")){
					Blank content = new Blank(this, "$work-request"); 
				};
			};
			
			Tr tr6 = new Tr(this){
				Td td51 = new Td(this,new CustomAttribute("colspan", "4")){
					Blank content = new Blank(this, "This document contains the steps required for GAMS Tandem and PSSC to Install, Verify, and Backout SDM Change Order $sdm-number, as required for Work request $work-request.<br/>"
							+ "The 6-digit primecode/subvolume changes, and is generally a derivative of the SDM number or work request number. It is used in the naming convention for the location of script files used during the steps to be executed."
							+ "The live (Production) system is currently $prod-sys and the backup (DR) is $dr-sys."){
					};
				};
			};
		};
		return descrTable;
	}

	public Style textHighlight() {
		Style style = new Style();
		style.addCssProperty("background-color", "#b3cce6");
		style.addCssProperty("padding", "10px");
		style.addCssProperty("font-weight", "bold");
		return style;
	}
	
	public Style textHighlightCenter() {
		Style style = new Style();
		style.addCssProperty("background-color", "#b3cce6");
		style.addCssProperty("padding", "10px");
		style.addCssProperty("font-weight", "bold");
		style.addCssProperty("text-align", "center");
		return style;
	}
	
	public Style tdStyle(){
		Style style = new Style();
		style.addCssProperty("padding", "10px");
		style.addCssProperty("text-align", "left");
		return style;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public void createHTMLFile(String file) {
		try {
			File f = new File(file);
			f.getParentFile().mkdirs();
			this.doc.toOutputStream(new FileOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
