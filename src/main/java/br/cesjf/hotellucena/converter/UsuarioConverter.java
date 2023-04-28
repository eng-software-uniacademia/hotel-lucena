/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.hotellucena.converter;

import br.cesjf.hotellucena.model.Usuarios;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Tassio
 */
@FacesConverter("usuarioConverter")
@ManagedBean
public class UsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            return (Usuarios) uic.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value instanceof Usuarios) {
            Usuarios pergunta = (Usuarios) value;
            if (pergunta != null && pergunta instanceof Usuarios && pergunta.getIdUsuario() != null) {
                uic.getAttributes().put(pergunta.getIdUsuario().toString(), pergunta);
                return pergunta.getIdUsuario().toString();
            }
        }
        return "";
    }
}
