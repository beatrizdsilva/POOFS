import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

/**
 * Sistema que gere clientes, faturas e produtos.
 * Permite criar clientes, adicionar produtos, entre outros.
 */
public class SistemaPOOFS implements Serializable {
    /**
     * Lista de clientes registados no sistema.
     */
    protected ArrayList<Cliente> listaClientes;

    /**
     * Lista de faturas no sistema.
     */
    protected ArrayList<Fatura> listaFaturas;

    /**
     * Lista de produtos disponíveis no sistema.
     */
    protected ArrayList<Produto> listaProdutos;

    /**
     * Construtor do sistema, inicializando as listas de clientes, faturas e produtos.
     * Também inicializa as taxas padrão para produtos alimentares e produtos de farmácia.
     */
    public SistemaPOOFS() {
        listaClientes = new ArrayList<>();
        listaFaturas = new ArrayList<>();
        listaProdutos = new ArrayList<>();

        // Definição das taxas padrão para as localizações
        double[] taxasNormal = {23, 22, 16};        // Index: 0 (Continente), 1 (Madeira), 2 (Açores e Acores)
        double[] taxasIntermedia = {13, 12, 9};     // Index: 0 (Continente), 1 (Madeira), 2 (Açores e Acores)
        double[] taxasReduzida = {6,5,4};           // Index: 0 (Continente), 1 (Madeira), 2 (Açores e Acores)

        // Definição das taxas para produtos de farmácia
        double[] taxasComPrescricao = {6, 5, 4};
        double[] taxasSemPrescricao = {23, 23,23};


        // Criar produtos para teste
        Produto p1 = new ProdutoAlimentarTaxaReduzida("Maça","eh bio!",10,1.20,true,new String[]{"ISO22000", "HACCP"},taxasReduzida);
        Produto p2 = new ProdutoAlimentarTaxaIntermedia("Vinho Tinto","Caixa de 5 litros", 5, 10.00,false,"vinho",taxasIntermedia);
        Produto p3 = new ProdutoAlimentarTaxaNormal("Refrigerante","Lata de refri",30,1.00,false,taxasNormal);

        // Criar produtos de farmácia para teste
        Produto p4 = new ProdutoFarmaciaComPrescricao("Brufen","5mg",10,2.00,"Rita",taxasComPrescricao);
        Produto p5 = new ProdutoFarmaciaSemPrescricao("Mucus","50g",5,1.50,"animais",taxasSemPrescricao);

        listaProdutos.add(p1);
        listaProdutos.add(p2);
        listaProdutos.add(p3);
        listaProdutos.add(p4);
        listaProdutos.add(p5);
    }

    /**
     * Cria um novo cliente, solicitando informações como nome, número de contribuinte e localização.
     */
    protected void criarCliente(){
        Scanner scanner = new Scanner(System.in);

        //Solicita e valida o nome do cliente
        String nome = lerNomeValido(scanner);

        //Solicita e valida numero de contribuinte
        String nif = lerNif(scanner);

        //Solicita e valida a localizacao
        String localizacao = lerLocalizacaoValida(scanner);

        //Após todas as validações, cria o Cliente
        Cliente cliente = new Cliente(nome,nif,localizacao);
        adicionarCliente(cliente);

        System.out.println("Cliente criado com sucesso!");
    }

    /**
     * Lê e valida o nome do cliente, garantindo que contém apenas letras e espaços.
     *
     * @param scanner O objeto Scanner para ler as entradas do usuário.
     * @return O nome válido do cliente.
     */
    private String lerNomeValido(Scanner scanner){
        System.out.println("Digite o nome do cliente (apenas letras e espaços): ");
        String nome = scanner.nextLine().trim();

        while(!nomeValido(nome)){
            System.out.println("Nome inválido! Usar apenas letras e espaços.");
            System.out.println("Digite o nome do cliente novamente:");
            nome = scanner.nextLine().trim();
        }
        return nome;
    }

    /**
     * Verifica se o nome contém apenas letras e espaços.
     *
     * @param nome O nome do cliente.
     * @return true se o nome for válido, caso contrário, false.
     */
    private boolean nomeValido(String nome){
        int tamanho_nome =2;
        if (nome.length() < tamanho_nome) return false;
        for(char c: nome.toCharArray()){
            if(!Character.isLetter(c) && c != ' '){
                return false;       //Nome inválido
            }
        }
        return true;
    }

    /**
     * Lê e valida o número de contribuinte (NIF) do cliente.
     *
     * @param scanner O objeto Scanner para ler as entradas do usuário.
     * @return O NIF válido do cliente.
     */
    private String lerNif(Scanner scanner){
        System.out.println("Digite o nif (9 digitos): ");
        String nif = scanner.nextLine();

        while(!nifValido(nif) || existeNif(nif)){
            if(!nifValido(nif)){
                System.out.println("Numero de contribuinte inválido! Deve conter 9 digitos.");
            }
            else{
                //Caso contrario significa que nao foi aceiti pois já existe um nif igual de outro cliente
                System.out.println("Número de contribuinte já atribuído.");
            }
            System.out.println("Digite o número de contribuinte novamente: ");
            nif = scanner.nextLine();
        }
        return nif;
    }

    /**
     * Verifica se o NIF contém exatamente 9 dígitos.
     *
     * @param nif O número de contribuinte a ser validado.
     * @return true se o NIF for válido, caso contrário, false.
     */
    private boolean nifValido(String nif){
        int tamanho = 9;
        // Caso o nif inserido nao tenha comprimento 9
        if(nif.length() != tamanho){
            return false;
        }

        // Verifica se todos os caracteres são dígitos
        for(char c : nif.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se o NIF já existe na lista de clientes.
     *
     * @param nif O número de contribuinte a ser verificado.
     * @return true se o NIF já existir, caso contrário, false.
     */
    private boolean existeNif(String nif){
        for(Cliente cliente : listaClientes){
            if (cliente.getNif().equals(nif)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lê e valida a localização do cliente, permitindo apenas três opções válidas:
     * Continente, Madeira e Açores.
     *
     * @param scanner O objeto Scanner para ler as entradas do usuário.
     * @return A localização válida do cliente.
     */
    private String lerLocalizacaoValida(Scanner scanner){
        System.out.println("Digite a localização do Cliente (Continente,Madeira,Açores):");
        String localizacao = scanner.nextLine();

        while(!localizacaoValida(localizacao)){
            System.out.println("Localização inválida! Escolha entre Continente,Madeira e Açores");
            System.out.println("Digite a localização novamente:");
            localizacao = scanner.nextLine();
        }
        return localizacao;
    }

    /**
     * Verifica se a localização é uma das opções válidas.
     *
     * @param localizacao A localização a ser validada.
     * @return true se a localização for válida, caso contrário, false.
     */
    private boolean localizacaoValida(String localizacao){
        return localizacao.trim().equalsIgnoreCase("Continente")
                || localizacao.trim().equalsIgnoreCase("Madeira")
                || localizacao.trim().equalsIgnoreCase("Açores")
                || localizacao.trim().equalsIgnoreCase("Acores");
    }

    /**
     * Edita as informações de um cliente existente. O cliente é encontrado
     * através do número de contribuinte (NIF).
     */
    protected void editarCliente(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o numero de contribuinte do cliente a editar: ");
        String nif = scanner.nextLine();

        Cliente cliente = buscarClientePorContribuinte(nif);

        if(cliente != null){
            System.out.println("Editar nome (atual: " + cliente.getNome() +"):" );
            String nome = lerNomeValido(scanner);
            if(!nome.isEmpty()){
                cliente.setNome(nome);
            }else{
                cliente.setNome(cliente.getNome());
            }

            System.out.println("Editar localização (atual: " + cliente.getLocalizacaoCliente() +"):" );
            String localizacao = lerLocalizacaoValida(scanner);
            if(!localizacao.isEmpty()){
                cliente.setLocalizacaoCliente(localizacao);
            }else{
                cliente.setLocalizacaoCliente(cliente.getLocalizacaoCliente());
            }

            //Mensagem para conclusao da operacao com sucesso!
            System.out.println("Cliente atualizado com sucesso!");
        }
        else{
            System.out.println("Cliente não encontrado.");
        }
    }

    /**
     * Procura um cliente na lista de clientes pelo número de contribuinte (NIF).
     *
     * @param nif O número de contribuinte do cliente.
     * @return O cliente correspondente ao NIF ou null se não encontrado.
     */
    private Cliente buscarClientePorContribuinte(String nif) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNif().equals(nif)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Lista todos os clientes registados no sistema.
     * Se não houver clientes, exibe uma mensagem com essa informação.
     */
    protected void listarClientes(){
        if(listaClientes.isEmpty()){
            System.out.println("Nenhum cliente registado.");
        }
        else{
            System.out.println("Lista de Clientes:");
            for(Cliente cliente : listaClientes){
                System.out.println(cliente);
                System.out.println("----------------------------");
            }
        }
    }

    /**
     * Adiciona um cliente à lista de clientes.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }

    //************************ FATURA PRODUTOS ************************
    /**
     * Cria uma fatura associada a um cliente. A fatura é preenchida com produtos
     * e, após ser criada, é salva no sistema.
     */
    protected void criarFatura() {
        Scanner scanner = new Scanner(System.in);

        // Associar a fatura a um cliente
        System.out.println("Digite o número de contribuinte do cliente:");
        String numContribuinte = scanner.nextLine();
        Cliente cliente = buscarClientePorContribuinte(numContribuinte);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Não é possível criar a fatura.");
            return;
        }

        Date data = new Date();

        // Criar a fatura
        Fatura fatura = new Fatura(cliente, data, new ArrayList<>());

        // Adicionar produtos à fatura (pelo menos 1!)
        while(fatura.getProdutos().isEmpty()) {
            System.out.println("Adicione produtos!");
            adicionarProdutosFatura(fatura);

            if (fatura.getProdutos().isEmpty()) {
                System.out.println("Nenhum produto foi adicionado. Por favor, adicione ao menos 1 produto.");
            }
        }

        // Salvar a fatura no sistema
        listaFaturas.add(fatura);
        System.out.println("Fatura criada com sucesso!");
        System.out.println("Detalhes da fatura: "+fatura);
    }

    /**
     * Edita uma fatura existente com base no número da fatura inserido pelo utilizador.
     * Permite modificar informações como cliente, data e produtos na fatura.
     */
    protected void editarFatura() {
        Scanner scanner = new Scanner(System.in);
        int numeroFatura = 0;
        boolean entradaValida = false;

        // Solicitar e validar o número da fatura a ser editada
        while (!entradaValida) {
            System.out.println("Digite o número da fatura que deseja editar:");
            String entrada = scanner.nextLine().trim();

            if (entrada.matches("\\d+")) {
                numeroFatura = Integer.parseInt(entrada);
                entradaValida = true;
            } else {
                System.out.println("Entrada inválida! Por favor, insira apenas números.");
            }
        }

        Fatura fatura = buscarFaturaPorNumero(numeroFatura);

        if (fatura == null) {
            System.out.println("Fatura não encontrada.");
            return;
        }

        // Menu de edição da fatura
        int opcao = 0;
        while (opcao != 6) {
            System.out.println("""
                    === Editar Fatura ===
                    1. Alterar Cliente
                    2. Alterar Data
                    3. Adicionar Produtos
                    4. Remover Produtos
                    5. Editar Produtos
                    6. Sair
                    """);
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine().trim();

            if (entrada.matches("\\d+")) {
                opcao = Integer.parseInt(entrada);
                switch (opcao) {
                    case 1:
                        alterarClienteFatura(fatura);
                        break;
                    case 2:
                        alterarDataFatura(fatura);
                        break;
                    case 3:
                        adicionarProdutosFatura(fatura);
                        break;
                    case 4:
                        removerProdutosFatura(fatura);
                        break;
                    case 5:
                        editarProdutosFatura(fatura);
                        break;
                    case 6:
                        System.out.println("Voltando...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }


            } else {
                //Caso a opcao inserida nao seja um numero inteiro
                System.out.println("Entrada inválida. Insira um número inteiro!");
            }
        }
    }

    /**
     * Altera a data da fatura para uma nova data fornecida pelo utilizador.
     * A data é validada para garantir que está no formato correto (DD/MM/YYYY)
     * e dentro de um intervalo aceitável de anos.
     *
     * @param fatura A fatura a ser alterada.
     */
    private void alterarDataFatura(Fatura fatura) {
        Scanner scanner = new Scanner(System.in);
        boolean dataValida = false;

        while (!dataValida) {
            System.out.println("Digite a nova data (formato DD/MM/YYYY):");
            String novaDataStr = scanner.nextLine().trim();

            try {
                //Converte a string inserida para objeto Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);//Ativa validação estrita, o que rejeita qualquer entrada de dia e mes invalidos!
                Date novaData = sdf.parse(novaDataStr);

                //Validação para anos num intervalo aceitável
                String[] partes = novaDataStr.split("/");
                int ano = Integer.parseInt(partes[2]);
                int min_ano = 1900, max_ano= 2024;
                if (ano < min_ano || ano > max_ano)
                    throw new IllegalArgumentException("Ano fora do intervalo permitido.");

                //Se tudo estiver válido, altera a data da fatura
                fatura.setData(novaData);
                System.out.println("Data alterada com sucesso!");
                dataValida = true;

            } catch (ParseException e) {
                System.out.println("Erro ao analisar a data: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    /**
     * Altera o cliente associado à fatura para um novo cliente identificado pelo NIF.
     *
     * @param fatura A fatura a ser alterada.
     */
    private void alterarClienteFatura(Fatura fatura) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o NIF do novo cliente:");
        String nif = scanner.nextLine().trim();
        Cliente novoCliente = buscarClientePorContribuinte(nif);

        if (novoCliente != null) {
            fatura.setCliente(novoCliente);
            System.out.println("Cliente alterado com sucesso!");
        } else {
            System.out.println("Cliente com NIF " + nif + " não encontrado.");
        }
    }

    /**
     * Permite editar os produtos da fatura, alterando a quantidade de um produto existente.
     * O utilizador deve informar o código do produto para edição.
     *
     * @param fatura A fatura que contém os produtos a serem editados.
     */
    private void editarProdutosFatura(Fatura fatura) {
        Scanner scanner = new Scanner(System.in);

        if (fatura.getProdutos().isEmpty()) {
            System.out.println("Nenhum produto na fatura para editar.");
            return;
        }

        System.out.println("Produtos na fatura:");
        for (Produto produto : fatura.getProdutos()) {
            System.out.println(produto);
        }

        System.out.println("Digite o código do produto para editar ou 'sair' para voltar:");
        String entrada = scanner.nextLine().trim();

        if (entrada.equalsIgnoreCase("sair")) {
            return;
        }

        try {
            int codigo = Integer.parseInt(entrada);
            Produto produto = buscarProdutoNaFatura(fatura, codigo);

            if (produto != null) {
                System.out.println("Produto selecionado: " + produto);
                System.out.println("Digite a nova quantidade:");
                int novaQuantidade = Integer.parseInt(scanner.nextLine().trim());

                if (novaQuantidade > 0) {
                    produto.setQuantidade(novaQuantidade);
                    System.out.println("Quantidade alterada com sucesso!");
                } else {
                    System.out.println("A quantidade deve ser maior que zero.");
                }
            } else {
                System.out.println("Produto com o código " + codigo + " não encontrado na fatura.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Certifique-se de digitar um número.");
        }
    }

    /**
     * Busca um produto dentro de uma fatura com base no código do produto.
     *
     * @param fatura A fatura onde o produto será procurado.
     * @param codigo O código do produto a ser procurado.
     * @return O produto correspondente ao código, ou null se não encontrado.
     */
    private Produto buscarProdutoNaFatura(Fatura fatura, int codigo) {
        for (Produto produto : fatura.getProdutos()) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    /**
     * Remove um produto da fatura com base no código do produto inserido pelo utilizador.
     * Se o produto não for encontrado na fatura, uma mensagem de erro será exibida.
     *
     * @param fatura A fatura de onde o produto será removido.
     */
    private void removerProdutosFatura(Fatura fatura) {
        Scanner scanner = new Scanner(System.in);

        if (fatura.getProdutos().isEmpty()) {
            System.out.println("Nenhum produto na fatura para remover.");
            return;
        }

        if (fatura.getProdutos().size() == 1) {
            System.out.println("A fatura só contém um produto. Não é possível removê-lo.");
            return;
        }

        System.out.println("Produtos na fatura:");
        for (Produto produto : fatura.getProdutos()) {
            System.out.println(produto);
        }

        //System.out.println("Digite o código do produto a remover:");
        //int codigo = scanner.nextInt();
        //scanner.nextLine(); // Consumir quebra de linha
        int codigo = 0;
        boolean entradaValida = false;

        // Solicitar e validar o número da fatura a ser editada
        while (!entradaValida) {
            System.out.println("Digite o código do produto a remover:");
            String entrada = scanner.nextLine().trim();

            if (entrada.matches("\\d+")) {
                codigo = Integer.parseInt(entrada);
                entradaValida = true;
            } else {
                System.out.println("Entrada inválida! Por favor, insira apenas números.");
            }
        }

        Produto produtoARemover = null;
        for (Produto produto : fatura.getProdutos()) {
            if (produto.getCodigo() == codigo) {
                produtoARemover = produto;
                break;
            }
        }

        if (produtoARemover != null) {
            if (fatura.getProdutos().size() > 1) {
                fatura.getProdutos().remove(produtoARemover);
                System.out.println("Produto removido com sucesso.");
            } else
                System.out.println("Não é possível remover o produto. A fatura precisa ter pelo menos um produto.");
        } else
            System.out.println("Produto não encontrado na fatura.");

    }

    /**
     * Adiciona produtos a uma fatura com base no código do produto fornecido pelo utilizador.
     * O utilizador pode adicionar múltiplos produtos até inserir "sair".
     *
     * @param fatura A fatura onde os produtos serão adicionados.
     */
    private void adicionarProdutosFatura(Fatura fatura) {
        Scanner scanner = new Scanner(System.in);
        String opcao;

        do{
            System.out.println("Produtos disponíveis:");
            listarProdutos();

            System.out.println("Digite o código do produto a adicionar ou 'sair' para finalizar:");
            opcao = scanner.nextLine().trim();

            if (opcao.equalsIgnoreCase("sair")) {
                System.out.println("Voltando ao menu anterior...");
                break;
            }

            try {
                // Tenta converter a entrada para um número inteiro
                int codigo = Integer.parseInt(opcao);

                Produto produto = buscarProdutoPorCodigo(codigo);

                if (produto != null) {
                    if(fatura.getProdutos().contains(produto)){
                        System.out.println("Este produto já está na fatura.");
                    } else{
                        fatura.addProduto(produto);
                        System.out.println("Produto adicionado com sucesso.");
                    }
                } else {
                    System.out.println("Produto com o código " + codigo + " não encontrado.");
                }
            } catch (NumberFormatException e) {
                // Para entradas não numéricas
                System.out.println("Entrada inválida. Por favor, digite um número válido ou 'sair'.");
            }
        } while(true);
    }

    /**
     * Procura um produto na lista de produtos disponíveis com base no código do produto.
     *
     * @param codigo O código do produto a ser procurado.
     * @return O produto correspondente ao código, ou null se não encontrado.
     */
    private Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto produto : listaProdutos) {
            if (produto.getCodigo() == codigo)
                return produto;
        }
        return null;
    }

    /**
     * Lista todos os produtos disponíveis na aplicação.
     * Caso não haja produtos registados, uma mensagem será exibida com essa informação.
     */
    protected void listarProdutos() {
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto disponível.");
            return;
        }

        System.out.println("Listar produtos:");
        for (Produto produto : listaProdutos) {
            System.out.println(produto);
        }
    }

    /**
     * Procura uma fatura na lista de faturas com base no número da fatura.
     *
     * @param numeroFatura O número da fatura a ser procurada.
     * @return A fatura correspondente ao número, ou null se não encontrada.
     */
    protected Fatura buscarFaturaPorNumero(int numeroFatura) {
        for (Fatura fatura : listaFaturas) {
            if (fatura.getNumeroFatura() == numeroFatura) {
                return fatura;
            }
        }
        return null;
    }

    /**
     * Lista todas as faturas registadas, exibindo informações como número da fatura,
     * cliente, localização, número de produtos e valor total com e sem IVA.
     * Caso não haja faturas registadas, uma mensagem será exibida com essa informação.
     */
    protected void listarFaturas(){
        if(listaFaturas.isEmpty()){
            System.out.println("Nenhum fatura encontrada");
            return;
        }

        System.out.println(" === Listando as Faturas ===");
        for (Fatura fatura : listaFaturas) {
            int numProdutos = fatura.getProdutos().size();
            double totalSemIVA = fatura.calcularTotalSemIVA();
            double totalComIVA = fatura.calcularTotalComIVA(fatura);

            System.out.println("Número da Fatura: " + fatura.getNumeroFatura() +
                    "\nCliente: " + fatura.getCliente().getNome()+
                    "\nLocalização: " + fatura.getCliente().getLocalizacaoCliente()+
                    "\nNúmero de produtos: " + numProdutos +
                    "\nValor Total Sem IVA: " + String.format("%.2f", totalSemIVA) +
                    "\nValor Total Com IVA: " + String.format("%.2f", totalComIVA));
            System.out.println("-------------------------");
        }
    }

    /**
     * Exibe os detalhes de uma fatura, incluindo informações do cliente e dos produtos.
     * Para cada produto, exibe o valor sem IVA, a taxa de IVA, o valor do IVA e o valor total com IVA.
     * Também apresenta os totais da fatura (sem IVA, IVA e com IVA).
     *
     * @param fatura A fatura a ser visualizada.
     */
    protected void visualizarFatura(Fatura fatura){
        System.out.println("=== Visualizando Fatura ===");
        // Número da fatura
        System.out.println("Numero da fatura: " + fatura.getNumeroFatura()+"\n");

        // Dados do cliente
        Cliente cliente = fatura.getCliente();
        System.out.println("Dados do cliente: ");
        System.out.println("Nome: " + cliente.getNome()
                + "     NIF: " + cliente.getNif()
                + "     Localização: " + cliente.getLocalizacaoCliente()+"\n");

        // Para cada produto da fatura
        System.out.println("---- Produtos ----");
        double totalSemIVA = 0.0;
        double totalIVA = 0.0;
        double totalComIVA = 0.0;

        for (Produto produto : fatura.getProdutos()) {
            System.out.println(produto);

            // Valor sem IVA
            double valorSemIVA = produto.calcularValorSemIVA();
            System.out.printf("Valor Total Sem IVA: %.2f \n" ,  valorSemIVA);

            // Valor com IVA
            double valorComIVA = produto.calcularValorComIVA(cliente);
            double valorIVA = valorComIVA - valorSemIVA;        //valor do IVA
            double taxaIVA = (valorIVA / valorSemIVA) * 100;    //taxa do IVA (%)

            System.out.printf("Taxa do IVA: %.2f%%\n", taxaIVA);
            System.out.printf("Valor do IVA: %.2f\n", valorIVA);
            System.out.printf("Valor Total Com IVA: %.2f\n", valorComIVA);
            System.out.println("-------------------------");

            // Acumula nos totais
            totalSemIVA += valorSemIVA;
            totalIVA += valorIVA;
            totalComIVA += valorComIVA;
        }

        // Totais da fatura: totalSemIVA, valorTotal do IVA e valorTotal com IVA
        System.out.println("\n---- Totais da Fatura ----");
        System.out.println("Valor total sem IVA: " + String.format("%.2f", totalSemIVA));
        System.out.println("Valor total do IVA: " + String.format("%.2f", totalIVA));
        System.out.println("Valor total com IVA: " + String.format("%.2f", totalComIVA) + "\n");
    }

    /**
     * Salva os dados dos clientes e faturas num arquivo de objetos.
     *
     * @param arquivo O arquivo onde os dados serão salvos.
     */
    protected void salvarDadosObj(File arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(listaClientes);
            oos.writeObject(listaFaturas);
            //oos.writeObject(listaProdutos);
            System.out.println("Dados salvos em " + arquivo + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao criar o ficheiro: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Permissão negada para acessar o ficheiro: " + e.getMessage());
        } catch (InvalidClassException e) {
            System.out.println("Erro de compatibilidade ao salvar dados: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao escrever no ficheiro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega os dados de clientes e faturas de um arquivo de objetos.
     * Atualiza o contador do número das faturas com base nos dados carregados.
     *
     * @param arquivo O arquivo de onde os dados serão carregados.
     */
    protected void carregarDadosObj(File arquivo) {
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                listaClientes = (ArrayList<Cliente>) ois.readObject();
                listaFaturas = (ArrayList<Fatura>) ois.readObject();

                //Atualizar o contador de número de faturas
                int maiorNumeroFatura = 0;
                for (Fatura fatura : listaFaturas) {
                    if (fatura.getNumeroFatura() > maiorNumeroFatura) {
                        maiorNumeroFatura = fatura.getNumeroFatura();
                    }
                }
                Fatura.setContadorNumeroFatura(maiorNumeroFatura + 1); //Ajustar o contador para o próximo número

                System.out.println("Dados carregados com sucesso de " + arquivo);
            } catch (FileNotFoundException e) {
                System.out.println("Ficheiro não encontrado. Reiniciando com listas vazias.");
                listaClientes = new ArrayList<>();
                listaFaturas = new ArrayList<>();
            } catch (EOFException e) {
                System.out.println("Erro: O ficheiro está vazio ou incompleto. Reiniciando dados.");
                listaClientes = new ArrayList<>();
                listaFaturas = new ArrayList<>();
            } catch (StreamCorruptedException e) {
                System.out.println("Erro: Ficheiro corrompido. Reiniciando dados.");
                listaClientes = new ArrayList<>();
                listaFaturas = new ArrayList<>();
            } catch (ClassNotFoundException e) {
                System.out.println("Erro ao converter objetos: " + e.getMessage());
                listaClientes = new ArrayList<>();
                listaFaturas = new ArrayList<>();
            } catch (SecurityException e) {
                System.out.println("Permissão negada para acessar o ficheiro: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro ao carregar dados: " + e.getMessage());
                listaClientes = new ArrayList<>();
                listaFaturas = new ArrayList<>();
            } catch (Exception e) {
                System.out.println("Erro inesperado ao carregar dados: " + e.getMessage());
                listaClientes = new ArrayList<>();
                listaFaturas = new ArrayList<>();
            }
        }
    }

    /**
     * Importa faturas de um arquivo de texto. O arquivo deve conter os dados formatados corretamente.
     * Se o arquivo não estiver no formato correto ou se algum erro ocorrer, a fatura será ignorada.
     *
     * @param arquivo O arquivo de onde as faturas serão importadas.
     */
    protected void importarFatura(File arquivo) {
        if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
            System.out.println("Erro: Apenas ficheiros com extensão .txt são permitidos.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            Cliente clienteAtual = null;
            Fatura faturaAtual = null;
            ArrayList<Produto> produtosFatura = new ArrayList<>();
            int linhaAtual = 0;

            while ((linha = br.readLine()) != null) {
                linhaAtual++;
                linha = linha.trim();

                try {
                    // Processa o número da fatura
                    if (linha.startsWith("Número da Fatura:")) {
                        if (faturaAtual != null) {
                            if (!faturaJaExiste(faturaAtual.getNumeroFatura())) {
                                faturaAtual.setProdutos(produtosFatura);
                                listaFaturas.add(faturaAtual);
                            } else {
                                System.out.println("Fatura com número " + faturaAtual.getNumeroFatura() + " já existe. Ignorada.");
                            }
                            produtosFatura = new ArrayList<>();
                        }

                        String[] partes = linha.split(":");
                        if (!partes[1].trim().matches("\\d+")) {// se o numero da fatura nao for inteiro
                            throw new IllegalArgumentException("Número da fatura mal formatado.");
                        }

                        int numeroFatura = Integer.parseInt(partes[1].trim());
                        faturaAtual = new Fatura();
                        faturaAtual.setNumeroFatura(numeroFatura);
                        faturaAtual.setProdutos(new ArrayList<>());
                    }

                    // Processa a data
                    else if (linha.startsWith("Data:")) {
                        String dataStr = linha.split(":")[1].trim();
                        try {
                            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
                            faturaAtual.setData(data);
                        } catch (ParseException e) {
                            System.out.println("Linha " + linhaAtual + ": Data inválida. Usando data atual.");
                            faturaAtual.setData(new Date());
                        }
                    }

                    // Processa os dados do cliente
                    else if (linha.startsWith("Cliente:")) {
                        String[] partesCliente = linha.split(":")[1].trim().split(",");
                        if (partesCliente.length != 3) {
                            throw new IllegalArgumentException("Linha " + linhaAtual + ": Cliente mal formatado.");
                        }

                        String nome = partesCliente[0].trim();
                        String nif = partesCliente[1].trim();
                        String localizacao = partesCliente[2].trim();

                        if (!nif.matches("\\d{9}")) {
                            throw new IllegalArgumentException("Linha " + linhaAtual + ": NIF inválido.");
                        }

                        clienteAtual = buscarClientePorContribuinte(nif);
                        if (clienteAtual == null) {
                            clienteAtual = new Cliente(nome, nif, localizacao);
                            adicionarCliente(clienteAtual);
                        } else {
                            System.out.println("Linha " + linhaAtual + ": Cliente com NIF " + nif + " já existe. Ignorado.");
                        }
                        faturaAtual.setCliente(clienteAtual);
                    }

                    // Processa os produtos
                    else if (linha.startsWith("Tipo:")) {
                        try {
                            Produto produto = processarProduto(linha);
                            if (produto != null) {
                                produtosFatura.add(produto);
                            }
                        } catch (Exception e) {
                            System.out.println("Linha " + linhaAtual + ": Produto mal formatado. Ignorado.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro na linha " + linhaAtual + ": " + e.getMessage());
                }
            }

            // Salva a última fatura ao final
            if (faturaAtual != null) {
                if (!faturaJaExiste(faturaAtual.getNumeroFatura())) {
                    faturaAtual.setProdutos(produtosFatura);
                    listaFaturas.add(faturaAtual);
                } else {
                    System.out.println("Fatura com número " + faturaAtual.getNumeroFatura() + " já existe. Ignorada.");
                }
            }
            System.out.println("Processo finalizado!");

        }catch (FileNotFoundException e) {
            System.out.println("Erro: Ficheiro não encontrado. Verifique o caminho.");
        } catch (SecurityException e) {
            System.out.println("Erro: Permissão negada para acessar o ficheiro.");
        } catch (EOFException e) {
            System.out.println("Erro: O ficheiro está vazio ou incompleto.");
        } catch (IOException e) {
            System.out.println("Erro ao importar faturas: " + e.getMessage());
        }
    }


    /**
     * Processa uma linha de texto que representa um produto e cria uma instância do tipo apropriado.
     * O tipo de produto pode ser Taxa Reduzida, Taxa Intermediária, Taxa Normal, Farmácia com Prescrição
     * ou Farmácia sem Prescrição.
     *
     * @param linha A linha de texto contendo as informações do produto.
     * @return Uma instância do tipo de produto correspondente.
     * @throws Exception Se ocorrer algum erro ao processar a linha.
     */
    private Produto processarProduto(String linha) throws Exception {
        // Determina o tipo do produto pela palavra-chave "Tipo:"
        String tipo = linha.split(",")[0].split(":")[1].trim();
        String[] atributos = linha.split(",");

        // Processa Produto de Taxa Reduzida
        if (tipo.equalsIgnoreCase("Taxa Reduzida")) {
            int codigo = Integer.parseInt(atributos[1].split(":")[1].trim());
            String nome = atributos[2].split(":")[1].trim();
            String descricao = atributos[3].split(":")[1].trim();
            int quantidade = Integer.parseInt(atributos[4].split(":")[1].trim());
            double valorUnitSemIVA = Double.parseDouble(atributos[5].split(":")[1].trim());
            boolean bio = Boolean.parseBoolean(atributos[6].split(":")[1].trim());
            String[] certificacoes = atributos[7].split(":")[1].trim().split(",");

            return new ProdutoAlimentarTaxaReduzida(codigo, nome, descricao, quantidade, valorUnitSemIVA, bio, certificacoes, new double[]{6, 5, 4});
        }

        // Processa Produto de Taxa Intermediária
        if (tipo.equalsIgnoreCase("Taxa Intermédia")) {
            int codigo = Integer.parseInt(atributos[1].split(":")[1].trim());
            String nome = atributos[2].split(":")[1].trim();
            String descricao = atributos[3].split(":")[1].trim();
            int quantidade = Integer.parseInt(atributos[4].split(":")[1].trim());
            double valorUnitSemIVA = Double.parseDouble(atributos[5].split(":")[1].trim());
            boolean bio = Boolean.parseBoolean(atributos[6].split(":")[1].trim());
            String categoria = atributos[7].split(":")[1].trim();

            return new ProdutoAlimentarTaxaIntermedia(codigo, nome, descricao, quantidade, valorUnitSemIVA, bio, categoria, new double[]{13, 12, 9});
        }

        // Processa Produto de Taxa Normal
        if (tipo.equalsIgnoreCase("Taxa Normal")) {
            int codigo = Integer.parseInt(atributos[1].split(":")[1].trim());
            String nome = atributos[2].split(":")[1].trim();
            String descricao = atributos[3].split(":")[1].trim();
            int quantidade = Integer.parseInt(atributos[4].split(":")[1].trim());
            double valorUnitSemIVA = Double.parseDouble(atributos[5].split(":")[1].trim());
            boolean bio = Boolean.parseBoolean(atributos[6].split(":")[1].trim());

            return new ProdutoAlimentarTaxaNormal(codigo, nome, descricao, quantidade, valorUnitSemIVA, bio, new double[]{23, 22, 16});
        }

        // Processa Produto de Farmácia Com Prescrição
        if (tipo.equalsIgnoreCase("Farmácia Com Prescrição")) {
            int codigo = Integer.parseInt(atributos[1].split(":")[1].trim());
            String nome = atributos[2].split(":")[1].trim();
            String descricao = atributos[3].split(":")[1].trim();
            int quantidade = Integer.parseInt(atributos[4].split(":")[1].trim());
            double valorUnitSemIVA = Double.parseDouble(atributos[5].split(":")[1].trim());
            String nomeMedico = atributos[6].split(":")[1].trim();

            return new ProdutoFarmaciaComPrescricao(codigo, nome, descricao, quantidade, valorUnitSemIVA, nomeMedico, new double[]{6, 5, 4});
        }

        // Processa Produto de Farmácia Sem Prescrição
        if (tipo.equalsIgnoreCase("Farmácia Sem Prescrição")) {
            int codigo = Integer.parseInt(atributos[1].split(":")[1].trim());
            String nome = atributos[2].split(":")[1].trim();
            String descricao = atributos[3].split(":")[1].trim();
            int quantidade = Integer.parseInt(atributos[4].split(":")[1].trim());
            double valorUnitSemIVA = Double.parseDouble(atributos[5].split(":")[1].trim());
            String categoria = atributos[6].split(":")[1].trim();

            return new ProdutoFarmaciaSemPrescricao(codigo, nome, descricao, quantidade, valorUnitSemIVA, categoria, new double[]{23, 23, 23});
        }

        // Se o tipo não for reconhecido, lança uma exceção
        throw new IllegalArgumentException("Tipo de produto desconhecido: " + tipo);
    }

    /**
     * Verifica se uma fatura com o número fornecido já existe na lista de faturas.
     *
     * @param numeroFatura O número da fatura a ser verificado.
     * @return true se a fatura já existir, false caso contrário.
     */
    private boolean faturaJaExiste(int numeroFatura) {
        for (Fatura fatura : listaFaturas) {
            if (fatura.getNumeroFatura() == numeroFatura) {
                return true;
            }
        }
        return false;
    }

    /**
     * Exporta as faturas registadas para um ficheiro de texto. O ficheiro deve ter a extensão .txt.
     *
     * @param arquivo O ficheiro onde as faturas serão salvas.
     */
    protected void exportarFatura(File arquivo) {
        if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
            System.out.println("Erro: Apenas ficheiros com extensão .txt são permitidos.");
            return;
        }

        if (listaFaturas.isEmpty()) {
            System.out.println("Nenhuma fatura para exportar.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Fatura fatura : listaFaturas) {
                //Escreve os detalhes da fatura
                bw.write("Número da Fatura: " + fatura.getNumeroFatura());
                bw.newLine();
                bw.write("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(fatura.getData()));
                bw.newLine();
                bw.write("Cliente: " + fatura.getCliente().getNome() + ", " +
                        fatura.getCliente().getNif() + ", " +
                        fatura.getCliente().getLocalizacaoCliente());
                bw.newLine();

                //Escreve os produtos
                bw.write("Produtos:");
                bw.newLine();
                for (Produto produto : fatura.getProdutos()) {
                    bw.write(produto.toTexto()); //Utiliza o polimorfismo para indexar cada produto
                    bw.newLine();
                }
                bw.write("---");
                bw.newLine();
            }
            System.out.println("Faturas salvas com sucesso em " + arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar faturas: " + e.getMessage());
        }
    }

    /**
     * Exibe estatísticas sobre as faturas registadas no sistema, incluindo o número de faturas,
     * número de produtos, valores totais com e sem IVA.
     */
    protected void mostrarEstatisticas() {
        if (listaFaturas.isEmpty()) {
            System.out.println("Nenhuma fatura registrada. Estatísticas indisponíveis!");
            return;
        }

        int numeroFaturas = listaFaturas.size();
        int numeroProdutos = 0;
        double valorTotalSemIVA = 0.0;
        double valorTotalIVA = 0.0;
        double valorTotalComIVA = 0.0;

        for (Fatura fatura : listaFaturas) {
            numeroProdutos += fatura.getProdutos().size();

            double totalFaturaSemIVA = fatura.calcularTotalSemIVA();
            double totalFaturaComIVA = fatura.calcularTotalComIVA(fatura);
            double totalFaturaIVA = totalFaturaComIVA - totalFaturaSemIVA;

            valorTotalSemIVA += totalFaturaSemIVA;
            valorTotalIVA += totalFaturaIVA;
            valorTotalComIVA += totalFaturaComIVA;
        }

        //Exibindo as estatisticas
        System.out.println("=== Estatísticas ===");
        System.out.println("Número de Faturas: " + numeroFaturas);
        System.out.println("Número de Produtos: " + numeroProdutos);
        System.out.printf("Valor Total Sem IVA: %.2f\n", valorTotalSemIVA);
        System.out.printf("Valor Total do IVA: %.2f\n", valorTotalIVA);
        System.out.printf("Valor Total Com IVA: %.2f\n\n", valorTotalComIVA);
    }

    /**
     * Obtém a lista de clientes registados no sistema.
     *
     * @return a lista de clientes.
     */
    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    /**
     * Define a lista de clientes no sistema.
     *
     * @param listaClientes a nova lista de clientes.
     */
    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    /**
     * Obtém a lista de faturas registadas no sistema.
     *
     * @return a lista de faturas.
     */
    public ArrayList<Fatura> getListaFaturas() {
        return listaFaturas;
    }

    /**
     * Define a lista de faturas no sistema.
     *
     * @param listaFaturas a nova lista de faturas.
     */
    public void setListaFaturas(ArrayList<Fatura> listaFaturas) {
        this.listaFaturas = listaFaturas;
    }

    /**
     * Retorna uma representação textual do sistema, incluindo a lista de clientes e faturas.
     *
     * @return Uma string com a informação do sistema.
     */
    @Override
    public String toString() {
        return "Sistema POOFS: {" +
                "Lista Clientes=" + listaClientes +
                ", Lista Faturas=" + listaFaturas +
                '}';
    }

}