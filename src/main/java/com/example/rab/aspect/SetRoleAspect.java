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
public class SetRoleAspect {

    @Pointcut("execution(public * com.example.rab.controller.*.setRole(..))")
    public void setRole(){
    }

    @Around("setRole()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = SessionUtil.getRequest();
        String u_id = String.valueOf(request.getSession().getAttribute("u_id"));
        Set<String> roleSet = new FindRoleSetDao().FindRoleSet(u_id);
        if ("login".equals(request.getSession().getAttribute("flag"))){
            System.out.println("用户已经登录，即将执行 setRole方法");

            //遍历角色，是否符合 coding需要的角色
            for (String string : roleSet){
                if ("god".equals(string)){  //因为这里 只能有一个 角色，所以不需要 标记 go
                    System.out.println("god角色符合，执行 setRole方法");
                    pjp.proceed();
                    System.out.println("setRole方法执行结束");
                }
            }
        } else {
            System.out.println("未登录");
        }
    }

}
