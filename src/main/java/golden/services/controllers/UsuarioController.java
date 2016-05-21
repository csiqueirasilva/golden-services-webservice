/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.controllers;

import golden.services.model.usuarios.ListaUsuarios;
import golden.services.model.usuarios.Usuario;
import golden.services.model.usuarios.UsuarioDLO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDLO usuarioDLO;

    @RequestMapping("/list")
    public ListaUsuarios getList() {
        return usuarioDLO.list();
    }

    @RequestMapping("/count")
    public Integer getCount() {
        return usuarioDLO.list().getUsuarios().size();
    }

    @RequestMapping("/criar")
    public Usuario criarUsuario(@RequestParam String email, @RequestParam String password, @RequestParam String nome, @RequestParam String endereco, @RequestParam String telefone, @RequestParam String sexo, @RequestParam String sobre) {
        Usuario u = usuarioDLO.createUsuario(email, password, nome, endereco, telefone, sexo, sobre);
        return u;
    }

    @RequestMapping("/ativar")
    public Usuario ativarUsuario(@RequestParam String id, @RequestParam String hash) {
        Usuario u = usuarioDLO.activateUsuario(id, hash);
        return u;
    }

    @RequestMapping("/current")
    public Usuario getUsuarioLogado() {
        return usuarioDLO.getCurrentUsuario();
    }

}
