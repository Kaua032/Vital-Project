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
import java.awt.Font;

/**
 *
 * @author User
 */

public class App extends JFrame {
    
    private static final long serialVersionUID = 711852754032306455L;
    
    // Declaração das variáveis que vão receber os campos de entrada:
    private JTextField campoTitulo;
    private JTextField campoDescricao;

    //Para selecionar o tipo de tarefa mostrado:
    private JComboBox<String> selectTipoLista;

    // Para selecionar o tipo de tarefa:
    private JComboBox<String> selectTipo;
    String[] tipos = {"Tarefa Comum", "Tarefa Prazo", "Tarefa Rotina"};

    // Para a tarefa do tipo PRAZO:
    private JSpinner campoData;

    // Para selecionar a frequência da tarefa do tipo ROTINA:
    private JComboBox<String> selectFrequencia;
    String[] tiposFreq = {"Diária", "Mensal", "Anual", "Horária"};

    // Botão de adicionar a nova tarefa:
    private JButton botaoAdicionar;

    // Onde será guardada as tarefas criadas:
    private JPanel painelTarefasComum;
    private ArrayList<TarefaModel.TarefaComum> tarefas = new ArrayList<>();
    private JPanel painelTarefasRotina;
    private ArrayList<TarefaModel.TarefaRotina> tarefasRotina = new ArrayList<>();
    private JPanel painelTarefasPrazo;
    private ArrayList<TarefaModel.TarefaComPrazo> tarefasPrazo = new ArrayList<>();

    private int proximoId = 0;

    public App() {
        // Título, tamanho, centraliza
        setTitle("✨ Minhas Tarefas ✨");
        setSize(750, 550);
        setLocationRelativeTo(null);

        // Estilo
        Color rosaClaro = new Color(255, 235, 245); 
        Color rosaMedio = new Color(255, 182, 193);
        Color cinza = new Color(245, 245, 245);
        Font fonteTitulo = new Font("DialogInput", Font.BOLD, 22);
        Font fontePadrao = new Font("DialogInput", Font.PLAIN, 14);
        Font fonteBotao = fontePadrao.deriveFont(Font.PLAIN, 16);

        // Tema Nimbus mais moderno
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Componentes ---
        // Atribuindo valor aos campos título e descrição:
        campoTitulo = new JTextField(20);
        campoDescricao = new JTextField(20);
        //Atribuindo valor e seus valora que pode ser escolhidos no campo para mostrar a lista:
        selectTipoLista = new JComboBox<>(tipos);
        // Atribuindo valor e seus valores que podem ser escolhidos no campo:
        selectTipo = new JComboBox<>(tipos);
        // Atribuindo valor e seus valores que podem ser escolhidos no campo:
        selectFrequencia = new JComboBox<>(tiposFreq);

        // Campo data
        campoData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(campoData, "dd/MM/yyyy");
        campoData.setEditor(editor);

        // Atribuindo valor ao botão:
        botaoAdicionar = new JButton("Adicionar");

        // Atribuindo valor ao listaTarefas onde será mostrado as tarefas criadas:


        painelTarefasComum = criarPainelLista();
        painelTarefasPrazo = criarPainelLista();
        painelTarefasRotina = criarPainelLista();

        // Aplicar estilo aos campos
        campoTitulo.setFont(fontePadrao);
        campoDescricao.setFont(fontePadrao);
        selectTipo.setFont(fontePadrao);
        selectFrequencia.setFont(fontePadrao);
        campoData.setFont(fontePadrao);

        // Estilo botão
        botaoAdicionar.setFont(fonteBotao);
        botaoAdicionar.setBackground(rosaMedio);
        botaoAdicionar.setForeground(Color.BLACK);
        botaoAdicionar.setOpaque(true);
        botaoAdicionar.setBorderPainted(false);
        botaoAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- PAINÉIS E LAYOUT ---

        // Container maior que vai receber painelEntrada e painelEntrada2:
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.setBackground(Color.WHITE);
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));

        // Header
        JLabel headerLabel = new JLabel("Criar Nova Tarefa");
        headerLabel.setFont(fonteTitulo);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelSuperior.add(headerLabel);
        painelSuperior.add(Box.createVerticalStrut(15));

        // Painel de entrada (Título e Descrição)
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        painelEntrada.setBackground(Color.WHITE);
        painelEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(rosaMedio, 1),
                "Informações",
                0, 0, fontePadrao.deriveFont(Font.BOLD)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- 1ª LINHA: TÍTULO ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painelEntrada.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        painelEntrada.add(campoTitulo, gbc);

        // --- 2ª LINHA: DESCRIÇÃO ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        painelEntrada.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        painelEntrada.add(campoDescricao, gbc);

        // Painel de tipo de tarefa
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

        // Painel para o botão Adicionar (na parte inferior da janela)
        JPanel painelBotaoInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        painelBotaoInferior.setBackground(Color.WHITE);
        painelBotaoInferior.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelBotaoInferior.add(botaoAdicionar);

        // Área de exibição de tarefas
        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(rosaMedio),
                        "Lista de Tarefas",
                        0, 0, fontePadrao.deriveFont(Font.BOLD))
        ));
        painelLista.setBackground(rosaClaro);

        // Alterando a visibilidade dos campos que só aparecem após escolher o tipo de tarefa:
        campoData.setVisible(false);
        dataLabel.setVisible(false);
        selectFrequencia.setVisible(false);
        freqLabel.setVisible(false);

        // Função para a lógica quando o valor do selectTipo for alterado:
        selectTipo.addActionListener(e -> {
            campoData.setVisible(false);
            dataLabel.setVisible(false);
            selectFrequencia.setVisible(false);
            freqLabel.setVisible(false);

            painelLista.removeAll();

            String tipo = (String) selectTipo.getSelectedItem();

            if (tipo.equals("Tarefa Prazo")) {
                campoData.setVisible(true);
                dataLabel.setVisible(true);
                painelLista.add(new JScrollPane(painelTarefasPrazo), BorderLayout.CENTER);
            } else if (tipo.equals("Tarefa Rotina")) {
                selectFrequencia.setVisible(true);
                freqLabel.setVisible(true);
                painelLista.add(new JScrollPane(painelTarefasRotina), BorderLayout.CENTER);
            } else {
                painelLista.add(new JScrollPane(painelTarefasComum), BorderLayout.CENTER);
            }

            painelLista.revalidate();
            painelLista.repaint();
        });

        selectTipo.setSelectedItem("Tarefa Comum");
        selectTipo.getActionListeners()[0].actionPerformed(null);

        // Adiciona os sub-painéis ao painel superior
        painelSuperior.add(painelEntrada);
        painelSuperior.add(Box.createVerticalStrut(10));
        painelSuperior.add(painelEntrada2);



        //Painel com os botões de escolher a lista que vai ser mostrada:
        JPanel painelBotoesLista = new JPanel(new BorderLayout());
        painelBotoesLista.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        // Adiciona os painéis ao JFrame
        add(painelSuperior, BorderLayout.NORTH);
        add(painelLista, BorderLayout.CENTER);
        add(painelBotaoInferior, BorderLayout.SOUTH);

        // Função para ler os campos preenchidos e criar as novas tarefas:
        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = campoTitulo.getText();
                String descricao = campoDescricao.getText();
                String tipo = (String) selectTipo.getSelectedItem();
                if (!titulo.isEmpty() && !descricao.isEmpty()) {
                    if (tipo.equals("Tarefa Comum")) {
                        TarefaModel.TarefaComum tarefa = new TarefaModel.TarefaComum(proximoId++, titulo, descricao);
                        tarefas.add(tarefa);
                    } else if (tipo.equals("Tarefa Rotina")) {
                        String frequencia = (String) selectFrequencia.getSelectedItem();
                        TarefaModel.TarefaRotina tarefa = new TarefaModel.TarefaRotina(proximoId++, titulo, descricao, frequencia);
                        tarefasRotina.add(tarefa);
                    } else if (tipo.equals("Tarefa Prazo")) {
                        Date data = (Date) campoData.getValue();
                        LocalDate dataTarefa = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        TarefaModel.TarefaComPrazo tarefa = new TarefaModel.TarefaComPrazo(proximoId++, titulo, descricao, dataTarefa);
                        tarefasPrazo.add(tarefa);
                    }

                    for (TarefaModel.Tarefa t : tarefas) {
                        t.exibirDetalhes();
                    }

                    // Atualiza a lista mostrada na tela
                    atualizarLista();
                    // Limpando os campos:
                    campoTitulo.setText("");
                    campoDescricao.setText("");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Preencha todos os campos para criar uma tarefa",
                            "Mensagem de Erro",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // Para parar de executar o programa quando a tela for fechada:
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel criarPainelLista(){
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        return painel;
    }

    private JPanel criarLinhaTarefa(String texto, Runnable aoDeletar) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setBackground(Color.WHITE);
        linha.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JLabel label = new JLabel(texto);
        JButton botaoDel = new JButton("🗑️");
        botaoDel.setBackground(new Color(255, 182, 193));
        botaoDel.setFocusPainted(false);
        botaoDel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        botaoDel.setPreferredSize(new Dimension(50, 15));

        botaoDel.addActionListener(e -> aoDeletar.run());

        linha.add(label, BorderLayout.CENTER);
        linha.add(botaoDel, BorderLayout.EAST);

        return linha;
    }


    // Função para atualizar a lista mostrada na tela:
    private void atualizarLista() {
        painelTarefasComum.removeAll();
        painelTarefasPrazo.removeAll();
        painelTarefasRotina.removeAll();

        for (TarefaModel.TarefaComum t : tarefas) {
            painelTarefasComum.add(criarLinhaTarefa(t.getTitulo() + " - " + t.getDescricao(),
                    () -> {
                        tarefas.remove(t);
                        atualizarLista();
                    }));
        }

        for (TarefaModel.TarefaRotina t : tarefasRotina) {
            painelTarefasRotina.add(criarLinhaTarefa(
                    t.getTitulo() + " - " + t.getDescricao() + " (" + t.getFrequencia() + ")",
                    () -> {
                        tarefasRotina.remove(t);
                        atualizarLista();
                    }));
        }

        for (TarefaModel.TarefaComPrazo t : tarefasPrazo) {
            painelTarefasPrazo.add(criarLinhaTarefa(
                    t.getTitulo() + " - " + t.getDescricao() + " até " + t.getDataLimite(),
                    () -> {
                        tarefasPrazo.remove(t);
                        atualizarLista();
                    }));
        }

        painelTarefasComum.revalidate();
        painelTarefasComum.repaint();
        painelTarefasPrazo.revalidate();
        painelTarefasPrazo.repaint();
        painelTarefasRotina.revalidate();
        painelTarefasRotina.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}