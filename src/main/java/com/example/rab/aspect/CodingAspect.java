package com.example.rab.aspect;

import com.example.rab.dao.FindRoleSetDao;
import com.example.utils.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component
@Aspect
public class CodingAspect {

    @Pointcut("execution(public * com.example.rab.controller.*.coding(..))")
    public void CodingA(){
    }

    @Around("CodingA()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =  SessionUtil.getRequest();
        String u_id = String.valueOf(request.getSession().getAttribute("u_id"));
        Set<String> roleSet = new FindRoleSetDao().FindRoleSet(u_id);

        String go = null; //标记 角色是否存在
        if ("login".equals(request.getSession().getAttribute("flag"))){
            System.out.println("用户已经登录，即将执行 coding方法");

            //遍历角色，是否符合 coding需要的角色
            for (String string : roleSet){
                if ("god".equals(string)||"programmer".equals(string)){
                    go =  "yes";
                }
            }

            if ("yes".equals(go)){
                System.out.println("programmer或者god角色符合，执行 coding方法");
                pjp.proceed();
                System.out.println("coding方法执行结束");
            }
        } else {
            System.out.println("未登录");
        }
    }
}
