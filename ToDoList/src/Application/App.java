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
import java.awt.Font;
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
        //titulo, tamanho, centraliza
        setTitle("✨ Minhas Tarefas ✨");
        setSize(750, 550);
        setLocationRelativeTo(null);

       //style
        Color rosaClaro = new Color(255, 235, 245); 
        Color rosaMedio = new Color(255, 182, 193);
        Color roxoPastel = new Color(180, 160, 255); 
        Color cinza = new Color(245, 245, 245);
        Font fonteTitulo = new Font("Serif", Font.BOLD, 22);
        Font fontePadrao = new Font("Serif", Font.PLAIN, 14);
        Font fonteBotao= fontePadrao.deriveFont(Font.PLAIN, 16);

        //tema nimbus mais moderno
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }//
        // -componentes-
        //Atribuindo valor aos campos titulo e descrição:
        campoTitulo = new JTextField(20);
        campoDescricao = new JTextField(20);
        //Atribuindo valor e seus valores que podem ser escolhidos no campo:
        selectTipo = new JComboBox<>(tipos);
        //Atribuindo valor e seus valores que podem ser escolhidos no campo:
        selectFrequencia = new JComboBox<>(tiposFreq);

        // -campo data-
        //Atribuindo valor ao campo Data e formatando ele para receber datas "dd/mm/yyyy":
        campoData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(campoData, "dd/MM/yyyy");
        campoData.setEditor(editor);

        //Atribuindo valor ao botao:
        botaoAdicionar = new JButton("Adicionar");

        //Atribuindo valor ao listaTarefas onde sera mostrado as tarefas criadas:
        listaTarefas = new JTextArea(15, 40);
        listaTarefas.setEditable(false);
        listaTarefas.setBackground(cinza); //fundo
        //borda
        listaTarefas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding interno
        listaTarefas.setFont(fontePadrao);

            // add style
        //aplica a fonte em todos os campos de entrada e selecao
        campoTitulo.setFont(fontePadrao);
        campoDescricao.setFont(fontePadrao);
        selectTipo.setFont(fontePadrao);
        selectFrequencia.setFont(fontePadrao);
        campoData.setFont(fontePadrao);

            //style botao
        botaoAdicionar.setFont(fonteBotao);
        botaoAdicionar.setBackground(roxoPastel);
        botaoAdicionar.setForeground(Color.BLACK);
        botaoAdicionar.setOpaque(true);
        botaoAdicionar.setBorderPainted(false);
        botaoAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR)); //maozinha

        //paineis layout


        //Container maior que vai receber o painelEntrada e painelEntrada2:
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.setBackground(Color.WHITE);
        // Adiciona um padding externo ao painel superior
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        //header
        JLabel headerLabel = new JLabel("Criar Nova Tarefa");
        headerLabel.setFont(fonteTitulo);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // centraliza
        painelSuperior.add(Box.createVerticalStrut(15)); // espaco vertical

        //titulo e descricao
        //Containers inferiores:
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        painelEntrada.setBackground(Color.WHITE);
        painelEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(rosaMedio, 1),
                "Informações",
                0, 0, fontePadrao.deriveFont(Font.BOLD)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

// --- 1ª LINHA: TÍTULO ---
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        gbc.anchor = GridBagConstraints.WEST;
        painelEntrada.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1; // Coluna 1
        gbc.weightx = 1.0; // FAZ O CAMPO OCUPAR TODO O ESPAÇO HORIZONTAL
        gbc.gridwidth = 3; // Ocupa 3 colunas (para ficar maior que a descrição)
        painelEntrada.add(campoTitulo, gbc);

// --- 2ª LINHA: DESCRIÇÃO ---
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 1; // Linha 1
        gbc.weightx = 0; // Volta o peso para zero para o JLabel
        gbc.gridwidth = 1; // Volta a ocupar 1 coluna
        painelEntrada.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1; // Coluna 1
        gbc.weightx = 1.0; // FAZ O CAMPO OCUPAR TODO O ESPAÇO HORIZONTAL
        gbc.gridwidth = 3; // Ocupa 3 colunas
        painelEntrada.add(campoDescricao, gbc);

        JPanel painelEntrada2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelEntrada2.setBackground(rosaClaro);
        painelEntrada2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(rosaMedio, 1),
                "Tipo de tarefa",
                0, 0, fontePadrao.deriveFont(Font.BOLD)));


        JLabel freqLabel = new JLabel("Frequência:");
        JLabel dataLabel = new JLabel("Data:");

        painelEntrada2.add(new JLabel("Tipo:"));
        painelEntrada2.add(selectTipo);
        painelEntrada2.add(freqLabel);
        painelEntrada2.add(selectFrequencia);
        painelEntrada2.add(dataLabel);
        painelEntrada2.add(campoData);
        painelEntrada2.add(botaoAdicionar);
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        painelBotao.setBackground(rosaClaro); // Mantém a cor do painelEntrada2
        painelBotao.add(botaoAdicionar);
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
        // Adiciona os sub-painéis ao painel superior
        painelSuperior.add(painelEntrada);
        painelSuperior.add(Box.createVerticalStrut(10)); // Espaço
        painelSuperior.add(painelEntrada2);
        painelSuperior.add(Box.createVerticalStrut(10)); // Espaço
        painelSuperior.add(painelBotao); // Adiciona o painel do botão

        // --- Adiciona Painéis ao JFrame ---
        add(painelSuperior, BorderLayout.NORTH);

        // Área de exibição de tarefas
        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20), // Margem externa
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(rosaMedio),
                        "Lista de Tarefas",
                        0, 0, fontePadrao.deriveFont(Font.BOLD))
        ));
        painelLista.add(new JScrollPane(listaTarefas), BorderLayout.CENTER);
        painelLista.setBackground(rosaClaro);

        add(painelLista, BorderLayout.CENTER);
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

                    for(TarefaModel.Tarefa t: tarefas){
                        t.exibirDetalhes();
                    }

                    //Chama a para atualizar a lista que é mostrada na tela
                    //não está atualizando a lista com as tarefas e sim a lista do campo que é mostrado
                    atualizarLista();
                    //limpando os campos:
                    campoTitulo.setText("");
                    campoDescricao.setText("");
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Preencha todos os campos para criar uma terefa",
                                "Mensagem de Erro",
                                    JOptionPane.PLAIN_MESSAGE);
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
//        pack();
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
