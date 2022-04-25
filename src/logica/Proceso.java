/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author cami2
 */
public class Proceso implements Comparable<Proceso>{
    private int nu;
    private int t_llegada;
    private int t_cpu;
    private int prioridad;
    private int t_comienzo;
    private int t_fin;
    private int t_espera;
    private int reinicio;
    private int pausa;
    private String estado;

    public Proceso() {
        this.t_llegada = 0;
        this.t_cpu = 0;
        this.prioridad = 0;
        this.t_comienzo = 0;
        this.t_fin = 0;
        this.t_espera = 0;
        
    }

    public Proceso(int nu, int t_llegada, int t_cpu, int prioridad, int t_comienzo, int t_fin, int t_espera, String estado) {
        this.nu = nu;
        this.t_llegada = t_llegada;
        this.t_cpu = t_cpu;
        this.prioridad = prioridad;
        this.t_comienzo = t_comienzo;
        this.t_fin = t_fin;
        this.t_espera = t_espera;
        this.estado = estado;
        this.reinicio = 0;
        this.pausa = 0;

    }

    public int getNu() {
        return nu;
    }

    public int getT_llegada() {
        return t_llegada;
    }

    public int getT_cpu() {
        return t_cpu;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getT_comienzo() {
        return t_comienzo;
    }

    public int getT_fin() {
        return t_fin;
    }

    public int getT_espera() {
        return t_espera;
    }

    public int getReinicio() {
        return reinicio;
    }

    public int getPausa() {
        return pausa;
    }

    public String getEstado() {
        return estado;
    }

    public void setNu(int nu) {
        this.nu = nu;
    }

    public void setT_llegada(int t_llegada) {
        this.t_llegada = t_llegada;
    }

    public void setT_cpu(int t_cpu) {
        this.t_cpu = t_cpu;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setT_comienzo(int t_comienzo) {
        this.t_comienzo = t_comienzo;
    }

    public void setT_fin(int t_fin) {
        this.t_fin = t_fin;
    }

    public void setT_espera(int t_espera) {
        this.t_espera = t_espera;
    }

    public void setReinicio(int reinicio) {
        this.reinicio = reinicio;
    }

    public void setPausa(int pausa) {
        this.pausa = pausa;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString(){
        if(getPrioridad() == 0){
            return  nu + "\t" + t_llegada +
                "\t " + t_cpu + 
                "\t " + t_comienzo +
                "\t " + t_fin +
                "\t " + t_espera;
        }else{
            return  nu + "\t" + t_llegada +
                "\t " + t_cpu + 
                "\t " + prioridad +
                "\t\t " + t_comienzo +
                "\t " + t_fin +
                "\t " + t_espera;
        }    
    }
    
    public int compareTo(Proceso p) {
        if(p.getT_llegada()> t_llegada){
            return -1;
        }else if(p.getT_llegada()> t_llegada){
            return 0;
        }else{
            return 1;
        }
            //To change body of generated methods, choose Tools | Templates.
    }

}
