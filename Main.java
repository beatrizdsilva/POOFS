import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principal do sistema POOFS, que fornece um menu interativo para
 * gerir clientes, faturas e produtos, além de funcionalidades de importação
 * e exportação de dados.
 */
public class Main {
    public static void main(String[] args) {
        // Instância do sistema principal
        SistemaPOOFS sistemaPoofs = new SistemaPOOFS();

        // Scanner para entrada do utlizador
        Scanner sc = new Scanner(System.in);

        // Arquivo para serialização de objetos
        String arquivoObj = "arquivo.obj";
        File arquivoObjFile = new File(arquivoObj);
        sistemaPoofs.carregarDadosObj(arquivoObjFile);

        int opcao = 0;

        while (opcao != 12) {
            // Menu principal
            System.out.println("""
                    === Sistema POOFS ===
                    1. Criar Cliente
                    2. Editar Cliente
                    3. Listar Clientes
                    4. Criar Fatura
                    5. Editar Fatura
                    6. Listar Produtos disponíveis
                    7. Listar Faturas
                    8. Visualizar fatura
                    9. Importar Faturas contidas num ficheiro de texto
                    10. Exportar faturas para um ficheiro de texto
                    11. Estatísticas
                    12. Sair
                    """);
            System.out.print("Escolha uma opção: ");

            String entrada = sc.nextLine().trim();

            // Verifica se a entrada é um número
            if (entrada.matches("\\d+")) {
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1:
                        sistemaPoofs.criarCliente();
                        break;
                    case 2:
                        sistemaPoofs.editarCliente();
                        break;
                    case 3:
                        sistemaPoofs.listarClientes();
                        break;
                    case 4:
                        sistemaPoofs.criarFatura();
                        break;
                    case 5:
                        sistemaPoofs.editarFatura();
                        break;
                    case 6:
                        sistemaPoofs.listarProdutos();
                        break;
                    case 7:
                        sistemaPoofs.listarFaturas();
                        break;
                    case 8:
                        Fatura faturaSelecionada = null;
                        while (faturaSelecionada == null) {
                            System.out.println("Digite o número da fatura para visualizar:");
                            String entrada2 = sc.nextLine().trim();
                            if (!entrada2.matches("\\d+")) {
                                System.out.println("Entrada inválida! Por favor, insira um número válido.");
                                continue;
                            }
                            int numeroFatura = Integer.parseInt(entrada2);
                            faturaSelecionada = sistemaPoofs.buscarFaturaPorNumero(numeroFatura);

                            if (faturaSelecionada == null) {
                                System.out.println("Fatura com número " + numeroFatura + " não encontrada.");
                            }
                        }

                        if (faturaSelecionada != null) {
                            sistemaPoofs.visualizarFatura(faturaSelecionada);
                        }
                        break;
                    case 9:
                        System.out.println("Digite o caminho para importar faturas contidas num ficheiro de texto: ");
                        String importarFatura = sc.nextLine().trim();
                        File fileImportarFaturas = new File(importarFatura);
                        sistemaPoofs.importarFatura(fileImportarFaturas);
                        break;
                    case 10:
                        System.out.println("Digite o caminho para exportar faturas para um ficheiro de texto:");
                        String fileExportarFatura = sc.nextLine().trim();
                        File fileExportar = new File(fileExportarFatura);
                        sistemaPoofs.exportarFatura(fileExportar);
                        break;
                    case 11:
                        System.out.println("Exibindo as Estatísticas: ");
                        sistemaPoofs.mostrarEstatisticas();
                        break;
                    case 12:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Número inválido!");
                        break;
                }
            } else {
                System.out.println("Entrada inválida! Insira um número entre 1 e 12.");
            }
        }
        // Salvar os dados no arquivo .obj
        sistemaPoofs.salvarDadosObj(arquivoObjFile);
        sc.close();
    }
}
