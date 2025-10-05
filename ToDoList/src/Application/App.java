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
    private JTextField campoTarefa;
    private JButton botaoAdicionar;
    private JTextArea listaTarefas;
    private ArrayList<String> tarefas = new ArrayList<>();

    public App(){
        campoTarefa = new JTextField(20);
        botaoAdicionar = new JButton("Adicionar");
        listaTarefas = new JTextArea(10, 30);
        listaTarefas.setEditable(false);

        JPanel painelEntrada = new JPanel();
        painelEntrada.add(new JLabel("Tarefa:"));
        painelEntrada.add(campoTarefa);
        painelEntrada.add(botaoAdicionar);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tarefa = campoTarefa.getText();
                if(!tarefa.isEmpty()) {
                    tarefas.add(tarefa);
                    atualizarLista();
                    campoTarefa.setText("");
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
        for (String t : tarefas) {
            listaTarefas.append(t + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
