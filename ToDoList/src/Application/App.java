/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class App extends JFrame {
    private JTextField campoTitulo;
    private JTextField campoDescricao;
    private JButton botaoAdicionar;
    private JTextArea listaTarefas;
    private ArrayList<TarefaModel.Tarefa> tarefas = new ArrayList<>();

    public App(){
        campoTitulo = new JTextField(20);
        campoDescricao = new JTextField(20);
        botaoAdicionar = new JButton("Adicionar");
        listaTarefas = new JTextArea(10, 30);
        listaTarefas.setEditable(false);

        JPanel painelEntrada = new JPanel();
        painelEntrada.add(new JLabel("Título:"));
        painelEntrada.add(campoTitulo);
        painelEntrada.add(new JLabel("Descrição:"));
        painelEntrada.add(campoDescricao);
        painelEntrada.add(botaoAdicionar);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = campoTitulo.getText();
                String descricao = campoDescricao.getText();
                if(!titulo.isEmpty() && !descricao.isEmpty()) {
                    TarefaModel.TarefaComum tarefa = new TarefaModel.TarefaComum(1, titulo, descricao);
                    tarefas.add(tarefa);
                    atualizarLista();
                    campoTitulo.setText("");
                    campoDescricao.setText("");
                }
            }
        });


        setLayout(new BorderLayout());
        add(painelEntrada, BorderLayout.NORTH);
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
