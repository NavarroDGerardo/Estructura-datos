package com.company;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import sun.java2d.loops.ProcessPath;
import sun.management.HotspotClassLoadingMBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;

public class GUI extends JFrame{
    //Variables generales de la app
    private JPanel pContenedor, pDatos, pMapa, pAppUsuario, pBotones;
    private JButton bAvanzarUnaHora, bAvanzar30Min, bAvanzar15min, bAvanzar5min;
    private JLabel lNumAutos, lAutosEntrada, lAutosSalida, lAutosActuales, lHora, lDia;
    private JTextField fNumeroAutos, fAutosEntrada, fAutosSalida, fAutosActuales, fHora, fDia;

    //Variables appUsuario
    private JPanel panelBusqueda, panelId;
    private JLabel lBuscarAutos, lIdAutos;
    private JButton bBuscarID;
    private JTextField fIDAutos;
    private JTextArea aIDAutos, aIDEncontrado;
    private JScrollPane scroll;

    //variables AppGeneral
    private JPanel pContenedorApp, pBotonesApp, pEntrada, pSalida;
    private JButton bRegistroEntrada, bRegistroSalida, bPanelBuscar, bBaseDeDatos;
    private JButton bEntradaNorte, bEntradaSur, bEntradaEste, bEntradaOeste;
    private JButton bRegresar;
    private JButton bSalidaNorte, bSalidaSur, bSalidaEste, bSalidaOeste;
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

    //Variables Funcionamiento
    private Hora reloj;
    private Calendario fecha;
    protected Queue<Auto> colaCarros;
    protected long id = 1, carrosSalieron = 0;
    protected Entrada[] arregloEntrada;
    protected Salida[] arregloSalida;
    protected DoubleLinkedList<Auto> eNorte, eSur, eEste, eOeste, sNorte, sSur, sEste, sOeste;

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
        pBotones = new JPanel();
        //Variables botones
        bAvanzarUnaHora = new JButton("Avanzar una hora");
        bAvanzar30Min = new JButton("Avanzar treinta minutos");
        bAvanzar15min = new JButton("Avanzar quince minutos");
        bAvanzar5min = new JButton("Avanzar 5 minutos");
            //Añadir Funcioanlidad a los botones
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
        pEntrada.setLayout(new GridLayout(5,1));
        pSalida = new JPanel();
        pSalida.setLayout(new GridLayout(5,1));
        //Botones AppGeneral
        bRegistroEntrada = new JButton("Registro Entrada");
        bRegistroSalida = new JButton("Registro Salida");
        bPanelBuscar = new JButton("Buscar ID");
        bBaseDeDatos = new JButton("Base de Datos");
        bRegresar = new JButton("Regresar");
        bEntradaNorte = new JButton("Norte");
        bEntradaSur = new JButton("Sur");
        bEntradaEste = new JButton("Este");
        bEntradaOeste = new JButton("Oeste");
        bSalidaNorte = new JButton("Norte");
        bSalidaSur = new JButton("Sur");
        bSalidaEste = new JButton("Este");
        bSalidaOeste = new JButton("Oeste");
        bRegresarRegistroN = new JButton("Regresar");
        bRegresarRegistroS = new JButton("Regresar");
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
        bEntradaNorte.addActionListener(new BEntrdaNorteListener());
        bEntradaSur.addActionListener(new BEntradaSurListener());
        bEntradaEste.addActionListener(new BEntrdaEsteLsitener());
        bEntradaOeste.addActionListener(new BEntradaOesteListener());
        bSalidaNorte.addActionListener(new BSalidaNorteListener());
        bSalidaSur.addActionListener(new  BSalidaSurListener());
        bSalidaEste.addActionListener(new BSalidaEsteListener());
        bSalidaOeste.addActionListener(new BSalidaOesteListener());
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
        pBotones.setLayout(new GridLayout(4,1));

        //Panel COntador de entrada y salida de autos
        panelContador = new JPanel();
        panelContador.setLayout(new GridLayout(2,2));
        contadorAutosEntradaySalida = new JTextField("0");
        contadorAutosEntradaySalida.setEditable(false);

        //Creación de la pDatos
        pDatos.add(lNumAutos);
        pDatos.add(new JLabel(""));
        pDatos.add(fNumeroAutos);
        /*pDatos.add(lAutosEntrada);
        pDatos.add(new JLabel(""));
        pDatos.add(fAutosEntrada);*/
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

        pEntrada.add(bEntradaNorte);
        pEntrada.add(bEntradaSur);
        pEntrada.add(bEntradaEste);
        pEntrada.add(bEntradaOeste);
        pEntrada.add(bRegresarRegistroN);

        pSalida.add(bSalidaNorte);
        pSalida.add(bSalidaSur);
        pSalida.add(bSalidaEste);
        pSalida.add(bSalidaOeste);
        pSalida.add(bRegresarRegistroS);

        pContenedorApp.add(pBotonesApp);

        pBotones.add(bAvanzarUnaHora);
        pBotones.add(bAvanzar30Min);
        pBotones.add(bAvanzar15min);
        pBotones.add(bAvanzar5min);

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
        pContenedor.add(pBotones);
        pContenedor.add(pMapa);
        pContenedor.add(pContenedorApp);
        pContenedor.add(pBaseDatos);
        pContenedor.add(pDatosApp);


        add(pContenedor);
    }

    public class BAHoraListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            reloj.setHora(reloj.getHora()+1);
            verificarTiempo();
            int nRandom = (int)(Math.random()*59);
            salidaCarros();
            crearCarros(nRandom);
            autosCirculando();
        }
    }

    public class BtreintaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            reloj.setMinutos(reloj.getMinutos()+30);
            verificarTiempo();
            int nRandom = (int)(Math.random()*30);
            salidaCarros();
            crearCarros(nRandom);
            autosCirculando();
        }
    }

    public class BQuinceListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            reloj.setMinutos(reloj.getMinutos()+15);
            verificarTiempo();
            int nRandom = (int)(Math.random()*15);
            salidaCarros();
            crearCarros(nRandom);
            autosCirculando();
        }
    }

    public class BCincoListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            reloj.setMinutos(reloj.getMinutos()+5);
            verificarTiempo();
            int nRandom = (int)(Math.random()*5);
            salidaCarros();
            crearCarros(nRandom);
            autosCirculando();
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

    public void crearCarros(int nRandom){
        Hora horaSalida = new Hora();
        Calendario fechaSalida = new Calendario(0,0,0);
        int h = reloj.getHora();
        int m = reloj.getMinutos();
        Hora horaEntrada = new Hora(h, m);
        for(int i=0; i<nRandom; i++){
            int entradaRandom = (int)(Math.random()*4);
            Auto a = new Auto(id, horaEntrada, fecha, true, arregloEntrada[entradaRandom].getNombre(), horaSalida,
                        fechaSalida, "");
            NodoD<Auto> nNodoD = new NodoD<Auto>(a, null, null);
            switch (entradaRandom){
                case 0:
                    eNorte.addFirst(nNodoD);
                    break;
                case 1:
                    eSur.addFirst(nNodoD);
                    break;
                case 2:
                    eEste.addFirst(nNodoD);
                    break;
                case 3:
                    eOeste.addFirst(nNodoD);
                    break;
            }
            colaCarros.add(nNodoD);
            id++;
        }
        fNumeroAutos.setText(String.valueOf(id-1));
    }

    public void salidaCarros(){
        NodoD<Auto> cabeza = colaCarros.getFirst();
        if(cabeza != null){
            do{
                int nRandom = (int)(Math.random() * 100);
                if(nRandom >= 90){
                    cabeza.getElement().setDentro(false);
                    registrarSalida(cabeza);
                    carrosSalieron++;
                }
                cabeza = cabeza.getNext();
            }while(cabeza != colaCarros.getLast());
            int nRandom = (int)(Math.random() * 100);
            if(nRandom >= 90){
                colaCarros.getLast().getElement().setDentro(false);
                carrosSalieron++;
            }
            fAutosSalida.setText(String.valueOf(carrosSalieron));
        }
    }

    public void autosCirculando(){
        NodoD<Auto> cabeza = colaCarros.getFirst();
        int aCirculando = 0;
        do{
            if(cabeza.getElement().isDentro() == true){
                aCirculando++;
            }
            cabeza = cabeza.getNext();
        }while(cabeza != colaCarros.getLast());
        if(colaCarros.getLast().getElement().isDentro() == true){
            aCirculando++;
        }
        fAutosActuales.setText(String.valueOf(aCirculando));

    }

    public void llenarArreglos(){
        Entrada eNorte = new Entrada("Norte", 0);
        Entrada eSur = new Entrada("Sur", 0);
        Entrada eEste = new Entrada("Este", 0);
        Entrada eOeste = new Entrada("Oeste", 0);
        arregloEntrada[0] = eNorte;
        arregloEntrada[1] = eSur;
        arregloEntrada[2] = eEste;
        arregloEntrada[3] = eOeste;
        Salida sNorte = new Salida("Norte", 0);
        Salida sSur = new Salida("Sur", 0);
        Salida sEste = new Salida("Este", 0);
        Salida sOeste = new Salida("Oeste", 0);
        arregloSalida[0] = sNorte;
        arregloSalida[1] = sSur;
        arregloSalida[2] = sEste;
        arregloSalida[3] = sOeste;
    }

    public void registrarSalida(NodoD<Auto> cabeza){
        int salidaRandom = (int)(Math.random() * 4);
        if(cabeza.getElement().getEntra().equals(arregloSalida[salidaRandom].getNombre())){
            registrarSalida(cabeza);
        }else{
            cabeza.getElement().setSale(arregloSalida[salidaRandom].getNombre());
            int h = reloj.getHora();
            int m = reloj.getMinutos();
            Hora hSalida = new Hora(h, m);
            cabeza.getElement().setHoraSalida(hSalida);
            cabeza.getElement().setFechaSalida(fecha);
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
    public class BEntrdaNorteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Entrada Norte");
            NodoD<Auto> cabeza = colaCarros.getFirst();
            contadorAutosEntradaySalida.setText(String.valueOf(eNorte.getSize()));
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
        }
    }
    public class BEntradaSurListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Entrada Sur");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BEntrdaEsteLsitener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Entrada Este");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BEntradaOesteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Entrada Oeste");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BSalidaNorteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Salida Norte");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BSalidaSurListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Salida Sur");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BSalidaEsteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Salida Este");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BSalidaOesteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            lIdAutos.setText("Salida Oeste");
            NodoD<Auto> cabeza = colaCarros.getFirst();
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
        }
    }
    public class BBuscarIdListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }


}
