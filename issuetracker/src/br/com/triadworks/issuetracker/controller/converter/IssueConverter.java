package br.com.triadworks.issuetracker.controller.converter;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.myfaces.extensions.cdi.core.api.Advanced;
import org.conventionsframework.converter.AbstractBaseConverter;
import org.conventionsframework.qualifier.StatelessService;
import org.conventionsframework.service.BaseService;

import br.com.triadworks.issuetracker.model.Issue;

@Advanced
@FacesConverter(value="issueConverter")
public class IssueConverter extends AbstractBaseConverter{


	@Inject
	public void setService(@StatelessService(entity=Issue.class)BaseService service){
		super.setBaseService(service);
	}

}
