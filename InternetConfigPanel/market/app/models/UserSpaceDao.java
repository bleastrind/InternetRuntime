package models;
import static me.prettyprint.hector.api.factory.HFactory.*;
import static org.junit.Assert.assertNotNull;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.*;
import me.prettyprint.hector.api.beans.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
/*
 * the methods for the UserSpace
 */
public class UserSpaceDao {
	final static String KEYSPACE = "InternetRuntime";
	final static String CF = "UserSpace";
	final static StringSerializer se = new StringSerializer();
	
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
	Keyspace keyspace = createKeyspace(KEYSPACE, cluster);	
	Mutator<String> m = createMutator(keyspace, se);
	
	
	
	public void save(UserSpace instance){		
		m.delete(instance.getId(), CF, null, se);
		for (AppConfig appconfig: instance.getAppConfigs())
			m.insert(instance.getId(), CF, createStringColumn(appconfig.getAppId(), appconfig.getConfig()));
	}
	
	public void delete(UserSpace instance){
		m.delete(instance.getId(), CF, null, se);
	}
	
	public UserSpace getUserSpace(User user){
		List<AppConfig> appConfigs = new ArrayList<AppConfig>();		
		SliceQuery<String, String, String> sliceQuery = createSliceQuery(keyspace, se, se, se);
		sliceQuery.setColumnFamily(CF);
		sliceQuery.setKey(user.getId());
		sliceQuery.setRange("", "", false, 100);
		QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
		for (HColumn<String, String> column: result.get().getColumns())
		{
			appConfigs.add(new AppConfig(column.getName(), column.getValue()));
		}
		
		UserSpace userspace = new UserSpace(user.getId(), appConfigs);
		return userspace;		
	}

	public UserSpace getUserSpace(String userId){
		List<AppConfig> appConfigs = new ArrayList<AppConfig>();		
		SliceQuery<String, String, String> sliceQuery = createSliceQuery(keyspace, se, se, se);
		sliceQuery.setColumnFamily(CF);
		sliceQuery.setKey(userId);
		sliceQuery.setRange("", "", false, 100);
		QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
		for (HColumn<String, String> column: result.get().getColumns())
		{
			appConfigs.add(new AppConfig(column.getName(), column.getValue()));
		}
		
		UserSpace userspace = new UserSpace(userId, appConfigs);
		return userspace;		
	}
}
