/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author User
 */
public class App extends JFrame {
    //Declaração das variáveis que vão receber os campos de entrada:
    private JTextField campoTitulo;
    private JTextField campoDescricao;

    //Para selecionar o tipo de tarefa:
    private JComboBox<String> selectTipo;
    String[] tipos = {"Tarefa Comum", "Tarefa Prazo", "Tarefa Rotina"};

    //Para a tarefa do tipo PRAZO:
    private JSpinner campoData;

    //Para selecionar a frequencia da tarefa do tipo ROTINA:
    private JComboBox<String> selectFrequencia;
    String[] tiposFreq = {"Diária", "Mensal", "Anual", "Horária"};

    //Botão de adicionar a nova tarefa:
    private JButton botaoAdicionar;

    //Onde será guardada as tarefas criadas:
    private JTextArea listaTarefas;
    private ArrayList<TarefaModel.Tarefa> tarefas = new ArrayList<>();

    public App(){
        //Atribuindo valor aos campos titulo e descrição:
        campoTitulo = new JTextField(20);
        campoDescricao = new JTextField(20);

        //Atribuindo valor e seus valores que podem ser escolhidos no campo:
        selectTipo = new JComboBox<>(tipos);

        //Atribuindo valor e seus valores que podem ser escolhidos no campo:
        selectFrequencia = new JComboBox<>(tiposFreq);

        //Atribuindo valor ao campo Data e formatando ele para receber datas "dd/mm/yyyy":
        campoData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(campoData, "dd/MM/yyyy");
        campoData.setEditor(editor);

        //Atribuindo valor ao botão:
        botaoAdicionar = new JButton("Adicionar");

        //Atribuindo valor ao listaTarefas onde será mostrado as tarefas criadas:
        listaTarefas = new JTextArea(10, 30);
        listaTarefas.setEditable(false);

        //Container maior que vai receber o painelEntrada e painelEntrada2:
        JPanel painelSuperior = new JPanel();

        //Containers inferiores:
        JPanel painelEntrada = new JPanel();
        JPanel painelEntrada2 = new JPanel();

        //Adicionando os componentes ao painelEntrada:
        painelEntrada.add(new JLabel("Título:"));
        painelEntrada.add(campoTitulo);
        painelEntrada.add(new JLabel("Descrição:"));
        painelEntrada.add(campoDescricao);

        //Adicionando os componentes ao PainelEntrada2:
        painelEntrada2.add(new JLabel("Tipo:"));
        painelEntrada2.add(selectTipo);

        JLabel freqLabel = new JLabel("Frequência:");
        painelEntrada2.add(freqLabel);
        painelEntrada2.add(selectFrequencia);

        JLabel dataLabel = new JLabel("Data:");
        painelEntrada2.add(dataLabel);
        painelEntrada2.add(campoData);

        painelEntrada2.add(botaoAdicionar);

        //Aterando a VISIBILIDADE dos campos que só aparecem após escolher o TIPO de TAREFA:
        campoData.setVisible(false);
        dataLabel.setVisible(false);
        selectFrequencia.setVisible(false);
        freqLabel.setVisible(false);

        //Função para a lógica quando o valor do selectTipo for alterado para mudar a visibilade dos campos:
        selectTipo.addActionListener(e -> {
            campoData.setVisible(false);
            dataLabel.setVisible(false);
            selectFrequencia.setVisible(false);
            freqLabel.setVisible(false);

            String Tipo = (String) selectTipo.getSelectedItem();

            if(Tipo.equals("Tarefa Prazo")){
                campoData.setVisible(true);
                dataLabel.setVisible(true);
            }else if(Tipo.equals("Tarefa Rotina")){
                selectFrequencia.setVisible(true);
                freqLabel.setVisible(true);
            }
        });

        //Função para ler os campos preenchidos e criar as novas tarefas:
        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = campoTitulo.getText();
                String descricao = campoDescricao.getText();
                String Tipo = (String) selectTipo.getSelectedItem();
                if(!titulo.isEmpty() && !descricao.isEmpty()) {
                    if(Tipo.equals("Tarefa Comum")){
                        TarefaModel.TarefaComum tarefa = new TarefaModel.TarefaComum(1, titulo, descricao);
                        tarefas.add(tarefa);
                    }else if(Tipo.equals("Tarefa Rotina")){
                        String frequencia =  (String) selectFrequencia.getSelectedItem();
                        TarefaModel.TarefaRotina tarefa = new TarefaModel.TarefaRotina(1, titulo, descricao, frequencia);
                        tarefas.add(tarefa);
                    }else if(Tipo.equals("Tarefa Prazo")){
                        Date data = (Date) campoData.getValue();
                        LocalDate dataTarefa = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        TarefaModel.TarefaComPrazo tarefa = new TarefaModel.TarefaComPrazo(1, titulo, descricao, dataTarefa);
                        tarefas.add(tarefa);
                    }

                    //Chama a para atualizar a lista que é mostrada na tela
                    //não está atualizando a lista com as tarefas e sim a lista do campo que é mostrado
                    atualizarLista();
                    //limpando os campos:
                    campoTitulo.setText("");
                    campoDescricao.setText("");
                }
            }
        });

        //Colando o painelSuperior que é o principal container no topo da tela e adicionando os outros containers
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.add(painelEntrada);
        painelSuperior.add(painelEntrada2);

        add(painelSuperior, BorderLayout.NORTH);
        //Para escrollar a lista de tarefas caso ela cresça e ultrapasse a tela:
        add(new JScrollPane(listaTarefas), BorderLayout.CENTER);

        //Para parar de executar o programa quando a tela for fechada:
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    //Função para atualizar a lista mostrada na tela:
    private void atualizarLista() {
        listaTarefas.setText("");
        for(TarefaModel.Tarefa t: tarefas){
            listaTarefas.append(t.getTitulo() + " - " + t.getDescricao() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
