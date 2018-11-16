package com.company;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import sun.java2d.loops.ProcessPath;
import sun.management.HotspotClassLoadingMBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.MarshalException;
import javax.swing.JOptionPane;

public class GUI extends JFrame{
    //Variables Funcionamiento
    private Hora reloj;
    private Calendario fecha;
    protected Queue<Auto> colaCarros;
    protected long id = 1, carrosSalieron = 0;
    protected Entrada[] arregloEntrada;
    protected Salida[] arregloSalida;
    protected DoubleLinkedList<Auto> eNorte, eSur, eEste, eOeste, sNorte, sSur, sEste, sOeste;
    protected Hora hMasEntradas, hMasSalidas;
    protected Calendario fechaMasEntradas, fechaMasSalidas;
    protected long lAutosAcumulados, lAutosAnteriores = 0, lAutosMaximos = 0;
    protected long lAutosSalidaA, lAutosSalidasAnteriores = 0, lAutosSalidasMaxima = 0;
    private boolean crear = true;

    //Variables para la hashTable de Coches
    private final int MAX_SIZE = 4;
    private SingleLinkedList<Auto> tablaHash[];

    //Definir arreglos
    //Entrada
    Entrada entradaNorte = new Entrada("Norte", 0);
    Entrada entradaSur = new Entrada("Sur", 0);
    Entrada entradaEste = new Entrada("Este", 0);
    Entrada entradaOeste = new Entrada("Oeste", 0);
    //Salida
    Salida salidaNorte = new Salida("Norte", 0);
    Salida salidaSur = new Salida("Sur", 0);
    Salida salidaEste = new Salida("Este", 0);
    Salida salidaOeste = new Salida("Oeste", 0);

    //Constructor
    public GUI(){
        reloj = new Hora();
        fecha = new Calendario();
        colaCarros = new Queue<Auto>();
        arregloEntrada = new Entrada[4];
        arregloSalida = new Salida[4];
        eNorte = new DoubleLinkedList<Auto>();
        eSur = new DoubleLinkedList<Auto>();
        eEste = new DoubleLinkedList<Auto>();
        eOeste = new DoubleLinkedList<Auto>();
        sNorte = new DoubleLinkedList<Auto>();
        sSur = new DoubleLinkedList<Auto>();
        sEste = new DoubleLinkedList<Auto>();
        sOeste = new DoubleLinkedList<Auto>();
        hMasEntradas = new Hora();
        hMasSalidas = new Hora();
        tablaHash = new SingleLinkedList[MAX_SIZE];
        for(int i=0; i<MAX_SIZE; i++)
            tablaHash[i] = new SingleLinkedList<Auto>();
        llenarArreglos();
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

        //ComboBox
        jSeleccionarCarros = new JComboBox();
        jSeleccionarEntrada = new JComboBox();
        jSeleccionarSalida = new JComboBox();

        jSeleccionarCarros.setModel(new DefaultComboBoxModel(new String[]
                {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        jSeleccionarEntrada.setModel(new DefaultComboBoxModel(new String[]
                {"-Selecciona Entrada-", "Norte", "Sur", "Este", "Oeste"}));
        jSeleccionarSalida.setModel(new DefaultComboBoxModel(new String[]
                {"-Selecciona Salida- ", "Norte", "Sur", "Este", "Oeste"}));

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
        jSelectEntrada.setModel(new DefaultComboBoxModel(new String[] {"-Seleccionar Entrada -", " Entrada Norte ",
                " Entrada Sur ", " Entrada Este ", " Entrada Oeste "}));
        bBuscarEntrada = new JButton("Buscar");

        jSelectSalida = new JComboBox();
        jSelectSalida.setModel(new DefaultComboBoxModel(new String[] {"-Seleccionar Salida -", "Salida Norte",
                "Salida Sur", "Salida Este", "Salida Oeste"}));
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

    public void VerificarHoraConMasSalidasYEntradas(){
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

    public void crearCarros(int nRandom){
        if(crear){
            Hora horaSalida = new Hora();
            Calendario fechaSalida = new Calendario(0,0,0);
            int h = reloj.getHora();
            int m = reloj.getMinutos();
            Hora horaEntrada = new Hora(h, m);
            for(int i=0; i<nRandom; i++){
                int entradaRandom = (int)(Math.random()*MAX_SIZE);
                Auto a = new Auto(id, horaEntrada, fecha, true, arregloEntrada[entradaRandom].getNombre(), horaSalida,
                        fechaSalida, "");
                NodoD<Auto> nNodoD = new NodoD<Auto>(a, null, null);
            Nodo<Auto> nNodo = new Nodo<Auto>(a, null);
            tablaHash[entradaRandom].addFirst(nNodo);
            arregloEntrada[entradaRandom].setlNumeroDeAutos(arregloEntrada[entradaRandom].getlNumeroDeAutos() + 1);
                colaCarros.add(nNodoD);
                id++;
            }
            fNumeroAutos.setText(String.valueOf(id-1));
        }
        crear = false;

    }

    public void salidaCarros(){
        //TODO remover el numero random por el peso de las aristas
        NodoD<Auto> head = colaCarros.getFirst();
        if(head != null){
            for(int i=0; i<colaCarros.getSize(); i++){
                //System.out.println("head = " + head.getElement().getId());
                int nRandom = (int)(Math.random() * 100);
                if(nRandom < 10 && head.getElement().isDentro() == true){
                    head.getElement().setDentro(false);
                    registrarSalida(head);
                    carrosSalieron++;
                }
                head = head.getNext();
            }
        }
        fAutosSalida.setText(String.valueOf(carrosSalieron));
    }

    public void autosCirculando(){
        NodoD<Auto> cabeza = colaCarros.getFirst();
        int aCirculando = 0;
        if(cabeza != null){
            do{
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

    public void llenarArreglos(){
        //Entrada
        arregloEntrada[0] = entradaNorte;
        arregloEntrada[1] = entradaSur;
        arregloEntrada[2] = entradaEste;
        arregloEntrada[3] = entradaOeste;
        //Salida
        arregloSalida[0] = salidaNorte;
        arregloSalida[1] = salidaSur;
        arregloSalida[2] = salidaEste;
        arregloSalida[3] = salidaOeste;
    }

    public void registrarSalida(NodoD<Auto> cabeza){
        int salidaRandom = 0;
        do{
            salidaRandom = (int)(Math.random() * 4);
        }while(cabeza.getElement().getEntra().equals(arregloSalida[salidaRandom].getNombre()));
        arregloSalida[salidaRandom].setlNumeroDeAutos(arregloSalida[salidaRandom].getlNumeroDeAutos() + 1);
        cabeza.getElement().setSale(arregloSalida[salidaRandom].getNombre());
        int h = reloj.getHora();
        int m = reloj.getMinutos();
        Hora hSalida = new Hora(h, m);
        cabeza.getElement().setHoraSalida(hSalida);
        cabeza.getElement().setFechaSalida(fecha);
    }

    public void setEntradaySalidaMasOcupada(){
        if(colaCarros.getFirst() != null){
            Entrada max = arregloEntrada[0];
            for(int i=1; i<arregloEntrada.length-1; i++){
                if(max.getlNumeroDeAutos() < arregloEntrada[i].getlNumeroDeAutos()){
                    max = arregloEntrada[i];
                }
            }
            aEntradaMasOcupada.setText(max.getNombre());
            Salida sMax = arregloSalida[0];
            for(int i=1; i<arregloSalida.length-1; i++){
                if(max.getlNumeroDeAutos() < arregloSalida[i].getlNumeroDeAutos()){
                    sMax = arregloSalida[i];
                }
            }
            aSalidaMasOcupada.setText(sMax.getNombre());
        }
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
            do{
                aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                cabeza = cabeza.getNext();
            }while(cabeza != colaCarros.getLast());
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
    //TODO repartir en diferentes LinkedList las entradas y salidas

    public class bBuscarListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int iEntrada =  jSelectEntrada.getSelectedIndex();
            NodoD<Auto> cabeza = colaCarros.getFirst();
            switch (iEntrada){
                case 1:
                    lIdAutos.setText("Entrada Norte");
                    contadorAutosEntradaySalida.setText(String.valueOf(entradaNorte.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getEntra().equals("Norte")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getEntra().equals("Norte")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
                case 2:
                    lIdAutos.setText("Entrada Sur");
                    contadorAutosEntradaySalida.setText(String.valueOf(entradaSur.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getEntra().equals("Sur")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getEntra().equals("Sur")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
                case 3:
                    lIdAutos.setText("Entrada Este");
                    contadorAutosEntradaySalida.setText(String.valueOf(entradaEste.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getEntra().equals("Este")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getEntra().equals("Este")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
                case 4:
                    lIdAutos.setText("Entrada Oeste");
                    contadorAutosEntradaySalida.setText(String.valueOf(entradaOeste.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getEntra().equals("Oeste")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getEntra().equals("Oeste")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
            }
        }
    }

    public class bBuscarSalidaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int iSalida =  jSelectSalida.getSelectedIndex();
            NodoD<Auto> cabeza = colaCarros.getFirst();
            switch (iSalida){
                case 1:
                    lIdAutos.setText("Salida Norte");
                    contadorAutosEntradaySalida.setText(String.valueOf(salidaNorte.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getSale().equals("Norte")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getSale().equals("Norte")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
                case 2:
                    lIdAutos.setText("Salida Sur");
                    contadorAutosEntradaySalida.setText(String.valueOf(salidaSur.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getSale().equals("Sur")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getSale().equals("Sur")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
                case 3:
                    lIdAutos.setText("Salida Este");
                    contadorAutosEntradaySalida.setText(String.valueOf(salidaEste.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getSale().equals("Este")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getSale().equals("Este")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
                case 4:
                    lIdAutos.setText("Salida Oeste");
                    contadorAutosEntradaySalida.setText(String.valueOf(salidaOeste.getlNumeroDeAutos()));
                    aIDAutos.setText("");
                    do{
                        if(cabeza.getElement().getSale().equals("Oeste")){
                            aIDAutos.setText(aIDAutos.getText() + cabeza.getElement().toString());
                        }
                        cabeza = cabeza.getNext();
                    }while(cabeza != colaCarros.getLast());
                    if(cabeza.getElement().getSale().equals("Oeste")){
                        aIDAutos.setText(aIDAutos.getText() + colaCarros.getLast().getElement().toString());
                    }
                    break;
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

}
