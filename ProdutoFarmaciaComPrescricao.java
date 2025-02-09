import java.io.Serializable;
import java.util.Arrays;

/**
 * Representa um produto farmacêutico com prescrição médica.
 * Subclasse de ProdutoFarmacia, contém detalhes adicionais sobre a prescrição médica e as taxas associadas.
 * A classe permite o cálculo do valor do produto com base na localização do cliente e nas taxas associadas a essa localização.
 */
public class ProdutoFarmaciaComPrescricao extends ProdutoFarmacia implements Serializable {

    /**
     * O nome do médico que emitiu a prescrição para o produto.
     */
    private String nome_medico;

    /**
     * A lista de taxas associadas ao produto em diferentes localizações.
     */
    private double[] listaTaxas;

    /**
     * Construtor padrão para ProdutoFarmaciaComPrescricao.
     * Inicializa um novo produto sem informações.
     *
     * @param nome_medico O nome do médico que emitiu a prescrição.
     */
    public ProdutoFarmaciaComPrescricao(String nome_medico) {}

    /**
     * Construtor para ProdutoFarmaciaComPrescricao, inicializando todos os atributos.
     *
     * @param codigo            O código do produto.
     * @param nome              O nome do produto.
     * @param descricao         A descrição do produto.
     * @param quantidade        A quantidade do produto.
     * @param valorUnitSemIVA   O valor unitário do produto sem IVA.
     * @param nome_medico       O nome do médico que emitiu a prescrição.
     * @param listaTaxas        A lista de taxas aplicáveis em diferentes localizações.
     */
    public ProdutoFarmaciaComPrescricao(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIVA, String nome_medico,double[] listaTaxas) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIVA);
        this.nome_medico = nome_medico;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Construtor para ProdutoFarmaciaComPrescricao, inicializando todos os atributos, exceto o código do produto.
     *
     * @param nome              O nome do produto.
     * @param descricao         A descrição do produto.
     * @param quantidade        A quantidade do produto.
     * @param valorUnitSemIVA   O valor unitário do produto sem IVA.
     * @param nome_medico       O nome do médico que emitiu a prescrição.
     * @param listaTaxas        A lista de taxas aplicáveis em diferentes localizações.
     */
    public ProdutoFarmaciaComPrescricao(String nome, String descricao, int quantidade, double valorUnitSemIVA, String nome_medico, double[] listaTaxas) {
        super(nome, descricao, quantidade, valorUnitSemIVA);
        this.nome_medico = nome_medico;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Calcula o valor do produto, incluindo o IVA, com base na localização do cliente.
     *
     * @param cliente O objeto cliente, utilizado para determinar o índice da localização.
     * @return O valor do produto incluindo IVA.
     */
    protected double calcularValorComIVA(Cliente cliente) {
        //double[] taxas = {6, 5, 4}; // Index: 0 (Continente), 1 (Madeira), 2 (Açores e Acores)
        int indice = cliente.localizacaoToIndex();
        double taxa = listaTaxas[indice];

        return calcularValorSemIVA() * (1 + taxa / 100);
    }

    /**
     * Retorna uma representação detalhada do produto, incluindo informações sobre a prescrição médica.
     *
     * @return Uma string detalhando os atributos do produto.
     */
    protected String toTexto(){
        return "Tipo: Farmácia Com Prescrição, " +
                "Código: " + codigo +
                ", Nome: " + nome +
                ", Descrição: " + descricao +
                ", Quantidade: " + quantidade +
                ", Valor Unitário Sem IVA: " + valorUnitSemIVA +
                ", Médico: " + nome_medico +
                ", Taxas: " + Arrays.toString(listaTaxas);
    }

    /**
     * Obtém o nome do médico associado ao produto.
     *
     * @return uma String representando o nome do médico.
     */
    public String getNome_medico() {
        return nome_medico;
    }

    /**
     * Define o nome do médico associado ao produto.
     *
     * @param nome_medico uma String representando o nome do médico.
     */
    public void setNome_medico(String nome_medico) {
        this.nome_medico = nome_medico;
    }

    /**
     * Obtém a lista de taxas associadas ao produto.
     *
     * @return um array de double representando as taxas.
     */
    public double[] getListaTaxas() {
        return listaTaxas;
    }

    /**
     * Define a lista de taxas associadas ao produto.
     *
     * @param listaTaxas um array de double representando as taxas.
     */
    public void setListaTaxas(double[] listaTaxas) {
        this.listaTaxas = listaTaxas;
    }

    @Override
    public String toString() {
        return "Produto Farmacia Com Prescricao: {"+ super.toString() +
                ", nome medico='" + nome_medico + '\'' +
                ", Lista Taxas=" + Arrays.toString(listaTaxas) +
                '}';
    }
}