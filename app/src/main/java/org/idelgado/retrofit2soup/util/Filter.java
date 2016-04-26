package org.idelgado.retrofit2soup.util;

import com.android.internal.util.Predicate;

import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.model.Repo;

import java.util.ArrayList;
import java.util.Collection;

public class Filter {

    public static Collection<Contributor> isFrequentContributor(Collection<Contributor> target) {
        return filter(target, isFrequentContributorPredicate);
    }

    private static Predicate<Contributor> isFrequentContributorPredicate = new Predicate<Contributor>() {
        @Override
        public boolean apply(Contributor contributor) {
            return contributor.contributions > 10;
        }
    };

    public static Collection<Repo> containsAndroid(Collection<Repo> target) {
        return filter(target, containsAndroid);
    }

    private static Predicate<Repo> containsAndroid = new Predicate<Repo>() {

        @Override
        public boolean apply(Repo repo) {
            return repo.name.toUpperCase().contains("android".toUpperCase());
        }
    };

    public static <T> Collection<T> filter(Collection<T> target, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element: target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

}
