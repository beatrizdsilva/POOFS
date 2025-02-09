import java.io.Serializable;

/**
 * Classe abstrata que representa um Produto genérico.
 * Implementa a interface Serializable para permitir que objetos
 * dessa classe e as suas subclasses sejam serializados.
 */
public abstract class Produto implements Serializable {

    /**
     * Contador estático usado para gerar códigos únicos para os produtos.
     * Incrementa automaticamente cada vez que um novo produto é criado.
     */
    protected static int contador_codigo = 1;

    /**
     * Código único do produto.
     */
    protected int codigo;

    /**
     * Quantidade disponível do produto.
     */
    protected int quantidade;

    /**
     * Nome do produto.
     */
    protected String nome;

    /**
     * Descrição do produto.
     */
    protected String descricao;

    /**
     * Valor unitário do produto sem o IVA (Imposto sobre Valor Acrescentado).
     */
    protected double valorUnitSemIVA;

    /**
     * Construtor vazio da classe Produto.
     * Útil para casos onde a inicialização dos atributos seja feita posteriormente.
     */
    public Produto() {  //Construtor vazio
    }

    /**
     * Construtor que permite inicializar todos os atributos do produto.
     *
     * @param codigo          Código único do produto.
     * @param nome            Nome do produto.
     * @param descricao       Descrição do produto.
     * @param quantidade      Quantidade disponível.
     * @param valorUnitSemIVA Valor unitário sem IVA.
     */
    public Produto(int codigo, String nome, String descricao, int quantidade, double valorUnitSemIVA){
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitSemIVA = valorUnitSemIVA;
    }

    /**
     * Construtor que gera automaticamente o código do produto
     * com base no contador estático.
     *
     * @param nome            Nome do produto.
     * @param descricao       Descrição do produto.
     * @param quantidade      Quantidade disponível.
     * @param valorUnitSemIVA Valor unitário sem IVA.
     */
    public Produto(String nome, String descricao, int quantidade, double valorUnitSemIVA){
        this.codigo = contador_codigo++;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitSemIVA = valorUnitSemIVA;
    }

    /**
     * Método abstrato para calcular o valor do produto com IVA.
     * Deve ser implementado pelas subclasses de Produto.
     *
     * @param cliente Cliente para o qual o cálculo será feito,
     *                possibilitando considerar descontos ou taxas específicas.
     * @return Valor total com IVA aplicado.
     */
    protected abstract double calcularValorComIVA(Cliente cliente);

    /**
     * Calcula o valor total do produto sem IVA.
     *
     * @return Valor total sem IVA.
     */
    protected double calcularValorSemIVA(){
        return quantidade * valorUnitSemIVA;
    }

    /**
     * Método abstrato para obter uma representação de texto do produto
     * em formato específico.
     * Deve ser implementado pelas subclasses.
     *
     * @return Representação textual do produto.
     */
    protected abstract String toTexto();

    /**
     * Obtém o código do produto.
     *
     * @return o código do produto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define o código do produto.
     *
     * @param codigo o código do produto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtém o nome do produto.
     *
     * @return o nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome o nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a descrição do produto.
     *
     * @return a descrição do produto.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     *
     * @param descricao a descrição do produto.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a quantidade do produto.
     *
     * @return a quantidade do produto.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto.
     *
     * @param quantidade a quantidade do produto.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém o valor unitário do produto sem IVA (Imposto sobre o Valor Acrescentado).
     *
     * @return o valor unitário sem IVA.
     */
    public double getValorUnitSemIVA() {
        return valorUnitSemIVA;
    }


    /**
     * Define o valor unitário do produto sem IVA (Imposto sobre o Valor Acrescentado).
     *
     * @param valorUnitSemIVA o valor unitário sem IVA.
     */

    public void setValorUnitSemIVA(double valorUnitSemIVA) {
        this.valorUnitSemIVA = valorUnitSemIVA;
    }

    /**
     * Retorna uma representação textual básica do produto, contendo
     * os principais atributos.
     *
     * @return String que representa o produto.
     */
    @Override
    public String toString() {
        return "Produto: {" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", valor unitário sem IVA=" + valorUnitSemIVA +
                '}';
    }

}