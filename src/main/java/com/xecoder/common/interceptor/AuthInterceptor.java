package com.xecoder.common.interceptor;

import com.xecoder.config.NonAuthoritative;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthInterceptor implements HandlerInterceptor {


    private List<String> excluded;

    public void setExcluded(List<String> excludedUrls) {
        this.excluded = excludedUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (excluded != null && excluded.contains(request.getRequestURI())) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        boolean isAn = method.getMethod().isAnnotationPresent(NonAuthoritative.class);

        if (isAn) {
            return true;
        }

        String authorization;
        String userId;
//
//        if (request.getHeader(JWTCode.AUTHORIZATION_STR) != null) {
//            authorization = request.getHeader(JWTCode.AUTHORIZATION_STR);
//        }
//        else if(request.getParameter(JWTCode.AUTHORIZATION_STR)!=null){//静态服务器调用时，数据放在url里面
//            authorization = request.getParameter(JWTCode.AUTHORIZATION_STR);
//        } else {
//            throw new SysException(UserExcepFactor.AUTH_FAILED);
//        }
//
//
//        try {
//            Map<String, Object> claims = JWTCode.VERIFIER.verify(authorization);//不会被篡改和超期
//            if(claims.size()!=0)
//            {
//                if(!claims.containsKey("exp"))//必须有超期限制
//                {
//                    throw new SysException(UserExcepFactor.AUTH_FAILED);
//                }
//            }
//
//            userId = String.valueOf(claims.get(BaseController.USERID_STR));//获取用户信息
//            request.setAttribute("claims", claims);
//        }
//        catch (Exception e) {
//            throw new SysException(UserExcepFactor.AUTH_FAILED);
//        }
//
//
//        if(!((HandlerMethod) handler).getBean().getClass().getName().equals("org.springframework.boot.autoconfigure.static.BasicErrorController")) {
//            BaseController base = (BaseController) ((HandlerMethod) handler).getBean();
//            base.setUserId(userId);
//            BaseBean baseBean = new BaseBean();
//            baseBean.setBaseCreator("系统用户");
//            baseBean.setBaseLastModifier("最后修");
//            base.setBaseBean(baseBean);
//            base.setDeviceVersion(request.getHeader(BaseController.VERSION_STR));
//        }

        return true;
}


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
