package com.chaolj.core.commonUtils.myHelper.Impl;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.chaolj.core.commonUtils.myHelper.MyHelperProperties;
import com.chaolj.core.MyApp;

public class ValidateHelper {
    private ApplicationContext applicationContext;
    private MyHelperProperties properties;

    public ValidateHelper(ApplicationContext applicationContext, MyHelperProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    public <T> List<String> validateToList(T modelBo) {
        var result = new ArrayList<String>();

        var validateList = MyApp.Helper().ValidateHelper().validateToList(modelBo);
        if (!validateList.isEmpty()) for (var validate : validateList) result.add(validate);

        return result;
    }

    public <T> String validateToString(T modelBo) {
        var result = new StringBuilder();

        var validateList = this.applicationContext.getBean(Validator.class).validate(modelBo);
        if (!validateList.isEmpty()) for (var validate : validateList) result.append(validate.getMessage()).append(";");

        return result.toString();
    }
}
