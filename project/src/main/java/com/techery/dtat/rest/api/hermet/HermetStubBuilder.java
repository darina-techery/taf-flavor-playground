package com.techery.dtat.rest.api.hermet;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.mbtest.javabank.fluent.ImposterBuilder;
import org.mbtest.javabank.fluent.PredicateTypeBuilder;
import org.mbtest.javabank.fluent.StubBuilder;
import org.mbtest.javabank.http.imposters.Imposter;

public abstract class HermetStubBuilder {
	private ImposterBuilder imposterBuilder = new ImposterBuilder();
	private StubBuilder predicateStubBuilder = imposterBuilder.stub();

	public PredicateTypeBuilder addPredicate() {
		return predicateStubBuilder.predicate();
	}

	protected JsonElement buildPredicates() {
		Imposter imposterWithPredicates = imposterBuilder.build();
		String predicatesList = imposterWithPredicates.getStub(0).getPredicates().toString();
		return new JsonParser().parse(predicatesList);
	}

	public enum ResponsePart {
		HEADERS("headers"),
		BODY("body");
		final String propertyName;

		ResponsePart(String propertyName) {
			this.propertyName = propertyName;
		}
	}
}
