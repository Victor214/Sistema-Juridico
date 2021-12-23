/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classe;

import java.io.InputStream;
import javax.servlet.http.Part;

public class Fase {
    //IDs
    private Integer idAdv;
    private Integer idProc;
    
    private String titulo;
    private String descricao;
    
    // PDF
    private String fileName;
    private InputStream file;
    
    private Integer tipo;

    public Integer getIdAdv() {
        return idAdv;
    }

    public void setIdAdv(Integer idAdv) {
        this.idAdv = idAdv;
    }

    public Integer getIdProc() {
        return idProc;
    }

    public void setIdProc(Integer idProc) {
        this.idProc = idProc;
    }

    
    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
    
}
