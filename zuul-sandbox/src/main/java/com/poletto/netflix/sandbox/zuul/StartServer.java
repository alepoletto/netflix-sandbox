package com.poletto.netflix.sandbox.zuul;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.monitoring.CounterFactory;
import com.netflix.zuul.monitoring.TracerFactory;
import com.netflix.zuul.plugins.Counter;
import com.netflix.zuul.plugins.Tracer;


public class StartServer implements ServletContextListener  {

	private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("inicializando o server");

		logger.info("Registering Servo Tracer");
        TracerFactory.initialize(new Tracer());
		
		logger.info("Registering Servo Counter");
		CounterFactory.initialize(new Counter());
		initJavaFilters();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("destruindo o server");

	}


	private void initJavaFilters() {
		final FilterRegistry r = FilterRegistry.instance();

		r.put("javaPreFilter", new ZuulFilter() {
			@Override
			public int filterOrder() {
				return 1;
			}

			@Override
			public String filterType() {
				return "pre";
			}

			@Override
			public boolean shouldFilter() {
				return true;
			}

			@Override
			public Object run() {
				logger.info("running javaPreFilter");
				RequestContext.getCurrentContext().set("javaPreFilter-ran", true);
				return null;
			}
		});

		r.put("javaPostFilter", new ZuulFilter() {
			@Override
			public int filterOrder() {
				return 2;
			}

			@Override
			public String filterType() {
				return "post";
			}

			@Override
			public boolean shouldFilter() {
				return true;
			}

			@Override
			public Object run() {
				logger.info("running javaPostFilter");
				RequestContext.getCurrentContext().set("javaPostFilter-ran", true);
				return null;
			}
		});
	}


}