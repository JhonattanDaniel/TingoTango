/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tingotango.controlador;

import co.edu.umanizales.listainfantes.modelo.Infante;
import co.edu.umanizales.listainfantes.modelo.ListaCircularDE;
import co.edu.umanizales.listainfantes.modelo.NodoDE;
import co.edu.umanizales.listainfantes.modelo.Oportunidades;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.model.DualListModel;
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
@Named(value = "listaInfantesController")
@SessionScoped
public class ListaInfantesController implements Serializable {

    private ListaCircularDE tingoTango;

    private NodoDE temp;

    private Infante infanteGuardar;

    private Infante infanteMostrar;

    private List<Infante> listadoInfantes;

    private boolean estadoJuego;

    private int cantInfantes;

    private int posicionIngreso;

    private List<Oportunidades> tablaOportunidades;

    private int arregloAleatorio[];

    private Oportunidades infanteOportunidad;

    private String reiniciar;

    private boolean estadoCiclo;

    private DefaultDiagramModel model;

    private NodoDE ayudanteColor;

    private boolean direccion;

    private int vidas;

    private boolean gen;

    /**
     * Creates a new instance of ListaInfantesController
     */
    public ListaInfantesController() {
    }

    @PostConstruct
    private void iniciar() {
        listadoInfantes = new ArrayList<>();

        listadoInfantes.add(new Infante("nicolas", true, (byte) 1));
        listadoInfantes.add(new Infante("pedro", true, (byte) 2));
        listadoInfantes.add(new Infante("sofia", false, (byte) 3));
        listadoInfantes.add(new Infante("valeria", false, (byte) 4));

        tablaOportunidades = new ArrayList<>();

        tingoTango = new ListaCircularDE();
        temp = tingoTango.getCabeza();
        reiniciar = "Iniciar";
        estadoCiclo = false;
        inicializarModelo();
        ayudanteColor = tingoTango.getCabeza();
        infanteOportunidad = new Oportunidades();

    }

    public ListaCircularDE getTingoTango() {
        return tingoTango;
    }

    public void setTingoTango(ListaCircularDE tingoTango) {
        this.tingoTango = tingoTango;
    }

    public NodoDE getTemp() {
        return temp;
    }

    public void setTemp(NodoDE temp) {
        this.temp = temp;
    }

    public Infante getInfanteGuardar() {
        return infanteGuardar;
    }

    public void setInfanteGuardar(Infante infanteGuardar) {
        this.infanteGuardar = infanteGuardar;
    }

    public Infante getInfanteMostrar() {
        return infanteMostrar;
    }

    public void setInfanteMostrar(Infante infanteMostrar) {
        this.infanteMostrar = infanteMostrar;
    }

    public boolean isEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(boolean estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    public int getCantInfantes() {
        return cantInfantes;
    }

    public void setCantInfantes(int cantInfantes) {
        this.cantInfantes = cantInfantes;
    }

    public int getPosicionIngreso() {
        return posicionIngreso;
    }

    public void setPosicionIngreso(int posicionIngreso) {
        this.posicionIngreso = posicionIngreso;
    }

    public List<Infante> getListadoInfantes() {
        return listadoInfantes;
    }

    public void setListadoInfantes(List<Infante> listadoInfantes) {
        this.listadoInfantes = listadoInfantes;
    }

    public List<Oportunidades> getTablaOportunidades() {
        return tablaOportunidades;
    }

    public void setTablaOportunidades(List<Oportunidades> tablaOportunidades) {
        this.tablaOportunidades = tablaOportunidades;
    }

    public int[] getArregloAleatorio() {
        return arregloAleatorio;
    }

    public void setArregloAleatorio(int[] arregloAleatorio) {
        this.arregloAleatorio = arregloAleatorio;
    }

    public Oportunidades getInfanteOportunidad() {
        return infanteOportunidad;
    }

    public void setInfanteOportunidad(Oportunidades infanteOportunidad) {
        this.infanteOportunidad = infanteOportunidad;
    }

    public String getReiniciar() {
        return reiniciar;
    }

    public void setReiniciar(String reiniciar) {
        this.reiniciar = reiniciar;
    }

    public boolean isEstadoCiclo() {
        return estadoCiclo;
    }

    public void setEstadoCiclo(boolean estadoCiclo) {
        this.estadoCiclo = estadoCiclo;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }

    public void guardarInfante() {
        boolean idexiste = false;

        for (Infante inf : listadoInfantes) {
            if (inf.getId() == infanteGuardar.getId()) {
                idexiste = true;
            }
        }

        if (idexiste == true) {
            JsfUtil.addErrorMessage("El ID del infante ya existe");
        } else {
            listadoInfantes.add(infanteGuardar);
            JsfUtil.addSuccessMessage("Se ha agregado el infante " + infanteGuardar.getNombre());
            infanteGuardar = new Infante();

        }
    }

    public String irTingoTango() {
        infanteGuardar = new Infante();
        return "home";
    }

    public String irCrearInfante() {
        infanteGuardar = new Infante();
        return "crear";
    }

    public void infanteOportunidades(Infante inf) {

        tablaOportunidades.add(new Oportunidades(inf, 3));
    }

    public void participacionInfantes() {

        tingoTango = new ListaCircularDE();
        tablaOportunidades = new ArrayList<>();
        if (cantInfantes > listadoInfantes.size()) {

            JsfUtil.addErrorMessage("La cantidad de infantes es ,mayor a la existente");

        } else {

            arregloAleatorio = new int[cantInfantes];
            int i = 0, rango = listadoInfantes.size();
            arregloAleatorio[i] = (int) (Math.random() * rango);
            for (i = 1; i < cantInfantes; i++) {
                arregloAleatorio[i] = (int) (Math.random() * rango);
                for (int j = 0; j < i; j++) {
                    if (arregloAleatorio[i] == arregloAleatorio[j]) {
                        i--;
                    }
                }
            }
            for (int k = 0; k < cantInfantes; k++) {

                Infante infante = listadoInfantes.get(arregloAleatorio[k]);
                tingoTango.adicionarNodoCircular(infante);
                infanteOportunidades(infante);

            }
            ayudanteColor = tingoTango.getCabeza();
            inicializarModelo();
        }
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
        if (tingoTango.getCabeza() != null) {
            //Llamo a mi ayudante y lo ubico en el primero
            NodoDE ayudante = tingoTango.getCabeza();
            //recorro mientras el ayudante tenga datos
            double R = (360 / tingoTango.getTotalInfantes());
            double posX = 25;
            double posY = 15;
            double X = 0;
            double Y = 0;
            while (ayudante.getSiguiente() != tingoTango.getCabeza()) {
                Element infantePintar = new Element(ayudante.getDato().getNombre(), posX + "em", posY + "em");

                if (ayudante.getDato().getId() == ayudanteColor.getDato().getId()) {

                    infantePintar.setStyleClass("ui-diagram-success");
                }

                infantePintar.addEndPoint(new BlankEndPoint(EndPointAnchor.CONTINUOUS));
                infantePintar.addEndPoint(new BlankEndPoint(EndPointAnchor.CONTINUOUS));
                model.addElement(infantePintar);
                ayudante = ayudante.getSiguiente();
                X = X + R;
                Y = Y + R;
                double Xrad = Math.toRadians(X);
                double Yrad = Math.toRadians(Y);
                posX = posX + ((Math.sin(Xrad)) * 10);
                posY = posY + ((Math.cos(Yrad)) * 10);

            }
            Element infantePintar = new Element(ayudante.getDato().getNombre(), posX + "em", posY + "em");

            if (ayudante.getDato().getId() == ayudanteColor.getDato().getId()) {

                infantePintar.setStyleClass("ui-diagram-success");
            }

            infantePintar.addEndPoint(new BlankEndPoint(EndPointAnchor.CONTINUOUS));
            infantePintar.addEndPoint(new BlankEndPoint(EndPointAnchor.CONTINUOUS));
            model.addElement(infantePintar);
            X = X + R;
            Y = Y + R;
            double Xrad = Math.toRadians(X);
            double Yrad = Math.toRadians(Y);
            posX = posX + ((Math.sin(Xrad)) * 10);
            posY = posY + ((Math.cos(Yrad)) * 10);

            // el ayudante quedo en el enlace del último
            //Ya pinte todos los elementos y los puntos de enlace
            for (int i = 0; i <= model.getElements().size() - 1; i++) {
                if (i == model.getElements().size() - 1) {
                    model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0),
                            model.getElements().get(0).getEndPoints().get(1), null));
                    model.connect(createConnection(model.getElements().get(0).getEndPoints().get(1),
                            model.getElements().get(i).getEndPoints().get(0), null));
                } else {
                    model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0),
                            model.getElements().get(i + 1).getEndPoints().get(1), null));
                    model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(1),
                            model.getElements().get(i).getEndPoints().get(0), null));
                }
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

    public void estadoJugar() {

        reiniciar = "Iniciar";
        estadoCiclo = !estadoCiclo;
        if (estadoCiclo) {
            reiniciar = "Parar";
        }
        restarOportunidades();
        inicializarModelo();
    }

    public void pasarColor() {

        if (direccion == true) {
            ayudanteColor = ayudanteColor.getAnterior();
            inicializarModelo();
        } else {
            ayudanteColor = ayudanteColor.getSiguiente();
            inicializarModelo();
        }
    }

    public void restarOportunidades() {
        if (tingoTango.getCabeza().getSiguiente() != tingoTango.getCabeza()) {
            if (estadoCiclo == false) {
                for (Oportunidades opor : tablaOportunidades) {
                    if (opor.getInfante() == ayudanteColor.getDato()) {
                        opor.setOportunidad(opor.getOportunidad() - 1);
                        if (opor.getOportunidad() == 0) {
                            tingoTango.eliminarEnSitio(ayudanteColor.getDato());
                            inicializarModelo();
                        }
                    }
                }

            }
        } else {
            JsfUtil.addSuccessMessage("el ganador es " + tingoTango.getCabeza().getDato().getNombre());
        }
    }

    public void selecInfante(Oportunidades opor) {

        infanteOportunidad = opor;
    }

    public void reingresarPorPosicion() {

        if (infanteOportunidad.getInfante() != null) {

            infanteOportunidad.setOportunidad(vidas);
            tingoTango.adicionarNodoporPosicionB(posicionIngreso, infanteOportunidad.getInfante());
            infanteOportunidad = new Oportunidades();
            inicializarModelo();

        } else {
            JsfUtil.addErrorMessage("Debe seleccionar un infante para reingresar");
            inicializarModelo();
        }

    }

    public void ingresarPorGenero() {

        if (!tablaOportunidades.isEmpty()) {

            if (gen == true) {
                for (Oportunidades opor : tablaOportunidades) {
                    if (opor.getOportunidad() == 0 && opor.getInfante().isGenero() == true) {
                        opor.setOportunidad(vidas);
                        tingoTango.adicionarNodoCircular(opor.getInfante());

                    }
                }
                inicializarModelo();
            } else if (gen == false) {
                for (Oportunidades opor : tablaOportunidades) {
                    if (opor.getOportunidad() == 0 && opor.getInfante().isGenero() == false) {
                        opor.setOportunidad(vidas);
                        tingoTango.adicionarNodoCircular(opor.getInfante());
                    }
                }
            }
            inicializarModelo();
        } else {
            JsfUtil.addErrorMessage("No hay nadie Jugando");
        }
    }

    public boolean revisarRetiro() {
        boolean revisar = false;
        if (gen == true) {
            for (Oportunidades opor : tablaOportunidades) {
                if (opor.getInfante().isGenero() == false && opor.getOportunidad() != 0) {
                    revisar = true;
                }
            }

        } else if (gen == false) {
            for (Oportunidades opor : tablaOportunidades) {
                if (opor.getInfante().isGenero() == true && opor.getOportunidad() != 0) {
                    revisar = true;
                }
            }
        }
        if (revisar == false) {
            JsfUtil.addErrorMessage("El juego no puede estar vacio");
        }
        return revisar;
    }

    public void retirarPorGenero() {

        if (!tablaOportunidades.isEmpty()) {
            if (revisarRetiro() == true) {
                if (gen == true) {
                    for (Oportunidades opor : tablaOportunidades) {
                        if (opor.getInfante().isGenero() == true) {
                            opor.setOportunidad(0);
                            tingoTango.eliminarEnSitio(opor.getInfante());
                        }
                    }
                    inicializarModelo();
                } else if (gen == false) {
                    for (Oportunidades opor : tablaOportunidades) {
                        if (opor.getInfante().isGenero() == false) {
                            opor.setOportunidad(0);
                            tingoTango.eliminarEnSitio(opor.getInfante());
                        }
                    }
                }
                inicializarModelo();
            }
        } else {
            JsfUtil.addErrorMessage("No hay nadie Jugando");
        }
    }
}
