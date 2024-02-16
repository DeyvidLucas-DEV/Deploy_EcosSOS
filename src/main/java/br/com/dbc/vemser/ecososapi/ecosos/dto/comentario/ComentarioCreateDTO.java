package br.com.dbc.vemser.ecososapi.ecosos.dto.comentario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioCreateDTO {

    @NotBlank(message = "Insira um comentário")
    @Schema(description = "Conteúdo do comentário", required = true, example = "Situação complicada, população desesperada!")
    @Size(max = 250)
    private String conteudo;

}
