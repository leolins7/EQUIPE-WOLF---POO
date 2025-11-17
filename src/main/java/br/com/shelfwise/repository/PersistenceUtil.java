// NOVO ARQUIVO: src/main/java/br/com/shelfwise/repository/PersistenceUtil.java
package br.com.shelfwise.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Classe utilitária para lidar com a serialização de objetos em arquivos.
 * Garante que as operações de I/O sejam centralizadas e sincronizadas.
 */
public class PersistenceUtil {

    /**
     * Salva uma lista de dados (ex: List<Livro>) em um arquivo.
     * Esta operação é sincronizada para evitar corrupção de dados.
     *
     * @param dados Objeto a ser salvo (geralmente uma List).
     * @param arquivo O nome do arquivo (ex: "livros.dat").
     */
    public static synchronized void salvarDados(Object dados, String arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(dados);
        } catch (Exception e) {
            System.err.println("ERRO AO SALVAR DADOS EM " + arquivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega uma lista de dados de um arquivo.
     * Se o arquivo não existir (primeira execução), retorna uma ArrayList vazia.
     *
     * @param arquivo O nome do arquivo (ex: "livros.dat").
     * @return O objeto desserializado (geralmente uma List) ou uma nova ArrayList se o arquivo não for encontrado.
     */
    @SuppressWarnings("unchecked")
    public static synchronized Object carregarDados(String arquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return ois.readObject();
        } catch (java.io.FileNotFoundException e) {
            // Normal na primeira execução ou se o arquivo for deletado
            System.out.println("Aviso: Arquivo " + arquivo + " não encontrado. Criando nova base de dados.");
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("ERRO AO CARREGAR DADOS DE " + arquivo + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vazia em caso de erro
        }
    }
}