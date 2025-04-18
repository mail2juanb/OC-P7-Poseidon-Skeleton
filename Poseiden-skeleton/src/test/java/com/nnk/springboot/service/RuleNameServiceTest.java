package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repository.RuleNameRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest extends AbstractCrudServiceTest<RuleName> {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Override
    protected AbstractCrudService<RuleName> initService() {
        this.repository = ruleNameRepository;
        return new RuleNameServiceImpl(ruleNameRepository);
    }


    @Override
    protected RuleName createModelWithId(int id) {
        RuleName ruleName = mock(RuleName.class);
        lenient().when(ruleName.getId()).thenReturn(id);
        return ruleName;
    }


    @Override
    protected RuleName createModelWithNullId() {
        RuleName ruleName = new RuleName();
        ruleName.setId(null);
        ruleName.setDescription("Test");
        return ruleName;
    }


    @Override
    protected RuleName createUpdatedModel() {
        return mock(RuleName.class);
    }

}
