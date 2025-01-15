package com.zaicev.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import com.zaicev.spring.transactions.conversion.TransactionCategoryConverter;
import com.zaicev.spring.transactions.dao.TransactionCategoryDAO;
import com.zaicev.spring.wallet.conversion.WalletConverter;
import com.zaicev.spring.wallet.dao.WalletDAO;

@Configuration
@ComponentScan("com.zaicev.spring")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

	private final ApplicationContext applicationContext;
	private WalletDAO walletDAO;
	private TransactionCategoryDAO transactionCategoryDAO;

	@Autowired
	public SpringConfig(ApplicationContext applicationContext, WalletDAO walletDAO,
			TransactionCategoryDAO transactionCategoryDAO) {
		this.applicationContext = applicationContext;
		this.walletDAO = walletDAO;
		this.transactionCategoryDAO = transactionCategoryDAO;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
		registry.addResourceHandler("/icons/**").addResourceLocations("/WEB-INF/icons/");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(transactionCategoryConverter(transactionCategoryDAO));
		registry.addFormatter(walletConverter(walletDAO));
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		registry.viewResolver(resolver);
	}

	@Bean
	public TransactionCategoryConverter transactionCategoryConverter(TransactionCategoryDAO transactionCategoryDAO) {
		return new TransactionCategoryConverter(transactionCategoryDAO);
	}

	@Bean
	WalletConverter walletConverter(WalletDAO walletDAO) {
		return new WalletConverter(walletDAO);
	}

}
