import java.io.Serializable;

/**
 * A classe Cliente representa um cliente com informações básicas como
 * nome, NIF e localização. Implementa Serializable para permitir
 * a serialização de objetos desta classe.
 */

public class Cliente implements Serializable {

    /**
     * O nome do cliente.
     */
    protected String nome;

    /**
     * O NIF do cliente. Representado como uma String, pois contém 9 dígitos.
     */
    protected String nif;

    /**
     * A localização do cliente.
     * Localizações válidas: Continente, Madeira, Açores.
     */
    protected String localizacaoCliente;

    /**
     * Construtor vazio da classe Cliente.
     */
    public Cliente() {};                                    //Construtor vazio

    /**
     * Construtor da classe Cliente que inicializa os atributos com valores fornecidos.
     *
     * @param nome Nome do cliente.
     * @param nif NIF do cliente (9 dígitos).
     * @param localizacaoCliente Localização do cliente (Continente, Madeira, Açores).
     */
    public Cliente(String nome, String nif, String localizacaoCliente){
        this.nome = nome;
        this.nif = nif;
        this.localizacaoCliente = localizacaoCliente;
    }

    /**
     * Converte a localização do cliente num índice numérico.
     *
     * @return O índice correspondente à localização:
     *         0 para "Continente", 1 para "Madeira", 2 para "Açores" ou "Acores".
     *         Retorna -1 se a localização for inválida.
     */
    protected int localizacaoToIndex() {
        if ("Continente".equalsIgnoreCase(localizacaoCliente)) {
            return 0;
        } else if ("Madeira".equalsIgnoreCase(localizacaoCliente)) {
            return 1;
        } else if ("Açores".equalsIgnoreCase(localizacaoCliente) || "Acores".equalsIgnoreCase(localizacaoCliente)) {
            return 2;
        }
        return -1; //Para localização inválida
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     *
     * @param nome O nome do cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o NIF do cliente.
     *
     * @return O NIF do cliente.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Define o NIF do cliente.
     *
     * @param nif O NIF do cliente (deve conter 9 dígitos).
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtém a localização do cliente.
     *
     * @return A localização do cliente (pode ser "Continente", "Madeira" ou "Açores").
     */
    public String getLocalizacaoCliente() {
        return localizacaoCliente;
    }

    /**
     * Define a localização do cliente.
     *
     * @param localizacaoCliente A localização do cliente.
     *                           Localizações válidas: "Continente", "Madeira", "Açores".
     */
    public void setLocalizacaoCliente(String localizacaoCliente) {
        this.localizacaoCliente = localizacaoCliente;
    }

    /**
     * Retorna uma representação em String dos atributos do cliente.
     *
     * @return Uma string contendo os valores de nome, NIF e localização do cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                ", localizacaoCliente=" + localizacaoCliente +
                '}';
    }
}