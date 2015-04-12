package org.tesys.recomendations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.tesys.core.estructures.Developer;
import org.tesys.core.estructures.Issue;
import org.tesys.core.estructures.Metric;
import org.tesys.core.project.tracking.IssuePOJO;

public class DevelopersCriteriaIssues {
	
	public List<RecomendedDeveloper> getBestDeveloperIssue(Metric criteria, List<Developer> similarIssues, IssuePOJO ip) {
		List<RecomendedDeveloper> developers = new LinkedList<RecomendedDeveloper>();
		List<String> originalLabels = new ArrayList<String>(Arrays.asList(ip.getLabels()));
		
		for (Developer d : similarIssues) {
			Double similarity = 0.0;
			
			Double metric = null;
			
			for (Issue i : d.getIssues()) {
				List<String> s = new ArrayList<String>(Arrays.asList(i.getLabels()));
				s.retainAll(originalLabels);
				if( similarity < s.size() ) {
					similarity = (double)s.size();
				}
				
				if(metric==null) {
					metric = criteria.evaluate(i);
				} else {
					metric = criteria.getType().avg(metric, criteria.evaluate(i));
				}
				
			}
			
			developers.add( new RecomendedDeveloper(d.getName(), metric, (double)d.getIssues().size(), similarity) );
		}
		
		
		
		
		return developers;
	}

}
