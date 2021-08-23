package com.abelium.inatrace.components.value_chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for value chain entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/value-chain")
public class ValueChainController {

	private final ValueChainService valueChainService;

	@Autowired
	public ValueChainController(ValueChainService valueChainService) {
		this.valueChainService = valueChainService;
	}


}
