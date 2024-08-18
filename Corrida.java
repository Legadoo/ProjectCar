package trab1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Corrida { // Declara a classe pública Corrida.
    private static final double LINHA_DE_CHEGADA = 20000.0; // Define a constante para a linha de chegada da corrida.
    private static volatile boolean fimDaCorrida = false; // Variável volátil que indica se a corrida terminou.
    private static List<String> logList = new ArrayList<>(); // Lista que armazena os logs da corrida.
    private static final Object lock = new Object(); // Objeto usado para sincronização.
    private static AtomicInteger posicaoChegada = new AtomicInteger(1); // Contador atômico para rastrear a posição de chegada dos carros.
    private static List<Carro> carros = new ArrayList<>(); // Lista que armazena os carros participantes da corrida.

    // Método para definir o fim da corrida e registrar a chegada de um carro.
    public static void setFimDaCorrida(String nomeCarro, long tempoTotal) {
        synchronized (lock) { // Bloqueia a seção crítica para garantir a consistência dos dados.
            if (!fimDaCorrida) { // Verifica se a corrida ainda não terminou.
                fimDaCorrida = true; // Marca o fim da corrida.
            }
            int posicao = posicaoChegada.getAndIncrement(); // Obtém e incrementa a posição de chegada.
            String logEntry = nomeCarro + " alcançou a linha de chegada e ficou em " + posicao + "º lugar";
            // String logEntry = nomeCarro + " alcançou a linha de chegada em " + tempoTotal + "ms e ficou em " + posicao + "º lugar";
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

    // Método para registrar os logs de progresso dos carros.
    public static void log(String nomeCarro, double velocidade, double deslocamento) {
        synchronized (lock) { // Bloqueia a seção crítica para garantir a consistência dos dados.
            String logEntry = String.format("O %s andou %.2fm e já percorreu %.2fm", nomeCarro, deslocamento, velocidade);
            logList.add(logEntry);
            System.out.println(logEntry);
        }
    }

    // Método para iniciar a corrida.
    public static void iniciarCorrida() {
        fimDaCorrida = false; // Reseta a variável que indica o fim da corrida.
        posicaoChegada.set(1); // Reseta a posição de chegada.
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

    // Método principal que inicia a corrida.
    public static void main(String[] args) {
        iniciarCorrida();
    }
}


