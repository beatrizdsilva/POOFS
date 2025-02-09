import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe que representa um produto alimentar com taxa intermédia de IVA.
 * Subclasse de ProdutoAlimentar e implementa Serializable para permitir a serialização.
 */
public class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar implements Serializable {

    /**
     * Categoria do produto alimentar.
     */
    private String categoria;

    /**
     * Lista de taxas de IVA aplicáveis em diferentes localizações (Continente, Madeira, Açores).
     */
    private double[] listaTaxas;

    /**
     * Incremento de 1% na taxa de IVA para produtos da categoria "vinho".
     */
    //private final int aumento = 1;

    /**
     * Construtor vazio.
     */
    public ProdutoAlimentarTaxaIntermedia() {}

    /**
     * Construtor com parâmetros completos.
     *
     * @param codigo           Código único do produto.
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico ou não.
     * @param categoria        Categoria do produto.
     * @param listaTaxas       Lista de taxas de IVA aplicáveis.
     */
    public ProdutoAlimentarTaxaIntermedia(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio, String categoria,double[] listaTaxas) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIva, bio);
        this.categoria = categoria;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Construtor que gere automaticamente o código do produto.
     *
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico ou não.
     * @param categoria        Categoria do produto.
     * @param listaTaxas       Lista de taxas de IVA aplicáveis.
     */
    public ProdutoAlimentarTaxaIntermedia(String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio, String categoria,double[] listaTaxas) {
        super(nome, descricao, quantidade, valorUnitSemIva, bio);
        this.categoria = categoria;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Calcula o valor total com IVA aplicado, considerando a localização do cliente,
     * a categoria do produto e possíveis descontos para produtos biológicos.
     *
     * @param cliente Cliente associado ao cálculo, usado para determinar a localização.
     * @return Valor total do produto com IVA.
     */
    @Override
    protected double calcularValorComIVA(Cliente cliente) {
        // Obtemos o índice da localização do cliente (ex.: 0 para Continente).
        int indice = cliente.localizacaoToIndex();

        // Carregamos a taxa correspondente à localização.
        double taxa = listaTaxas[indice];

        // Se a categoria for "vinho", adiciona 1% na taxa de IVA.
        if ("vinho".trim().equalsIgnoreCase(categoria))
            taxa += 1; // Aumenta 1% para categoria "vinho"

        // Aplica desconto para produtos biológicos, caso aplicável.
        taxa = descontoBio(taxa);

        // Calcula o valor final com IVA.
        return calcularValorSemIVA() * (1 + taxa / 100);
    }

    /**
     * Retorna uma representação textual detalhada do produto, incluindo todas as informações relevantes.
     *
     * @return Representação textual do produto.
     */
    protected String toTexto(){
        return "Tipo: Taxa Intermédia, " +
                "Código: " + codigo +
                ", Nome: " + nome +
                ", Descrição: " + descricao +
                ", Quantidade: " + quantidade +
                ", Valor Unitário Sem IVA: " + valorUnitSemIVA +
                ", Biológico: " + bio +
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
     * Retorna uma representação textual básica do produto, incluindo informações de taxas e categoria.
     *
     * @return String representando o produto alimentar de taxa intermédia.
     */
    @Override
    public String toString() {
        return "Produto Alimentar de Taxa Intermedia: {" + super.toString() + ", categoria= '" + categoria +"', Lista Taxas= " + Arrays.toString(listaTaxas) +"}";
    }
}