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
    private JTextField campoTitulo;
    private JTextField campoDescricao;
    private JComboBox<String> selectTipo;
    private JSpinner campoData;
    String[] tipos = {"Tarefa Comum", "Tarefa Prazo", "Tarefa Rotina"};
    private JTextField campoFrequencia;
    private JButton botaoAdicionar;
    private JTextArea listaTarefas;
    private ArrayList<TarefaModel.Tarefa> tarefas = new ArrayList<>();

    public App(){
        campoTitulo = new JTextField(20);
        campoDescricao = new JTextField(20);
        selectTipo = new JComboBox<>(tipos);

        campoFrequencia = new JTextField(20);

        campoData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(campoData, "dd/MM/yyyy");
        campoData.setEditor(editor);

        botaoAdicionar = new JButton("Adicionar");

        listaTarefas = new JTextArea(10, 30);
        listaTarefas.setEditable(false);

        JPanel painelSuperior = new JPanel();
        JPanel painelEntrada = new JPanel();
        JPanel painelEntrada2 = new JPanel();
        painelEntrada.add(new JLabel("Título:"));
        painelEntrada.add(campoTitulo);
        painelEntrada.add(new JLabel("Descrição:"));
        painelEntrada.add(campoDescricao);

        painelEntrada2.add(new JLabel("Tipo:"));
        painelEntrada2.add(selectTipo);

        JLabel freqLabel = new JLabel("Frequência:");
        painelEntrada2.add(freqLabel);
        painelEntrada2.add(campoFrequencia);

        JLabel dataLabel = new JLabel("Data:");
        painelEntrada2.add(dataLabel);
        painelEntrada2.add(campoData);

        painelEntrada2.add(botaoAdicionar);

        campoData.setVisible(false);
        dataLabel.setVisible(false);
        campoFrequencia.setVisible(false);
        freqLabel.setVisible(false);

        selectTipo.addActionListener(e -> {
            campoData.setVisible(false);
            dataLabel.setVisible(false);
            campoFrequencia.setVisible(false);
            freqLabel.setVisible(false);
            String Tipo = (String) selectTipo.getSelectedItem();
            if(Tipo.equals("Tarefa Prazo")){
                campoData.setVisible(true);
                dataLabel.setVisible(true);
            }else if(Tipo.equals("Tarefa Rotina")){
                campoFrequencia.setVisible(true);
                freqLabel.setVisible(true);
            }
        });

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
                        String frequencia = campoFrequencia.getText();
                        TarefaModel.TarefaRotina tarefa = new TarefaModel.TarefaRotina(1, titulo, descricao, frequencia);
                        tarefas.add(tarefa);
                    }else if(Tipo.equals("Tarefa Prazo")){
                        Date data = (Date) campoData.getValue();
                        LocalDate dataTarefa = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        TarefaModel.TarefaComPrazo tarefa = new TarefaModel.TarefaComPrazo(1, titulo, descricao, dataTarefa);
                        tarefas.add(tarefa);
                    }

                    for(TarefaModel.Tarefa t: tarefas){
                        t.exibirDetalhes();
                    }

                    atualizarLista();
                    campoTitulo.setText("");
                    campoDescricao.setText("");
                }
            }
        });


        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.add(painelEntrada);
        painelSuperior.add(painelEntrada2);

        add(painelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(listaTarefas), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

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
