package com.company;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import sun.java2d.loops.ProcessPath;
import sun.management.HotspotClassLoadingMBean;

import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.MarshalException;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthOptionPaneUI;

public class GUI extends JFrame{
    //Variables Funcionamiento
    private Hora reloj;
    private Calendario fecha;
    protected Queue<Auto> colaCarros;
    protected long id = 1, carrosSalieron = 0;
    protected Hora hMasEntradas, hMasSalidas;
    protected Calendario fechaMasEntradas, fechaMasSalidas;
    protected long lAutosAcumulados, lAutosAnteriores = 0, lAutosMaximos = 0;
    protected long lAutosSalidaA, lAutosSalidasAnteriores = 0, lAutosSalidasMaxima = 0;
    private boolean crear = true;

    //Variables para la hashTable de Coches
    private final int MAX_SIZE = 21;
    private SingleLinkedList<Auto> tablaHash[];
    private SingleLinkedList<Auto> tablaHashSalida[];

    //variables algoritmo de Dijkstra
    private Dijkstra mapa = new Dijkstra();
    private int nodes, source;

    //Salida
    Salida salidaNorte = new Salida("Norte", 0);
    Salida salidaSur = new Salida("Sur", 0);
    Salida salidaEste = new Salida("Este", 0);
    Salida salidaOeste = new Salida("Oeste", 0);

    //Constructor
    public GUI(){
        nodes = 21;
        reloj = new Hora();
        fecha = new Calendario();
        colaCarros = new Queue<Auto>();
        hMasEntradas = new Hora();
        hMasSalidas = new Hora();
        tablaHash = new SingleLinkedList[MAX_SIZE];
        for(int i=0; i<MAX_SIZE; i++)
            tablaHash[i] = new SingleLinkedList<Auto>();
        tablaHashSalida = new SingleLinkedList[MAX_SIZE];
        for(int i=0; i<MAX_SIZE; i++)
            tablaHashSalida[i] = new SingleLinkedList<Auto>();
        /*vertice = new SingleLinkedList<String>();*/
        crearGrafo();
        initComponents();
    }

    public void initComponents(){
        //Inicializando las variables de antes
        //Variables panel
        pContenedor = new JPanel();
        pDatos = new JPanel();
        pMapa = new JPanel();
        pAppUsuario = new JPanel();
        panelControlTiempo = new JPanel();
        pContenedorTiempoYEspecial = new JPanel();
        pCrearCarroEspecial = new JPanel();
        pSeleccion = new JPanel();
        pBotones = new JPanel();
        //Variables botones
        bAgregarCarros = new JButton("Agregar Carros");
        bCrearCarroEspecial = new JButton("Crear Carro Especial");
        bSeleccionarEntrada = new JButton("Seleccionar Entrada");
        bSeleccionarSalida = new JButton("Seleccionar Salida");
        bRegresarCarroEspecial = new JButton("Regreasr");
        bCrear = new JButton("Crear");
        bAvanzarUnaHora = new JButton("+ 1:00");
        bAvanzar30Min = new JButton("+ 00:30");
        bAvanzar15min = new JButton("+ 00:15");
        bAvanzar5min = new JButton("+ 00:05");
            //Añadir Funcioanlidad a los botones
            bAgregarCarros.addActionListener(new BAgregarCarrosListener());
            bCrearCarroEspecial.addActionListener(new BCrearCarroEspecialListener());
            bSeleccionarEntrada.addActionListener(new BSeleccionarEntradaListener());
            bSeleccionarSalida.addActionListener(new BSeleccionarSalidaListener());
            bRegresarCarroEspecial.addActionListener(new BRegresarCarroEspecial());
            bCrear.addActionListener(new BCrearListener());
            bAvanzarUnaHora.addActionListener(new BAHoraListener());
            bAvanzar30Min.addActionListener(new BtreintaListener());
            bAvanzar15min.addActionListener(new BQuinceListener());
            bAvanzar5min.addActionListener(new BCincoListener());
        //Variables label
        lNumAutos = new JLabel("Número de autos que entraron");
        lAutosEntrada = new JLabel("Numero de autos que entraron");
        lAutosSalida = new JLabel("Numero de autos que salieron");
        lAutosActuales = new JLabel("Numero de autos circulando");
        lHora = new JLabel("Hora:");
        lDia = new JLabel("Fecha:");
        //Variable TextField
        fNumeroAutos = new JTextField("0");
        fAutosEntrada = new JTextField("0");
        fAutosSalida = new JTextField("0");
        fAutosActuales = new JTextField(String.valueOf(colaCarros.getSize()));
        fHora = new JTextField(reloj.toString());
        fDia = new JTextField(fecha.toString());
        //Variable appUsuario
        aIDEncontrado = new JTextArea();
        aIDEncontrado.setLayout(new FlowLayout());
        aIDEncontrado.setEditable(false);
        bBuscarID = new JButton("Buscar ID");
        bBuscarID.addActionListener(new BBuscarIdListener());
        panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridLayout(4,1));
        panelId = new JPanel();
        panelId.setLayout(new GridLayout(1,1));
        lBuscarAutos = new JLabel("Buscar ID: ");
        fIDAutos = new JTextField();
        lIdAutos = new JLabel("Base de Datos");
        aIDAutos = new JTextArea();
        scroll = new JScrollPane(aIDAutos);
        aIDAutos.setEditable(false);
        //Inicializacion de las variables de imagen
        image = new ImageIcon(getClass().getResource("Mapa.png"));

        pMapa.add(new JLabel(image));

        //ComboBox
        jSeleccionarCarros = new JComboBox();
        jSeleccionarEntrada = new JComboBox();
        jSeleccionarSalida = new JComboBox();

        jSeleccionarCarros.setModel(new DefaultComboBoxModel(new String[]
                {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        jSeleccionarEntrada.setModel(new DefaultComboBoxModel(new String[]
                {"-Selecciona Entrada-", "CDI", "CONSCRIPTO", "CHAPULTEPEC REFORMA", "ALENCASTRE", "SAN ANTONIO", "SAN JERONIMO", "BLVD LA LUZ", "LUIS CABRERA", "LAS TORRES", "LAS AGUILAS", "POETAS", "PICACHO AJUSCO", "CERRO ZACATEPETL", "CUICUILCO", "AZTECA", "CUERNAVACA", "XOCHIMILCO"}));
        jSeleccionarSalida.setModel(new DefaultComboBoxModel(new String[]
                {"-Selecciona Salida- ", "CDI", "CONSCRIPTO", "CHAPULTEPEC REFORMA", "ALENCASTRE", "SAN ANTONIO", "SAN JERONIMO", "BLVD LA LUZ", "DISTRIBUIDOR VIAL", "LUIS CABRERA", "LAS TORRES", "LAS AGUILAS", "POETAS", "PICACHO AJUSCO", "CERRO ZACATEPETL", "INSURGENTES SUR", "AZTECA", "TLALPAN", "CUERNAVACA", "XOCHIMILCO"}));

        //Inicializacion VariablesNuevosPaneles
        pBaseDatos = new JPanel();
        pBaseDatos.setLayout(new GridLayout(2,1));
        pDatosApp = new JPanel();
        pDatosApp.setLayout(new GridLayout(4,2));
        hMayorEntrada = new JTextField();
        hMayorSalida = new JTextField();
        aEntradaMasOcupada = new JTextField();
        aSalidaMasOcupada = new JTextField();
        //Negar permisos de textArea
        hMayorEntrada.setEditable(false);
        hMayorSalida.setEditable(false);
        aEntradaMasOcupada.setEditable(false);
        aSalidaMasOcupada.setEditable(false);

        //Variables AppGeneral
        pContenedorApp = new JPanel();
        pContenedorApp.setLayout(new GridLayout(1,1));
        pBotonesApp = new JPanel();
        pBotonesApp.setLayout(new GridLayout(5,1));
        pEntrada = new JPanel();
        pEntrada.setLayout(new GridLayout(3,1));
        pEntradaP1 = new JPanel();
        pEntradaP2 = new JPanel();
        pEntradaP1.setLayout(new GridLayout(1, 3));
        pEntradaP2.setLayout(new GridLayout(1,2));
        pSalida = new JPanel();
        pSalida.setLayout(new GridLayout(3,1));
        pSalidaP1 = new JPanel();
        pSalidaP1.setLayout(new GridLayout(1,2));
        //Botones AppGeneral
        bRegistroEntrada = new JButton("Registro Entrada");
        bRegistroSalida = new JButton("Registro Salida");
        bPanelBuscar = new JButton("Buscar ID");
        bBaseDeDatos = new JButton("Base de Datos");
        bRegresar = new JButton("Regresar");
        /*bSalidaNorte = new JButton("Norte");
        bSalidaSur = new JButton("Sur");
        bSalidaEste = new JButton("Este");
        bSalidaOeste = new JButton("Oeste");*/
        bRegresarRegistroN = new JButton("Regresar");
        bRegresarRegistroS = new JButton("Regresar");

        jSelectEntrada = new JComboBox();
        jSelectEntrada.setModel(new DefaultComboBoxModel(new String[] {"-Selecciona Entrada-", "CDI", "CONSCRIPTO",
                "CHAPULTEPEC REFORMA", "ALENCASTRE", "SAN ANTONIO", "SAN JERONIMO", "BLVD LA LUZ", "LUIS CABRERA",
                "LAS TORRES", "LAS AGUILAS", "POETAS", "PICACHO AJUSCO", "CERRO ZACATEPETL", "CUICUILCO", "AZTECA",
                "CUERNAVACA", "XOCHIMILCO"}));
        bBuscarEntrada = new JButton("Buscar");

        jSelectSalida = new JComboBox();
        jSelectSalida.setModel(new DefaultComboBoxModel(new String[] {"-Selecciona Salida- ", "CDI", "CONSCRIPTO",
                "CHAPULTEPEC REFORMA", "ALENCASTRE", "SAN ANTONIO", "SAN JERONIMO", "BLVD LA LUZ", "DISTRIBUIDOR VIAL",
                "LUIS CABRERA", "LAS TORRES", "LAS AGUILAS", "POETAS", "PICACHO AJUSCO", "CERRO ZACATEPETL",
                "INSURGENTES SUR", "AZTECA", "TLALPAN", "CUERNAVACA", "XOCHIMILCO"}));
        bBuscarSalida = new JButton("Buscar");

        //label AppGeneral
        lNorte = new JLabel("Norte");
        lSur = new JLabel("Sur");
        lEste = new JLabel("Este");
        lOeste = new JLabel("Oste");
        lOpciones = new JLabel("Opciones");
        //Funcionalidad a los botones
        bRegistroEntrada.addActionListener(new BRegistroEntradaListener());
        bRegistroSalida.addActionListener(new BRegistroSalidaListener());
        bPanelBuscar.addActionListener(new BPanelBusquedaListener());
        bBaseDeDatos.addActionListener(new BBaseDatosListener());
        bRegresar.addActionListener(new BRegresarListener());
        bBuscarEntrada.addActionListener(new bBuscarListener());
        bBuscarSalida.addActionListener(new bBuscarSalidaListener());
        bRegresarRegistroN.addActionListener(new BRRegistroNorte());
        bRegresarRegistroS.addActionListener(new BRRegistroSur());

        //Especificaciones de la GUI
        setTitle("Proyecto Final");
        setVisible(true);
        setSize(1600, 700);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pContenedor.setLayout(new GridLayout(2,3));
        pDatos.setLayout(new GridLayout(5,3));
        pMapa.setLayout(new GridLayout(1,1));
        pAppUsuario.setLayout(new GridLayout(2,1));
        panelControlTiempo.setLayout(new GridLayout(3,1));
        pContenedorTiempoYEspecial.setLayout(new GridLayout(1,1));
        pCrearCarroEspecial.setLayout(new GridLayout(3, 2));
        pSeleccion.setLayout(new GridLayout(2, 2));
        pBotones.setLayout(new GridLayout(1,4));

        //Panel COntador de entrada y salida de autos
        panelContador = new JPanel();
        panelContador.setLayout(new GridLayout(2,2));
        contadorAutosEntradaySalida = new JTextField("0");
        contadorAutosEntradaySalida.setEditable(false);

        //Creación de la pDatos
        pDatos.add(lNumAutos);
        pDatos.add(new JLabel(""));
        pDatos.add(fNumeroAutos);
        pDatos.add(lAutosSalida);
        pDatos.add(new JLabel(""));
        pDatos.add(fAutosSalida);
        pDatos.add(lAutosActuales);
        pDatos.add(new JLabel(""));
        pDatos.add(fAutosActuales);
        pDatos.add(lHora);
        pDatos.add(new JLabel(""));
        pDatos.add(fHora);
        pDatos.add(lDia);
        pDatos.add(new JLabel(""));
        pDatos.add(fDia);

        //Se niega el permiso de editar a todos los JtextArea
        fNumeroAutos.setEditable(false);
        fAutosEntrada.setEditable(false);
        fAutosSalida.setEditable(false);
        fAutosActuales.setEditable(false);
        fHora.setEditable(false);
        fDia.setEditable(false);

        //TODO añadir la foto del mapa de la carretera al panel pMapa

        panelBusqueda.add(lBuscarAutos);
        panelBusqueda.add(fIDAutos);
        panelBusqueda.add(bBuscarID);
        panelBusqueda.add(bRegresar);

        pAppUsuario.add(panelBusqueda);
        pAppUsuario.add(aIDEncontrado);

        //Creacion app
        pBotonesApp.add(lOpciones);
        pBotonesApp.add(bBaseDeDatos);
        pBotonesApp.add(bPanelBuscar);
        pBotonesApp.add(bRegistroEntrada);
        pBotonesApp.add(bRegistroSalida);

        pEntradaP1.add(new JLabel(""));
        pEntradaP1.add(new JLabel("Entradas"));
        pEntradaP1.add(new JLabel(""));

        pEntradaP2.add(bRegresarRegistroN);
        pEntradaP2.add(bBuscarEntrada);

        pEntrada.add(pEntradaP1);
        pEntrada.add(jSelectEntrada);
        pEntrada.add(pEntradaP2);

        pSalidaP1.add(bRegresarRegistroS);
        pSalidaP1.add(bBuscarSalida);

        pSalida.add(new JLabel("Salida"));
        pSalida.add(jSelectSalida);
        pSalida.add(pSalidaP1);

        pContenedorApp.add(pBotonesApp);

        pBotones.add(bAvanzarUnaHora);
        pBotones.add(bAvanzar30Min);
        pBotones.add(bAvanzar15min);
        pBotones.add(bAvanzar5min);

        pSeleccion.add(jSeleccionarCarros);
        pSeleccion.add(new JLabel("# de carros"));
        pSeleccion.add(bAgregarCarros);
        pSeleccion.add(bCrearCarroEspecial);

        panelControlTiempo.add(pSeleccion);
        panelControlTiempo.add(new JLabel("Reloj"));
        panelControlTiempo.add(pBotones);

        pCrearCarroEspecial.add(jSeleccionarEntrada);
        pCrearCarroEspecial.add(bSeleccionarEntrada);
        pCrearCarroEspecial.add(jSeleccionarSalida);
        pCrearCarroEspecial.add(bSeleccionarSalida);
        pCrearCarroEspecial.add(bRegresarCarroEspecial);
        pCrearCarroEspecial.add(bCrear);

        pContenedorTiempoYEspecial.add(panelControlTiempo);

        panelContador.add(lIdAutos);
        panelContador.add(new JLabel(""));
        panelContador.add(new JLabel("Numero de Autos:"));
        panelContador.add(contadorAutosEntradaySalida);

        pBaseDatos.add(panelContador);
        panelId.add(scroll);
        pBaseDatos.add(panelId);

        pDatosApp.add(new JLabel("Hora con más entradas:"));
        pDatosApp.add(hMayorEntrada);
        pDatosApp.add(new JLabel("Hora con más salida"));
        pDatosApp.add(hMayorSalida);
        pDatosApp.add(new JLabel("Entrada con mayor # carros"));
        pDatosApp.add(aEntradaMasOcupada);
        pDatosApp.add(new JLabel("Salida con mayor # carros"));
        pDatosApp.add(aSalidaMasOcupada);

        pContenedor.add(pDatos);
        pContenedor.add(pContenedorTiempoYEspecial);
        pContenedor.add(pMapa);
        pContenedor.add(pContenedorApp);
        pContenedor.add(pBaseDatos);
        pContenedor.add(pDatosApp);


        add(pContenedor);
    }

    public class BAgregarCarrosListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int iNumeroCarros = jSeleccionarCarros.getSelectedIndex();
            crearCarros(iNumeroCarros);
        }
    }

    public class BCrearCarroEspecialListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorTiempoYEspecial.remove(panelControlTiempo);
            pContenedorTiempoYEspecial.add(pCrearCarroEspecial);
            revalidate();
            repaint();
        }
    }

    public class  BSeleccionarEntradaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }

    public class  BSeleccionarSalidaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }

    public class  BRegresarCarroEspecial implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorTiempoYEspecial.remove(pCrearCarroEspecial);
            pContenedorTiempoYEspecial.add(panelControlTiempo);
            revalidate();
            repaint();
        }
    }

    public class  BCrearListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }

    public class BAHoraListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            crear = true;
            reloj.setHora(reloj.getHora()+1);
            verificarTiempo();
            salidaCarros();
            autosCirculando();
            setEntradaySalidaMasOcupada();
            if(reloj.getMinutos() == 0){
                VerificarHoraConMasSalidasYEntradas();
            }
        }
    }

    public class BtreintaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            crear = true;
            reloj.setMinutos(reloj.getMinutos()+30);
            verificarTiempo();
            salidaCarros();
            autosCirculando();
            setEntradaySalidaMasOcupada();
            if(reloj.getMinutos() == 0){
                VerificarHoraConMasSalidasYEntradas();
            }
        }
    }

    public class BQuinceListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            crear = true;
            reloj.setMinutos(reloj.getMinutos()+15);
            verificarTiempo();
            salidaCarros();
            autosCirculando();
            setEntradaySalidaMasOcupada();
            if(reloj.getMinutos() == 0){
                VerificarHoraConMasSalidasYEntradas();
            }
        }
    }

    public class BCincoListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            crear = true;
            reloj.setMinutos(reloj.getMinutos()+5);
            verificarTiempo();
            salidaCarros();
            autosCirculando();
            setEntradaySalidaMasOcupada();
            if(reloj.getMinutos() == 0){
                VerificarHoraConMasSalidasYEntradas();
            }
        }
    }

    public void verificarTiempo(){
        if(reloj.getMinutos() >= 60){
            reloj.setMinutos(0);
            reloj.setHora(reloj.getHora()+1);
        }
        if(reloj.getHora()>= 25){
            reloj.setHora(0);
            fecha.setDia(fecha.getDia()+1);
        }
        if(fecha.getDia() >= 32){
            fecha.setDia(1);
            fecha.setMes(fecha.getMes()+1);
        }
        fHora.setText(reloj.toString());
        fDia.setText(fecha.toString());
    }

    public Hora arreglarTiempo(Hora watch, Calendario nFecha){
        if(watch.getMinutos() >= 60){
            watch.setMinutos(0);
            watch.setHora(watch.getHora()+1);
        }
        if(watch.getHora()>= 25){
            watch.setHora(0);
            nFecha.setDia(nFecha.getDia()+1);
        }
        return watch;
    }

    public void VerificarHoraConMasSalidasYEntradas(){
        //TODO arreglar esto
        /*if(colaCarros.getFirst() != null){
            for(int i=1; i<MAX_SIZE; i++){
                System.out.println("\n*** Entrada # " + getEntradaoSalida(i) + "\n");
                if(tablaHash[i].getSize() > 0){
                    Nodo<Auto> tempNodo = tablaHash[i].getFirst();
                    while(tempNodo != null){
                        System.out.println(tempNodo.getElement().toString() + "\n");
                        tempNodo = tempNodo.getNext();
                    }
                }else System.out.println("Vacio");
            }
        }*/
        if(colaCarros.getFirst() != null){
            lAutosAcumulados = colaCarros.getSize() - lAutosAnteriores;
            lAutosAnteriores = colaCarros.getSize();
            if(lAutosAcumulados >= lAutosMaximos){
                lAutosMaximos = lAutosAcumulados;
                hMayorEntrada.setText(reloj.toString());
            }

            lAutosSalidaA = carrosSalieron - lAutosSalidasAnteriores;
            lAutosSalidasAnteriores = carrosSalieron;
            if(lAutosSalidaA >= lAutosSalidasMaxima){
                lAutosSalidasMaxima = lAutosSalidaA;
                hMayorSalida.setText(reloj.toString());
            }
        }
    }

    public void crearCarros(int iNumeroCarros){
        if(crear){
            Hora horaSalida = new Hora();
            Calendario fechaSalida = new Calendario(0,0,0);
            int di = fecha.getDia();
            int me = fecha.getMes();
            int ani = fecha.getAño();
            Calendario nFecha = new Calendario(di, me, ani);
            int h = reloj.getHora();
            int m = reloj.getMinutos();
            Hora horaEntrada = new Hora(h, m);
            for(int i=0; i<iNumeroCarros; i++){
                int entradaRandom = getEntradaRandom();
                int salidaRandom = getSalidaRandom();
                getRecorridoRandom(entradaRandom, salidaRandom);
                String e = getEntradaoSalida(entradaRandom);
                String s = getEntradaoSalida(salidaRandom);
                int vMaxima = 80;
                int vMinima = 60;
                float velocidadRandom = (float)((Math.random()*((vMaxima - vMinima) + 1))) + vMinima;
                mapa.calc(nodes, entradaRandom);
                float distancia = mapa.distance[salidaRandom];
                float tiempo = (distancia/velocidadRandom)*60;
                m += tiempo;
                Hora hSalida = new Hora(h, m);
                Hora nHora = arreglarTiempo(hSalida, nFecha);
                Auto a = new Auto(id, horaEntrada, fecha, true, e, horaSalida, fechaSalida, "", hSalida,
                        nFecha, s, velocidadRandom, distancia);
                NodoD<Auto> nNodoD = new NodoD<Auto>(a, null, null);
                Nodo<Auto> nNodo = new Nodo<Auto>(a, null);
                //TODO entrada random [0] -> [20]
                //
                entradaRandom = entradaRandom -1;
                tablaHash[entradaRandom].addLast(nNodo);
                colaCarros.add(nNodoD);
                id++;
            }
            fNumeroAutos.setText(String.valueOf(id-1));
        }
        crear = false;

        /*source = 2;
        String e = getEntradaoSalida(source);
        int fin = 13;
        String s = getEntradaoSalida(fin);
        mapa.calc(nodes, source);
        System.out.println("source :"+e+"\t destination :"+s+"\t MinCost is :"+mapa.distance[fin]+"\t");*/

    }

    public int getRecorridoRandom(int entradaRandom, int salidaRandom){
        if(entradaRandom == salidaRandom){
            do{
                salidaRandom = getSalidaRandom();
            }while(entradaRandom == salidaRandom);
        }
        return salidaRandom;
    }

    public int getEntradaRandom(){
        int entradaRandom;
        do{
            entradaRandom = (int)(Math.random()*MAX_SIZE+1);
        }while(entradaRandom == 9 || entradaRandom == 17 || entradaRandom == 8 || entradaRandom == 0);
        return entradaRandom;
    }

    public int getSalidaRandom(){
        int salidaRandom;
        do{
            salidaRandom = (int)(Math.random()*MAX_SIZE + 1);
        }while(salidaRandom == 16 || salidaRandom == 8);
        return salidaRandom;
    }

    public void salidaCarros(){
        NodoD<Auto> head = colaCarros.getFirst();
        if(head != null){
            for(int i=0; i<colaCarros.getSize(); i++){
                if(head.getElement().getHoraAuxiliar().esMenor(reloj)
                ||  head.getElement().getHoraAuxiliar().esIgual(reloj)){
                    if(head.getElement().isDentro()){
                        Nodo<Auto> n = new Nodo<Auto>(head.getElement(), null);
                        int p = registarEnTablaSalida(head.getElement().getSale());
                        tablaHashSalida[p].addLast(n);
                        head.getElement().setDentro(false);
                        registrarSalida(head);
                        carrosSalieron++;
                    }
                }
                head = head.getNext();
            }
        }
        fAutosSalida.setText(String.valueOf(carrosSalieron));
    }

    public int registarEnTablaSalida(String salida){
        if(salida.equals("CDI")){
            return 1;
        }else if(salida.equals("CONSCRIPTO")){
            return 2 ;
        }else if(salida.equals("CHAPULTEPEC REFORMA")){
            return 3 ;
        }else if(salida.equals("ALENCASTRE")){
            return 4 ;
        }else if(salida.equals("SAN ANTONIO")){
            return 5 ;
        }else if(salida.equals("SAN JERONIMO")){
            return 6 ;
        }else if(salida.equals("BLVD LA LUZ")){
            return 7 ;
        }else if(salida.equals("CONEXION AUTOPISTA URBANA PTE")){
            return 8 ;
        }else if(salida.equals("DISTRIBUIDOR VIAL")){
            return 9 ;
        }else if(salida.equals("LUIS CABRERA")){
            return 10 ;
        }else if(salida.equals("LAS TORRES")){
            return 11 ;
        }else if(salida.equals("LAS AGUILAS")){
            return 12 ;
        }else if(salida.equals("POETAS")){
            return 13 ;
        }else if(salida.equals("PICACHO AJUSCO")){
            return 14 ;
        }else if(salida.equals("ZACATEPETL")){
            return 15 ;
        }else if(salida.equals("CUICUILCO")){
            return 16 ;
        }else if(salida.equals("INSURGENTES SUR")){
            return 17 ;
        }else if(salida.equals("AZTECA")){
            return 18 ;
        }else if(salida.equals("TLALPAN")){
            return 19 ;
        }else if(salida.equals("CUERNAVACA")){
            return 20 ;
        }else if(salida.equals("XOCHIMILCO")){
            return 21 ;
        }
        return 0;
    }

    public void autosCirculando(){
        NodoD<Auto> cabeza = colaCarros.getFirst();
        int aCirculando = 0;
        if(cabeza != null && cabeza.getNext() != null){
            do{
                //TODO nullPointerException
                if(cabeza.getElement().isDentro() == true){
                    aCirculando++;
                }
                cabeza = cabeza.getNext();
            }while(cabeza != colaCarros.getLast());
            if(colaCarros.getLast().getElement().isDentro() == true){
                aCirculando++;
            }
        }
        fAutosActuales.setText(String.valueOf(aCirculando));

    }

    public void crearGrafo(){
        //conexiones de CDI
        mapa.cost[1][1] = 0;
        mapa.cost[1][2] = 3;
        for(int i=3; i<=21; i++){
            mapa.cost[1][i] = 99999;
        }
        //conexiones de conscripto
        mapa.cost[2][1] = 3;
        mapa.cost[2][2] = 0;
        mapa.cost[2][3] = 5;
        for(int i=4; i<=21; i++){
            mapa.cost[2][i] = 99999;
        }
        //conexiones de chapultepec
        mapa.cost[3][1] = 99999;
        mapa.cost[3][2] = 5;
        mapa.cost[3][3] = 0;
        mapa.cost[3][4] = 4;
        for(int i=5; i<=21; i++){
            mapa.cost[3][i] = 99999;
        }
        //conexiones de Alencastre
        for(int i=1; i<=2; i++){
            mapa.cost[4][i] = 99999;
        }
        mapa.cost[4][3] = 4;
        mapa.cost[4][4] = 0;
        mapa.cost[4][5] = 2;
        for(int i=6; i<=21; i++){
            mapa.cost[4][i] = 99999;
        }
        //conexiones de San Antonio
        for(int i=1; i<=3; i++){
            mapa.cost[5][i] = 99999;
        }
        mapa.cost[5][4] = 2;
        mapa.cost[5][5] = 0;
        mapa.cost[5][6] = 6;
        for(int i=7; i<=21; i++){
            mapa.cost[5][i] = 99999;
        }
        //conexiones de San jeronimo
        for(int i=1; i<=4; i++){
            mapa.cost[6][i] = 99999;
        }
        mapa.cost[6][5] = 6;
        mapa.cost[6][6] = 0;
        mapa.cost[6][7] = 4;
        for(int i=8; i<=21; i++){
            mapa.cost[6][i] = 99999;
        }
        //conexiones de BLVD la luz
        for(int i=1; i<=5; i++){
            mapa.cost[7][i] = 99999;
        }
        mapa.cost[7][6] = 4;
        mapa.cost[7][7] = 0;
        mapa.cost[7][8] = 1;
        for(int i=9; i<=21; i++){
            mapa.cost[7][i] = 99999;
        }
        //conexiones de conexion
        for(int i=1; i<=6; i++){
            mapa.cost[8][i] = 99999;
        }
        mapa.cost[8][7] = 1;
        mapa.cost[8][8] = 0;
        mapa.cost[8][9] = 4;
        for(int i=10; i<=21; i++){
            mapa.cost[8][i] = 99999;
        }
        mapa.cost[8][14] = 2;
        //conexiones de Distribuidora
        for(int i=1; i<=7; i++){
            mapa.cost[9][i] = 99999;
        }
        mapa.cost[9][8] = 4;
        mapa.cost[9][9] = 0;
        mapa.cost[9][10] = 3;
        for(int i=11; i<=21; i++){
            mapa.cost[9][i] = 99999;
        }
        //conexiones de luis cabrera
        for(int i=1; i<=8; i++){
            mapa.cost[10][i] = 99999;
        }
        mapa.cost[10][9] = 3;
        mapa.cost[10][10] = 0;
        mapa.cost[10][11] = 5;
        for(int i=12; i<=21; i++){
            mapa.cost[10][i] = 99999;
        }
        //conexiones de las torres
        for(int i=1; i<=9; i++){
            mapa.cost[11][i] = 99999;
        }
        mapa.cost[11][10] = 5;
        mapa.cost[11][11] = 0;
        mapa.cost[11][12] = 3;
        for(int i=13; i<=21; i++){
            mapa.cost[11][i] = 99999;
        }
        //conexiones de las aguilas
        for(int i=1; i<=10; i++){
            mapa.cost[12][i] = 99999;
        }
        mapa.cost[12][11] = 3;
        mapa.cost[12][12] = 0;
        mapa.cost[12][13] = 7;
        for(int i=14; i<=21; i++){
            mapa.cost[12][i] = 99999;
        }
        //conexiones de poetas
        for(int i=1; i<=11; i++){
            mapa.cost[13][i] = 99999;
        }
        mapa.cost[13][12] = 7;
        mapa.cost[13][13] = 0;
        for(int i=14; i<=21; i++){
            mapa.cost[13][i] = 99999;
        }
        //conexiones de picacho
        for(int i=1; i<=13; i++){
            mapa.cost[14][i] = 99999;
        }
        mapa.cost[14][8] = 2;
        mapa.cost[14][14] = 0;
        mapa.cost[14][15] = 2;
        for(int i=16; i<=21; i++){
            mapa.cost[14][i] = 99999;
        }
        //conexiones de cerro
        for(int i=1; i<=13; i++){
            mapa.cost[15][i] = 99999;
        }
        mapa.cost[15][14] = 2;
        mapa.cost[15][15] = 0;
        mapa.cost[15][16] = 4;
        for(int i=17; i<=21; i++){
            mapa.cost[15][i] = 99999;
        }
        //conexiones de Cuicuilco
        for(int i=1; i<=14; i++){
            mapa.cost[16][i] = 99999;
        }
        mapa.cost[16][15] = 4;
        mapa.cost[16][16] = 0;
        mapa.cost[16][17] = 3;
        for(int i=18; i<=21; i++){
            mapa.cost[16][i] = 99999;
        }
        //conexiones de Insurgentes
        for(int i=1; i<=15; i++){
            mapa.cost[17][i] = 99999;
        }
        mapa.cost[17][16] = 3;
        mapa.cost[17][17] = 0;
        mapa.cost[17][18] = 5;
        for(int i=19; i<=21; i++){
            mapa.cost[17][i] = 99999;
        }
        //conexiones de Azteca
        for(int i=1; i<=16; i++){
            mapa.cost[18][i] = 99999;
        }
        mapa.cost[18][17] = 5;
        mapa.cost[18][18] = 0;
        mapa.cost[18][19] = 4;
        for(int i=20; i<=21; i++){
            mapa.cost[18][i] = 99999;
        }
        //conexiones de Tlalpan
        for(int i=1; i<=17; i++){
            mapa.cost[19][i] = 99999;
        }
        mapa.cost[19][18] = 4;
        mapa.cost[19][19] = 0;
        mapa.cost[19][20] = 6;
        mapa.cost[19][21] = 2;
        //conexiones de Cuernavaca
        for(int i=1; i<=18; i++){
            mapa.cost[20][i] = 99999;
        }
        mapa.cost[20][19] = 6;
        mapa.cost[20][20] = 0;
        mapa.cost[20][21] = 99999;
        //conexiones de Xochimilco
        for(int i=1; i<=18; i++){
            mapa.cost[21][i] = 99999;
        }
        mapa.cost[21][19] = 2;
        mapa.cost[21][20] = 99999;
        mapa.cost[21][21] = 0;
    }

    public String getEntradaoSalida(int nRamdom){
        String nombre = "";
        if(nRamdom == 1){
            return "CDI";
        }else if(nRamdom == 2){
            return "CONSCRIPTO";
        }else if(nRamdom == 3){
            return "CHAPULTEPEC REFORMA";
        }else if(nRamdom == 4){
            return "ALENCASTRE";
        }else if(nRamdom == 5){
            return "SAN ANTONIO";
        }else if(nRamdom == 6){
            return "SAN JERONIMO";
        }else if(nRamdom == 7){
            return "BLVD LA LUZ";
        }else if(nRamdom == 8){
            return "CONEXION AUTOPISTA URBANA PTE";
        }else if(nRamdom == 9){
            return "DISTRIBUIDOR VIAL";
        }else if(nRamdom == 10){
            return "LUIS CABRERA";
        }else if(nRamdom == 11){
            return "LAS TORRES";
        }else if(nRamdom == 12){
            return "LAS AGUILAS";
        }else if(nRamdom == 13){
            return "POETAS";
        }else if(nRamdom == 14){
            return "PICACHO AJUSCO";
        }else if(nRamdom == 15){
            return "ZACATEPETL";
        }else if(nRamdom == 16){
            return "CUICUILCO";
        }else if(nRamdom == 17){
            return "INSURGENTES SUR";
        }else if(nRamdom == 18){
            return "AZTECA";
        }else if(nRamdom == 19){
            return "TLALPAN";
        }else if(nRamdom == 20){
            return "CUERNAVACA";
        }else if(nRamdom == 21){
            return "XOCHIMILCO" ;
        }
        return nombre;

    }

    public void registrarSalida(NodoD<Auto> cabeza){
        cabeza.getElement().setHoraSalida(cabeza.getElement().getHoraAuxiliar());
        cabeza.getElement().setFechaSalida(cabeza.getElement().getFechaAuxiliar());
        cabeza.getElement().setSale(cabeza.getElement().getsAuxiliar());

    }

    public void setEntradaySalidaMasOcupada(){
        String eMax = "";
        String sMax = "";
        long maxCarrosE = 0;
        long maxCarrosS = 0;
        if(colaCarros.getFirst() != null){
            for(int i=1 ; i<MAX_SIZE; i++){
                if(tablaHash[i].getSize() > 0 && tablaHashSalida[i].getSize() == maxCarrosE){
                    eMax = "none";
                }
                if(tablaHash[i].getSize() > 0 && tablaHash[i].getSize() > maxCarrosE){
                    eMax = getEntradaoSalida(i);
                    maxCarrosE = tablaHash[i].getSize();
                }
                if(tablaHashSalida[i].getSize() > 0 && tablaHashSalida[i].getSize() > maxCarrosS){
                    sMax = getEntradaoSalida(i);
                    maxCarrosS = tablaHashSalida[i].getSize();
                }
                if(tablaHashSalida[i].getSize() > 0 && tablaHashSalida[i].getSize() == maxCarrosS){
                    sMax = "none";
                }
            }
        }
        aEntradaMasOcupada.setText(eMax);
        aSalidaMasOcupada.setText(sMax);
    }

    public class BRegistroEntradaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorApp.remove(pBotonesApp);
            pContenedorApp.add(pEntrada);
            revalidate();
            repaint();
        }
    }
    public class BRegistroSalidaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorApp.remove(pBotonesApp);
            pContenedorApp.add(pSalida);
            revalidate();
            repaint();
        }
    }
    public class BPanelBusquedaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorApp.remove(pBotonesApp);
            pContenedorApp.add(pAppUsuario);
            revalidate();
            repaint();
        }
    }
    public class BBaseDatosListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Base de Datos");
            contadorAutosEntradaySalida.setText(String.valueOf(colaCarros.getSize()));
            NodoD<Auto> cabeza = colaCarros.getFirst();
            aIDAutos.setText("");
            while(cabeza != colaCarros.getLast()){
                //TODO arreglar linea de error NullpointerException
                aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                cabeza = cabeza.getNext();
            }
            aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
        }
    }
    public class BRegresarListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorApp.remove(pAppUsuario);
            pContenedorApp.add(pBotonesApp);
            repaint();
        }
    }
    public class BRRegistroNorte implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorApp.remove(pEntrada);
            pContenedorApp.add(pBotonesApp);
            repaint();
        }
    }
    public class BRRegistroSur implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pContenedorApp.remove(pSalida);
            pContenedorApp.add(pBotonesApp);
            repaint();
        }
    }

    public class bBuscarListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int iEntrada =  jSelectEntrada.getSelectedIndex();
            lIdAutos.setText("Entrada: " + getEntradaoSalida(iEntrada));
            if(iEntrada > 8){
                if(iEntrada > 14){
                    iEntrada +=3;
                }else if(iEntrada >15){
                    iEntrada += 4;
                }else{
                    iEntrada += 2;
                }
            }
            contadorAutosEntradaySalida.setText(String.valueOf(tablaHash[iEntrada].getSize()));
            if(tablaHash[iEntrada].getSize() > 0){
                aIDAutos.setText("");
                Nodo<Auto> tempNodo = tablaHash[iEntrada].getFirst();
                while(tempNodo != null){
                    aIDAutos.setText(aIDAutos.getText() + tempNodo.getElement().toString() + "\n");
                    tempNodo = tempNodo.getNext();
                }
            }else{
                aIDAutos.setText("Vacio");
            }
        }
    }

    public class bBuscarSalidaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int iSalida =  jSelectSalida.getSelectedIndex();
            lIdAutos.setText("Salida: " + getEntradaoSalida(iSalida));
            if(iSalida > 15){
                iSalida += 1;
            }
            contadorAutosEntradaySalida.setText(String.valueOf(tablaHash[iSalida].getSize()));
            if(tablaHash[iSalida].getSize() > 0){
                aIDAutos.setText("");
                Nodo<Auto> tempNodo = tablaHash[iSalida].getFirst();
                while(tempNodo != null){
                    aIDAutos.setText(tempNodo.getElement().toString() + "\n");
                    tempNodo = tempNodo.getNext();
                }
            }else{
                aIDAutos.setText("Vacio");
            }
        }
    }

    public class BBuscarIdListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }

    //Variables generales de la app
    private JPanel pContenedor, pDatos, pMapa, pAppUsuario, pBotones;
    private JButton bAvanzarUnaHora, bAvanzar30Min, bAvanzar15min, bAvanzar5min;
    private JLabel lNumAutos, lAutosEntrada, lAutosSalida, lAutosActuales, lHora, lDia;
    private JTextField fNumeroAutos, fAutosEntrada, fAutosSalida, fAutosActuales, fHora, fDia;

    //Variables para controlar el tiempo
    private JPanel panelControlTiempo, pSeleccion, pContenedorTiempoYEspecial, pCrearCarroEspecial;
    private JComboBox jSeleccionarCarros, jSeleccionarEntrada, jSeleccionarSalida;
    private JButton bAgregarCarros, bCrearCarroEspecial, bSeleccionarSalida, bSeleccionarEntrada;
    private JButton bRegresarCarroEspecial, bCrear;


    //Variables appUsuario
    private JPanel panelBusqueda, panelId;
    private JLabel lBuscarAutos, lIdAutos;
    private JButton bBuscarID;
    private JTextField fIDAutos;
    private JTextArea aIDAutos, aIDEncontrado;
    private JScrollPane scroll;

    //variables AppGeneral
    private JPanel pContenedorApp, pBotonesApp, pEntrada, pSalida;
    private JPanel pEntradaP1, pEntradaP2;
    private JPanel pSalidaP1;
    private JButton bRegistroEntrada, bRegistroSalida, bPanelBuscar, bBaseDeDatos;
    private JComboBox jSelectEntrada, jSelectSalida;
    private JButton bRegresar, bBuscarEntrada, bBuscarSalida;
    /*private JButton bSalidaNorte, bSalidaSur, bSalidaEste, bSalidaOeste;*/
    private JLabel lNorte, lSur, lEste, lOeste, lOpciones;

    //Variables panel contador de carros  entrada
    private JPanel panelContador;
    private JTextField contadorAutosEntradaySalida;

    //Variables NuevosPaneles
    private JPanel pBaseDatos, pDatosApp;
    private JTextField hMayorEntrada, hMayorSalida, aEntradaMasOcupada, aSalidaMasOcupada;
    //TODO Agregar funcionalida a los JTextFields que estan aquí arriba
    //TODO ir a cenar porque tengo hambre

    //Variables botonesRegresar
    private JButton bRegresarRegistroN, bRegresarRegistroS;

    //variables para agreagar la imagen del grafo
    private ImageIcon image;


}
