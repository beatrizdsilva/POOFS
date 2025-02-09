import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe que representa um produto alimentar sujeito à taxa reduzida de IVA.
 * Subclasse de ProdutoAlimentar e implementa Serializable para permitir a serialização.
 */
public class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar implements Serializable {

    /**
     * Lista de certificações do produto
     */
    private String[] certificacoes;

    /**
     * Lista de taxas de IVA aplicáveis em diferentes localizações (Continente, Madeira, Açores).
     */
    private double[] listaTaxas;

    /**
     * Construtor vazio.
     */
    public ProdutoAlimentarTaxaReduzida(){}

    /**
     * Construtor com parâmetros completos para inicialização do produto.
     *
     * @param codigo           Código único do produto.
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico ou não.
     * @param certificacoes    Lista de certificações do produto.
     * @param listaTaxas       Lista de taxas de IVA aplicáveis.
     */
    public ProdutoAlimentarTaxaReduzida(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio, String[] certificacoes,double[] listaTaxas) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIva, bio);
        this.certificacoes = certificacoes;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Construtor que gera automaticamente o código do produto.
     *
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade de produto.
     * @param valor_unSemIVA   Valor unitário sem IVA.
     * @param ehBiologico      Indica se o produto é biológico ou não.
     * @param certificacoes    Lista de certificações do produto.
     * @param listaTaxas       Lista de taxas de IVA aplicáveis.
     */
    public ProdutoAlimentarTaxaReduzida(String nome, String descricao, int quantidade, double valor_unSemIVA, boolean ehBiologico, String[] certificacoes,double[] listaTaxas) {
        super(nome, descricao, quantidade, valor_unSemIVA, ehBiologico);
        this.certificacoes = certificacoes;
        this.listaTaxas = listaTaxas;
    }

    /**
     * Calcula o valor total com IVA aplicado, considerando a localização do cliente,
     * possíveis descontos para produtos biológicos e um ajuste de taxa baseado nas certificações.
     *
     * @param cliente Cliente associado ao cálculo, usado para determinar a localização.
     * @return Valor total do produto com IVA.
     */
    protected double calcularValorComIVA(Cliente cliente) {
        // Obtemos o índice da localização do cliente (ex.: 0 para Continente).
        int index = cliente.localizacaoToIndex();

        //Carregamos a taxa correspondente à localização.
        double taxa = listaTaxas[index];

        // Se o produto tem exatamente 4 certificações, reduzimos a taxa de IVA em 1%.
        if (certificacoes != null && certificacoes.length == 4) {
            taxa -= 1; //reduzir 1% se tiver 4 certificações
        }

        // Aplica desconto para produtos biológicos, caso aplicável.
        taxa = descontoBio(taxa); //aplicar desconto para produto biologico

        // Calcula o valor final com IVA.
        return calcularValorSemIVA() * (1 + taxa / 100);
    }

    /**
     * Retorna uma representação textual detalhada do produto, incluindo todas as informações relevantes.
     *
     * @return Representação textual detalhada do produto.
     */
    protected String toTexto(){
        return "Tipo: Taxa Reduzida, " +
                "Código: " + codigo +
                ", Nome: " + nome +
                ", Descrição: " + descricao +
                ", Quantidade: " + quantidade +
                ", Valor Unitário Sem IVA: " + valorUnitSemIVA +
                ", Biológico: " + bio +
                ", Certificações: " + String.join(",", certificacoes) +
                ", Taxas: " + Arrays.toString(listaTaxas);
    }

    /**
     * Obtém as certificações associadas ao produto.
     *
     * @return um array de String representando as certificações.
     */
    public String[] getCertificacoes() {
        return certificacoes;
    }

    /**
     * Define as certificações associadas ao produto.
     *
     * @param certificacoes um array de String representando as certificações.
     */
    public void setCertificacoes(String[] certificacoes) {
        this.certificacoes = certificacoes;
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

    /**
     * Retorna uma representação textual básica do produto, incluindo informações de certificações e taxas.
     *
     * @return String que representa o produto alimentar de taxa reduzida.
     */
    @Override
    public String toString() {
        return "Produto Alimentar de Taxa Reduzida: {" + super.toString() +
                ", certificacoes= " + Arrays.toString(certificacoes) +
                ", Lista Taxas= " + Arrays.toString(listaTaxas)
                + '}';
    }
}