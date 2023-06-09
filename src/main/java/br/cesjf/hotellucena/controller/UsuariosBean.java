/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.hotellucena.controller;

import br.cesjf.hotellucena.dao.UsuariosDAO;
import br.cesjf.hotellucena.model.Usuarios;
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
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author tassio
 */
@ManagedBean(name = "usuariosBean")
@SuppressWarnings("unused")
@ViewScoped
public class UsuariosBean {
    Usuarios usuario = new Usuarios();
    List<Usuarios> usuarios = new UsuariosDAO().buscarTodas();

    //Métodos dos botões 
    public void add() {
        new UsuariosDAO().persistir(usuario);
        usuarios = new UsuariosDAO().buscarTodas();
        usuario = new Usuarios();
    }

    public void exclude() {
        new UsuariosDAO().remover(usuario);
        usuarios = new UsuariosDAO().buscarTodas();
        usuario = new Usuarios();
    }

    //getters and setters
    public Usuarios getUsuarios() {
        return usuario;
    }

    public void setUsuarios(Usuarios usuario) {
        this.usuario = usuario;
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
