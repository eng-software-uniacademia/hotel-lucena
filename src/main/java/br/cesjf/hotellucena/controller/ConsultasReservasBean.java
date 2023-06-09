/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.hotellucena.controller;

import br.cesjf.hotellucena.dao.ReservasDAO;
import br.cesjf.hotellucena.model.Reservas;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import java.time.Duration;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "consultaReservasBean")
@SessionScoped
@SuppressWarnings("unused")
public class ConsultasReservasBean {

    Reservas reserva = new Reservas();

    List<Reservas> reservas = new ReservasDAO().buscarAtivos();

    //Métodos dos botões
    @SuppressWarnings("unused")
    public void add() {
        Reservas r = new Reservas();
        Duration duracao = Duration.between(reserva.getDataEntrada().toInstant(), reserva.getDataSaida().toInstant());
        if (!duracao.isNegative() && !duracao.isZero()) {
            double valor = r.camaExtra(reserva);
            if (valor != 0.0) {
                reserva.setValorPago(valor);
                new ReservasDAO().persistir(reserva);
                reservas = new ReservasDAO().buscarAtivos();
                reserva = new Reservas();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Quantidade de Hóspedes inválida"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Data selecionada inválida"));
        }
    }

    public void exclude() {
        new ReservasDAO().remover(reserva);
        reservas = new ReservasDAO().buscarAtivos();
        reserva = new Reservas();
    }

    public void checkin() {
        new ReservasDAO().checkin(reserva.getCodigoReserva());
        reservas = new ReservasDAO().buscarAtivos();
        reserva = new Reservas();
    }

    public void checkout() {
        new ReservasDAO().checkout(reserva.getCodigoReserva());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Check out", "Saída Concluída"));
        reservas = new ReservasDAO().buscarAtivos();
        reserva = new Reservas();
    }

    //getters and setters
    public Reservas getReservas() {
        return reserva;
    }

    public void setReservas(Reservas reserva) {
        this.reserva = reserva;
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

    public List<Reservas> buscarReservasUsuario(int id) {
        return new ReservasDAO().buscarReservas(id);
    }

    public List<Reservas> buscarReservasApartamento(int id) {
        return new ReservasDAO().buscarReservasApartamento(id);
    }
        
}
