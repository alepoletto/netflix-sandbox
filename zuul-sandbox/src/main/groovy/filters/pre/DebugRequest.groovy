package filters.pre

import javax.servlet.http.HttpServletRequest

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.Debug
import com.netflix.zuul.context.RequestContext

class DebugRequest extends ZuulFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(DebugRequest.class)

	@Override
	public boolean shouldFilter() {
		logger.info("sera que vai solar o debugRequest? "+ Debug.debugRequest())
		return Debug.debugRequest()
	}

	@Override
	public Object run() {
		HttpServletRequest req = RequestContext.currentContext.request as HttpServletRequest

        Debug.addRequestDebug("REQUEST:: " + req.getScheme() + " " + req.getRemoteAddr() + ":" + req.getRemotePort())

        Debug.addRequestDebug("REQUEST:: > " + req.getMethod() + " " + req.getRequestURI() + " " + req.getProtocol())

        Iterator headerIt = req.getHeaderNames().iterator()
        while (headerIt.hasNext()) {
            String name = (String) headerIt.next()
            String value = req.getHeader(name)
            Debug.addRequestDebug("REQUEST:: > " + name + ":" + value)

        }

        final RequestContext ctx = RequestContext.getCurrentContext()
        if (!ctx.isChunkedRequestBody()) {
            InputStream inp = ctx.request.getInputStream()
            String body = null
            if (inp != null) {
                body = inp.getText()
                Debug.addRequestDebug("REQUEST:: > " + body)

            }
        }
        return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 10000;
	}

}
