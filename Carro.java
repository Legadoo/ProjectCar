package trab1;

import java.util.Random;

public class Carro implements Runnable {
    private String nome; // Nome do carro.
    private double velocidade; // Velocidade do carro.
    private double deslocamento; // Distância total percorrida pelo carro.
    private double aceleracao; // Aceleração atual do carro.
    private static final double TEMPO_FRAME = 1.0; // Define o tempo de cada frame.
    private long tempoInicial; // Tempo inicial da corrida para este carro.

    // Construtor que inicializa o nome do carro e define a velocidade e deslocamento iniciais.
    public Carro(String nome) {
        this.nome = nome;
        this.velocidade = 0;
        this.deslocamento = 0;
    }

    // Método run que será executado quando a thread for iniciada.
    public void run() {
        Random random = new Random(); // Cria uma instância de Random para gerar acelerações aleatórias.
        tempoInicial = System.currentTimeMillis(); // Captura o tempo inicial da corrida.
        while (!Corrida.isFimDaCorrida()) { // Loop continua enquanto a corrida não termina.
            aceleracao = random.nextDouble() * 10; // Gera uma aceleração aleatória entre 0 e 10 m/s².
            velocidade = velocidade + aceleracao * TEMPO_FRAME; // Atualiza a velocidade do carro.
            deslocamento = deslocamento + velocidade * TEMPO_FRAME; // Atualiza o deslocamento do carro.
            Corrida.log(nome, velocidade, deslocamento); // Registra o progresso do carro.
            if (deslocamento >= Corrida.getLinhaDeChegada()) { // Verifica se o carro alcançou a linha de chegada.
                Corrida.setFimDaCorrida(nome, System.currentTimeMillis() - tempoInicial); // Registra o fim da corrida para este carro.
                break; // Sai do loop.
            }
            try {
                Thread.sleep(100); // Pausa de 100 ms entre cada iteração para simular o tempo real.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
