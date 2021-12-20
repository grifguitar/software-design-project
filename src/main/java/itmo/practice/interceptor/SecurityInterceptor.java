package itmo.practice.interceptor;

import itmo.practice.controller.IndexPage;
import itmo.practice.controller.Page;
import itmo.practice.domain.Role;
import itmo.practice.domain.Client;
import itmo.practice.security.AnyRole;
import itmo.practice.security.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    private IndexPage indexPage;

    @Autowired
    public void setIndexPage(IndexPage indexPage) {
        this.indexPage = indexPage;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (Page.class.isAssignableFrom(method.getDeclaringClass())) {
                if (method.getAnnotation(Guest.class) != null) {
                    return true;
                }

                Client client = indexPage.getClient(request.getSession());
                if (client != null) {
                    AnyRole anyRole = method.getAnnotation(AnyRole.class);
                    if (anyRole == null) {
                        return true;
                    }
                    for (Role.Name name : anyRole.value()) {
                        for (Role role : client.getRoles()) {
                            if (role.getName().equals(name)) {
                                return true;
                            }
                        }
                    }
                }

                indexPage.putNotification(request.getSession(), "Access is denied!");
                response.sendRedirect(client != null ? "/accessDenied" : "/enter");
                return false;
            }
        }

        return true;
    }
}
