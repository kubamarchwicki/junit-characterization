package pl.marchwicki.junitcharacterization;

import difflib.Patch;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyPatchMatcher extends TypeSafeMatcher<Patch> {

    @Override
    protected boolean matchesSafely(Patch item) {
        return item.getDeltas().isEmpty();
    }

    @Override
    protected void describeMismatchSafely(Patch item, Description description) {
        description.appendText("was a ")
                .appendValueList("{", ", ", "}", item.getDeltas());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty patch");
    }

    /**
     * Matches an empty collection.
     */
    @Factory
    public static Matcher<Patch> empty() {
        return new IsEmptyPatchMatcher();
    }

}
