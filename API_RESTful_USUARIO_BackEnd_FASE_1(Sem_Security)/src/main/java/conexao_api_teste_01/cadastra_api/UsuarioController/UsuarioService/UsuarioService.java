package conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioEntity.UsuarioEntity;
import conexao_api_teste_01.cadastra_api.UsuarioController.UsuarioRepository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
	private PasswordEncoder passwordEncoder;

    
    public List<UsuarioEntity> listaTodos() {
        return (List<UsuarioEntity>) usuarioRepository.findAll(); 
    }

    
    public Optional<UsuarioEntity> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    
    public UsuarioEntity inserir(UsuarioEntity usuario) {
        // Codifica a senha do objeto recebido
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    
        // Salva diretamente o objeto atualizado
        return usuarioRepository.save(usuario);
    }
    
    
    public UsuarioEntity alterar(Long id, UsuarioEntity usuarioAtualizado) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setLogin(usuarioAtualizado.getLogin());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + id));
    }
    
    

    
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}