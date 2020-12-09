package com.example.tk.mq.rabbitMq.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class RabbitMqListenerConfig implements ImportBeanDefinitionRegistrar {
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition def;
        if (!registry.containsBeanDefinition("rabbitmqAnnotationBeanPostProcessor")) {
            def = new RootBeanDefinition(RabbitmqAnnotationBeanPostProcessor.class);
            registerPostProcessor(registry, def, "rabbitmqAnnotationBeanPostProcessor");
        }
    }

    private BeanDefinitionHolder registerPostProcessor(BeanDefinitionRegistry registry, RootBeanDefinition definition, String beanName) {
        definition.setRole(2);
        registry.registerBeanDefinition(beanName, definition);
        return new BeanDefinitionHolder(definition, beanName);
    }

}
