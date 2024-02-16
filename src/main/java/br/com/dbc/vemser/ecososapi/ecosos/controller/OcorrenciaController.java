package br.com.dbc.vemser.ecososapi.ecosos.controller;

import br.com.dbc.vemser.ecososapi.ecosos.controller.interfaces.IOcorrenciaController;
import br.com.dbc.vemser.ecososapi.ecosos.dto.ocorrencia.OcorrenciaCreateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.ocorrencia.OcorrenciaDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.ocorrencia.OcorrenciaUpdateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Ocorrência")
@RequestMapping("/ocorrencia")
public class OcorrenciaController implements IOcorrenciaController{

  private final OcorrenciaService ocorrenciaService;

  @GetMapping("/listar-todas")
  public ResponseEntity<List<OcorrenciaDTO>> listarTodas() throws Exception {
    return ResponseEntity.ok(ocorrenciaService.listarTodas());
  }

  @GetMapping("/listar-todas-id/{idOcorrencia}")
  public ResponseEntity<OcorrenciaDTO> listarPorId(@PathVariable("id") Integer idOcorrencia) throws Exception {
    return ResponseEntity.ok(ocorrenciaService.listarPorId(idOcorrencia));
  }

  @GetMapping("/feed")
  public ResponseEntity<List<OcorrenciaDTO>> listarAtivas() throws Exception {
    return ResponseEntity.ok(ocorrenciaService.listarAtivas());
  }

  @GetMapping("/feed/{id}")
  public ResponseEntity<List<OcorrenciaDTO>> listarAtivasPorId(@PathVariable("id") Integer idOcorrencia) throws Exception {
    return ResponseEntity.ok(ocorrenciaService.listarAtivasPorId(idOcorrencia));
  }

  @PostMapping("/adicionar")
  public ResponseEntity<OcorrenciaDTO> criar(@Valid @RequestBody OcorrenciaCreateDTO ocorrenciaCreateDTO) throws Exception {
    return ResponseEntity.ok(ocorrenciaService.adicionar(ocorrenciaCreateDTO));
  }

  @PutMapping("/userAdmin/{idOcorrencia}")
  public ResponseEntity<OcorrenciaDTO> editarParaAdmin(@PathVariable("idOcorrencia") Integer idOcorrencia,
                                              @Valid @RequestBody OcorrenciaUpdateDTO ocorrenciaAtualizar) throws Exception{
    return ResponseEntity.ok(ocorrenciaService.atualizarParaAdmin(idOcorrencia, ocorrenciaAtualizar));
  }

  @PutMapping("/userComum/{idOcorrencia}")
  public ResponseEntity<OcorrenciaDTO> editarParaComum(@PathVariable("idOcorrencia") Integer idOcorrencia,
                                              @Valid @RequestBody OcorrenciaUpdateDTO ocorrenciaAtualizar) throws Exception{
    return ResponseEntity.ok(ocorrenciaService.atualizarParaComum(idOcorrencia, ocorrenciaAtualizar));
  }

  @DeleteMapping("/userComum/{idOcorrencia}")
  public ResponseEntity<Void> removerParaComum(@PathVariable("idOcorrencia") Integer idOcorrencia) throws Exception{
    ocorrenciaService.removerDeComum(idOcorrencia);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/userAdmin/{idOcorrencia}")
  public ResponseEntity<Void> remover(@PathVariable("idOcorrencia") Integer idOcorrencia) throws Exception{
    ocorrenciaService.removerDeAdmin(idOcorrencia);
    return ResponseEntity.ok().build();
  }

}