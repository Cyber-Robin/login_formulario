package com.robinmayorga.formulariologin.editors;

import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyEditorSupport;

public class PaisPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private PaisService service;

    @Override
    public void setAsText(String idString) throws IllegalArgumentException {
        try{
            Integer id = Integer.parseInt(idString);
            this.setValue(service.obtenerPorId(id));
        } catch (NumberFormatException e){
            setValue(null);
        }
    }
}
