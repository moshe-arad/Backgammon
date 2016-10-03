package org.moshe.arad.repositories.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameUserValidator implements Validator, EntityValidator {

	private final static Logger logger = LogManager.getLogger(GameUserValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return GameUser.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {	
	}
	
	public static boolean acceptableErrors(Errors errors) {
		return EntityValidator.acceptableErrors(errors);
	}
}
