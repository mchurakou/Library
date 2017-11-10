package com.mikalai.library.dao.data.specification;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.ajax_json.Rule;
import com.mikalai.library.utils.Constants;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class GenericSpecification<T> implements Specification {
    public GenericSpecification(Filter filter) {
        this.filter = filter;
    }

    private Filter filter;

    protected Predicate[] buildFilter(CriteriaBuilder cb, Root<T> root) {
        Predicate[] predicates = null;
        predicates = new Predicate[filter.getRules().size()];

        for (int i = 0; i < filter.getRules().size();i++){
            Rule r = filter.getRules().get(i);
            String field = r.getField();

            if (field.endsWith("Id")) // search by lookup field
                field = field.substring(0, field.length() - 2);

            Path f = root.get(field);

            Predicate predicate = null;


            if (r.getOp().equals(Constants.EQUALS))
                predicate = cb.equal(f, r.getData());


            if (r.getOp().equals(Constants.NOT_EQUALS))
                predicate = cb.notEqual(f, r.getData());

            if (r.getOp().equals(Constants.BEGIN_WITH))
                predicate = cb.like(f, r.getData() + "%");

            if (r.getOp().equals(Constants.CONTAIN))
                predicate = cb.like(f, "%" + r.getData() + "%");

            if (r.getOp().equals(Constants.LESS))
                predicate = cb.lessThan(f, r.getData());

            if (r.getOp().equals(Constants.LESS_OR_EQUAL))
                predicate = cb.lessThanOrEqualTo(f, r.getData());

            if (r.getOp().equals(Constants.GREATER))
                predicate = cb.greaterThan(f, r.getData());

            if (r.getOp().equals(Constants.GREATER_OR_EQUAL))
                predicate = cb.greaterThanOrEqualTo(f, r.getData());

            predicates[i] = predicate;

        }

        return predicates;
    }


    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        Predicate[] restrictions = buildFilter(cb, root);
        Predicate res = null;
        if ("AND".equals(filter.getGroupOp())) {
            res = cb.and(restrictions);
        }
        else if ("OR".equals(filter.getGroupOp())) {
            res = cb.or(restrictions);
        }
        return res;
    }
}
