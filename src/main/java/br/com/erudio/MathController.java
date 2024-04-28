package br.com.erudio;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsupportedMathOperationException;

@RestController
public class MathController {

	private static final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", 
			method = RequestMethod.GET)
	public Double sum(
				@PathVariable(value = "numberOne") String numberOne,
				@PathVariable(value = "numberTwo") String numberTwo) throws NumberFormatException {
		
		if(!isNumber(numberOne) || !isNumber(numberTwo) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}

	private Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if(isNumber(number)) return Double.parseDouble(number);
		return 0D;
	}

	private boolean isNumber(String strNumber) {
		if (strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
}
