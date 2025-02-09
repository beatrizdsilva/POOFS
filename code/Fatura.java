import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * A classe Fatura representa uma fatura emitida para um cliente, contendo informações
 * sobre o cliente, a data de emissão, os produtos associados e o número da fatura.
 * Implementa Serializable para permitir a serialização de objetos desta classe.
 */
public class Fatura implements Serializable {

    /**
     * Contador estático para gerar automaticamente os números das faturas.
     */
    protected static int contador_numeroFatura = 1;

    /**
     * Número único atribuído à fatura.
     */
    protected int numeroFatura;

    /**
     * O cliente associado à fatura.
     */
    protected Cliente cliente;

    /**
     * A data de emissão da fatura (no formato DD/MM/YYYY).
     */
    protected Date data;    //modelo : DD/MM/YYYY

    /**
     * Lista de produtos incluídos na fatura.
     */
    protected ArrayList<Produto> produtos;

    /**
     * Construtor vazio para a classe Fatura.
     */
    public Fatura(){}

    /**
     * Construtor que inicializa os atributos da fatura.
     *
     * @param cliente O cliente associado à fatura.
     * @param data A data de emissão da fatura.
     * @param produtos Lista de produtos incluídos na fatura.
     */
    public Fatura(Cliente cliente, Date data, ArrayList<Produto> produtos){
        this.numeroFatura = contador_numeroFatura++;
        this.cliente = cliente;
        this.data = data;
        this.produtos = produtos;
    }

    /**
     * Adiciona um produto à lista de produtos da fatura.
     *
     * @param produto O produto a ser adicionado.
     */
    public void addProduto(Produto produto){
        produtos.add(produto);
    }

    /**
     * Calcula o valor total da fatura com IVA (Imposto sobre o Valor Acrescentado).
     *
     * @param fatura A fatura para a qual será calculado o total com IVA.
     * @return O valor total da fatura com IVA.
     */
    protected double calcularTotalComIVA(Fatura fatura) {
        double totalComIVA = 0.0;
        for (Produto produto : fatura.getProdutos()) {
            totalComIVA += produto.calcularValorComIVA(cliente);
        }
        return totalComIVA;
    }

    /**
     * Calcula o valor total da fatura sem incluir o IVA.
     *
     * @return O valor total da fatura sem IVA.
     */
    public double calcularTotalSemIVA(){
        double total = 0.0;
        for(Produto produto : produtos){
            total += produto.calcularValorSemIVA();
        }
        return total;
    }

    /**
     * Define o valor inicial do contador para o número das faturas.
     *
     * @param novoValor O novo valor inicial para o contador.
     */
    public static void setContadorNumeroFatura(int novoValor) {
        contador_numeroFatura = novoValor;
    }

    /**
     * Obtém o número da fatura.
     *
     * @return O número da fatura.
     */
    public int getNumeroFatura() {
        return numeroFatura;
    }

    /**
     * Define o número da fatura.
     *
     * @param numeroFatura O número da fatura.
     */
    public void setNumeroFatura(int numeroFatura) {
        this.numeroFatura = numeroFatura;
    }

    /**
     * Obtém o cliente associado à fatura.
     *
     * @return O cliente associado à fatura.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente associado à fatura.
     *
     * @param cliente O cliente associado à fatura.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtém a data de emissão da fatura.
     *
     * @return A data de emissão da fatura.
     */
    public Date getData() {
        return data;
    }

    /**
     * Define a data de emissão da fatura.
     *
     * @param data A data de emissão da fatura.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Obtém a lista de produtos incluídos na fatura.
     *
     * @return A lista de produtos incluídos na fatura.
     */
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    /**
     * Define a lista de produtos incluídos na fatura.
     *
     * @param produtos A lista de produtos a ser associada à fatura.
     */
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * Retorna uma representação em String da fatura, contendo informações sobre
     * o número da fatura, cliente, data de emissão e os produtos incluídos.
     *
     * @return Uma string representando os detalhes da fatura.
     */
    @Override
    public String toString() {
        return "Fatura{" +
                "numeroFatura=" + numeroFatura +
                ", cliente=" + cliente +
                ", data=" + data +
                ", produtos=" + produtos +
                '}';
    }


}