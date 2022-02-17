package myProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class is used for to show game on screen and allow to play.
 * @autor Juan Esteban Mazuera Yunda, juan.yunda@correounivalle.edu.co
 * @autor Sheilly Daylin Ortega Granobles, sheilly.ortega@correounivalle.edu.co
 * @version v.1.0.0 date: 05/02/2022
 */

public class GUIIKnowThatWord extends JFrame {

    private Header headerProject;
    private JPanel panelPalabras, panelEspacioEnBlanco1, panelEspacioEnBlanco2, panelEspacioEnBlanco3,
                   panelEspacioEnBlanco4, panelEspacioEnBlanco5, panelInstrucciones;
    private JButton ayuda, salir, botonSI, botonNO, empezarNivel;
    private JTextArea nivel, aciertos, errores, instrucciones, panelInfo;
    private Timer timer;
    int numeroNivel, numeroAciertos, numeroErrores, cualGUI, conter = 0;
    private  static final String INSTRUCCIONES = "En la pantalla aparecerán palabras para memorizar, cuentas con 5 "
            + "segundos para memorizar cada una de las palabras\n"
            + "\nTras la serie de palabras a memorizar, el juego te presentará un listado con el doble de palabras que se "
            + "mostraron. Por cada una las palabras debes indicar si la palabra estaba o no contenida en el "
            + "listado a memorizar y tendrás un tiempo de 7 segundos para responder, en caso de no hacerlo se tomará "
            + "como un error.\n"
            + "\nInicias en el nivel 1 y solo puedes pasar al siguiente nivel si lo logras superar. Aquí están las"
            + " condiciones para poder superar cada nivel: ";
    private ImageIcon reglasDelNivel, imagenNuevoTamanho;
    private Image imagenOtroTamanho;
    private JLabel imagenReglas, palabra;
    private ModelIKnowThatWord game;
    private Escucha escucha;

    /**
     * Constructor of GUI class
     */

    public GUIIKnowThatWord(){
        initGUI();

        //Default JFrame configuration
        this.setTitle("I Know That Word");
        //this.setSize(200,100);
        this.pack();
        //this.setUndecorated(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 230, 153));
    }

    //---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     *
     */

    private void initGUI() {
        //Set up JFrame Container's Layout
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        //Create Listener Object and Control Object
        escucha = new Escucha();
        game = new ModelIKnowThatWord();
        //Set up JComponents

        reglasDelNivel = new ImageIcon(getClass().getResource("/resources/reglas.jpeg"));
        imagenOtroTamanho = reglasDelNivel.getImage().getScaledInstance(410,805,Image.SCALE_SMOOTH);
        imagenNuevoTamanho = new ImageIcon(imagenOtroTamanho);

        imagenReglas = new JLabel(imagenNuevoTamanho);

        panelInstrucciones = new JPanel();
        panelInstrucciones.setPreferredSize(new Dimension(410,1005));
        panelInstrucciones.setBackground(Color.WHITE);
        panelInstrucciones.setBorder(BorderFactory.createTitledBorder("Instrucciones del juego."));
        panelInstrucciones.setLayout(new BorderLayout());

        instrucciones = new JTextArea();
        instrucciones.setBackground(null);
        instrucciones.setText(INSTRUCCIONES);
        instrucciones.setLineWrap(true);
        instrucciones.setPreferredSize(new Dimension(408, 200));
        instrucciones.setWrapStyleWord(true);
        instrucciones.setLineWrap(true);
        instrucciones.setEditable(false);

        palabra = new JLabel("");
        palabra.setBackground(null);
        palabra.setAlignmentY(SwingConstants.CENTER);
        palabra.setHorizontalAlignment(JLabel.CENTER);
        palabra.setVerticalAlignment(JLabel.CENTER);
        palabra.setFont(new Font(Font.DIALOG,Font.BOLD,60));

        game.pedirDatos();


        numeroNivel = game.getSuNivel();
        createPalabrasAMemorizarGUI(constraints);
        createPalabrasAVerificarGUI(constraints);
        createConclusionGUI(constraints);

        comenzarNivel();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function starts a new level
     */

    public void comenzarNivel() {
        cualGUI = 1;

        numeroNivel = game.getSuNivel();

        game.palabrasPorNivel(numeroNivel);

        empezarNivel.setVisible(false);
        escucha.buildGUI1();

        conter = 0;
        revalidate();
        repaint();
        pack();

        timer = new Timer(500, escucha);
        escucha.printMemoryWords();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function is responsible for displaying the interface in the window where the player indicates wether the
     * word appeared or not
     */

    public void verificarPalabras() {
        cualGUI = 2;
        timer.stop();

        revalidate();
        repaint();

        conter = 0;
        timer = new Timer(7000, escucha);
        escucha.printAllWords();
        pack();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function shows how many successes and errors there were, if the level was passed and the button to pass the level
     */

    public void terminarNivel() {
        cualGUI = 3;
        timer.stop();

        numeroAciertos = game.getAciertos();
        numeroErrores = game.getErrores();

        empezarNivel.setVisible(true);
        pack();
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the GUI that show the words to memorize.
     * @param constraints
     */

    public void createPalabrasAMemorizarGUI(GridBagConstraints constraints) {

        createHeader(constraints);
        createHelpButton(constraints);
        createLevelCounter(constraints);
        createExitButton(constraints);
        createSpace1(constraints);
        createSpace2(constraints);
        createPanelPalabrasAMemorizar(constraints);
        createStartLevelButton(constraints);

        cualGUI = 1;

        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the header.
     * @param constraints
     */

    public void createHeader(GridBagConstraints constraints) {
        headerProject = new Header("I Know that Word", new Color(128, 96, 0));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        this.add(headerProject, constraints); //Change this line if you change JFrame Container's Layout

        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the ayuda button.
     * @param constraints
     */

    public void createHelpButton(GridBagConstraints constraints) {
        ayuda = new JButton(" ? ");
        ayuda.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 14));
        ayuda.setForeground(Color.white);
        ayuda.removeMouseListener(escucha);
        ayuda.addMouseListener(escucha);
        ayuda.setBackground(new Color(0, 112, 192));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.FIRST_LINE_START;
        constraints.anchor = GridBagConstraints.LINE_START;

        add(ayuda, constraints);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates white space to separate the ayuda button and nivel text area.
     * @param constraints
     */

    public void createSpace1(GridBagConstraints constraints) {
        panelEspacioEnBlanco1 = new JPanel();
        panelEspacioEnBlanco1.setOpaque(false);
        panelEspacioEnBlanco1.setPreferredSize(new Dimension(130, 5));


        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        add(panelEspacioEnBlanco1, constraints);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates white space to separate the nivel text area and salir button.
     * @param constraints
     */

    public void createSpace2(GridBagConstraints constraints) {
        panelEspacioEnBlanco2 = new JPanel();
        panelEspacioEnBlanco2.setOpaque(false);
        panelEspacioEnBlanco2.setPreferredSize(new Dimension(115, 5));

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        add(panelEspacioEnBlanco2, constraints);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * This function creates the text area to display the actually level.
     * @param constraints
     */

    public void createLevelCounter(GridBagConstraints constraints) {
        nivel = new JTextArea(1, 2);
        nivel.setMinimumSize(new Dimension(5, 5));
        nivel.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 14));
        nivel.setText("Nivel: " + numeroNivel);
        nivel.setBackground(new Color(255, 242, 204));
        nivel.setEditable(false);
        nivel.setBorder(BorderFactory.createRaisedBevelBorder());

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        add(nivel, constraints);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This function creates the salir button.
     * @param constraints
     */

    public void createExitButton(GridBagConstraints constraints) {
        salir = new JButton("Salir");
        salir.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 14));
        salir.setForeground(Color.WHITE);
        salir.removeMouseListener(escucha);
        salir.addMouseListener(escucha);
        salir.setBackground(new Color(192, 0, 0));
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.LINE_END;

        add(salir, constraints);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the PanelPalabras panel.
     * @param constraints
     */

    public void createPanelPalabrasAMemorizar(GridBagConstraints constraints)
    {
        panelPalabras = new JPanel();
        panelPalabras.setPreferredSize(new Dimension(390, 240));
        panelPalabras.setBorder(BorderFactory.createTitledBorder("Palabras"));
        panelPalabras.setBackground(new Color(0,0,0,0));
        panelPalabras.setLayout(new BorderLayout());

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 5;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;

        panelPalabras.add(palabra, BorderLayout.CENTER);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the GUI that show the all words of level and the player will to choose the correct answer.
     * @param constraints
     */

    public void createPalabrasAVerificarGUI(GridBagConstraints constraints) {

        createPanelPalabrasAVerificar(constraints);
        createSpace3(constraints);
        createBotonSI(constraints);
        createBotonNO(constraints);
        createSpace4(constraints);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function repaint the PanelPalabras panel.
     * @param constraints
     */

    public void createPanelPalabrasAVerificar(GridBagConstraints constraints)
    {
        panelPalabras = new JPanel();
        panelPalabras.setBackground(new Color(0,0,0,0));
        panelPalabras.setPreferredSize(new Dimension(390, 140));
        panelPalabras.setFont(new Font("SansSerif", Font.BOLD, 40));
        panelPalabras.setBorder(BorderFactory.createTitledBorder("Palabras"));
        panelPalabras.setLayout(new BorderLayout());

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 5;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function create the BotonSI button.
     * @param constraints
     */

    public void createBotonSI(GridBagConstraints constraints)
    {
        botonSI = new JButton("\uF0FC\n");
        botonSI.setPreferredSize(new Dimension(100, 40));
        botonSI.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 25));
        botonSI.setForeground(new Color(32, 50, 20));
        botonSI.removeMouseListener(escucha);
        botonSI.addMouseListener(escucha);
        botonSI.setBackground(new Color(146, 208, 80));

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function create the BotonNO button.
     * @param constraints
     */

    public void createBotonNO(GridBagConstraints constraints)
    {
        botonNO = new JButton("\uF0FB");
        botonNO.setPreferredSize(new Dimension(100, 40));
        botonNO.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 25));
        botonNO.setForeground(new Color(62, 0, 0));
        botonNO.removeMouseListener(escucha);
        botonNO.addMouseListener(escucha);
        botonNO.setBackground(new Color(255, 0, 0));

        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates white space to separate the panelPalabras pane to botonSI button and botonNO button.
     * @param constraints
     */

    public void createSpace3(GridBagConstraints constraints) {
        panelEspacioEnBlanco3 = new JPanel();
        panelEspacioEnBlanco3.setOpaque(false);
        panelEspacioEnBlanco3.setPreferredSize(new Dimension(115, 40));

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 5;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates white space to separate the botonSI button, botonNO button and the GUI border.
     * @param constraints
     */

    public void createSpace4(GridBagConstraints constraints) {
        panelEspacioEnBlanco4 = new JPanel();
        panelEspacioEnBlanco4.setOpaque(false);
        panelEspacioEnBlanco4.setPreferredSize(new Dimension(115, 40));

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 5;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the GUI that show the all words of level and the player will to choose the correct answer.
     * @param constraints
     */

    public void createConclusionGUI(GridBagConstraints constraints) {

        createSpace5(constraints);
        createSuccessCounter(constraints);
        createMistakesCounter(constraints);
        createPanelInfo(constraints);

        cualGUI=3;

        revalidate();
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates white space to separate the ayuda button, salir button and nivel text counter to aciertos button and errores boton.
     * @param constraints
     */

    public void createSpace5(GridBagConstraints constraints) {
        panelEspacioEnBlanco5 = new JPanel();
        panelEspacioEnBlanco5.setOpaque(false);
        panelEspacioEnBlanco5.setPreferredSize(new Dimension(115, 40));

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 5;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * This function creates the text area to display the right answers in the level.
     * @param constraints
     */

    public void createSuccessCounter(GridBagConstraints constraints) {
        aciertos = new JTextArea(1, 2);
        aciertos.setMinimumSize(new Dimension(50, 15));
        aciertos.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 14));
        aciertos.setText("Aciertos: " + numeroAciertos);
        aciertos.setBackground(new Color(146, 208, 80));
        aciertos.setEditable(false);
        aciertos.setBorder(BorderFactory.createRaisedBevelBorder());

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * This function creates the text area to display the wrong answers in the level.
     * @param constraints
     */

    public void createMistakesCounter(GridBagConstraints constraints) {
        errores = new JTextArea(1, 2);
        errores.setMinimumSize(new Dimension(50, 15));
        errores.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 14));
        errores.setText("Errores: " + numeroErrores);
        errores.setBackground(new Color(255,0,0));
        errores.setEditable(false);
        errores.setBorder(BorderFactory.createRaisedBevelBorder());

        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function create the PanelInfo pane.
     * @param constraints
     */
    public void createPanelInfo(GridBagConstraints constraints)
    {
        panelInfo = new JTextArea();
        panelInfo.setWrapStyleWord(true);
        panelInfo.setLineWrap(true);
        panelInfo.setPreferredSize(new Dimension(390, 240));
        panelInfo.setFont(new Font(Font.DIALOG,Font.BOLD,20));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));
        panelInfo.setBackground(new Color(0,0,0,0));
        panelInfo.setEditable(false);
        panelInfo.setVisible(true);

        /*info = new JLabel();
        info.setPreferredSize(new Dimension(390, 240));
        info.setFont(new Font(Font.DIALOG,Font.BOLD,20));
        info.setBorder(BorderFactory.createTitledBorder("Información"));
        info.setBackground(new Color(0,0,0,0));
        info.setVisible(true);*/

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 5;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This function creates the empezarNivel button.
     * @param constraints
     */

    public void createStartLevelButton(GridBagConstraints constraints) {
        empezarNivel = new JButton("~Empezar Nivel~");
        empezarNivel.setFont(new Font("SansSerif", Font.BOLD + Font.PLAIN, 14));
        empezarNivel.setForeground(Color.black);
        empezarNivel.removeMouseListener(escucha);
        empezarNivel.addMouseListener(escucha);
        empezarNivel.setBackground(new Color(255, 242, 204));
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
    }

    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUIIKnowThatWord miProjectGUIIKnowThatWord = new GUIIKnowThatWord();
        });
    }

    /**
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */

    private class Escucha extends MouseAdapter implements ActionListener {

        GridBagConstraints constraints = new GridBagConstraints();

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (cualGUI)
            {
                case 1:
                    if(e.getSource()==timer) {
                        if (conter < game.getCantidadPalabrasDelNivel() / 2) {
                            printMemoryWords();
                        } else {
                            conter = 0;

                            buildGUI2();

                            verificarPalabras();
                        }
                    }
                    repaint();
                    revalidate();

                    break;
                case 2:
                    if(e.getSource()==timer) {
                        if (conter < game.getCantidadPalabrasDelNivel()) {
                            game.noAnswer();
                            conter++;
                        } else {
                            conter = 0;

                            buildGUI3();

                            terminarNivel();

                            conclusion();
                        }
                    }
                    repaint();
                    revalidate();

                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            GridBagConstraints constraints = new GridBagConstraints();

            if (e.getSource() == salir) {
                game.registrarUsuario();
                System.exit(0);

            } else if (e.getSource() == ayuda) {
                panelInstrucciones.add(instrucciones, BorderLayout.NORTH);
                panelInstrucciones.add(imagenReglas, BorderLayout.CENTER);

                JScrollPane scroll = new JScrollPane(panelInstrucciones);
                scroll.setPreferredSize(new Dimension(440, 455));

                JOptionPane.showMessageDialog(null, scroll, "Instrucciones del juego", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == empezarNivel) {

                numeroNivel = game.subirNivelUsuario(game.getCantidadPalabrasDelNivel(), game.getAciertos());

                buildGUI1();

                comenzarNivel();
            }
            else if(e.getSource() == botonSI)
            {
                timer.stop();
                if(conter<game.getCantidadPalabrasDelNivel()) {
                    game.validarPalabra(game.getPalabrasDelNivel().get(conter), true);
                    conter++;
                    printAllWords();
                } else {
                    printAllWords();
                    timer.stop();
                }
            }
            else if(e.getSource() == botonNO)
            {
                timer.stop();
                if(conter<game.getCantidadPalabrasDelNivel()) {
                    game.validarPalabra(game.getPalabrasDelNivel().get(conter), false);
                    conter++;
                    printAllWords();
                } else {
                    printAllWords();
                    timer.stop();
                }
            }
        }

        public void printMemoryWords()
        {
            if(conter<game.getCantidadPalabrasDelNivel()/2) {
                palabra.setText(game.getPalabrasAMemorizar().get(conter));
                conter++;

                palabra.revalidate();
                palabra.repaint();
                panelPalabras.revalidate();
                panelPalabras.repaint();
            }
            timer.start();
            repaint();
            revalidate();
        }

        public void printAllWords()
        {
            if(conter<game.getCantidadPalabrasDelNivel()) {
                palabra.setText(game.getPalabrasDelNivel().get(conter));

                palabra.revalidate();
                palabra.repaint();
                panelPalabras.revalidate();
                panelPalabras.repaint();
            }else{
                conter = 0;

                buildGUI3();

                terminarNivel();

                conclusion();
            }
            timer.start();
            repaint();
            revalidate();
        }

        public void buildGUI1()
        {
            remove(panelEspacioEnBlanco5);
            remove(aciertos);
            remove(errores);
            remove(panelInfo);

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 5;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.anchor = GridBagConstraints.CENTER;

            add(panelPalabras, constraints);

            revalidate();
            repaint();
        }

        public void buildGUI2()
        {
            remove(panelPalabras);

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 5;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.anchor = GridBagConstraints.CENTER;

            add(panelPalabras, constraints);

            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridwidth = 5;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;

            add(panelEspacioEnBlanco3, constraints);

            constraints.gridx = 1;
            constraints.gridy = 4;
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.anchor = GridBagConstraints.CENTER;

            add(botonSI, constraints);

            constraints.gridx = 3;
            constraints.gridy = 4;
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.anchor = GridBagConstraints.CENTER;

            add(botonNO, constraints);

            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 5;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;

            add(panelEspacioEnBlanco4, constraints);

            revalidate();
            repaint();
        }

        public void buildGUI3()
        {
            remove(panelPalabras);
            remove(panelEspacioEnBlanco3);
            remove(botonSI);
            remove(botonNO);
            remove(panelEspacioEnBlanco4);

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 5;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;

            add(panelEspacioEnBlanco5, constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;

            add(aciertos, constraints);

            constraints.gridx = 3;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.CENTER;

            add(errores, constraints);

            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 5;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.anchor = GridBagConstraints.CENTER;

            add(panelInfo, constraints);

            revalidate();
            repaint();
        }

        public void conclusion()
        {
            if(game.isGanar())
            {
                panelInfo.setText("Has obtenido "+numeroAciertos+" respuestas correctas y "+numeroErrores+" repuestas incorrectas.\n" +
                        "\nEs decir, ¡puedes pasar al próximo nivel! Da click en ~Empezar nivel~");
            }
            else if(numeroNivel==10)
            {
                if(game.isGanar()) {
                    panelInfo.setText("Has obtenido " + numeroAciertos + " respuestas correctas y " + numeroErrores + " repuestas incorrectas.\n" +
                            "\n¡Lo hiciste muy bien!\nPuedes volver a intentar el máximo nivel dando click en el botón ~Empezar Nivel~");
                }
                else
                {
                    panelInfo.setText("Has obtenido " + numeroAciertos + " respuestas correctas y " + numeroErrores + " repuestas incorrectas.\n" +
                            "\nPuedes hacerlo mejor.\nSi quieres ganar el máximo nivel, inténtalo de nuevo dando click en el botón ~Empezar Nivel~");
                }
            }
            else
            {
                panelInfo.setText("Has obtenido "+numeroAciertos+" respuestas correctas y "+numeroErrores+" repuestas incorrectas.\n" +
                        "\nEsto no es suficiente para pasar el nivel. Inténtalo de nuevo dando click en el botón ~Empezar nivel~");
            }
        }
    }
}
