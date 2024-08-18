package trab1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Corrida { // Declara a classe p�blica Corrida.
    private static final double LINHA_DE_CHEGADA = 20000.0; // Define a constante para a linha de chegada da corrida.
    private static volatile boolean fimDaCorrida = false; // Vari�vel vol�til que indica se a corrida terminou.
    private static List<String> logList = new ArrayList<>(); // Lista que armazena os logs da corrida.
    private static final Object lock = new Object(); // Objeto usado para sincroniza��o.
    private static AtomicInteger posicaoChegada = new AtomicInteger(1); // Contador at�mico para rastrear a posi��o de chegada dos carros.
    private static List<Carro> carros = new ArrayList<>(); // Lista que armazena os carros participantes da corrida.

    // M�todo para definir o fim da corrida e registrar a chegada de um carro.
    public static void setFimDaCorrida(String nomeCarro, long tempoTotal) {
        synchronized (lock) { // Bloqueia a se��o cr�tica para garantir a consist�ncia dos dados.
            if (!fimDaCorrida) { // Verifica se a corrida ainda n�o terminou.
                fimDaCorrida = true; // Marca o fim da corrida.
            }
            int posicao = posicaoChegada.getAndIncrement(); // Obt�m e incrementa a posi��o de chegada.
            String logEntry = nomeCarro + " alcan�ou a linha de chegada e ficou em " + posicao + "� lugar";
            // String logEntry = nomeCarro + " alcan�ou a linha de chegada em " + tempoTotal + "ms e ficou em " + posicao + "� lugar";
            // Linha comentada que poderia ser usada para incluir o tempo total.
            logList.add(logEntry);
            System.out.println(logEntry);
        }
    }

    public static boolean isFimDaCorrida() {
        return fimDaCorrida; // Retorna se a corrida terminou.
    }

    public static double getLinhaDeChegada() {
        return LINHA_DE_CHEGADA; // Retorna a linha de chegada.
    }

    // M�todo para registrar os logs de progresso dos carros.
    public static void log(String nomeCarro, double velocidade, double deslocamento) {
        synchronized (lock) { // Bloqueia a se��o cr�tica para garantir a consist�ncia dos dados.
            String logEntry = String.format("O %s andou %.2fm e j� percorreu %.2fm", nomeCarro, deslocamento, velocidade);
            logList.add(logEntry);
            System.out.println(logEntry);
        }
    }

    // M�todo para iniciar a corrida.
    public static void iniciarCorrida() {
        fimDaCorrida = false; // Reseta a vari�vel que indica o fim da corrida.
        posicaoChegada.set(1); // Reseta a posi��o de chegada.
        carros.clear(); // Limpa a lista de carros.
        carros.add(new Carro("Carro_01"));
        carros.add(new Carro("Carro_02"));
        carros.add(new Carro("Carro_03"));
        carros.add(new Carro("Carro_04"));
        carros.add(new Carro("Carro_05"));

        for (Carro carro : carros) {
            new Thread(carro).start(); // Cria e inicia uma nova thread para o carro.
        }
    }

    // M�todo principal que inicia a corrida.
    public static void main(String[] args) {
        iniciarCorrida();
    }
}


