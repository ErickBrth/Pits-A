package com.ufcg.psoft.pitsa9.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPizza {

    static final String PIZZA_NAO_CADASTRADA = "Pizza com id %s não está cadastrado";

    static final String PIZZAS_NAO_CADASTRADAS = "Não há pizzas cadastradas";

    static final String PIZZA_JA_CADASTRADA = "A pizza de %s já esta cadastrada";

    static final String PIZZA_DISPONIVEL = "Pizza com id %s está disponível";

    public static ResponseEntity<CustomErrorType> erroPizzaNaoEncontrada(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPizza.PIZZA_NAO_CADASTRADA, id)),
                HttpStatus.NOT_FOUND);
    }
    
    public static ResponseEntity<CustomErrorType> erroSemPizzasCadastradas() {		
		    return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPizza.PIZZAS_NAO_CADASTRADAS)),
				    HttpStatus.NO_CONTENT);
	  }

    public static ResponseEntity<CustomErrorType> erroPizzaJaCadastrada(String nome) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPizza.PIZZA_JA_CADASTRADA
            , nome)), HttpStatus.CONFLICT);
    }

    public static ResponseEntity<CustomErrorType> erroPizzaDisponivel(long id) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroPizza.PIZZA_DISPONIVEL, id)),
                HttpStatus.CONFLICT);
    }
}
