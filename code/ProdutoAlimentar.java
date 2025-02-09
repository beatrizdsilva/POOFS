import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe abstrata que representa um produto alimentar.
 * Extende a classe Produto e implementa a interface Serializable,
 * permitindo que os objetos sejam serializados.
 */
public abstract class ProdutoAlimentar extends Produto implements Serializable {

    /**
     * Indica se o produto é biológico (true para biológico, false caso contrário).
     */
    protected boolean bio;

    /**
     * Taxa de desconto aplicada para produtos biológicos.
     * É um valor constante (10% de desconto).
     */
    //private final double desconto = 0.9;

    /**
     * Construtor vazio da classe ProdutoAlimentar.
     * Útil para inicialização sem parâmetros.
     */
    public ProdutoAlimentar() {}    //Construtor vazio

    /**
     * Construtor completo da classe ProdutoAlimentar.
     *
     * @param codigo           Código único do produto.
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade disponível do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico ou não.
     */
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIva);
        this.bio = bio;
    }

    /**
     * Construtor que gera automaticamente o código do produto com base no contador estático.
     *
     * @param nome             Nome do produto.
     * @param descricao        Descrição do produto.
     * @param quantidade       Quantidade disponível do produto.
     * @param valorUnitSemIva  Valor unitário sem IVA.
     * @param bio              Indica se o produto é biológico ou não.
     */
    public ProdutoAlimentar(String nome, String descricao, int quantidade, double valorUnitSemIva, boolean bio) {
        super(nome, descricao, quantidade, valorUnitSemIva);
        this.bio = bio;
    }

    /**
     * Calcula o valor total do produto sem IVA.
     * Este método utiliza a implementação da superclasse.
     *
     * @return Valor total sem IVA.
     */
    @Override
    protected double calcularValorSemIVA() {
        return super.calcularValorSemIVA();
    }

    /**
     * Método abstrato para calcular o valor do produto com IVA.
     * Deve ser implementado pelas subclasses.
     *
     * @param cliente Cliente associado ao cálculo, permitindo personalizações.
     * @return Valor total com IVA.
     */
    protected abstract double calcularValorComIVA(Cliente cliente); //sera implementado nas subclassses

    /**
     * Aplica o desconto para produtos biológicos, caso o produto seja marcado como "bio".
     *
     * @param taxa Taxa original a ser ajustada.
     * @return Taxa ajustada com desconto, caso aplicável.
     */
    protected double descontoBio(double taxa){
        if(bio) taxa *= 0.9;
        return taxa;
    }

    /**
     * Método abstrato para obter uma representação textual do produto alimentar.
     * Deve ser implementado pelas subclasses.
     *
     * @return Representação textual do produto.
     */
    @Override
    protected abstract String toTexto();

    // Métodos Getter e Setter para o atributo bio.

    public boolean isBio() {
        return bio;
    }

    public void setBio(boolean bio) {
        this.bio = bio;
    }

    /**
     * Retorna uma representação textual básica do produto alimentar,
     * incluindo informações herdadas e o atributo bio.
     *
     * @return String que representa o produto alimentar.
     */
    @Override
    public String toString() {
        return "Produto Alimentar: {"+ super.toString() +
                ", biologico= " + bio +
                '}';
    }
}