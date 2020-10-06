/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paseoperros.controlador;

import co.edu.umanizales.listase.modelo.ListaSE;
import co.edu.umanizales.listase.modelo.Nodo;
import co.edu.umanizales.listase.modelo.Perro;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.primefaces.component.breadcrumb.BreadCrumbBase;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author
 */
@Named(value = "listaSEController")
@SessionScoped
public class ListaSEController implements Serializable {

    private ListaSE listaPerros;

    private Perro perroMostrar;

    private Nodo temp;

    private int perroPosicion;

    private Perro perroEncontrado;

    private DefaultDiagramModel model;

    private boolean entrar = false;
    
    private int seleccionUbicacion = 0;
    
    private int genero = 0;

    /**
     * Creates a new instance of ListaSEController
     */
    public ListaSEController() {
    }

    @PostConstruct
    private void iniciar() {
        listaPerros = new ListaSE();
        //// Conectaría a un archivo plano o a una base de datos para llenar la 
        //lista de perros
      //  listaPerros.adicionarNodo(new Perro("Pastor", (byte) 1, (byte) 3, "macho"));
       // listaPerros.adicionarNodo(new Perro("Lulú", (byte) 2, (byte) 4, "hembra"));
       // listaPerros.adicionarNodo(new Perro("Firulais", (byte) 3, (byte) 6, "hembra"));

       // listaPerros.adicionarNodoAlInicio(new Perro("Rocky", (byte) 4, (byte) 5, "macho"));
       // perroMostrar = listaPerros.getCabeza().getDato();
        temp = listaPerros.getCabeza();

        inicializarModelo();

       
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }
    
    public int getSeleccionUbicacion() {
        return seleccionUbicacion;
    }

    public void setSeleccionUbicacion(int seleccionUbicacion) {
        this.seleccionUbicacion = seleccionUbicacion;
    }

    public Nodo getTemp() {
        return temp;
    }

    public void setTemp(Nodo temp) {
        this.temp = temp;
    }

    public Perro getPerroMostrar() {
        return perroMostrar;
    }

    public void setPerroMostrar(Perro perroMostrar) {
        this.perroMostrar = perroMostrar;
    }

    public ListaSE getListaPerros() {
        return listaPerros;
    }

    public void setListaPerros(ListaSE listaPerros) {
        this.listaPerros = listaPerros;
    }

    public int getPerroPosicion() {
        return perroPosicion;
    }

    public void setPerroPosicion(int perroPosicion) {
        this.perroPosicion = perroPosicion;
    }

    public Perro getPerroEncontrado() {
        return perroEncontrado;
    }

    public void setPerroEncontrado(Perro perroEncontrado) {
        this.perroEncontrado = perroEncontrado;
    }

    public void irSiguiente() {
        //if(temp.getSiguiente()!=null)
        //{
        temp = temp.getSiguiente();
        perroMostrar = temp.getDato();
        //}
    }

    public void irPrimero() {
        if(listaPerros.getCabeza()!= null){
            temp = listaPerros.getCabeza();
        perroMostrar = temp.getDato();
        }else {
            JsfUtil.addErrorMessage("No hay datos en la lista");
        }
        
    }

    public void irUltimo() {

        temp = listaPerros.getCabeza();
        while (temp.getSiguiente() != null) {
            temp = temp.getSiguiente();
        }
        /// Parado en el último nodo
        perroMostrar = temp.getDato();
    }

    public void invertir() {
        listaPerros.invertir();
        irPrimero();
        inicializarModelo();
    }

    public void intercambiar() {
        listaPerros.intercambiarExtremos();
        irPrimero();
        entrar = true;
        perroEncontrado = null;
        inicializarModelo();

    }

    public void eliminar(byte id) {

        listaPerros.eliminarNodo(id);
        irPrimero();
        inicializarModelo();
    }

    public void encontrarPerro() {

        perroEncontrado = listaPerros.encontrarPosicion(perroPosicion);
        inicializarModelo();
    }

    public void mostrarGen() {
        listaPerros.ordenarGenero(genero);
        irPrimero();
        inicializarModelo();
    }

    public void inicializarModelo() {
        //Instanciar e modelo
        model = new DefaultDiagramModel();
        //Definirle al modelo la cantidad de enlaces -1 (infinito)
        model.setMaxConnections(-1);

        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        //pregunto si hay datos
        if (listaPerros.getCabeza() != null) {
            //Llamo a mi ayudante y lo ubico en el primero
            Nodo ayudante = listaPerros.getCabeza();
            //recorro mientras el ayudante tenga datos
            int posX = 2;
            int posY = 2;
            while (ayudante != null) {
                Element perroPintar = new Element(ayudante.getDato().getNombre(), posX + "em", posY + "em");

                if (ayudante.getDato().equals(perroEncontrado)) {

                    perroPintar.setStyleClass("ui-diagram-success");

                }

                if (entrar == true) {

                    if (ayudante.getDato().equals(listaPerros.encontrarPosicion(1))) {
                        perroPintar.setStyleClass("ui-diagram-success");
                    }
                    if (ayudante.getDato().equals(listaPerros.encontrarPosicion(listaPerros.contarNodos()))) {
                        perroPintar.setStyleClass("ui-diagram-success");
                    }
                }

                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                model.addElement(perroPintar);
                ayudante = ayudante.getSiguiente();
                posX = posX + 5;
                posY = posY + 5;
            }
            entrar = false;

            // el ayudante quedo en el enlace del último
            //Ya pinte todos los elementos y los puntos de enlace
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0),
                        model.getElements().get(i + 1).getEndPoints().get(1), null));
            }
        }
    }

    /* 
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        
            Element start = new Element("Fight for your dream", "20em", "6em");
            start.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
            start.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));

            Element trouble = new Element("Do you meet some trouble?", "20em", "18em");
            trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
            trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
            trouble.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));

            Element giveup = new Element("Do you give up?", "20em", "30em");
            giveup.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
            giveup.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
            giveup.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
            
            Element succeed = new Element("Succeed", "50em", "18em");
            succeed.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
            succeed.setStyleClass("ui-diagram-success");
            
            Element fail = new Element("Fail", "50em", "30em");
            fail.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
            fail.setStyleClass("ui-diagram-fail");
            
            model.addElement(start);
            model.addElement(trouble);
            model.addElement(giveup);
            model.addElement(succeed);
            model.addElement(fail);

            model.connect(createConnection(start.getEndPoints().get(0), trouble.getEndPoints().get(0), null));
            model.connect(createConnection(trouble.getEndPoints().get(1), giveup.getEndPoints().get(0), "Yes"));
            model.connect(createConnection(giveup.getEndPoints().get(1), start.getEndPoints().get(1), "No"));
            model.connect(createConnection(trouble.getEndPoints().get(2), succeed.getEndPoints().get(0), "No"));
            model.connect(createConnection(giveup.getEndPoints().get(2), fail.getEndPoints().get(0), "Yes"));
     */
    public DiagramModel getModel() {
        return model;
    }

    private Connection createConnection(EndPoint from, EndPoint to, String label) {

        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
    }

    public String irCrearPerro() {
        perroEncontrado = new Perro();
        return "crear";
    }
    
    public void guardarPerro(){
        
        switch(seleccionUbicacion){
            case 1: listaPerros.adicionarNodoAlInicio(perroEncontrado); 
                break;
                case 2:listaPerros.adicionarNodo(perroEncontrado); 
                    break;
                    default: listaPerros.adicionarNodo(perroEncontrado); 
        }
        perroEncontrado = new Perro();
        irPrimero();
        JsfUtil.addSuccessMessage("Se adiciono el perro a la lista");
    }
    
    public String irHome() {
        perroEncontrado = new Perro();
        inicializarModelo();
        return "home";
    }
}
