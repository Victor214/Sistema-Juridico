/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classe;

import java.io.Serializable;

public class Processo implements Serializable {
    private Integer idProcesso;
    private String cpfCliente;
    private String cpfPromovido;

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getCpfPromovido() {
        return cpfPromovido;
    }

    public void setCpfPromovido(String cpfPromovido) {
        this.cpfPromovido = cpfPromovido;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }
    
}
