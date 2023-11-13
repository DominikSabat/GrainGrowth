import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame{
    private JFrame frame;

    private DataManager dm;
    private Utility util;

    private JPanel mainPanel;
    private JCanvasPanel canvasPanel;

    private JPanel buttonPanel;
    private JPanel leftButtonPanel;

    private JTextField widthField;
    private JTextField heightField;
    private JButton makeImageBut;

    private JTextField rowField;
    private JTextField columnField;
    private JButton drawRegularBut;

    private JTextField numberRandomField;
    private JButton drawRandomBut;

    private JButton animateBut;

    private JButton startStopBut;
    private JButton exportBut;

    private JComboBox<String> NeighborBox;
    private JComboBox<String> BoundaryBox;

    private JButton separator1;
    private JButton separator2;
    private JButton separator3;
    private JButton separator4;
    private JButton separator5;
    private JButton separator6;


    //==================Import===========

    private JComboBox<String> Import_ChoiceBox;

    private JButton Import_ExecuteBut;

    //==================Import===========

    //==================Inclusions===========
    private JTextField Inclusions_NumberField;
    private JTextField Inclusions_SizeField;

    private JComboBox<String> Inclusions_ChoiceBox;

    private JButton Inclusions_ExecuteBut;

    //==================Inclusions===========

    //==================DualPhase===========
    private JTextField DualPhase_NumberField;

    private JButton DualPhase_ExecuteBut;
    //==================DualPhase===========




    public MainWindow (String title) {
        super(title);
        dm = new DataManager();
        util = new Utility(dm);

        //===============Ustawienie "Płótna"==============

            BufferedImage bg = new BufferedImage(300,300,BufferedImage.TYPE_INT_RGB);
            dm.img = bg;
            dm.width = dm.img.getWidth();
            dm.height = dm.img.getHeight();
            dm.nucleonLast =new Nucleon[dm.width][dm.height];
            dm.nucleonCurrent =new Nucleon[dm.width][dm.height];
            util.Initial();

        //=============================

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        makeImageBut = new JButton("Apply dimensions"); makeImageBut.setBackground(Color.WHITE.brighter()); makeImageBut.setForeground(Color.BLACK.darker());

        drawRegularBut = new JButton("Draw regularly");
        drawRandomBut = new JButton("Draw randomly");

        exportBut=new JButton("Export");
        startStopBut=new JButton("Stop");

        animateBut=new JButton("Animate");

        widthField = new JTextField("500");
        widthField.setBorder(new TitledBorder("Width"));

        heightField = new JTextField("500");
        heightField.setBorder(new TitledBorder("Height"));

        rowField = new JTextField("10");
        rowField.setBorder(new TitledBorder("In row"));

        columnField = new JTextField("10");
        columnField.setBorder(new TitledBorder("In column"));

        numberRandomField = new JTextField("100");
        numberRandomField.setBorder(new TitledBorder("Amount of random grains"));

        String[] choice1={"Von Neumann","Moore","Hexagonal","Pentagonal"};
        NeighborBox = new JComboBox(choice1);

        String[] choice2={"Periodic","Absorbing"};
        BoundaryBox = new JComboBox(choice2);

        separator1 = new JButton();separator1.setEnabled(false);
        separator2 = new JButton();separator2.setEnabled(false);
        separator3 = new JButton();separator3.setEnabled(false);
        separator4 = new JButton();separator4.setEnabled(false);
        separator5 = new JButton();separator5.setEnabled(false);
        separator6 = new JButton();separator6.setEnabled(false);



        //==================Import===============

        String[] choice3={"TXT","BMP"};
        Import_ChoiceBox = new JComboBox(choice3);

        Import_ExecuteBut = new JButton("Import");

        //==================Import===============

        //==================Inclusions===========

        Inclusions_NumberField= new JTextField("6");
        Inclusions_NumberField.setBorder(new TitledBorder("Number of inclusions"));

        Inclusions_SizeField = new JTextField("25");
        Inclusions_SizeField.setBorder(new TitledBorder("Size of inclusions"));

        String[] choice4={"Circular","Squared"};
        Inclusions_ChoiceBox = new JComboBox(choice4);

        Inclusions_ExecuteBut = new JButton("Draw Inclusions");

        //==================Inclusions===========

        //==================DualPhase============
        DualPhase_NumberField= new JTextField("6");
        DualPhase_NumberField.setBorder(new TitledBorder("Number of grains"));

        DualPhase_ExecuteBut= new JButton("Dual Phase Execute");
        //==================DualPhase============



        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,3));

        buttonPanel.add(widthField);
        buttonPanel.add(heightField);

        buttonPanel.add(makeImageBut);

        buttonPanel.add(rowField);
        buttonPanel.add(columnField);
        buttonPanel.add(drawRegularBut);

        buttonPanel.add(separator1);////////////

        buttonPanel.add(numberRandomField);
        buttonPanel.add(drawRandomBut);

        buttonPanel.add(separator2);////////////

        buttonPanel.add(NeighborBox);
        buttonPanel.add(BoundaryBox);

        buttonPanel.add(animateBut);

        buttonPanel.add(startStopBut);
        buttonPanel.add(exportBut);

        //==================Left button layout===========

        leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new GridLayout(4,3));

        leftButtonPanel.add(Import_ChoiceBox);
        leftButtonPanel.add(Import_ExecuteBut);

        leftButtonPanel.add(separator3);////////////

        leftButtonPanel.add(Inclusions_ChoiceBox);
        leftButtonPanel.add(Inclusions_NumberField);
        leftButtonPanel.add(Inclusions_SizeField);

        leftButtonPanel.add(separator4);////////////
        leftButtonPanel.add(Inclusions_ExecuteBut);
        leftButtonPanel.add(separator5);////////////

        leftButtonPanel.add(DualPhase_NumberField);
        leftButtonPanel.add(DualPhase_ExecuteBut);
        leftButtonPanel.add(separator6);////////////


        //==================Left button layout===========

        //buttonPanel.setPreferredSize(new Dimension(1000,0));

        canvasPanel = new JCanvasPanel(dm);

        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        mainPanel.add(BorderLayout.EAST, buttonPanel);
        mainPanel.add(BorderLayout.WEST, leftButtonPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(1680, 1050));
        this.setLocationRelativeTo(null);

        canvasPanel.addMouseListener(new MouseListener() {
                                         @Override
                                         public void mouseClicked(MouseEvent e) {

                                         }

                                         @Override
                                         public void mousePressed(MouseEvent e) {
                                             util.drawPixel(e.getX()-((1680-600-dm.width)/2),e.getY()-((1050-dm.height)/2));
                                             canvasPanel.repaint();
                                         }

                                         @Override
                                         public void mouseReleased(MouseEvent e) {

                                         }

                                         @Override
                                         public void mouseEntered(MouseEvent e) {

                                         }

                                         @Override
                                         public void mouseExited(MouseEvent e) {

                                         }
                                     }
        );

        makeImageBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Utility util = new Utility(dm);

                dm.startStop=1;

                try {

                dm.img=new BufferedImage(1,1,BufferedImage.TYPE_BYTE_GRAY);
                dm.width=1;
                dm.height=1;
                dm.nucleonLast=new Nucleon [1][1];
                dm.nucleonCurrent=new Nucleon [1][1];
                util.Initial();


                dm.img = new BufferedImage(Math.abs(Integer.parseInt(widthField.getText())), Math.abs(Integer.parseInt(heightField.getText())), BufferedImage.TYPE_INT_RGB);
                dm.width = dm.img.getWidth();
                dm.height = dm.img.getHeight();
                dm.nucleonLast=new Nucleon [dm.width][dm.height];
                dm.nucleonCurrent=new Nucleon [dm.width][dm.height];
                util.Initial();
                }

                catch(Exception a)
                {
                    JOptionPane.showMessageDialog(frame, "Enter a number using numbers");
                }


                canvasPanel.repaint();
            }
        });

        BoundaryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.choiceBoundary= BoundaryBox.getSelectedIndex();
            }
        });

        NeighborBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.choiceNeighbor= NeighborBox.getSelectedIndex();
            }
        });

        drawRegularBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Integer.parseInt(rowField.getText())>(dm.width/2) ||  Integer.parseInt(columnField.getText())>(dm.height/2) ){
                        throw new Exception();
                    }
                    util.placeNucleonRegular(Math.abs(Integer.parseInt(rowField.getText())), Math.abs(Integer.parseInt(columnField.getText())));
                }
                catch(Exception a){
                    JOptionPane.showMessageDialog(frame, "For columns/rows, enter numbers less than half the height/width, using digits");
                }
                canvasPanel.repaint();
            }
        });

        drawRandomBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    util.placeNucleonRandom(Math.abs(Integer.parseInt(numberRandomField.getText())));
                }
                catch(Exception a){
                    JOptionPane.showMessageDialog(frame, "Enter a number using numbers");
                }
                canvasPanel.repaint();
            }
        });

        exportBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.exportImage();
                util.exportToTXT();
            }
        });

        startStopBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    dm.startStop = 1;
            }
        });

        animateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Utility util = new Utility(dm);

                dm.startStop=0;

                final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        util.animateNucleonGrow();

                    }
                }, 0, 1, TimeUnit.MILLISECONDS); //szybkosc

                final ScheduledExecutorService executorService2 = Executors.newSingleThreadScheduledExecutor();
                executorService2.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        canvasPanel.repaint();
                        if(dm.startStop==1){
                            executorService.shutdown();
                            executorService2.shutdown();
                        }
                    }
                }, 0, 60, TimeUnit.MILLISECONDS);
            }
        });

        //==================Import===========
        Import_ChoiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.choiceImport= Import_ChoiceBox.getSelectedIndex();
            }
        });

        Import_ExecuteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dm.choiceImport==0)
                {
                    util.importFromTXT();
                    canvasPanel.repaint();
                }
                else
                {
                    util.importFromBMP();
                    canvasPanel.repaint();
                }

            }
        });

        //==================Import===========

        //=================Inclusions========
        Inclusions_ChoiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.choiceInclusion= Inclusions_ChoiceBox.getSelectedIndex();
            }
        });

        Inclusions_ExecuteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (dm.choiceInclusion==0)
                    {
                        util.drawInclusionsCircular(Integer.parseInt(Inclusions_NumberField.getText()), Integer.parseInt(Inclusions_SizeField.getText()));
                    }

                    else
                    {
                        util.drawInclusionsSquared(Integer.parseInt(Inclusions_NumberField.getText()), Integer.parseInt(Inclusions_SizeField.getText()));
                    }
                }
                catch(Exception a){
                    JOptionPane.showMessageDialog(frame, "Enter a number using numbers");
                }
                canvasPanel.repaint();
            }
        });
        //=================Inclusions========

        //=================DualPhase=========

        DualPhase_ExecuteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(Integer.parseInt(DualPhase_NumberField.getText())>dm.nucleons.size()-1)
                {
                    JOptionPane.showMessageDialog(frame, "Chosen number cannot be greater than number of existing grains: " + (dm.nucleons.size()-1) );
                }

                else
                {
                    util.dualPhase(Integer.parseInt(DualPhase_NumberField.getText()));

                    canvasPanel.repaint();
                }
            }
        });

        //=================DualPhase=========

    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow("Grain Growth Algorithm DS306692");
        mw.setVisible(true);
        mw.canvasPanel.repaint();

    }
}
