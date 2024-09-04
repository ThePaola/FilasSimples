//IMPORTS ===============================================================================================================================================
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.SwingWorker;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

//CLASSE E ATRIBUTOS===============================================================================================================================================
public class App {
    boolean executando = false;
    boolean ligado = false;
    Random random = new Random();

    int servidores = 1;
    int capacidade = 3;

    double minChegada = 1.0;
    double maxChegada = 2.0;
    double minAtendimento = 2.0;
    double maxAtendimento = 4.0;

    double chegadaInicial = 1.0;

    int perdas = 0;

    ArrayList<String> estado0 = new ArrayList<>();
    ArrayList<String> estado1 = new ArrayList<>();
    ArrayList<String> estado2 = new ArrayList<>();
    ArrayList<String> estado3 = new ArrayList<>();

    Queue<JLabel> filaPeople = new LinkedList<>();
    JLabel newPeople = null;

    int fila = 0;

    double tg = 0.0;
    PriorityQueue<Evento> escalonador = new PriorityQueue<>(); 
    Tela tela;

    //METODO EXECUTA - Inicia o programa, instânciando uma tela, criando a função do botão e iniciando o loop
    public void executa(){
        tela = new Tela(tg);
        
        tela.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executando = true;
                tela.button.setVisible(false);
                tela.pauseButton.setVisible(true);
                try {
                    simular();

                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        tela.pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executando = false;
                tela.pauseButton.setVisible(false);
                tela.continuarButton.setVisible(true);
                while(executando = false){
                    //Loop em pausa
                }
            }
        });

        tela.continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tela.pauseButton.setVisible(true);
                tela.continuarButton.setVisible(false);
                executando = true;
                try {
                    simular();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        ligado = true;

        while(ligado){

        }
    }

    //METODO SIMULAR - Inicia a simulação, Cria os eventos, adiciona no escalonador e lida com as chegadas/saidas

    public void simular() throws InterruptedException{
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (executando) {
                    if(tg == 0.0){
                        Evento evento = new Evento(chegadaInicial, "chegada");
                        escalonador.add(evento);
                        tela.escalonadorLabel.setText(toStringEscalonador());
                    }
                    double tempoEscalonador = escalonador.peek().tempo;
                    int segundos = (int) Math.round((tempoEscalonador - tg) * 10);
                    contar(segundos);
                    contabilizaTempo((double) segundos/10);
                    tela.escalonadorLabel.setText(toStringEscalonador());
                    
                    Thread.sleep(2000);
                    if(escalonador.poll().tipo.equals("chegada")){
                        tela.escalonadorLabel.setText(toStringEscalonador());
                        chegada();
                        tela.filaLabel.setText(String.valueOf(fila));
                    }
                    else{
                        tela.escalonadorLabel.setText(toStringEscalonador());
                        saida();
                        tela.filaLabel.setText(String.valueOf(fila));
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    //CHEGADA=======================================================================================================
    public void chegada(){
        newPeople = tela.criarPeople(gerarAleatorioInt(1, 5));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        if(fila < capacidade){
            
            fila++;
            if(filaPeople.size() == 0){
                tela.animar(newPeople, 470, 140);
            }
            if(filaPeople.size() == 1){
                tela.animar(newPeople, 470, 160);
            }
            if(filaPeople.size() == 2){
                tela.animar(newPeople, 470, 180);
            }
            if(filaPeople.size() == 3){
                tela.animar(newPeople, 470, 200);
            }
            filaPeople.add(newPeople);

            if(fila <= servidores){
                Evento saida = new Evento(gerarAleatorio(minAtendimento, maxAtendimento) + tg, "saida");
                escalonador.add(saida);
                tela.escalonadorLabel.setText(toStringEscalonador());
            }
            
        }
        else {
            tela.animar(newPeople, 600, 230);
            perdas++;
            tela.perdasTextLabel.setText("PERDAS");
            tela.perdasLabel.setText(String.valueOf(perdas));
        }
        Evento chegada = new Evento(gerarAleatorio(minChegada, maxChegada) + tg, "chegada");
        escalonador.add(chegada);
        tela.escalonadorLabel.setText(toStringEscalonador());
    }

    //SAIDA====================================================
    public void saida(){
        try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();    
            }
        fila--;
        JLabel peopleToLeave = filaPeople.poll();
        tela.animar(peopleToLeave, 600, peopleToLeave.getLocation().y);
        for(int i=0; i < fila; i++){
            JLabel peopleMovimentar = filaPeople.poll();
            tela.animar(peopleMovimentar, 470, peopleMovimentar.getLocation().y - 20);
            filaPeople.add(peopleMovimentar);
            
        }
        if(fila >= servidores){
            Evento saida = new Evento(gerarAleatorio(minAtendimento, maxAtendimento) + tg, "saida");
            escalonador.add(saida);
            tela.escalonadorLabel.setText(toStringEscalonador());
        }
    }

    //CONTAR - Conta uma certa quantidade de segundos =============================================================================
    public void contar(double segundos){
        try {
            for(int i=0; i < segundos; i++){
                Thread.sleep(100);
                tg = tg + 0.1;
                tela.setTg(tg);
                
            
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //GERAR ALEATORIO - Gera um numero aleatorio dentro do intervalo fornecido no parâmetro =========================================
    public double gerarAleatorio(double from, double to){
        double aleatorio = random.nextDouble();
        aleatorio = (aleatorio * (to - from)) + from;
        aleatorio = formatarDecimal(aleatorio);
        return aleatorio;
    }

    //GERAR ALEATORIO - Gera um numero aleatorio dentro do intervalo fornecido no parâmetro =========================================
    public int gerarAleatorioInt(int from, int to){
        float gerador = random.nextFloat();
        gerador = (gerador * (to - from)) + from;
        int aleatorio = Math.round(gerador);
        return aleatorio;
    }

    //CLASSE EVENTO ================================================================================================================
    public class Evento implements Comparable<Evento> {
        double tempo;
        String tipo;

        @Override
        public int compareTo(Evento other) {
            return Double.compare(this.tempo, other.tempo);
        }

        public Evento(double tempo, String tipo) {
            this.tempo = formatarDecimal(tempo);
            this.tipo = tipo;
        }
    }

    //FORMATAR DECIMAL - Formata um decimal para 1 casa depois da virgula ==========================================================
    public double formatarDecimal(double valor){
        valor = valor * 10;
        valor = (Math.floor(valor))/10;
        return valor;
    }

    public void contabilizaTempo(double estado){
        if(fila == 0){
            estado0.add(String.valueOf(estado));
            estado1.add(String.valueOf(0.0));
            estado2.add(String.valueOf(0.0));
            estado3.add(String.valueOf(0.0));
            setEstados();
            
        }
        if(fila == 1){
            estado1.add(String.valueOf(estado));
            estado0.add(String.valueOf(0.0));
            estado2.add(String.valueOf(0.0));
            estado3.add(String.valueOf(0.0));
            setEstados();
        }
        if(fila == 2){
            estado2.add(String.valueOf(estado));
            estado1.add(String.valueOf(0.0));
            estado0.add(String.valueOf(0.0));
            estado3.add(String.valueOf(0.0));
            setEstados();
        }
        if(fila == 3){
            estado3.add(String.valueOf(estado));
            estado1.add(String.valueOf(0.0));
            estado2.add(String.valueOf(0.0));
            estado0.add(String.valueOf(0.0));
            setEstados();
        }
    }

    //STRING ESCALONADOR - Cria uma stirng com os eventos do escalonador ============================================================
    public String toStringEscalonador() {
        StringBuilder string = new StringBuilder();
        for (Evento evento : escalonador) {
            String cor = evento.tipo.equals("saida") ? "red" : "green";
            
            string.append("( ").
            append(evento.tempo).append(" / ").append("<font color=").
            append(cor).append(">").append(evento.tipo).
            append(" )").append("</font>").append("<br>");
        }
        return "<html>" + string.toString() + "</html>";
    }

    public String toStringEstado(ArrayList<String> estado){
        StringBuilder string = new StringBuilder();
        for(String tempo : estado){
            string.append(tempo).append("<br>");
        }
        return "<html>" + string.toString() + "</html>";
    }

    public void setEstados(){
        tela.estado0Label.setText(toStringEstado(estado0));
        tela.estado1Label.setText(toStringEstado(estado1));
        tela.estado2Label.setText(toStringEstado(estado2));
        tela.estado3Label.setText(toStringEstado(estado3));
    }
}