package com.ufcg.psoft.pitsa9.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Listener {

	private String nome;

	private String endereco;

	private String codigoAcesso;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Pedido> pedidos;

	public Cliente() {
		this.pedidos = new ArrayList<Pedido>();
	}

	public Cliente(String nome, String endereco, String codigoAcesso) {
		this.nome = nome;
		this.endereco = endereco;
		this.codigoAcesso = codigoAcesso;
		this.pedidos = new ArrayList<Pedido>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCodigoAcesso() {
		return codigoAcesso;
	}

	public void setCodigoAcesso(String codigoAcesso) {
		this.codigoAcesso = codigoAcesso;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido getPedidoById(Long idPedido) {
		Pedido result = new Pedido();
		for (Pedido pedido : pedidos) {
			if (pedido.getId().equals(idPedido)) {
				result = pedido;
			}
		}

		return result;
	}

	public void addPedido(Pedido pedido) {
		pedidos.add(pedido);
	}

	public void removePedido(Pedido pedido) {
		this.pedidos.remove(pedido);
	}

	public String pedidosToString() {
		String pedidos = "";
		for (Pedido pedido : this.pedidos) {
			pedidos += pedido.toString() + "\n";
		}
		return pedidos;
	}

	@Override
	public void alertaPizza(PizzaEvent alerta) {
		String result = "Olá, " + this.nome + ". A pizza de " + alerta.getNome() +
				", que você demonstrou interesse, já está disponível, peça já a sua!";
		System.out.println(result);
	}

	@Override
	public void alertaPedidoSaiuParaEntrega(PedidoEmRotaEvent alerta, Long idPedido) {
		String result = "Olá, " + this.nome + ". O seu pedido ID: " + idPedido +
				", que você demonstrou interesse, está no estado: " + alerta.getSituacao()
				+ ", aguarda só mais um pouquinho!";
		System.out.println(result);
	}

}
