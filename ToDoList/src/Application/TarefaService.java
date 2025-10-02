package Application;
import java.util.ArrayList;
import java.util.List;
// este arquivo será o CRUD da aplicação
public class TarefaService {
    private List <TarefaModel.Tarefa> tarefas;
    
    public TarefaService(){
        this.tarefas = new ArrayList<>();
    }
    // criar tarefa
    public void adicionarTarefa(TarefaModel.Tarefa tarefa){
        tarefas.add(tarefa);
        System.out.println("✔ tarefa adicionada com sucesso!");
    }
    // ver tarefas
    public void listarTarefas(){
        if (tarefas.isEmpty()){
            System.out.println("Não tem tarefas cadastradas");
            return;
        }
        for (TarefaModel.Tarefa t : tarefas){
            t.exibirDetalhes();
            System.out.println("-----------------------------");
        }
    }
    // editar tarefas
    public void editarTarefas(int id, String novoTitulo, String novaDescricao){
        for (TarefaModel.Tarefa t : tarefas){
            if (t.getId() == id){
                t.setTitulo(novoTitulo);
                t.setDescricao(novaDescricao);
                System.out.println("Tarefa editada Com sucesso!");
                return;
            }
        }
        System.out.println("Tarefa não encontrada!");
    }
    // deletar tarefas
    public void excluirTarefas(int id){
       tarefas.removeIf(t -> t.getId() == id);
       System.out.println("Tarefa removida com sucesso!"); 
    }
    // concluir tarefas
    public void concluirTarefas(int id){
        for (TarefaModel.Tarefa t : tarefas){
            if (t.getId() == id){
                t.setConcluida(true);
                System.out.println("Tarefa concluida!");
                return;
            }
        }
        System.out.println("Tarefa não encontrada!");
    }
}
