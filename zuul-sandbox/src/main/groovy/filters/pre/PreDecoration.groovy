package filters.pre

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

class PreDecoration extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext()

        // sets origin
        ctx.setRouteHost(new URL("http://apache.org/"));

        // sets custom header to send to the origin
        ctx.addOriginResponseHeader("cache-control", "max-age=3600");
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 5;
	}

}
