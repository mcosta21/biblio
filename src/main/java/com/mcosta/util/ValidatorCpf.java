package com.mcosta.util;

public class ValidatorCpf {
	
	public static boolean isValid(String cpf) { return validating(cpf) != null; }

	public static boolean isNotValid(String cpf) {
		return (!isValid(cpf));
	}

	private static String validating(String cpf) {
		cpf = clear(cpf);

		if (verifyIfNotValid(cpf)) {
			return null;
		}

		char digit10 = calculatesFirstValidator(cpf);
		char digit11 = calculatesSecondValidator(cpf);

		if ((digit10 == cpf.charAt(9)) && (digit11 == cpf.charAt(10)))
			return applyMask(cpf);
		else
			return null;
	}

	private static String clear(String cpf) {
		return cpf.replaceAll("-","").replaceAll("/","").replaceAll("\\.","").replaceAll(" ", "");
	}

	private static char calculatesSecondValidator(String cpf) {
		char digit11;
		int sum = 0;
		int number;
		int weight = 11;
        for(int i = 0; i < 10; i++) {
	        number = (int)(cpf.charAt(i) - 48);
	        sum = sum + (number * weight);
	        weight = weight - 1;
        }
      
        int rest = 11 - (sum % 11);
        if ((rest == 10) || (rest == 11)) {
        	digit11 = '0';
        }
        else {
        	digit11 = (char)(rest + 48);
        }
        
		return digit11;
	}

	private static char calculatesFirstValidator(String cpf) {
		char digito10;
		int soma = 0;
		int numero;
        int peso = 10;
        
        for (int i = 0; i < 9; i++) {              
            numero = (int)(cpf.charAt(i) - 48); 
            soma = soma + (numero * peso);
            peso = peso - 1;
        }
      
        int resto = 11 - (soma % 11);
        
        if ((resto == 10) || (resto == 11)) {
        	 digito10 = '0';
        }           
        else {
        	digito10 = (char)(resto + 48); // converte no respectivo caractere numerico
        }
        
		return digito10;
	}

	private static boolean verifyIfNotValid(String cpf) {
		return cpf.equals("00000000000") ||
            cpf.equals("11111111111") ||
            cpf.equals("22222222222") || cpf.equals("33333333333") ||
            cpf.equals("44444444444") || cpf.equals("55555555555") ||
            cpf.equals("66666666666") || cpf.equals("77777777777") ||
            cpf.equals("88888888888") || cpf.equals("99999999999") ||
            (cpf.length() != 11);
	}
	          
    public static String applyMask(String cpf) {
	 	cpf = clear(cpf);
        return (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
        		cpf.substring(6, 9) + "-" + cpf.substring(9, 11));
    }
	        

}
