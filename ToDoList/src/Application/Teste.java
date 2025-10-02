package Application;

import java.time.LocalDate;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TarefaService Service =  new TarefaService();
        int escolha;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Criar tarefa comum");
            System.out.println("2 - Criar rotina");
            System.out.println("3 - Criar tarefa com prazo");
            System.out.println("4 - Listar tarefas");
            System.out.println("5 - Editar tarefa");
            System.out.println("6 - Concluir tarefa");
            System.out.println("7 - Excluir tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");


            escolha = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (escolha) {
                case 1 -> { // tarefa comum
                    System.out.print("ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();

                    Service.adicionarTarefa(new TarefaModel.TarefaComum(id, titulo, desc));

                }
                case 2 -> { // rotina 
                    System.out.print("ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();
                    System.out.print("Frequência: ");
                    String freq = sc.nextLine();

                    Service.adicionarTarefa(new TarefaModel.TarefaRotina(id, titulo, desc, freq));                    
                }
                case 3 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine();

                    System.out.print("Ano do prazo: ");
                    int ano = sc.nextInt();
                    System.out.print("Mês do prazo: ");
                    int mes = sc.nextInt();
                    System.out.print("Dia do prazo: ");
                    int dia = sc.nextInt();

                    LocalDate prazo = LocalDate.of(ano, mes, dia);

                    Service.adicionarTarefa(new TarefaModel.TarefaComPrazo(id, titulo, desc, prazo));

                }
                case 4 -> { // listar tarefas
                    Service.listarTarefas();
                }
                case 5 -> { // editar tarefas
                    System.out.print("ID da tarefa: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Nova descrição: ");
                    String desc = sc.nextLine();

                    Service.editarTarefas(id, titulo, desc);
                }
                case 6 -> { // concluir tarefas
                     System.out.print("ID da tarefa: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Service.concluirTarefas(id);
                }
                case 7 -> {
                    System.out.print("ID da tarefa: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Service.excluirTarefas(id);
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (escolha != 0);

        sc.close();
    }
}
