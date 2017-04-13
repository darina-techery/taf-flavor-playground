package dagger;

import data.Configuration;

import javax.inject.Singleton;

@Module
public class ConfigurationModule {
	@Provides
	@Singleton
	public Configuration getConfiguration(){
		return new Configuration();
	}
}
