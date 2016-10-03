package org.moshe.arad.repositories.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public interface EntityValidator {

	public final static Logger logger = LogManager.getLogger(EntityValidator.class);
	
	public static final Set<String> ignore = new HashSet<>(Arrays.asList(
			"createdDate","lastUpdatedBy","createdBy","lastUpdatedDate"
			));
	
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
