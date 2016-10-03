package org.moshe.arad.repositories.validators;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

public class GameUserValidator implements Validator {

	private final static Logger logger = LogManager.getLogger(GameUserValidator.class);
	
	private static Set<String> ignore = new HashSet<>();
	
	static{
		ignore.add("createdDate");
		ignore.add("lastUpdatedBy");
		ignore.add("createdBy");
		ignore.add("lastUpdatedDate");
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return GameUser.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {	
	}
	
	public static boolean acceptableErrors(Errors errors) {
		for(FieldError error:errors.getFieldErrors()){
			if(!ignore.contains(error.getField())){
				logger.info("This error couldn't be ignore " + error);
				return false;
			}
			else logger.info("Ignoring error " + error);
		}
		return true;
	}
}
