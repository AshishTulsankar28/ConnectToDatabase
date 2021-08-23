package com.dto.demo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;

import org.dozer.DozerBeanMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class WebArgumentConfig extends RequestResponseBodyMethodProcessor {

    private static final DozerBeanMapper modelMapper = new DozerBeanMapper();

    public WebArgumentConfig() {
        super(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestDto.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        return modelMapper.map(dto, parameter.getParameterType());
    }

	@Override
	protected <T> Object readWithMessageConverters(NativeWebRequest webRequest, MethodParameter parameter,
			Type paramType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		// return super.readWithMessageConverters(webRequest, parameter, paramType);
		for (Annotation ann : parameter.getParameterAnnotations()) {
            RequestDto dtoType = AnnotationUtils.getAnnotation(ann, RequestDto.class);

            if (dtoType != null) {
                return super.readWithMessageConverters(webRequest, parameter, dtoType.value());
            }
        }

        throw new RuntimeException();
	}

    

}
