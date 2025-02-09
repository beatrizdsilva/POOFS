import java.io.Serializable;

/**
 * Classe abstrata que representa um produto farmacêutico.
 * Subclasse de Produto, fornece os métodos básicos para manipulação de produtos farmacêuticos.
 * As subclasses devem implementar os métodos abstratos para cálculos específicos de IVA e representação textual.
 */
public abstract class ProdutoFarmacia extends Produto implements Serializable {

    /**
     * Construtor padrão que inicializa o produto farmacêutico sem parâmetros.
     */
    public ProdutoFarmacia() {}

    /**
     * Construtor que inicializa um produto farmacêutico com os parâmetros fornecidos.
     * @param codigo O código do produto.
     * @param nome O nome do produto.
     * @param descricao A descrição do produto.
     * @param quantidade A quantidade do produto.
     * @param valorUnitSemIVA O valor unitário do produto sem IVA.
     */
    public ProdutoFarmacia(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIVA) {
        super(codigo, nome, descricao, quantidade, valorUnitSemIVA);
    }

    /**
     * Construtor que inicializa um produto farmacêutico com os parâmetros fornecidos, sem o código.
     * O código será gerado automaticamente pela classe Produto.
     * @param nome O nome do produto.
     * @param descricao A descrição do produto.
     * @param quantidade A quantidade do produto.
     * @param valorUnitSemIVA O valor unitário do produto sem IVA.
     */
    public ProdutoFarmacia(String nome, String descricao, int quantidade, double valorUnitSemIVA) {
        super(nome, descricao, quantidade, valorUnitSemIVA);
    }

    /**
     * Método abstrato para calcular o valor do produto com IVA.
     * Este método deve ser implementado nas subclasses, fornecendo a lógica específica de cálculo de IVA.
     *
     * @param cliente O cliente para o qual o valor do produto com IVA será calculado.
     * @return O valor do produto com IVA aplicado.
     */
    protected abstract double calcularValorComIVA(Cliente cliente);

    /**
     * Método para calcular o valor do produto sem IVA.
     * Este método chama o método calcularValorSemIVA() da classe Produto para obter o valor sem IVA da subclasse.
     *
     * @return O valor do produto sem IVA.
     */
    protected double calcularValorSemIVA() {
        return super.calcularValorSemIVA();
    }

    /**
     * Método abstrato para retornar a representação textual do produto.
     * Este método deve ser implementado nas subclasses para fornecer uma descrição personalizada do produto.
     *
     * @return A representação textual do produto.
     */
    protected abstract String toTexto();

    /**
     * Método para gerar uma string representando o produto farmacêutico.
     * Este método retorna uma string com os detalhes do produto, incluindo os atributos herdados da classe Produto.
     *
     * @return A string representando o produto farmacêutico.
     */
    @Override
    public String toString() {
        return "Produto Farmacia: {" +
                super.toString() +
                '}';
    }
}