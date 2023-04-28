/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.hotellucena.model;

import br.cesjf.hotellucena.decorator.ServicosExtras;
import br.cesjf.hotellucena.dao.ApartamentoDAO;
import br.cesjf.hotellucena.dao.ReservasDAO;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tassio
 */
@Entity
@Table(name = "reservas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r"),
    @NamedQuery(name = "Reservas.findByCodigoReserva", query = "SELECT r FROM Reservas r WHERE r.codigoReserva = :codigoReserva"),
    @NamedQuery(name = "Reservas.findByDataEntrada", query = "SELECT r FROM Reservas r WHERE r.dataEntrada = :dataEntrada"),
    @NamedQuery(name = "Reservas.findByDataSaida", query = "SELECT r FROM Reservas r WHERE r.dataSaida = :dataSaida"),
    @NamedQuery(name = "Reservas.findByNumeroHospedes", query = "SELECT r FROM Reservas r WHERE r.numeroHospedes = :numeroHospedes"),
    @NamedQuery(name = "Reservas.findByStatus", query = "SELECT r FROM Reservas r WHERE r.status = :status"),
    @NamedQuery(name = "Reservas.findByValorPago", query = "SELECT r FROM Reservas r WHERE r.valorPago = :valorPago")})
public class Reservas extends ServicosExtras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoReserva")
    private Integer codigoReserva;
    @Basic(optional = false)
    @Column(name = "dataEntrada")
    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    @Column(name = "dataSaida")
    @Temporal(TemporalType.DATE)
    private Date dataSaida;
    @Basic(optional = false)
    @Column(name = "numeroHospedes")
    private int numeroHospedes;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorPago")
    private Double valorPago;
    @JoinColumn(name = "Apartamento_codigoApartamento", referencedColumnName = "codigoApartamento")
    @ManyToOne(optional = false)
    private Apartamento apartamentocodigoApartamento;
    @JoinColumn(name = "usuarios_codUsuario", referencedColumnName = "codUsuario")
    @ManyToOne(optional = false)
    private Usuarios usuarioscodUsuario;
    
    public Reservas() {
    }

    public Reservas(Integer codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Reservas(Integer codigoReserva, Date dataEntrada, int numeroHospedes, String status) {
        this.codigoReserva = codigoReserva;
        this.dataEntrada = dataEntrada;
        this.numeroHospedes = numeroHospedes;
        this.status = status;
    }

    public Integer getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(Integer codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getNumeroHospedes() {
        return numeroHospedes;
    }

    public void setNumeroHospedes(int numeroHospedes) {
        this.numeroHospedes = numeroHospedes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Apartamento getApartamentocodigoApartamento() {
        return apartamentocodigoApartamento;
    }

    public void setApartamentocodigoApartamento(Apartamento apartamentocodigoApartamento) {
        this.apartamentocodigoApartamento = apartamentocodigoApartamento;
    }

    public Usuarios getUsuarioscodUsuario() {
        return usuarioscodUsuario;
    }

    public void setUsuarioscodUsuario(Usuarios usuarioscodUsuario) {
        this.usuarioscodUsuario = usuarioscodUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoReserva != null ? codigoReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservas)) {
            return false;
        }
        Reservas other = (Reservas) object;
        if ((this.codigoReserva == null && other.codigoReserva != null) || (this.codigoReserva != null && !this.codigoReserva.equals(other.codigoReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.cesjf.hotellucena.model.Reservas[ codigoReserva=" + codigoReserva + " ]";
    }

    @Override
    public Double camaExtra(Reservas reserva) {
        Double total = new Double(0);

        if (reserva.getNumeroHospedes() <= reserva.getApartamentocodigoApartamento().getCategoriacodigoCategoria().getCapacidade()) {
            total = reserva.getApartamentocodigoApartamento().getCategoriacodigoCategoria().getValorDiaria() * 1.0;
        } else if (reserva.getNumeroHospedes() == reserva.getApartamentocodigoApartamento().getCategoriacodigoCategoria().getCapacidade() + 1) {
            total = reserva.getApartamentocodigoApartamento().getCategoriacodigoCategoria().getValorDiaria() * 1.3;
        } else {
            return 0.0;
        }

        return total;
    }

    public long permanencia(Reservas reserva) {
        Duration duracao = Duration.between(reserva.getDataEntrada().toInstant(), reserva.getDataSaida().toInstant());
        return duracao.toDays();
    }

    public Double totalRecebido() {

        Double total = new Double(0);

        Reservas reserva = new Reservas();

        List reservas = new ArrayList();
        reservas = new ReservasDAO().buscarTodas();

        for (Object r : reservas) {
            reserva = (Reservas) r;
            total += reserva.getValorPago();

        }

        return total;

    }

    public int totalHospedes() {

        int total = 0;

        Reservas reserva = new Reservas();

        List reservas = new ArrayList();
        reservas = new ReservasDAO().buscarTodas();

        for (Object r : reservas) {
            reserva = (Reservas) r;
            total += reserva.getNumeroHospedes();

        }

        return total;

    }

    public int totalOcupados() {

        int total = 0;

        Reservas reserva = new Reservas();

        List reservas = new ArrayList();
        reservas = new ReservasDAO().buscarAtivos();

        for (Object r : reservas) {

            total += 1;

        }

        return total;

    }

    public int totalQuartos() {

        int total = 0;

        Apartamento apartamento = new Apartamento();

        List aps = new ArrayList();
        aps = new ApartamentoDAO().buscarTodas();

        for (Object r : aps) {

            total += 1;

        }

        return total;

    }

    public int totalReservasHoje() {

        int total = 0;

        Reservas reserva = new Reservas();

        List reservas = new ArrayList();
        reservas = new ReservasDAO().buscarAtivos();

        for (Object r : reservas) {
            reserva = (Reservas) r;
            Date hoje = new Date();
            Duration duracao = Duration.between(hoje.toInstant(), reserva.getDataEntrada().toInstant());
            if (duracao.isZero()) {
                total += 1;
            }
        }

        return total;

    }

}
