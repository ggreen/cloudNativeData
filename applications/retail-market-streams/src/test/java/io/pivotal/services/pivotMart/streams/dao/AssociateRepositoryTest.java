package io.pivotal.services.pivotMart.streams.dao;

import io.pivotal.market.api.dao.ProductAssociationEntity;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociateRepositoryTest
{

    private AssociateRepository subject;

    private ProductAssociationEntity expected;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp()
    {

        expected = JavaBeanGeneratorCreator
                .of(ProductAssociationEntity.class).create();

        subject = new AssociateRepository(jdbcTemplate);
    }

    @Test
    void saveNull()
    {
        subject.save(null);
        verify(jdbcTemplate,never()).update(anyString(),any(PreparedStatementSetter.class));
    }

    @Test
    void saveNotAssocation()
    {
        subject.save(new ProductAssociationEntity("id",(String)null));
        verify(jdbcTemplate,never()).update(anyString(),any(PreparedStatementSetter.class));
    }

    @Test
    void saveNotAssocationEmpty()
    {
        subject.save(new ProductAssociationEntity("id",""));
        verify(jdbcTemplate,never()).update(anyString(),any(PreparedStatementSetter.class));
    }

    @Test
    void saveNotAssocationEmptySet()
    {
        subject.save(new ProductAssociationEntity("id", Collections.emptySet()));
        verify(jdbcTemplate,never()).update(anyString(),any(PreparedStatementSetter.class));
    }

    @Test
    void save()
    {
        Collection<String> associations = Arrays.asList("1","2");
        this.expected.setAssociations(associations);

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class))).thenReturn(expected);
        when(jdbcTemplate.update(anyString())).thenReturn(0)
        .thenReturn(1);

        subject.save(expected);
        verify(jdbcTemplate,times(2)).update(anyString());



        Optional<ProductAssociationEntity> actual = subject.findById(expected);
        assertNotNull(actual);
        assertEquals(expected,actual.get());
    }
}