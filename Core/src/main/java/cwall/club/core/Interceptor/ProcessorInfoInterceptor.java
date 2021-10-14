package cwall.club.core.Interceptor;

import cwall.club.common.Item.UserContext;
import cwall.club.core.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Component
public class ProcessorInfoInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserInfoService userInfoService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        UserContext userContext = new UserContext();
        userContext.setSession(session);
        Enumeration<String> attributes = session.getAttributeNames();
        boolean hasLogin = false;
        while (attributes.hasMoreElements()) {
            String name = attributes.nextElement();
            if (name.equals("uid")) {
                hasLogin = true;
                break;
            }
        }
        if (hasLogin) {
            userContext.setUid((String) session.getAttribute("uid"));
            userContext.setPwd((String) session.getAttribute("pwd"));
            userContext.setId((Long) session.getAttribute("id"));
            UserContext.setUser(userContext);
            if (userInfoService.canLogin()) {
                userContext.setLogin(true);
            }
        }
        if (request.getParameterMap().containsKey("cid")){
            userContext.setCid(request.getParameter("cid"));
        }
        UserContext.setUser(userContext);
        return super.preHandle(request,response,handler);
    }
}
