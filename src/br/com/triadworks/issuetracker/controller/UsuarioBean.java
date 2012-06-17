package br.com.triadworks.issuetracker.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIForm;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

import br.com.triadworks.issuetracker.controller.util.FacesUtils;
import br.com.triadworks.issuetracker.model.Usuario;
import br.com.triadworks.issuetracker.service.UsuarioService;

@Named
@ViewAccessScoped
public class UsuarioBean implements Serializable{
	
	private Usuario usuario = new Usuario();
	private String confirmacaoDeSenha;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";
	
	private String state = ESTADO_DE_PESQUISA;
	
	private UIForm form;
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private FacesUtils facesUtils;
	
	public void lista() {
		usuarios = usuarioService.listaTudo();
		setState(ESTADO_DE_PESQUISA);
	}
	
	public void preparaParaAdicionar() {
		this.usuario = new Usuario();
		setState(ESTADO_DE_NOVO);
	}
	
	public void adiciona() {
		
		boolean senhaInvalida = !confirmacaoDeSenha.equals(usuario.getSenha());
		if (senhaInvalida) {
			facesUtils.adicionaMensagemDeErro("Senha e confirmação de senha não conferem.");
			return;
		}
		
		usuarioService.salva(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário adicionado com sucesso!");
		lista();
	}
	
	public void remove() {
		usuarioService.remove(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário removido com sucesso!");
		lista();
	}
	
	public void preparaParaAlterar(Usuario usuario) {
		this.usuario = usuarioService.carrega(usuario.getId()); // evita LazyInitializationException
		setState(ESTADO_DE_EDICAO);
	}
	
	public void altera() {
		
		boolean senhaInvalida = !confirmacaoDeSenha.equals(usuario.getSenha());
		if (senhaInvalida) {
			facesUtils.adicionaMensagemDeErro("Senha e confirmação de senha não conferem.");
			return;
		}
		
		usuarioService.atualiza(usuario);
		facesUtils.adicionaMensagemDeInformacao("Usuário atualizado com sucesso!");
		lista();
	}
	
	public void voltar() {
		this.usuario = new Usuario();
		lista();
	}
	
	public boolean isAdicionando() {
		return ESTADO_DE_NOVO.equals(state);
	}
	public boolean isEditando() {
		return ESTADO_DE_EDICAO.equals(state);
	}
	public boolean isPesquisando() {
		return ESTADO_DE_PESQUISA.equals(state);
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarioDao(UsuarioService usuarioDao) {
		this.usuarioService = usuarioDao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getConfirmacaoDeSenha() {
		return confirmacaoDeSenha;
	}
	public void setConfirmacaoDeSenha(String confirmacaoDeSenha) {
		this.confirmacaoDeSenha = confirmacaoDeSenha;
	}
	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UIForm getForm() {
		return form;
	}
	public void setForm(UIForm form) {
		this.form = form;
	}
	
}
