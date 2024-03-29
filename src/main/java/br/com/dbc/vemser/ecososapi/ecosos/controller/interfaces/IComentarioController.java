package br.com.dbc.vemser.ecososapi.ecosos.controller.interfaces;

import br.com.dbc.vemser.ecososapi.ecosos.dto.comentario.ComentarioCreateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.comentario.ComentarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IComentarioController {

    @Operation(
            summary = "Listar comentários por ocorrência",
            description = "Lista todos os comentários de uma ocorrência com base no ID da ocorrência"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentários listados com sucesso",
                            content = @Content(schema = @Schema(implementation = ComentarioDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID de ocorrência inválido. Verifique os parâmetros da requisição.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nenhum comentário encontrado para a ocorrência fornecida.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor. Entre em contato com o administrador.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso proibido. O usuário não tem permissão para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "ID de ocorrência não processável. O ID fornecido não pôde ser processado.",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @GetMapping("/{idOcorrencia}")
    ResponseEntity<List<ComentarioDTO>> listar(@PathVariable("idOcorrencia") Integer idOcorrencia) throws Exception;

    @Operation(
            summary = "Adicionar comentário",
            description = "Adiciona um novo comentário a uma ocorrência"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentário adicionado com sucesso",
                            content = @Content(schema = @Schema(implementation = ComentarioDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida. Verifique os dados do comentário fornecidos.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor. Entre em contato com o administrador.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado. É necessário autenticação para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso proibido. O usuário não tem permissão para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Comentário não processável. Os dados do comentário não puderam ser processados.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflito. O comentário já existe na ocorrência.",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("/ocorrencia/{idOcorrencia}/usuario/{idUsuario}")
    ResponseEntity<ComentarioDTO> adicionar(@PathVariable("idOcorrencia") Integer idOcorrencia,
                                            @PathVariable("idUsuario") Integer idUsuario,
                                            @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws Exception;

    @Operation(
            summary = "Editar comentário",
            description = "Edita um comentário de uma ocorrência. Apenas usuário admin"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentário editado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida. Verifique os dados do comentário fornecidos.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso proibido. O usuário não tem permissão para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado para a ocorrência fornecida.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Comentário não processável. Os dados do comentário não puderam ser processados.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor. Entre em contato com o administrador.",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PutMapping("/userAdmin/{idComentario}")
    ResponseEntity<ComentarioDTO> editarParaAdmin(@PathVariable("idComentario") Integer idComentario,
                                         @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws Exception;

    @Operation(
            summary = "Editar comentário",
            description = "Edita um comentário de uma ocorrência feito pelo usuário logado"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentário editado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida. Verifique os dados do comentário fornecidos.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso proibido. O usuário não tem permissão para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado para a ocorrência fornecida.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Comentário não processável. Os dados do comentário não puderam ser processados.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor. Entre em contato com o administrador.",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PutMapping("/userComum/{idComentario}")
    ResponseEntity<ComentarioDTO> editarParaComum(@PathVariable("idComentario") Integer idComentario,
                                         @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws Exception;

    @Operation(
            summary = "Remover comentário",
            description = "Remove um comentário de uma ocorrência. Apenas para usuário admin"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentário removido com sucesso."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID de comentário inválido. Verifique os parâmetros da requisição.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso proibido. O usuário não tem permissão para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado para a ocorrência fornecida.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "ID de comentário não processável. O ID fornecido não pôde ser processado.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor. Entre em contato com o administrador.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
            }
    )
    @DeleteMapping("/userAdmin/{idComentario}")
    ResponseEntity<Void> removerParaAdmin(@PathVariable("idComentario") Integer id) throws Exception;

    @Operation(
            summary = "Remover comentário",
            description = "Remove um comentário de uma ocorrência feito pelo usuário logado."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentário removido com sucesso."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID de comentário inválido. Verifique os parâmetros da requisição.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso proibido. O usuário não tem permissão para acessar este recurso.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Comentário não encontrado para a ocorrência fornecida.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "ID de comentário não processável. O ID fornecido não pôde ser processado.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor. Entre em contato com o administrador.",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
            }
    )
    @DeleteMapping("/userComum/{idComentario}")
    ResponseEntity<Void> removerParaComum(@PathVariable("idComentario") Integer id) throws Exception;
}
