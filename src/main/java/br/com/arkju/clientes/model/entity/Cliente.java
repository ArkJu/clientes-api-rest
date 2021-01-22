package br.com.arkju.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Entity
//@Getter@Setter
@Data//Anotação do Lombok para gerar os getter and setter em tempo de execução
@NoArgsConstructor//Anotação do Lombok para construtor vazio em tempo de execução
@AllArgsConstructor//Anotação do Lombok para construtor completo em tempo de execução
@Builder//Anotação do Lombok para criar um objeto em tempo de execução
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(nullable = false, length = 11)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    @PrePersist //Anotação para executar metodo antes de persistir a informação no BD
    public  void prePersist(){
        setDataCadastro(LocalDate.now());
    }
}
