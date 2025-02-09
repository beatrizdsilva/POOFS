import java.io.Serializable;
import java.util.Arrays;

/**
 * Representa um produto de farmácia que não requer prescrição médica.
 * Subclasse de ProdutoFarmacia.
 */
public class ProdutoFarmaciaSemPrescricao extends ProdutoFarmacia implements Serializable {

    /**
     * A categoria do produto (ex: "animais").
     */
    private String categoria;

    /**
     * A lista de taxas associadas ao produto em diferentes localizações.
     */
    private double[] listaTaxas;

    /**
     * Construtor padrão para ProdutoFarmaciaSemPrescricao.
     * Inicializa um novo produto sem informações.
     *
     * @param categoria   A categoria do produto.
     * @param listaTaxas  A lista de taxas aplicáveis em diferentes localizações.
     */
    public ProdutoFarmaciaSemPrescricao(String categoria, double[] listaTaxas) {}

    /**
     * Construtor para ProdutoFarmaciaSemPrescricao, inicializando todos os atributos.
     *
     * @param codigo            O código do produto.
     * @param nome              O nome do produto.
     * @param descricao         A descrição do produto.
     * @param quantidade        A quantidade do produto.
     * @param valorUnitSemIVA   O valor unitário do produto sem IVA.
     * @param categoria         A categoria do produto.
     * @param listaTaxas        A lista de taxas aplicáveis em diferentes localizações.
     */
    public ProdutoFarmaciaSemPrescricao(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIVA, String categoria, double[] listaTaxas) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIVA);
        this.categoria = categoria;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Construtor para ProdutoFarmaciaSemPrescricao, inicializando todos os atributos, exceto o código do produto.
     *
     * @param nome              O nome do produto.
     * @param descricao         A descrição do produto.
     * @param quantidade        A quantidade do produto.
     * @param valorUnitSemIVA   O valor unitário do produto sem IVA.
     * @param categoria         A categoria do produto.
     * @param listaTaxas        A lista de taxas aplicáveis em diferentes localizações.
     */
    public ProdutoFarmaciaSemPrescricao(String nome, String descricao, int quantidade, double valorUnitSemIVA, String categoria, double[] listaTaxas) {
        super(nome, descricao, quantidade, valorUnitSemIVA);
        this.categoria = categoria;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Calcula o valor do produto, incluindo o IVA, com base na localização do cliente.
     * Se o produto for da categoria "animais", aplica um desconto de 1% na taxa de IVA.
     *
     * @param cliente O objeto cliente, utilizado para determinar o índice da localização.
     * @return O valor do produto incluindo IVA.
     */
    protected double calcularValorComIVA(Cliente cliente) {
        //double[] taxas = {23, 23,23}; // Index: 0 (Continente), 1 (Madeira), 2 (Açores e Acores)
        int indice = cliente.localizacaoToIndex();
        double taxa = listaTaxas[indice];

        if ("animais".trim().equalsIgnoreCase(categoria))
            taxa -= 1; // Diminui 1% para categoria "animais"


        return calcularValorSemIVA() * (1 + taxa / 100);
    }

    /**
     * Retorna uma representação detalhada do produto, incluindo informações sobre a categoria e as taxas aplicadas.
     *
     * @return Uma string detalhando os atributos do produto.
     */
    protected String toTexto(){
        return "Tipo: Farmácia Sem Prescrição, " +
                "Código: " + codigo +
                ", Nome: " + nome +
                ", Descrição: " + descricao +
                ", Quantidade: " + quantidade +
                ", Valor Unitário Sem IVA: " + valorUnitSemIVA +
                ", Categoria: " + categoria +
                ", Taxas: " + Arrays.toString(listaTaxas);
    }

    // Métodos Getter e Setter

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double[] getListaTaxas() {
        return listaTaxas;
    }

    public void setListaTaxas(double[] listaTaxas) {
        this.listaTaxas = listaTaxas;
    }

    /**
     * Retorna uma representação em string do produto de farmácia sem prescrição.
     *
     * @return Uma string com detalhes sobre os atributos do produto.
     */
    @Override
    public String toString() {
        return "Produto Farmacia Sem Prescricao: {"+ super.toString() +
                ", categoria='" + categoria + '\'' +
                ", Lista Taxas=" + Arrays.toString(listaTaxas) +
                '}';
    }
}