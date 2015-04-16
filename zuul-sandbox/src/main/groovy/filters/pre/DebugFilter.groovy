package filters.pre

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.DynamicBooleanProperty
import com.netflix.config.DynamicPropertyFactory
import com.netflix.config.DynamicStringProperty
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.constants.ZuulConstants
import com.netflix.zuul.context.RequestContext
import com.poletto.netflix.sandbox.zuul.StartServer;

class DebugFilter extends ZuulFilter {
	
	static final DynamicBooleanProperty routingDebug = DynamicPropertyFactory.getInstance().getBooleanProperty(ZuulConstants.ZUUL_DEBUG_REQUEST, true)
	static final DynamicStringProperty debugParameter = DynamicPropertyFactory.getInstance().getStringProperty(ZuulConstants.ZUUL_DEBUG_PARAMETER, "d")

	private static final Logger logger = LoggerFactory.getLogger(DebugFilter.class)
	
	@Override
	public boolean shouldFilter() {
		logger.info(" sera que vai: " + RequestContext.getCurrentContext().getRequest().getParameter(debugParameter.get()))
		if ("true".equals(RequestContext.getCurrentContext().getRequest().getParameter(debugParameter.get()))) return true;
        return routingDebug.get();
	}

	@Override
	public Object run() {
		logger.info("executou o Debugger")
		
		RequestContext ctx = RequestContext.getCurrentContext()
        ctx.setDebugRouting(true)
        ctx.setDebugRequest(true)
        return null;
	}

	@Override
	public String filterType() {
		return "pre"
	}

	@Override
	public int filterOrder() {
		return 1
	}

}
