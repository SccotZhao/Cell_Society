/**
 * 
 */
package UserPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Zhiyong
 *
 */
public class PercentageHandler {
	
	public Map<String, List<Double>> calculatePercentage(List<String> states, Map<String, List<Double>> map) {
		Map<String, List<String>> temp = new HashMap<>();
		for(String state : states){
			if(!temp.containsKey(state)){
				List<String> l = new ArrayList<>();
				temp.put(state, l);
			}
			temp.get(state).add(state);
		}
		
		Set<String> uniqueState = new HashSet<String>(states);
		for(String s : uniqueState){
			if(!map.containsKey(s)){
				List<Double> list = new ArrayList<>();
				map.put(s, list);
			}
			map.get(s).add( ((double)temp.get(s).size()/(double)states.size()));
		}
		return map;
	}


}
