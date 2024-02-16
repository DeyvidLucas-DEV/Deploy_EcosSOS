package br.com.dbc.vemser.ecososapi.ecosos.controller;

import br.com.dbc.vemser.ecososapi.ecosos.controller.interfaces.IUsuarioComumController;
import br.com.dbc.vemser.ecososapi.ecosos.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.service.UsuarioComumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Usuario Comum")
@RequestMapping("/usuariocomum")
public class UsuarioComumController implements IUsuarioComumController {

    private final UsuarioComumService usuarioComumService;

    @GetMapping("/exibir-perfil")
    public ResponseEntity<List<UsuarioDTO>> exibirPerfil() throws Exception {
        List<UsuarioDTO> usuarioDTOS = usuarioComumService.listar();
        return ResponseEntity.ok(usuarioDTOS);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable("idUsuario") Integer id,
                                             @Valid @RequestBody UsuarioUpdateDTO usuarioAtualizar) throws Exception {
        return new ResponseEntity<>(usuarioComumService.atualizar(id, usuarioAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable("idUsuario") Integer id) throws Exception {
        usuarioComumService.deletar(id);
        return ResponseEntity.ok().build();
    }

    // não há necessidade de existir na controller de UsuarioComum, passado para controller de comentário

//    @DeleteMapping("/{idComentario}")
//    public ResponseEntity<Void> deletarComentario(@PathVariable("idComentario") Integer id) throws Exception {
//        usuarioComumService.deletarComentario(id);
//        return ResponseEntity.ok().build();
//    }

}
