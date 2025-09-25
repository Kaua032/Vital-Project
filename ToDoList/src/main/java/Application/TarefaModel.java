package Application;

import java.time.LocalDate;

public class TarefaModel {
    // Classe abstrata base
    public abstract static class Tarefa {
        protected int id;
        protected String titulo;
        protected String descricao;
        protected boolean concluida;

        public Tarefa(int id, String titulo, String descricao) {
            this.id = id;
            this.titulo = titulo;
            this.descricao = descricao;
            this.concluida = false;
        }

        public void concluir() {
            this.concluida = true;
        }

        public abstract void exibirDetalhes();
    }

    // Subclasse Tarefa Comum
    public static class TarefaComum extends Tarefa {
        public TarefaComum(int id, String titulo, String descricao) {
            super(id, titulo, descricao);
        }

        @Override
        public void exibirDetalhes() {
            System.out.println("Tarefa Comum: " + titulo + " | " + descricao + " | " +
                    (concluida ? "Concluída" : "Pendente"));
        }
    }

    // Subclasse Tarefa Rotina
    public static class TarefaRotina extends Tarefa {
        private String frequencia;

        public TarefaRotina(int id, String titulo, String descricao, String frequencia) {
            super(id, titulo, descricao);
            this.frequencia = frequencia;
        }

        @Override
        public void exibirDetalhes() {
            System.out.println("Tarefa Rotina: " + titulo + " | " + descricao +
                    " | Frequência: " + frequencia + " | " +
                    (concluida ? "Concluída" : "Pendente"));
        }
    }

    // Subclasse Tarefa com Prazo
    public static class TarefaComPrazo extends Tarefa {
        private LocalDate dataLimite;

        public TarefaComPrazo(int id, String titulo, String descricao, LocalDate dataLimite) {
            super(id, titulo, descricao);
            this.dataLimite = dataLimite;
        }

        @Override
        public void exibirDetalhes() {
            System.out.println("Tarefa com Prazo: " + titulo + " | " + descricao +
                    " | Prazo: " + dataLimite + " | " +
                    (concluida ? "Concluída" : "Pendente"));
        }
    }
}
