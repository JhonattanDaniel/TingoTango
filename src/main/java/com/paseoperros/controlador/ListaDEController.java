/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paseoperros.controlador;

import co.edu.umanizales.listase.modelo.ListaDE;
import co.edu.umanizales.listase.modelo.Nodo;
import co.edu.umanizales.listase.modelo.NodoDE;
import co.edu.umanizales.listase.modelo.Perro;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
 * @author Daniel Quintero
 */
@Named(value = "listaDEController")
@SessionScoped
public class ListaDEController implements Serializable {

    private ListaDE listaPerrosDE;

    private NodoDE temp;

    private Perro perroMostrar;

    private int perroPosicion;

    private int perroPosicion2;

    private int genero = 0;

    private Perro perroEncontrado;

    private DefaultDiagramModel model;

    private boolean entrar = false;

    private int seleccionUbicacion = 0;
    
    private int posEliminar = 0;

    /**
     * Creates a new instance of ListaDEController
     */
    public ListaDEController() {
    }

    @PostConstruct
    private void iniciar() {
        listaPerrosDE = new ListaDE();
        temp = listaPerrosDE.getCabeza();
        inicializarModelo();
    }

    public ListaDE getListaPerrosDE() {
        return listaPerrosDE;
    }

    public void setListaPerrosDE(ListaDE listaPerrosDE) {
        this.listaPerrosDE = listaPerrosDE;
    }

    public NodoDE getTemp() {
        return temp;
    }

    public void setTemp(NodoDE temp) {
        this.temp = temp;
    }

    public Perro getPerroMostrar() {
        return perroMostrar;
    }

    public void setPerroMostrar(Perro perroMostrar) {
        this.perroMostrar = perroMostrar;
    }

    public int getPerroPosicion() {
        return perroPosicion;
    }

    public void setPerroPosicion(int perroPosicion) {
        this.perroPosicion = perroPosicion;
    }

    public int getPerroPosicion2() {
        return perroPosicion2;
    }

    public void setPerroPosicion2(int perroPosicion2) {
        this.perroPosicion2 = perroPosicion2;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public Perro getPerroEncontrado() {
        return perroEncontrado;
    }

    public void setPerroEncontrado(Perro perroEncontrado) {
        this.perroEncontrado = perroEncontrado;
    }

    public int getSeleccionUbicacion() {
        return seleccionUbicacion;
    }

    public void setSeleccionUbicacion(int seleccionUbicacion) {
        this.seleccionUbicacion = seleccionUbicacion;
    }

    public int getPosEliminar() {
        return posEliminar;
    }

    public void setPosEliminar(int posEliminar) {
        this.posEliminar = posEliminar;
    }
    

    public void irSiguiente() {
        //if(temp.getSiguiente()!=null)
        //{
        temp = temp.getSiguiente();
        perroMostrar = temp.getDato();
        //}
    }

    public void irAnterior() {
        //if(temp.getSiguiente()!=null)
        //{
        temp = temp.getAnterior();
        perroMostrar = temp.getDato();
        //}
    }

    public void irPrimero() {
        if (listaPerrosDE.getCabeza() != null) {
            temp = listaPerrosDE.getCabeza();
            perroMostrar = temp.getDato();
        } else {
            JsfUtil.addErrorMessage("No hay datos en la lista");
        }

    }

    public void irUltimo() {

        temp = listaPerrosDE.getCabeza();
        while (temp.getSiguiente() != null) {
            temp = temp.getSiguiente();
        }
        /// Parado en el último nodo
        perroMostrar = temp.getDato();
    }

    public void invertir() {
        listaPerrosDE.invertir();
        irPrimero();
        inicializarModelo();
    }

    public void intercambiarPorPosicion() {
        listaPerrosDE.intercambiarPorPosicion(perroPosicion, perroPosicion2);
        irPrimero();
        entrar = true;
        perroEncontrado = null;
        inicializarModelo();

    }

    public void eliminarEnSitio(Perro perroEliminar) {
        if(temp.getSiguiente() != null){    
        listaPerrosDE.eliminarEnSitio(perroEliminar);
            irPrimero();
            inicializarModelo();
        }else{
            listaPerrosDE.eliminarEnSitio(perroEliminar);
            perroMostrar = new Perro();
            inicializarModelo();
        }
    }
    
    public void eliminarPorPosicion(int posEliminar) { 
        if(temp.getSiguiente() != null){
            listaPerrosDE.eliminarPorPosicion(posEliminar);
            irPrimero();
            inicializarModelo();
        }else{
            listaPerrosDE.eliminarPorPosicion(posEliminar);
            perroMostrar = new Perro();
            inicializarModelo();
        }
        
    }

    public void encontrarPerro() {

        perroEncontrado = listaPerrosDE.encontrarPosicion(perroPosicion);
        inicializarModelo();
    }

    public void mostrarGen() {
        listaPerrosDE.ordenarGenero(genero);
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
        if (listaPerrosDE.getCabeza() != null) {
            //Llamo a mi ayudante y lo ubico en el primero
            NodoDE ayudante = listaPerrosDE.getCabeza();
            //recorro mientras el ayudante tenga datos
            int posX = 2;
            int posY = 2;
            while (ayudante != null) {
                Element perroPintar = new Element(ayudante.getDato().getNombre(), posX + "em", posY + "em");

                if (ayudante.getDato().equals(perroEncontrado)) {

                    perroPintar.setStyleClass("ui-diagram-success");

                }

                if (entrar == true) {

                    if (ayudante.getDato().equals(listaPerrosDE.encontrarPosicion(perroPosicion))) {
                        perroPintar.setStyleClass("ui-diagram-success");
                    }
                    if (ayudante.getDato().equals(listaPerrosDE.encontrarPosicion(perroPosicion2))) {
                        perroPintar.setStyleClass("ui-diagram-success");
                    }
                }

                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                perroPintar.addEndPoint(new BlankEndPoint(EndPointAnchor.AUTO_DEFAULT));
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
                model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(2),
                        model.getElements().get(i).getEndPoints().get(3), null));
            }
        }
    }

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

    public String irListaDE() {
        perroEncontrado = new Perro();
        inicializarModelo();
        return "listade";
    }

    public String irCrearPerroDE() {
        perroEncontrado = new Perro();
        return "crearde";
    }

    public void guardarPerroDE() {

        if (listaPerrosDE.comprobarID(perroEncontrado) == true) {

            switch (seleccionUbicacion) {
                case 1:
                    listaPerrosDE.adicionarAlInicio(perroEncontrado);
                    break;
                case 2:
                    listaPerrosDE.adicionarNodo(perroEncontrado);
                    break;
                       case 3:
                    listaPerrosDE.adicionarNodoporPosicionB(perroPosicion, perroEncontrado);
                    break;
                default:
                    listaPerrosDE.adicionarNodo(perroEncontrado);
            }

            perroEncontrado = new Perro();
            irPrimero();
            JsfUtil.addSuccessMessage("Se adiciono el perro a la lista");

        } else {
            JsfUtil.addErrorMessage("El numero del perro ya existe");
        }
    }
 
}
