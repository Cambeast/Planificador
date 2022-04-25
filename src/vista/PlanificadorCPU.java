/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import logica.Proceso;
/**
 *
 * @author cami2
 */
public class PlanificadorCPU {
    public static void main(String[] args) {
        ArrayList<Proceso> procesos = new ArrayList();
        
        int ll, cpu, prioridad = 0;
        int nro_procesos;
        int tipo_planificacion;
        
        do{
            tipo_planificacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tipo de Planificacion: \n[1]FIFO \n[2]SJF \n[3]Prioridad (No expropiativo) \n[4]SRTF \n[5]Prioridad (expropiativo) "));
            if(tipo_planificacion < 1 || tipo_planificacion > 5){
                JOptionPane.showMessageDialog(null, "Numero de planifiacion ERRONEO","ERROR!!", JOptionPane.ERROR_MESSAGE);} 
       }while(tipo_planificacion < 1 || tipo_planificacion > 5);
        
       do{
            nro_procesos = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nro de procesos a planificar: "));
            if(nro_procesos < 4 || nro_procesos > 8){
                JOptionPane.showMessageDialog(null, "Numero de procesos ERRONEO","ERROR!!", JOptionPane.ERROR_MESSAGE);} 
       }while(nro_procesos < 4 || nro_procesos > 8);
       
       JOptionPane.showMessageDialog(null, "Ingrese la informacion de procesos:");
           for(int i=0; i< nro_procesos; i++) { 
                
                ll = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nro llegada del proceso " + (i+1)));
                cpu = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tiempo de cpu del proceso " + (i+1)));
                
                if(tipo_planificacion == 3 || tipo_planificacion == 5){
                    prioridad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nro de prioridad del proceso " + (i+1)));
                }
                
                procesos.add(new Proceso(i+1, ll, cpu, prioridad, 0, 0, 0, "pendiente"));
           }
        
        switch (tipo_planificacion) {
           case 1 -> fifo(procesos);
           case 2 -> sjf(procesos);
           case 3 -> prioridadNoExp(procesos);
           case 4 -> srtf(procesos);
           case 5 -> prioridadExp(procesos);
       }
    }
    
    private static void fifo(ArrayList<Proceso> procesos) {
        int tiempo = 0, menor = 0;
        int nummenor;
        int com;
        
        Collections.sort(procesos);
       
       for(int i=0; i < procesos.size(); i++){
              
                nummenor = procesos.get(menor).getT_llegada();
                
                if( nummenor < procesos.get(i).getT_llegada()){
                    menor = i; 
                }
             
                if(tiempo < procesos.get(menor).getT_llegada()){
                   com = procesos.get(menor).getT_llegada();
                }else{
                   com = tiempo;  
                } 
                
                  procesos.get(menor).setT_comienzo(com);
                  procesos.get(menor).setEstado("terminado");
                  procesos.get(menor).setT_fin(procesos.get(menor).getT_cpu()+ tiempo);
                  procesos.get(menor).setT_espera(procesos.get(menor).getT_comienzo()- procesos.get(menor).getT_llegada());
                  tiempo += procesos.get(menor).getT_cpu();
        }
       
       System.out.println("\t\tFIFO"); 
       mostrartabla(procesos);
    }
    
    private static void sjf(ArrayList<Proceso> procesos) {
        int tiempo = 0;
        int com, fin, esp, temp;
        
        Collections.sort(procesos);
        
        procesos.get(0).setT_comienzo(procesos.get(0).getT_llegada());
        procesos.get(0).setEstado("terminado");
        procesos.get(0).setT_fin(procesos.get(0).getT_cpu()+ tiempo);
        procesos.get(0).setT_espera(procesos.get(0).getT_comienzo()- procesos.get(0).getT_llegada());
        tiempo += procesos.get(0).getT_cpu();
       
        for (int i = 1; i < procesos.size(); i++)
        {  
           for (int j = i+1; j < procesos.size(); j++)
           {  
               if(procesos.get(i).getT_llegada()<= tiempo && procesos.get(j).getT_llegada()<= tiempo)
               {
                    if (procesos.get(i).getT_cpu()> procesos.get(j).getT_cpu())
                    {
                            temp = procesos.get(i).getT_llegada();
                            procesos.get(i).setT_llegada(procesos.get(j).getT_llegada());
                            procesos.get(j).setT_llegada(temp);

                            temp = procesos.get(i).getT_cpu();
                            procesos.get(i).setT_cpu(procesos.get(j).getT_cpu());
                            procesos.get(j).setT_cpu(temp);

                            temp = procesos.get(i).getNu();
                            procesos.get(i).setNu(procesos.get(j).getNu());
                            procesos.get(j).setNu(temp);
                    }
               }
           }  tiempo+=procesos.get(i).getT_cpu();
        }    
           
     
        tiempo=procesos.get(0).getT_cpu();
        
        for(int i=1; i < procesos.size(); i++){
                
               
                if(tiempo < procesos.get(i).getT_llegada()){
                   com = procesos.get(i).getT_llegada();
                }else{
                   com = tiempo;  
                }
                fin = procesos.get(i).getT_llegada()+ tiempo;

                if(procesos.get(i).getT_llegada()> fin){
                    com = procesos.get(i).getT_llegada();
                    fin = procesos.get(i).getT_llegada()+ procesos.get(i).getT_cpu();
                }
               
                esp = com - procesos.get(i).getT_llegada();
                procesos.get(i).setT_comienzo(com);
                procesos.get(i).setEstado("terminado");
                procesos.get(i).setT_fin(fin);
                procesos.get(i).setT_espera(esp);
                
                tiempo += procesos.get(i).getT_cpu();
        }
        
        System.out.println("\t\tSJF");
        mostrartabla(procesos);
    }
    
    private static void prioridadNoExp(ArrayList<Proceso> procesos) {
        int tiempo = 0;
        int com, fin, esp, temp;
        
        Collections.sort(procesos);
        
        procesos.get(0).setT_comienzo(procesos.get(0).getT_llegada());
        procesos.get(0).setEstado("terminado");
        procesos.get(0).setT_fin(procesos.get(0).getT_cpu()+ tiempo);
        procesos.get(0).setT_espera(procesos.get(0).getT_comienzo()- procesos.get(0).getT_llegada());
        tiempo += procesos.get(0).getT_cpu();
       
        for (int i = 1; i < procesos.size(); i++)
        {  
           for (int j = i+1; j < procesos.size(); j++)
           {  
               if(procesos.get(i).getT_llegada()<= tiempo && procesos.get(j).getT_llegada()<= tiempo)
               {
                    if (procesos.get(i).getPrioridad()> procesos.get(j).getPrioridad())
                    {
                            temp = procesos.get(i).getT_llegada();
                            procesos.get(i).setT_llegada(procesos.get(j).getT_llegada());
                            procesos.get(j).setT_llegada(temp);

                            temp = procesos.get(i).getT_cpu();
                            procesos.get(i).setT_cpu(procesos.get(j).getT_cpu());
                            procesos.get(j).setT_cpu(temp);

                            temp = procesos.get(i).getNu();
                            procesos.get(i).setNu(procesos.get(j).getNu());
                            procesos.get(j).setNu(temp);
                            
                            temp = procesos.get(i).getPrioridad();
                            procesos.get(i).setPrioridad(procesos.get(j).getPrioridad());
                            procesos.get(j).setPrioridad(temp);
                    }
               }
           }  tiempo+=procesos.get(i).getT_cpu();
        }    
           
     
        tiempo=procesos.get(0).getT_cpu();
        
        for(int i=1; i < procesos.size(); i++){
                
               
                if(tiempo < procesos.get(i).getT_llegada()){
                   com = procesos.get(i).getT_llegada();
                }else{
                   com = tiempo;  
                }
                fin = procesos.get(i).getT_llegada()+ tiempo;

                if(procesos.get(i).getT_llegada()> fin){
                    com = procesos.get(i).getT_llegada();
                    fin = procesos.get(i).getT_llegada()+ procesos.get(i).getT_cpu();
                }
               
                esp = com - procesos.get(i).getT_llegada();
                procesos.get(i).setT_comienzo(com);
                procesos.get(i).setEstado("terminado");
                procesos.get(i).setT_fin(fin);
                procesos.get(i).setT_espera(esp);
                
                tiempo += procesos.get(i).getT_cpu();
        }
        
        System.out.println("\t\tPrioridad [No expropiativo]");
        mostrartabla(procesos);
    }
    
    private static void srtf(ArrayList<Proceso> procesos) {
        int tiempo = 0;
        int temp;
        int cpu[] = new int[procesos.size()];
        
        Collections.sort(procesos);
        
        for (int i = 1; i < procesos.size(); i++)
        {  
           for (int j = i+1; j < procesos.size(); j++)
           {  
               if(procesos.get(i).getT_llegada()<= tiempo && procesos.get(j).getT_llegada()<= tiempo)
               {
                    if (procesos.get(i).getT_cpu()> procesos.get(j).getT_cpu())
                    {
                            temp = procesos.get(i).getT_llegada();
                            procesos.get(i).setT_llegada(procesos.get(j).getT_llegada());
                            procesos.get(j).setT_llegada(temp);

                            temp = procesos.get(i).getT_cpu();
                            procesos.get(i).setT_cpu(procesos.get(j).getT_cpu());
                            procesos.get(j).setT_cpu(temp);

                            temp = procesos.get(i).getNu();
                            procesos.get(i).setNu(procesos.get(j).getNu());
                            procesos.get(j).setNu(temp);
                    }
               }
           }  tiempo+=procesos.get(i).getT_cpu();
        }
        
        tiempo = 0;
        
        for (int i = 0; i < procesos.size(); i++) {
            tiempo += procesos.get(i).getT_cpu();
            cpu[i] = procesos.get(i).getT_cpu();

        }
        int tiemA = 0;
        int actual = 0;

        int menor = 100;
        int actualaux=0;
        
        while (tiempo > 0) {
          
            if(procesos.get(actual).getEstado().equals("pendiente")){
                cpu[actual] = cpu[actual] -1;
                
                if(procesos.get(actual).getT_comienzo()==0 && procesos.get(actual).getT_llegada()!=0){
                    procesos.get(actual).setT_comienzo(tiemA);
                }
                
                tiemA++;
                tiempo--;               
                
            }
                                     
              for (int i = 0; i < procesos.size(); i++) {                                  

                    if (procesos.get(i).getT_llegada()<= tiemA && cpu[i] < menor && ( procesos.get(i).getEstado().equals("pendiente") || procesos.get(i).getEstado().equals("pausado") )) {
                        
                        if(procesos.get(i).getEstado().equals("pausado")&& !procesos.get(i).getEstado().equals("despausado")){
                            
                            System.out.println("El proceso que se despausa es: "+procesos.get(i).getNu());
                            procesos.get(i).setEstado("pendiente");
                            procesos.get(i).setReinicio(tiemA);
                        }
                        actualaux=actual;
                        actual = i;
                        
                        if(actualaux!=actual && !procesos.get(actualaux).getEstado().equals("terminado")){
                            System.out.println("El proceso que se pausa es: "+procesos.get(actualaux).getNu());
                            procesos.get(actualaux).setEstado("pausado");
                            procesos.get(actualaux).setPausa(tiemA);
                        }
                        menor = cpu[i];               
                    }//end if 
                    
                    if(cpu[actual]==0){
                        procesos.get(actual).setEstado("terminado");
                        procesos.get(actual).setT_fin(tiemA);
                        menor=menor+1;
                    }
                }//end for
        }//end while
        
        for (int i = 0; i < procesos.size(); i++) {
             
              if(procesos.get(i).getPausa()==0){
                  procesos.get(i).setT_espera(procesos.get(i).getT_comienzo()-procesos.get(i).getT_llegada());
              }else{
                  
                 procesos.get(i).setT_espera(procesos.get(i).getReinicio()-procesos.get(i).getPausa());
              }  
        }
        
        System.out.println("\t\tSRTF");
        mostrartabla(procesos);
    }
    
    private static void prioridadExp(ArrayList<Proceso> procesos) {
        int tiempo = 0;
        int cpu[] = new int[procesos.size()];
        int temp;
        
        Collections.sort(procesos);
        
        for (int i = 1; i < procesos.size(); i++)
        {  
           for (int j = i+1; j < procesos.size(); j++)
           {  
               if(procesos.get(i).getT_llegada()<= tiempo && procesos.get(j).getT_llegada()<= tiempo)
               {
                    if (procesos.get(i).getPrioridad()> procesos.get(j).getPrioridad())
                    {
                            temp = procesos.get(i).getT_llegada();
                            procesos.get(i).setT_llegada(procesos.get(j).getT_llegada());
                            procesos.get(j).setT_llegada(temp);

                            temp = procesos.get(i).getT_cpu();
                            procesos.get(i).setT_cpu(procesos.get(j).getT_cpu());
                            procesos.get(j).setT_cpu(temp);

                            temp = procesos.get(i).getNu();
                            procesos.get(i).setNu(procesos.get(j).getNu());
                            procesos.get(j).setNu(temp);
                            
                            temp = procesos.get(i).getPrioridad();
                            procesos.get(i).setPrioridad(procesos.get(j).getPrioridad());
                            procesos.get(j).setPrioridad(temp);
                    }
               }
           }  tiempo+=procesos.get(i).getT_cpu();
        } 
        
        tiempo = 0;

        for (int i = 0; i < procesos.size(); i++) {
            tiempo += procesos.get(i).getT_cpu();
            cpu[i] = procesos.get(i).getT_cpu();

        }
        int tiemA = 0;
        int actual = 0;

        int menor = 100;
        int actualaux=0;
        
        
        while (tiempo > 0) {
            
            if(procesos.get(actual).getEstado().equals("pendiente")){
                cpu[actual] = cpu[actual] -1;
                
                if(procesos.get(actual).getT_comienzo()==0 && procesos.get(actual).getT_llegada()!=0){
                    procesos.get(actual).setT_comienzo(tiemA);
                    //System.out.println(procesos.get(actual).getT_comienzo());
                }
                tiemA++;
                tiempo--; 
            }
                                     
              for (int i = 0; i < procesos.size(); i++) {                                  

                    if (procesos.get(i).getT_llegada()<= tiemA && procesos.get(i).getPrioridad() < menor && ( procesos.get(i).getEstado().equals("pendiente") || procesos.get(i).getEstado().equals("pausado") )) {
           
                        if(procesos.get(i).getEstado().equals("pausado")&& !procesos.get(i).getEstado().equals("despausado")){
                            
                            System.out.println("El proceso que se despausa es: "+procesos.get(i).getNu());
                            procesos.get(i).setEstado("pendiente");
                            procesos.get(i).setReinicio(tiemA);
                        }
                        actualaux=actual;
                        actual = i;
                        
                        if(actualaux!=actual && !procesos.get(actualaux).getEstado().equals("terminado")){
                            
                            System.out.println("El proceso que se pausa es: "+ procesos.get(actualaux).getNu());
                            procesos.get(actualaux).setEstado("pausado");
                            procesos.get(actualaux).setPausa(tiemA);
                        }
                        menor = procesos.get(i).getPrioridad();               
                        
                    }//end if 
                    
                    if(cpu[actual]==0){    
                        procesos.get(actual).setEstado("terminado");
                        procesos.get(actual).setT_fin(tiemA);
                        menor=menor+1;
                    }
                }//end for
        }//end while
        
        
          for (int i = 0; i < procesos.size(); i++) {
             
              if(procesos.get(i).getPausa()==0){
                  procesos.get(i).setT_espera(procesos.get(i).getT_comienzo()-procesos.get(i).getT_llegada());
              }else{
                 procesos.get(i).setT_espera(procesos.get(i).getReinicio()-procesos.get(i).getPausa());
              }      
          }
        
        System.out.println("\t\tPrioridad [expropiativo]");
        mostrartabla(procesos);
    }
    
    private static void mostrartabla(ArrayList<Proceso> procesos){
        if(procesos.get(0).getPrioridad() == 0){
            System.out.println("Nro.\tT.lleg\tT.CPU\tT.Com\tT.Fin\t T.Esp");
        }else{
            System.out.println("Nro.\tT.lleg\tT.CPU\tPrioridad\tT.Com\tT.Fin\t T.Esp");
        }
        
        for(int i=0; i < procesos.size(); i++){
            for(int j=0; j < procesos.size(); j++){
                if(procesos.get(j).getNu() == (i+1)){
                    System.out.print(procesos.get(j));
                    System.out.print("\t");
                    System.out.println();
                }
            }
        }
    }
}
