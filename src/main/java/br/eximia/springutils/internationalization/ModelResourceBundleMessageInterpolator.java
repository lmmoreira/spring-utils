package br.eximia.springutils.internationalization;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import br.eximia.springutils.context.SpringApplicationContext;
import br.eximia.springutils.internationalization.Messages;

public class ModelResourceBundleMessageInterpolator extends org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator implements     MessageInterpolator {

  @Override
  public String interpolate(String message, Context context, Locale locale) {
    if (message != null && message.startsWith("{")) {
    	Messages messages = (Messages) SpringApplicationContext.getBean(Messages.class);
    	return messages.getMessage(message.substring(1, message.length() - 1));
    } else {
      return super.interpolate(message, context, locale);
    }
  }
  
}