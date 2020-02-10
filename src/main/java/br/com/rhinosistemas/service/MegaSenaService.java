package br.com.rhinosistemas.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MegaSenaService {

	public static void executarLeitura(boolean maiorParaMenor, Integer quantidadeRegistros) {

		Map<Integer, Integer> dados = new HashMap<Integer, Integer>();

		try {
			
			FileInputStream file = new FileInputStream(new File("/Users/gilsonbraggion/Desktop/MegaSena.xlsx"));

			Workbook workbook = new XSSFWorkbook(file);
			// Create Workbook instance holding reference to .xlsx file
			
			Sheet sheet = workbook.getSheetAt(0);
			 
			for (Row row : sheet) {
			    for (Cell cell : row) {
			        switch (cell.getCellTypeEnum()) {
			            case STRING: 
			            break;
			            case NUMERIC:
			            	
			            	Integer numero = Integer.parseInt(String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
			            	
			            	if (dados.containsKey(numero)) {
			            		dados.put(numero, dados.get(numero) + 1);
			            	} else {
			            		dados.put(numero, 1);
			            	}
			            	
			            	break;
			            case BOOLEAN: 
			            	break;
			            case FORMULA: 
			            	break;
			        }
			    }
			}
			file.close();
			workbook.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		Map<Integer, Integer> primeiros10Mais = new HashMap<Integer, Integer>();
		
		if (maiorParaMenor) {
			final Map<Integer, Integer> sortedMaiorParaMenor = dados.entrySet()
					.stream()
					.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			
			
			System.out.println(sortedMaiorParaMenor);
			
			primeiros10Mais = dados.entrySet()
					.stream().limit(quantidadeRegistros)
					.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			
		} else {
			final Map<Integer, Integer> sortedMenorParaMaior = dados.entrySet()
					.stream()
					.sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			
			System.out.println(sortedMenorParaMaior);
			
			 primeiros10Mais = dados.entrySet()
					.stream().limit(quantidadeRegistros)
					.sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		}
		
		System.out.println("Primeiros "+ quantidadeRegistros + " acessados = " + primeiros10Mais);
		for (int i = 0; i < primeiros10Mais.size(); i++) {
			Optional<Entry<Integer, Integer>> retorno = primeiros10Mais.entrySet().stream().skip((int) (primeiros10Mais.size() * Math.random())).findAny();
			
			if (i % 6 == 0) {
				System.out.println("\n");
			}
			
			System.out.println("Dezena " + retorno);
		}
		
		
		
	}
		
}
