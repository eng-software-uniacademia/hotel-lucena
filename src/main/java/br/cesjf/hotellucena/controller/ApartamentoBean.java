/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.hotellucena.controller;

import br.cesjf.hotellucena.dao.ApartamentoDAO;
import br.cesjf.hotellucena.model.Apartamento;
import javax.faces.bean.ViewScoped;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 *
 * @author tassio
 */
@ManagedBean(name = "apartamentosBean")
@ViewScoped
public class ApartamentoBean {
    Apartamento apartamento = new Apartamento();
    List<Apartamento> apartamentos = new ApartamentoDAO().buscarTodas();

    //Métodos dos botões
    @SuppressWarnings("unused")
    public void add() {
        new ApartamentoDAO().persistir(apartamento);
        apartamentos = new ApartamentoDAO().buscarTodas();
        apartamento = new Apartamento();
    }

    public void exclude() {
        new ApartamentoDAO().remover(apartamento);
        apartamentos = new ApartamentoDAO().buscarTodas();
        apartamento = new Apartamento();
    }

    //getters and setters
    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
    }
}
