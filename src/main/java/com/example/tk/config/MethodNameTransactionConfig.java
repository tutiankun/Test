package com.example.tk.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class MethodNameTransactionConfig {

    private static final String DEFAULT_POINTCUT_EXPRESSION = "execution(* com.ttpai..service..*.*(..))";

    @Primary
    @Bean(name = "dataSourceTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(DefaultBeanFactoryPointcutAdvisor.class)
    public DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor(
            PlatformTransactionManager transactionManager) {

        return customBeanFactoryPointcutAdvisor(transactionManager, DEFAULT_POINTCUT_EXPRESSION);
    }

    /**
     * 多数据库源的情况下，可以调用该方式，为每个 service 包，指定不同的事务管理器
     *
     * @param transactionManager DataSourceTransactionManager
     * @param expression         默认是 execution(* com.ttpai..service..*.*(..))
     */
    public static DefaultBeanFactoryPointcutAdvisor customBeanFactoryPointcutAdvisor(
            PlatformTransactionManager transactionManager,
            String expression) {
        DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor = new DefaultBeanFactoryPointcutAdvisor();

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributeSources(newTransactionAttributes());
        defaultBeanFactoryPointcutAdvisor.setAdvice(transactionInterceptor);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        defaultBeanFactoryPointcutAdvisor.setPointcut(pointcut);

        return defaultBeanFactoryPointcutAdvisor;
    }

    /**
     * <tx:method name="save*" propagation="REQUIRED" rollback-for="Exception"/>
     * <tx:method name="insert*" propagation="REQUIRED" rollback-for="Exception"/>
     * <tx:method name="update*" propagation="REQUIRED" rollback-for="Exception"/>
     * <tx:method name="del*" propagation="REQUIRED" rollback-for="Exception"/>
     * <tx:method name="add*" propagation="REQUIRED" rollback-for="Exception"/>
     * <tx:method name="select*" read-only="true" propagation="SUPPORTS"/>
     * <tx:method name="find*" read-only="true" propagation="SUPPORTS"/>
     */
    private static NameMatchTransactionAttributeSource[] newTransactionAttributes() {

        return new NameMatchTransactionAttributeSource[] {
                newNameMatchTransactionAttributeSource("select*", newReadTransactionAttribute()),
                newNameMatchTransactionAttributeSource("find*", newReadTransactionAttribute()),

                newNameMatchTransactionAttributeSource("save*", newWriteTransactionAttribute()),
                newNameMatchTransactionAttributeSource("insert*", newWriteTransactionAttribute()),
                newNameMatchTransactionAttributeSource("update*", newWriteTransactionAttribute()),
                newNameMatchTransactionAttributeSource("del*", newWriteTransactionAttribute()),
                newNameMatchTransactionAttributeSource("add*", newWriteTransactionAttribute()),
        };

    }

    private static NameMatchTransactionAttributeSource newNameMatchTransactionAttributeSource(
            String methodName,
            TransactionAttribute attr) {

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod(methodName, attr);
        return source;
    }

    /**
     * 读事务属性
     */
    private static RuleBasedTransactionAttribute newReadTransactionAttribute() {
        RuleBasedTransactionAttribute attribute = new RuleBasedTransactionAttribute();

        // 设定传播行为
        attribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        // 只读
        attribute.setReadOnly(true);

        return attribute;
    }

    /**
     * 写事务属性
     */
    private static RuleBasedTransactionAttribute newWriteTransactionAttribute() {
        RuleBasedTransactionAttribute attribute = new RuleBasedTransactionAttribute();

        // 设定传播行为
        attribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 设定回滚规则
        List<RollbackRuleAttribute> rollbackRules = new LinkedList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));
        attribute.setRollbackRules(rollbackRules);

        return attribute;
    }

}
