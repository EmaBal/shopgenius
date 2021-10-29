package it.univpm.shopgenius.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "it.univpm.shopgenius"
})
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public UrlRewriteFilter getUrlRewriteFilter() {
	    System.out.println("Calling Bean URL Rewrite Filter");
	    UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();
	    return urlRewriteFilter;
	}
	
	@Bean
	public String appName() {
		return "Shop Genius";
	}
	
    @Bean
    public InternalResourceViewResolver resolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry
          .addResourceHandler("/resources/**").addResourceLocations("classpath:/img");
  }

	@Bean
	UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}

	@Bean
	TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(
				"/WEB-INF/views/tiles.xml"
		);
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}
}
