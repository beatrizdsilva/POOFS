import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe que representa um produto alimentar sujeito à taxa normal de IVA.
 * Subclasse de ProdutoAlimentar e implementa Serializable para permitir a serialização.
 */
public class ProdutoAlimentarTaxaNormal extends ProdutoAlimentar implements Serializable {

    /**
     * Lista de taxas de IVA aplicáveis em diferentes localizações (Continente, Madeira, Açores).
     */
    private double[] listaTaxas;

    /**
     * Construtor vazio.
     */
    public ProdutoAlimentarTaxaNormal() {
    }

    /**
     * Construtor com parâmetros completos.
     *
     * @param codigo           Código único do produto.
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico ou não.
     * @param listaTaxas       Lista de taxas de IVA aplicáveis.
     */
    public ProdutoAlimentarTaxaNormal(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio,double[] listaTaxas) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIva, bio);
        this.listaTaxas = listaTaxas;
    }

    /**
     * Construtor que gera automaticamente o código do produto.
     *
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico.
     * @param listaTaxas       Lista de taxas de IVA aplicáveis.
     */
    public ProdutoAlimentarTaxaNormal(String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio,double[] listaTaxas) {
        super(nome, descricao, quantidade, valorUnitSemIva, bio);
        this.listaTaxas = listaTaxas;
    }

    /**
     * Retorna uma representação textual detalhada do produto, incluindo todas as informações relevantes.
     *
     * @return Representação textual detalhada do produto.
     */
    protected String toTexto(){
        return "Tipo: Taxa Normal, " +
                "Código: " + codigo +
                ", Nome: " + nome +
                ", Descrição: " + descricao +
                ", Quantidade: " + quantidade +
                ", Valor Unitário Sem IVA: " + valorUnitSemIVA +
                ", Biológico: " + bio +
                ", Taxas: " + Arrays.toString(listaTaxas);
    }

    /**
     * Calcula o valor total com IVA aplicado, considerando a localização do cliente e
     * possíveis descontos para produtos biológicos.
     *
     * @param cliente Cliente associado ao cálculo, usado para determinar a localização.
     * @return Valor total do produto com IVA.
     */
    protected double calcularValorComIVA(Cliente cliente) {
        // Obtemos o índice da localização do cliente (ex.: 0 para Continente).
        int indice = cliente.localizacaoToIndex();

        // Carregamos a taxa correspondente à localização.
        double taxa = listaTaxas[indice];

        // Aplica desconto para produtos biológicos, caso aplicável.
        taxa = descontoBio(taxa);

        // Calcula o valor final com IVA.
        return calcularValorSemIVA() * (1 + taxa / 100);
    }

    // Métodos Getter e Setter

    public double[] getListaTaxas() {
        return listaTaxas;
    }

    public void setListaTaxas(double[] listaTaxas) {
        this.listaTaxas = listaTaxas;
    }

    /**
     * Retorna uma representação textual básica do produto, incluindo informações de taxas.
     *
     * @return String representando o produto alimentar de taxa normal.
     */
    @Override
    public String toString() {
        return "Produto Alimentar de Taxa Normal: {" + super.toString() + ", Lista Taxas="+ Arrays.toString(listaTaxas) +"}";
    }
}