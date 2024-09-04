import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class Tela extends JFrame {
    JFrame tela = new JFrame();
    JButton button = new JButton("Iniciar Simulação");

    JButton pauseButton = new JButton("Pausar");

    JButton continuarButton = new JButton("Contiuar");

    JLabel tgTextJLabel = new JLabel();
    JLabel tgJLabel = new JLabel();

    JLabel escalonadorTextLabel  = new JLabel();
    JLabel escalonadorLabel = new JLabel();

    JLabel filaTextLabel = new JLabel();
    JLabel filaLabel = new JLabel();

    JLabel estadosTextLabel = new JLabel();

    JLabel estado0TextLabel = new JLabel();
    JLabel estado0Label = new JLabel();

    JLabel estado1TextLabel = new JLabel();
    JLabel estado1Label = new JLabel();
    
    JLabel estado2TextLabel = new JLabel();
    JLabel estado2Label = new JLabel();
    
    JLabel estado3TextLabel = new JLabel();
    JLabel estado3Label = new JLabel();

    JLabel perdasLabel = new JLabel();
    JLabel perdasTextLabel = new JLabel();

    public Tela(double tg){
        tela.setTitle("Simulador de Fila Simples");
        tela.setSize(580, 800);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLocationRelativeTo(null);
        tela.setLayout(null);

        //Botão
        button.setSize(150, 60);
        button.setLocation(230, 500);
        tela.add(button);

        //Botão Pausar
        pauseButton.setSize(80, 25);
        pauseButton.setLocation(470, 10);
        tela.add(pauseButton);

        //Botão Continuar
        continuarButton.setSize(90, 25);
        continuarButton.setLocation(470, 10);
        tela.add(continuarButton);

        //--------------------------------------------------------------------

        //Texto "Tempo Global"
        tgTextJLabel.setSize(100, 25);
        tgTextJLabel.setLocation(250, 20);
        tgTextJLabel.setText("Tempo Global");
        tela.add(tgTextJLabel);

        //Texto "0.0"
        tgJLabel.setSize(88, 25);
        tgJLabel.setLocation(275, 40);
        tgJLabel.setText(String.format("%s", tg));
        tela.add(tgJLabel);

        //--------------------------------------------------------------------
        
        //Texto "ESCALONADOR"
        escalonadorTextLabel.setSize(100, 20);
        escalonadorTextLabel.setLocation(100, 150);
        escalonadorTextLabel.setText("ESCALONADOR");
        tela.add(escalonadorTextLabel);
       
        //Texto "(0.0 / chegada)"
        escalonadorLabel.setSize(120, 500);
        escalonadorLabel.setLocation(100, 170);
        escalonadorLabel.setText("null");
        escalonadorLabel.setVerticalAlignment(SwingConstants.TOP);
        tela.add(escalonadorLabel);

        //--------------------------------------------------------------------

        //Texto "FILA"
        filaTextLabel.setSize(88, 20);
        filaTextLabel.setLocation(400, 150);
        filaTextLabel.setText("FILA");
        tela.add(filaTextLabel);
        
        //Texto "(cliente 0.0))"
        filaLabel.setSize(88, 20);
        filaLabel.setLocation(409, 170);
        filaLabel.setText("0");
        tela.add(filaLabel);

        //--------------------------------------------------------------------

        //Texto "ESTADOS"
        estadosTextLabel.setSize(88, 20);
        estadosTextLabel.setLocation(270, 250);
        estadosTextLabel.setText("ESTADOS");
        tela.add(estadosTextLabel);

        //ESTADO 0
        estado0TextLabel.setSize(88, 20);
        estado0TextLabel.setLocation(170, 270);
        estado0TextLabel.setText("== 0 ==");
        tela.add(estado0TextLabel);

        //Texto "(0.0)"
        estado0Label.setSize(120, 500);
        estado0Label.setLocation(181, 290);
        estado0Label.setText("0.0");
        estado0Label.setVerticalAlignment(SwingConstants.TOP);
        tela.add(estado0Label);

        //ESTADO 1
        estado1TextLabel.setSize(88, 20);
        estado1TextLabel.setLocation(240, 270);
        estado1TextLabel.setText("== 1 ==");
        tela.add(estado1TextLabel);

        //Texto "(1.0)"
        estado1Label.setSize(120, 500);
        estado1Label.setLocation(251, 290);
        estado1Label.setText("0.0");
        estado1Label.setVerticalAlignment(SwingConstants.TOP);
        tela.add(estado1Label);

        //ESTADO 2
        estado2TextLabel.setSize(88, 20);
        estado2TextLabel.setLocation(310, 270);
        estado2TextLabel.setText("== 2 ==");
        tela.add(estado2TextLabel);

        //Texto "(2.0)"
        estado2Label.setSize(120, 500);
        estado2Label.setLocation(321, 290);
        estado2Label.setText("0.0");
        estado2Label.setVerticalAlignment(SwingConstants.TOP);
        tela.add(estado2Label);

        //ESTADO 3
        estado3TextLabel.setSize(88, 20);
        estado3TextLabel.setLocation(380, 270);
        estado3TextLabel.setText("== 3 ==");
        tela.add(estado3TextLabel);

        //Texto "(3.0)"
        estado3Label.setSize(120, 500);
        estado3Label.setLocation(391, 290);
        estado3Label.setText("0.0");
        estado3Label.setVerticalAlignment(SwingConstants.TOP);
        tela.add(estado3Label);

        //--------------------------------------------------------------------

        //PERDAS
        perdasTextLabel.setSize(88, 20);
        perdasTextLabel.setLocation(270, 150);
        perdasTextLabel.setText(" ");
        tela.add(perdasTextLabel);

        //Texto "0"
        perdasLabel.setSize(88, 20);
        perdasLabel.setLocation(287, 170);
        perdasLabel.setText(" ");
        tela.add(perdasLabel);

        tela.setVisible(true);
        pauseButton.setVisible(false);
        continuarButton.setVisible(false);
    }

    public void setTg(double tg){
        tgJLabel.setText(String.format("%.1f", tg));
    }

    public JLabel criarPeople(int numero){
        ImageIcon peoplePng = new ImageIcon("lib\\Person" + numero + ".png");
        Image scaledPeoplePng = peoplePng.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel people = new JLabel(new ImageIcon(scaledPeoplePng));
        people.setSize(40, 40);
        people.setLocation(470, 230);
        tela.add(people);
        tela.repaint();
        return people;
    }

    public void animar(JLabel label, int toX, int toY){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                label.setVisible(true);
                Thread.sleep(100);

                int frameRate = 30;

                int deltaX = toX - label.getLocation().x;
                int sobraX = deltaX%frameRate;
                int frameX = (deltaX - sobraX)/frameRate;

                int deltaY = toY - label.getLocation().y;
                int sobraY = deltaY%frameRate;
                int frameY = (deltaY - sobraY)/frameRate;

                for(int i = 0; i < frameRate; i++){
                    label.setLocation(label.getLocation().x + frameX , label.getLocation().y + frameY);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                label.setLocation(label.getLocation().x + sobraX , label.getLocation().y + sobraY);
                return null;
            }
        };
        worker.execute();
    }

    public void rotacionar(JLabel label, double graus){
        double rotationAngle = Math.toRadians(graus);
        AffineTransform transform = new AffineTransform();
        transform.rotate(rotationAngle, label.getWidth() / 2.0, label.getHeight() / 2.0);
        label.setUI(new VerticalLabelUI(transform));
    }
}