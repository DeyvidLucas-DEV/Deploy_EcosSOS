package br.com.dbc.vemser.ecososapi.ecosos.controller;

import br.com.dbc.vemser.ecososapi.ecosos.controller.interfaces.IComentarioController;
import br.com.dbc.vemser.ecososapi.ecosos.dto.comentario.ComentarioCreateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.comentario.ComentarioDTO;
import br.com.dbc.vemser.ecososapi.ecosos.service.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comentário")
@RequestMapping("/comentario")
public class ComentarioController implements IComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping("/{idOcorrencia}")
    public ResponseEntity<List<ComentarioDTO>> listar(@PathVariable("idOcorrencia") Integer idOcorrencia) throws Exception{
        List<ComentarioDTO> comentarioDTOS = comentarioService.listarPorComentario(idOcorrencia);
        return ResponseEntity.ok(comentarioDTOS);
    }

    @PostMapping("/ocorrencia/{idOcorrencia}/usuario/{idUsuario}")
    public ResponseEntity<ComentarioDTO> adicionar(@PathVariable("idOcorrencia") Integer idOcorrencia,
                                                   @PathVariable("idUsuario") Integer idUsuario,
                                                   @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws Exception{
        return  new ResponseEntity<>(comentarioService.adicionar(idOcorrencia, idUsuario, comentarioCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/userAdmin/{idComentario}")
    public ResponseEntity<ComentarioDTO> editarParaAdmin(@PathVariable("idComentario") Integer idComentario,
                                                @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws Exception{
        return new ResponseEntity<>(comentarioService.atualizarParaAdmin(idComentario, comentarioCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/userComum/{idComentario}")
    public ResponseEntity<ComentarioDTO> editarParaComum(@PathVariable("idComentario") Integer idComentario,
                                                         @Valid @RequestBody ComentarioCreateDTO comentarioCreateDTO) throws Exception{
        return new ResponseEntity<>(comentarioService.atualizarParaComum(idComentario, comentarioCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/userAdmin/{idComentario}")
    public ResponseEntity<Void> removerParaAdmin(@PathVariable("idComentario") Integer id) throws Exception {
        comentarioService.removerParaAdmin(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/userComum/{idComentario}")
    public ResponseEntity<Void> removerParaComum(@PathVariable("idComentario") Integer id) throws Exception {
        comentarioService.removerParaComum(id);
        return ResponseEntity.ok().build();
    }
}
