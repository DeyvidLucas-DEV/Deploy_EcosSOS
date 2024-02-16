package br.com.dbc.vemser.ecososapi.ecosos.service;

import br.com.dbc.vemser.ecososapi.ecosos.dto.auth.AlteraSenhaDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.ecososapi.ecosos.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.ecososapi.ecosos.entity.Cargo;
import br.com.dbc.vemser.ecososapi.ecosos.entity.Comentario;
import br.com.dbc.vemser.ecososapi.ecosos.entity.Endereco;
import br.com.dbc.vemser.ecososapi.ecosos.entity.Usuario;
import br.com.dbc.vemser.ecososapi.ecosos.exceptions.EntidadeNaoEncontradaException;
import br.com.dbc.vemser.ecososapi.ecosos.exceptions.PermissaoNegadaException;
import br.com.dbc.vemser.ecososapi.ecosos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecososapi.ecosos.repository.ComentarioRepository;
import br.com.dbc.vemser.ecososapi.ecosos.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioComumService {

    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;
    private final EnderecoService enderecoService;
    private final ObjectMapper objectMapper;
// não chamar pois não tem necessidade. OBS: não se chama repository de outras classes numa service que não é dela private final ComentarioRepository comentarioRepository;

    public UsuarioDTO alterarSenha(AlteraSenhaDTO alteraSenhaDTO) throws Exception {
        Optional<Usuario> usuarioAtualizar = findByEmail(alteraSenhaDTO.getEmail());
        if (usuarioAtualizar.isEmpty()) {
            throw new RegraDeNegocioException("Usuário ou Senha inválida");
        }
        Md4PasswordEncoder encoder = new Md4PasswordEncoder();
        if (!encoder.matches(alteraSenhaDTO.getSenhaAtual(), usuarioAtualizar.get().getSenha())) {
            throw new RegraDeNegocioException("Usuário ou Senha inválida");
        }

        String senhaNovaCriptografada = encoder.encode(alteraSenhaDTO.getSenhaNova());
        usuarioAtualizar.get().setSenha(senhaNovaCriptografada);
        return retornarDTO(usuarioRepository.save(usuarioAtualizar.get()));
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuarioCreateDTO) throws Exception{

        if (telefoneExiste(usuarioCreateDTO.getTelefone())) {
            throw new RegraDeNegocioException("Telefone já cadastrado.");
        }

        if (emailExiste(usuarioCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("Email já cadastrado.");
        }

        Endereco endereco = enderecoService.getEndereco(usuarioCreateDTO.getIdEndereco());

        Md4PasswordEncoder encoder = new Md4PasswordEncoder();
        String senhaCriptografada = encoder.encode(usuarioCreateDTO.getSenha());
        Cargo cargo = cargoService.findCargoById(2);

        Usuario usuarioEntity = converterDTO(usuarioCreateDTO);
        usuarioEntity.setEndereco(endereco);
        usuarioEntity.setSenha(senhaCriptografada);
        usuarioEntity.setCargos(new HashSet<>());
        usuarioEntity.getCargos().add(cargo);
        usuarioEntity.setCriadoEm(Timestamp.valueOf(LocalDateTime.now()));
        usuarioEntity.setStatus("A");
        return retornarDTO(usuarioRepository.save(usuarioEntity));
    }

    public List<UsuarioDTO> listar() throws Exception{
       return usuarioRepository.findByEmail(getEmail()).stream()
               .map(this::retornarDTO)
               .collect(Collectors.toList());
    }

    public UsuarioDTO atualizar(Integer id, UsuarioUpdateDTO usuarioUpdateDTO) throws Exception {
        Usuario usuarioAtualizar = returnUsuario(id);

        if(usuarioAtualizar.getStatus() == "D") {
            throw new RegraDeNegocioException("Usuário não existe no sistema");
        }

        if(usuarioAtualizar.getIdUsuario() == getIdLoggedUser()) {

            if (usuarioUpdateDTO.getNome() != null){
                usuarioAtualizar.setNome(usuarioUpdateDTO.getNome());
            } if(usuarioUpdateDTO.getTelefone() != null){
                usuarioAtualizar.setTelefone(usuarioUpdateDTO.getTelefone());
            } if(usuarioUpdateDTO.getEmail() != null) {
                usuarioAtualizar.setEmail(usuarioUpdateDTO.getEmail());
            }

            usuarioAtualizar.setAtualizadoEm(Timestamp.valueOf(LocalDateTime.now()));
            return retornarDTO(usuarioRepository.save(usuarioAtualizar));

        }
        else {
            throw new PermissaoNegadaException("Você não tem permissão para atualizar um usuário que não é você");
        }
    }

// método passado para classe Comentário Service pois não há mais necessidade dele ser chamado na UsuarioComumService
    // utilizei apenas o getIdUsuario de comentarioDeletado pois já é algo presente na entidade, então torna mais fácil para entender/economiza linhas de código (fica a seu critério)
//    public void deletarComentario(Integer idUsuario) throws Exception {
//        Comentario comentarioDeletado = returnComentario(idUsuario);
//        Usuario usuarioComentado = returnUsuario(comentarioDeletado.getIdUsuario());
//        if (!usuarioComentado.getEmail().equals(getEmail())) {
//            throw new PermissaoNegadaException("Permissão negada para excluir este comentario");
//        }
//        comentarioRepository.delete(comentarioDeletado);
//    }

    public void deletar(Integer idUsuario) throws Exception {
        Usuario usuarioDeletado = returnUsuario(idUsuario);
        if (!usuarioDeletado.getEmail().equals(getEmail())) {
            throw new PermissaoNegadaException("Permissão negada para excluir este usuário");
        }
        usuarioDeletado.setStatus("D");
        usuarioRepository.save(usuarioDeletado);
    }

    public Integer getIdLoggedUser() {
        Integer findUserId = Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return findUserId;
    }

    public String getEmail(){
        Usuario usuario = usuarioRepository.getById(getIdLoggedUser());
        return usuario.getEmail();
    }

    public Usuario returnUsuario(Integer idUsuario) throws Exception{
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Nenhum usuário encontrado com o id " + idUsuario));
    }

    private boolean telefoneExiste(String telefone) {
        return usuarioRepository.findByTelefoneEquals(telefone) != null;
    }

    private boolean emailExiste(String email) throws Exception {
        return usuarioRepository.findByEmailEquals(email) != null;
    }

// não utilizado. OBS: não se chama repository de outras classes numa service que não é dela
//    public Comentario returnComentario(Integer idComentario) throws Exception{
//        return comentarioRepository.findById(idComentario)
//                .orElseThrow(() -> new EntidadeNaoEncontradaException("Nenhum comentario encontrado com o id " + idComentario));
//    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario converterDTO(UsuarioCreateDTO dto) {
        return objectMapper.convertValue(dto, Usuario.class);
    }

    public UsuarioDTO retornarDTO(Usuario usuario) {
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

}
