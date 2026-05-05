package br.com.fiap.ecommerce.api.cliente;

import br.com.fiap.ecommerce.api.endereco.DadosCadastroEndereco;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroCliente(
        @NotBlank //Não aceita nulos ou espaços em branco
        @Size(min = 3, max = 100) //define tamanho mínimo e/ou máximo
        String nome,

        @NotBlank
        @Email
        @Column(unique=true)
        String email,

        @NotBlank
        @CPF
        @Size(min = 11, max = 11)
        String cpf,

        @Size(max = 20)
        String telefone,

        @NotNull @Valid
        DadosCadastroEndereco endereco
){
}


