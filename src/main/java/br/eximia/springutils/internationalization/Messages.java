package br.eximia.springutils.internationalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component("messages")
public class Messages {
	
	private MessageSource messageSource;

	@Autowired
    public Messages(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}
	
	public String getMessage(String key){
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}
	
}
