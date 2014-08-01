package com.examples;

import com.github.junitcharacterization.CharacterizationRule;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class BusinessClassTest {

    @ClassRule
    public static CharacterizationRule rule = new CharacterizationRule(BusinessClassTest.class);

    private BusinessClass service = new BusinessClass();

    @Test
    @FileParameters("classpath:tst.csv")
    public void just_run_the_method(String parameter) {
        service.businessMethod(parameter);
    }

}

